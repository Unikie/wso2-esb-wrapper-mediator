/**
 * Copyright 2016 Mystes Oy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fi.mystes.synapse.mediator;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.om.OMNode;
import org.apache.axiom.soap.SOAPBody;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.MessageContext;
import org.apache.synapse.mediators.AbstractMediator;

import fi.mystes.synapse.mediator.common.util.OmHelper;

/**
 * WSO2 ESB custom mediator for wrapping payload into desired element. Mediator
 * can be used after builded by Maven as the following:
 * <wrapper wrapperName={name of the wrapper element} nsPrefix={Namespace prefix
 * for wrapper element} nsUrl={Namespace for wrapper element} />
 * 
 * All three attributes of the wrapper mediator are mandatory fields.
 */
public class WrapperMediator extends AbstractMediator {
    private static final Log log = LogFactory.getLog(WrapperMediator.class);

    private String wrapperName;
    private String nsUrl;
    private String nsPrefix;

    @Override
    public boolean mediate(MessageContext mc) {
        log.debug("Starting WrapperMediator.mediate...");

        OMElement wrapper = createWrapper();

        moveChildrenFromSoapBodyToWrapper(mc, wrapper);
        addWrapperAsChild(mc, wrapper);

        log.debug("Exiting WrapperMediator.mediate. Mediation was successful.");
        return true;
    }

    private static void addWrapperAsChild(MessageContext mc, OMElement wrapper) {
        getSoapBody(mc).addChild(wrapper);
    }

    private static void moveChildrenFromSoapBodyToWrapper(MessageContext mc, OMElement wrapper) {
        while (null != getFirstChild(mc)) {
            OMNode node = getFirstChild(mc).detach();
            wrapper.addChild(node);
        }
    }

    private static OMElement getFirstChild(MessageContext mc) {
        return getSoapBody(mc).getFirstElement();
    }

    private static SOAPBody getSoapBody(MessageContext mc) {
        SOAPEnvelope envelope = mc.getEnvelope();
        return envelope.getBody();
    }

    private OMElement createWrapper() {
        OMNamespace namespace = OmHelper.createNamespace(getNsUrl(), getNsPrefix());
        OMElement wrapper = OmHelper.createElement(getWrapperName(), namespace);
        log.debug("Created a wrapper element with name " + wrapper.getLocalName());
        return wrapper;
    }

    public String getWrapperName() {
        return wrapperName;
    }

    public void setWrapperName(String wrapperName) {
        this.wrapperName = wrapperName;
    }

    public String getNsUrl() {
        return nsUrl;
    }

    public void setNsUrl(String nsUrl) {
        this.nsUrl = nsUrl;
    }

    public String getNsPrefix() {
        return nsPrefix;
    }

    public void setNsPrefix(String nsPrefix) {
        this.nsPrefix = nsPrefix;
    }

}
