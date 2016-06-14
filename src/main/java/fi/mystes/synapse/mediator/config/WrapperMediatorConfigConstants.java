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
package fi.mystes.synapse.mediator.config;

import javax.xml.namespace.QName;

import org.apache.synapse.config.xml.XMLConfigConstants;

public class WrapperMediatorConfigConstants {

    public static final String NAMESPACE_STRING = XMLConfigConstants.SYNAPSE_NAMESPACE;

    public static final String ROOT_TAG_NAME = "wrapper";

    public static final QName ROOT_TAG = new QName(NAMESPACE_STRING, ROOT_TAG_NAME);

    public static final String WRAPPER_NAME_ATT_NAME = "wrapperName";
    public static final String NS_PREFIX_ATT_NAME = "nsPrefix";
    public static final String NS_URL_ATT_NAME = "nsUrl";

    public static final QName ATT_WRAPPER_NAME_Q = new QName(WRAPPER_NAME_ATT_NAME);
    public static final QName ATT_NS_PREFIX_Q = new QName(NS_PREFIX_ATT_NAME);
    public static final QName ATT_NS_URL_Q = new QName(NS_URL_ATT_NAME);
}
