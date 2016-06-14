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

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import javax.xml.stream.XMLStreamException;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.om.impl.OMNamespaceImpl;
import org.apache.axiom.om.impl.llom.factory.OMLinkedListMetaFactory;
import org.apache.axiom.soap.SOAPBody;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axiom.soap.SOAPFactory;
import org.apache.axiom.soap.impl.llom.SOAPEnvelopeImpl;
import org.apache.axiom.soap.impl.llom.soap11.SOAP11BodyImpl;
import org.apache.axiom.soap.impl.llom.soap11.SOAP11Factory;
import org.apache.synapse.MessageContext;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import fi.mystes.synapse.mediator.common.util.OmHelper;

public class WrapperMediatorTest {

    private WrapperMediator wrapperMediator;
    private String wrapperNamespace = "http://www.mystes.fi/ns";
    private String wrapperNSprefix = "mys";
    private String wrapperName = "wrapped";
    private String payloadElementName = "firstChild";
    private String payloadSecondElementName = "secondChild";

    @Mock
    private MessageContext mc;

    private SOAPEnvelope envelope;

    private SOAPBody body;

    private OMElement firstChild;
    private OMElement secondChild;

    private SOAPFactory soapFactory;

    private OMLinkedListMetaFactory omFactory;

    private OMNamespace ns;

    @Before
    public void setUp() throws XMLStreamException {
        MockitoAnnotations.initMocks(this);
        wrapperMediator = new WrapperMediator();
        wrapperMediator.setNsUrl(wrapperNamespace);
        wrapperMediator.setNsPrefix(wrapperNSprefix);
        wrapperMediator.setWrapperName(wrapperName);

        omFactory = new OMLinkedListMetaFactory();

        soapFactory = new SOAP11Factory(omFactory);

        ns = new OMNamespaceImpl("http://schemas.xmlsoap.org/soap/envelope/", "env");

        envelope = new SOAPEnvelopeImpl(ns, soapFactory);

        body = new SOAP11BodyImpl(envelope, soapFactory);

        firstChild = OmHelper.createElement(payloadElementName, null);
        body.addChild(firstChild);

        when(mc.getEnvelope()).thenReturn(envelope);
    }

    @Test
    public void payloadShouldBeWrappedToOneElement() {
        wrapperMediator.mediate(mc);
        OMElement wrappedElement = mc.getEnvelope().getBody().getFirstElement();
        assertTrue("Payload must be \"" + wrapperName + "\"", wrappedElement.getLocalName().equals(wrapperName));
        assertTrue("Payload's root namespace must be \"" + wrapperNamespace + "\"",
                wrappedElement.getNamespace().getNamespaceURI().equals(wrapperNamespace));
        assertTrue("Payload's root namespace prefix must be \"" + wrapperNSprefix + "\"",
                wrappedElement.getNamespace().getPrefix().equals(wrapperNSprefix));

        assertTrue("Payload's first element must be wrapped inside \"" + wrapperName + "\" element",
                wrappedElement.getFirstElement().getLocalName().equals(payloadElementName));

        assertTrue("Wrapper element should not have next sibling", wrappedElement.getNextOMSibling() == null);
        assertTrue("Wrapper element should not have previous sibling", wrappedElement.getPreviousOMSibling() == null);
    }

    @Test
    public void payloadWithMultipleRootElementsShouldBeWrappedToOneElement() {
        secondChild = OmHelper.createElement(payloadSecondElementName, null);
        body.addChild(secondChild);

        wrapperMediator.mediate(mc);
        OMElement wrappedElement = mc.getEnvelope().getBody().getFirstElement();

        assertTrue("Payload must be \"" + wrapperName + "\"", wrappedElement.getLocalName().equals(wrapperName));
        assertTrue("Payload's root element namespace must be \"" + wrapperNamespace + "\"",
                wrappedElement.getNamespace().getNamespaceURI().equals(wrapperNamespace));
        assertTrue("Payload's root namespace prefix must be \"" + wrapperNSprefix + "\"",
                wrappedElement.getNamespace().getPrefix().equals(wrapperNSprefix));

        assertTrue("Payload's first element must be wrapped inside \"" + wrapperName + "\" element",
                wrappedElement.getFirstElement().getLocalName().equals(payloadElementName));
        assertTrue("Payload's first element must have next sibling element",
                wrappedElement.getFirstElement().getNextOMSibling() != null);

        OMElement secondElement = (OMElement) wrappedElement.getChildrenWithLocalName(payloadSecondElementName).next();
        assertTrue("Payload's second element must be wrapped inside \"" + wrapperName + "\" element",
                secondElement.getLocalName().equals(payloadSecondElementName));

        assertTrue("Payload's second element must have previous sibling element",
                secondElement.getPreviousOMSibling() != null);

        assertTrue("Wrapper element should not have next sibling", wrappedElement.getNextOMSibling() == null);
        assertTrue("Wrapper element should not have previous sibling", wrappedElement.getPreviousOMSibling() == null);
    }

}
