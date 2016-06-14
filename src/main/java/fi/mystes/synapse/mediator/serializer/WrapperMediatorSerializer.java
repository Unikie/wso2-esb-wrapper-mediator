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
package fi.mystes.synapse.mediator.serializer;

import org.apache.axiom.om.OMElement;
import org.apache.synapse.Mediator;
import org.apache.synapse.config.xml.AbstractMediatorSerializer;

import fi.mystes.synapse.mediator.WrapperMediator;
import fi.mystes.synapse.mediator.config.WrapperMediatorConfigConstants;

/**
 * Mediator serializer class to transform mediator instance to OMElement
 * instance.
 * 
 */
public class WrapperMediatorSerializer extends AbstractMediatorSerializer {

    /**
     * Get Wrapper Mediator class name
     */
    @Override
    public String getMediatorClassName() {
        return WrapperMediator.class.getName();
    }

    /**
     * Performs the mediator serialization by transfoming mediator instance into
     * OMElement instance.
     */
    @Override
    protected OMElement serializeSpecificMediator(Mediator mediator) {
        WrapperMediator wrapperMediator = (WrapperMediator) mediator;

        OMElement rootElement = fac.createOMElement(WrapperMediatorConfigConstants.ROOT_TAG_NAME, synNS);

        String wrapperName = wrapperMediator.getWrapperName();
        if (wrapperName != null) {
            rootElement.addAttribute(WrapperMediatorConfigConstants.WRAPPER_NAME_ATT_NAME, wrapperName, null);
        }

        String nsPrefix = wrapperMediator.getNsPrefix();
        if (nsPrefix != null) {
            rootElement.addAttribute(WrapperMediatorConfigConstants.NS_PREFIX_ATT_NAME, nsPrefix, null);
        }

        String nsUrl = wrapperMediator.getNsUrl();
        if (nsUrl != null) {
            rootElement.addAttribute(WrapperMediatorConfigConstants.NS_URL_ATT_NAME, nsUrl, null);
        }

        return rootElement;
    }

}
