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

import java.text.MessageFormat;
import java.util.Properties;

import javax.xml.namespace.QName;

import org.apache.axiom.om.OMElement;
import org.apache.synapse.Mediator;
import org.apache.synapse.config.xml.AbstractMediatorFactory;

import fi.mystes.synapse.mediator.WrapperMediator;
import fi.mystes.synapse.mediator.config.WrapperMediatorConfigConstants;

/**
 * Factory class implementation for building wrapper mediator.
 */
public class WrapperMediatorFactory extends AbstractMediatorFactory {

    /**
     * The QName of wrapper mediator element in the XML config
     * 
     * @return QName of wrapper mediator
     */
    @Override
    public QName getTagQName() {
        return WrapperMediatorConfigConstants.ROOT_TAG;
    }

    /**
     * Specific mediator factory implementation to build the
     * org.apache.synapse.Mediator by the given XML configuration
     * 
     * @param OMElement
     *            element configuration element describing the properties of the
     *            mediator
     * @param properties
     *            bag of properties to pass in any information to the factory
     * 
     * @return built wrapper mediator
     */
    @Override
    protected Mediator createSpecificMediator(OMElement element, Properties properties) {
        WrapperMediator mediator = new WrapperMediator();

        setWrapperName(mediator, element);

        setWrapperElementNSPrefix(mediator, element);

        setWrapperElementNSURL(mediator, element);

        return mediator;
    }

    /**
     * Set name to wrapper mediator instance using given OMElement
     * 
     * @param mediator
     *            To set name to
     * @param element
     *            To read wrapper's name from
     */
    private void setWrapperName(WrapperMediator mediator, OMElement element) {
        String wrapperName = element.getAttributeValue(WrapperMediatorConfigConstants.ATT_WRAPPER_NAME_Q);

        if (wrapperName == null || wrapperName.isEmpty()) {
            // throws SynapseException
            handleException(MessageFormat.format("The [{0}] attribute must have a non-empty value.",
                    WrapperMediatorConfigConstants.ATT_WRAPPER_NAME_Q));
        }
        mediator.setWrapperName(wrapperName);
    }

    /**
     * Set namespace prefix to wrapper mediator instance using given OMElement
     * 
     * @param mediator
     *            To set namespace prefix to
     * @param element
     *            To read wrapper's namespace prefix from
     */
    private void setWrapperElementNSPrefix(WrapperMediator mediator, OMElement element) {
        String nsPrefix = element.getAttributeValue(WrapperMediatorConfigConstants.ATT_NS_PREFIX_Q);

        if (nsPrefix == null) {
            // throws SynapseException
            handleException(MessageFormat.format("The [{0}] attribute must be specified.",
                    WrapperMediatorConfigConstants.ATT_NS_PREFIX_Q));
        }
        mediator.setNsPrefix(nsPrefix);
    }

    /**
     * Set namespace URL to wrapper mediator instance using given OMElement
     * 
     * @param mediator
     *            To set namespace URL to
     * @param element
     *            To read wrapper's namespace URL from
     */
    private void setWrapperElementNSURL(WrapperMediator mediator, OMElement element) {
        String nsUrl = element.getAttributeValue(WrapperMediatorConfigConstants.ATT_NS_URL_Q);

        if (nsUrl == null) {
            // throws SynapseException
            handleException(MessageFormat.format("The [{0}] attribute must be specified.",
                    WrapperMediatorConfigConstants.ATT_NS_URL_Q));
        }
        mediator.setNsUrl(nsUrl);
    }
}
