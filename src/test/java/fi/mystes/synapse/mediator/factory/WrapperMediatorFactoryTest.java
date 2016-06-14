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
package fi.mystes.synapse.mediator.factory;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.apache.axiom.om.OMAttribute;
import org.apache.axiom.om.OMElement;
import org.apache.synapse.Mediator;
import org.apache.synapse.SynapseException;
import org.junit.Before;
import org.junit.Test;

import fi.mystes.synapse.mediator.WrapperMediator;
import fi.mystes.synapse.mediator.common.util.OmHelper;
import fi.mystes.synapse.mediator.config.WrapperMediatorConfigConstants;

public class WrapperMediatorFactoryTest {

    private final String namespaceURL = "http://www.mystes.fi/ns";
    private final String nsPrefix = "mys";
    private final String wrapperMediatorName = "wrapperElement";

    private OMElement wrapperElement;
    private OMAttribute wrapperNameAttr;
    private OMAttribute wrapperNSPrefixAttr;
    private OMAttribute wrapperNSURLAttr;

    private WrapperMediatorFactory factory;

    @Before
    public void setUp() {
        factory = new WrapperMediatorFactory();

        wrapperElement = OmHelper.createElement("wrapper", null);
        wrapperNameAttr = OmHelper.createAttribute(WrapperMediatorConfigConstants.WRAPPER_NAME_ATT_NAME,
                wrapperMediatorName);
        wrapperNSPrefixAttr = OmHelper.createAttribute(WrapperMediatorConfigConstants.NS_PREFIX_ATT_NAME, nsPrefix);
        wrapperNSURLAttr = OmHelper.createAttribute(WrapperMediatorConfigConstants.NS_URL_ATT_NAME, namespaceURL);
        wrapperElement.addAttribute(wrapperNameAttr);
        wrapperElement.addAttribute(wrapperNSPrefixAttr);
        wrapperElement.addAttribute(wrapperNSURLAttr);
    }

    @Test(expected = SynapseException.class)
    public void shouldFailDueToNoWrapperNameDefinition() {
        wrapperElement.removeAttribute(wrapperNameAttr);
        createWrapperMediatorWithFailure(WrapperMediatorConfigConstants.WRAPPER_NAME_ATT_NAME);
    }

    @Test(expected = SynapseException.class)
    public void shouldFailDueToNoWrapperNSPrefixDefinition() {
        wrapperElement.removeAttribute(wrapperNSPrefixAttr);
        createWrapperMediatorWithFailure(WrapperMediatorConfigConstants.NS_PREFIX_ATT_NAME);
    }

    @Test(expected = SynapseException.class)
    public void shouldFailDueToNoWrapperNSURLDefinition() {
        wrapperElement.removeAttribute(wrapperNSURLAttr);
        createWrapperMediatorWithFailure(WrapperMediatorConfigConstants.NS_URL_ATT_NAME);
    }

    @Test
    public void shouldCreateWrapperMediatorInstance() {
        Mediator mediator = factory.createSpecificMediator(wrapperElement, null);
        assertTrue("Instance of mediator should be type of WrapperMediator", mediator instanceof WrapperMediator);
        WrapperMediator wrapperMediator = (WrapperMediator) mediator;
        assertTrue("Wrapper Mediator name is not as expected",
                wrapperMediator.getWrapperName().equals(wrapperMediatorName));
        assertTrue("Wrapper Mediator namespace prefix is not as expected",
                wrapperMediator.getNsPrefix().equals(nsPrefix));
        assertTrue("Wrapper Mediator namespace URL is not as expected",
                wrapperMediator.getNsUrl().equals(namespaceURL));
    }

    /**
     * Generic creator method for different cases.
     * 
     * @param expectedContainedMessage
     *            Message that should be present in exception message
     */
    private void createWrapperMediatorWithFailure(String expectedContainedMessage) {
        try {
            factory.createSpecificMediator(wrapperElement, null);
        } catch (SynapseException e) {
            if (e.getMessage().contains(expectedContainedMessage)) {
                throw e;
            }
            fail("Unexpected exception occured: " + e.getMessage() + " It doesn't contain expected message: "
                    + expectedContainedMessage);
        }
    }
}
