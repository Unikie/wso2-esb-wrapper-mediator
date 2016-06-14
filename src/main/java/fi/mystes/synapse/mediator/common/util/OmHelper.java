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
package fi.mystes.synapse.mediator.common.util;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMAttribute;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.synapse.MessageContext;

/**
 * Helper class for creating OMElement, OMAttribute, and OMNamespace instances,
 * and also adding OMElement instances to message context.
 * 
 */
public final class OmHelper {

    /**
     * Non-instantiable class
     */
    private OmHelper() {

    }

    /**
     * Creates OMAttribute instance with given attribute name and value
     * 
     * @param localName
     *            Attribute name
     * @param value
     *            Attribute value
     * 
     * @return created OMAttribute instance
     */
    public static OMAttribute createAttribute(final String localName, final String value) {
        final OMFactory factory = OMAbstractFactory.getOMFactory();
        return factory.createOMAttribute(localName, null, value);
    }

    /**
     * Creates attribute with given name and value, and adds it to the given
     * element
     * 
     * @param toThis
     *            Element to add created attribute
     * @param name
     *            Attribute name
     * @param value
     *            Attribute value
     * 
     */
    public static void addAttribute(final OMElement toThis, final String name, final String value) {
        final OMNamespace namespace = toThis.getNamespace();
        addAttribute(toThis, namespace, name, value);
    }

    /**
     * Creates attribute with given name, value, and Namespace, and adds it to
     * the given element
     * 
     * @param toThis
     *            Element to add created attribute
     * @param namespace
     *            Namespace of the attribute
     * @param name
     *            Attribute name
     * @param value
     *            Attribute value
     */
    public static void addAttribute(final OMElement toThis, final OMNamespace namespace, final String name,
            final String value) {
        toThis.addAttribute(name, value, namespace);
    }

    /**
     * Adds given element to given message context's SOAP body element
     * 
     * @param addThis
     *            Element to be added
     * @param toThis
     *            Message context where element will be added to
     */
    public static void addElement(final OMElement addThis, final MessageContext toThis) {
        toThis.getEnvelope().getBody().addChild(addThis);
    }

    /**
     * Adds given element to given target element as child
     * 
     * @param addThis
     *            Element to be added
     * @param toThis
     *            Element to add element to
     */
    public static void addElement(final OMElement addThis, final OMElement toThis) {
        toThis.addChild(addThis);
    }

    /**
     * Create Namespace with given URL and prefix
     * 
     * @param url
     *            Namespace URL
     * @param prefix
     *            Namespace
     * 
     * @return created OMNamespace instance
     */
    public static OMNamespace createNamespace(final String url, final String prefix) {
        final OMFactory factory = OMAbstractFactory.getOMFactory();
        return factory.createOMNamespace(url, prefix);
    }

    /**
     * Creates OMElement instance without parent with given element local name
     * and namespace
     * 
     * @param name
     *            Local name of the element
     * @param namespace
     *            Namespace of the element
     * 
     * @return created OMElement instance
     */
    public static OMElement createElement(final String name, final OMNamespace namespace) {
        final OMFactory factory = OMAbstractFactory.getOMFactory();
        return factory.createOMElement(name, namespace);
    }
}
