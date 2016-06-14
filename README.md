# wso2-esb-wrapper-mediator

## What is WSO2 ESB?
[WSO2 ESB](http://wso2.com/products/enterprise-service-bus/) is an open source Enterprise Service Bus that enables interoperability among various heterogeneous systems and business applications.

## Features
Wrapper Mediator is a custom WSO2 ESB mediator for wrapping payload content into desired element. Wrapper mediator takes all the child elements from payload and moves them under the new root element.

## Usage

### 1. Install the mediator
Copy the `WrapperMediator-x.y.jar` to `$WSO2_ESB_HOME/repository/components/dropins/`.

### 2. Use it

```xml
<wrapper wrapperName="{name of the wrapper element}"
         nsPrefix="{Namespace prefix for wrapper element}"
         nsUrl="{Namespace for wrapper element}"/>
```

All three attributes of the wrapper mediator are mandatory fields.

#### Example
```xml
<wrapper wrapperName="rootElement"
         nsPrefix="mys"
         nsUrl="http://www.mystes.fi/ns"/>
```

## Technical Requirements

#### Usage

* Oracle Java 6 & 7
* WSO2 ESB
    * Wrapper Mediator has been tested with WSO2 ESB versions 4.5.1 & 4.8.1

#### Development

* All above + Maven 3.0.X

## [License](LICENSE)

Copyright &copy; 2016 [Mystes Oy](http://www.mystes.fi). Licensed under the [Apache 2.0 License](LICENSE).
