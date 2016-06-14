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

import static org.junit.Assert.assertTrue;

import org.apache.axiom.om.OMElement;
import org.junit.Before;
import org.junit.Test;

import fi.mystes.synapse.mediator.WrapperMediator;
import fi.mystes.synapse.mediator.config.WrapperMediatorConfigConstants;

public class WrapperMediatorSerializerTest {

    private WrapperMediatorSerializer serializer;
    private WrapperMediator mediator;

    private final String wrapperMediatorName = "wrapperMediatorName";
    private final String wrapperNSPrefix = "mys";
    private final String wrapperNamespaceURL = "http://www.mystes.fi/ns";

    @Before
    public void setUp() {
        serializer = new WrapperMediatorSerializer();
        mediator = new WrapperMediator();
        mediator.setWrapperName(wrapperMediatorName);
        mediator.setNsPrefix(wrapperNSPrefix);
        mediator.setNsUrl(wrapperNamespaceURL);
    }

    @Test
    public void shouldCreateOMElementWithWrapperLocalName() {
        mediator.setWrapperName(null);
        mediator.setNsPrefix(null);
        mediator.setNsUrl(null);
        OMElement element = serializer.serializeSpecificMediator(mediator);

        assertTrue("Mediator class name doesn't match expected one",
                serializer.getMediatorClassName().equals(WrapperMediator.class.getName()));

        assertTrue("OMElement local name doesn't equal to \"" + WrapperMediatorConfigConstants.ROOT_TAG_NAME + "\"",
                element.getLocalName().equals(WrapperMediatorConfigConstants.ROOT_TAG_NAME));

        String mediatorName = element.getAttributeValue(WrapperMediatorConfigConstants.ATT_WRAPPER_NAME_Q);
        assertTrue("Mediator name should be null", mediatorName == null);

        String nsPrefix = element.getAttributeValue(WrapperMediatorConfigConstants.ATT_NS_PREFIX_Q);
        assertTrue("Mediator namespace prefix should be null", nsPrefix == null);

        String nsURL = element.getAttributeValue(WrapperMediatorConfigConstants.ATT_NS_URL_Q);
        assertTrue("Mediator namespace URL should be null", nsURL == null);
    }

    @Test
    public void shouldCreateOMElementWithWrapperName() {
        mediator.setNsPrefix(null);
        mediator.setNsUrl(null);
        OMElement element = serializer.serializeSpecificMediator(mediator);

        assertTrue("Mediator class name doesn't match expected one",
                serializer.getMediatorClassName().equals(WrapperMediator.class.getName()));

        assertTrue("OMElement local name doesn't equal to \"" + WrapperMediatorConfigConstants.ROOT_TAG_NAME + "\"",
                element.getLocalName().equals(WrapperMediatorConfigConstants.ROOT_TAG_NAME));

        String mediatorName = element.getAttributeValue(WrapperMediatorConfigConstants.ATT_WRAPPER_NAME_Q);
        assertTrue("Mediator name should be \"" + wrapperMediatorName + "\"", mediatorName.equals(wrapperMediatorName));

        String nsPrefix = element.getAttributeValue(WrapperMediatorConfigConstants.ATT_NS_PREFIX_Q);
        assertTrue("Mediator namespace prefix should be null", nsPrefix == null);

        String nsURL = element.getAttributeValue(WrapperMediatorConfigConstants.ATT_NS_URL_Q);
        assertTrue("Mediator namespace URL should be null", nsURL == null);
    }

    @Test
    public void shouldCreateOMElementWithWrapperNameAndNsPrefix() {
        mediator.setNsUrl(null);
        OMElement element = serializer.serializeSpecificMediator(mediator);

        assertTrue("Mediator class name doesn't match expected one",
                serializer.getMediatorClassName().equals(WrapperMediator.class.getName()));

        assertTrue("OMElement local name doesn't equal to \"" + WrapperMediatorConfigConstants.ROOT_TAG_NAME + "\"",
                element.getLocalName().equals(WrapperMediatorConfigConstants.ROOT_TAG_NAME));

        String mediatorName = element.getAttributeValue(WrapperMediatorConfigConstants.ATT_WRAPPER_NAME_Q);
        assertTrue("Mediator name should be \"" + wrapperMediatorName + "\"", mediatorName.equals(wrapperMediatorName));

        String nsPrefix = element.getAttributeValue(WrapperMediatorConfigConstants.ATT_NS_PREFIX_Q);
        assertTrue("Mediator namespace prefix should be \"" + wrapperNSPrefix + "\"", nsPrefix.equals(wrapperNSPrefix));

        String nsURL = element.getAttributeValue(WrapperMediatorConfigConstants.ATT_NS_URL_Q);
        assertTrue("Mediator namespace URL should be null", nsURL == null);
    }

    @Test
    public void shouldCreateOMElementWithWrapperNamAndNsPrefixAndNsURL() {
        OMElement element = serializer.serializeSpecificMediator(mediator);

        assertTrue("Mediator class name doesn't match expected one",
                serializer.getMediatorClassName().equals(WrapperMediator.class.getName()));

        assertTrue("OMElement local name doesn't equal to \"" + WrapperMediatorConfigConstants.ROOT_TAG_NAME + "\"",
                element.getLocalName().equals(WrapperMediatorConfigConstants.ROOT_TAG_NAME));

        String mediatorName = element.getAttributeValue(WrapperMediatorConfigConstants.ATT_WRAPPER_NAME_Q);
        assertTrue("Mediator name should be \"" + wrapperMediatorName + "\"", mediatorName.equals(wrapperMediatorName));

        String nsPrefix = element.getAttributeValue(WrapperMediatorConfigConstants.ATT_NS_PREFIX_Q);
        assertTrue("Mediator namespace prefix should be \"" + wrapperNSPrefix + "\"", nsPrefix.equals(wrapperNSPrefix));

        String nsURL = element.getAttributeValue(WrapperMediatorConfigConstants.ATT_NS_URL_Q);
        assertTrue("Mediator namespace URL should be \"" + wrapperNamespaceURL + "\"",
                nsURL.equals(wrapperNamespaceURL));
    }

}
