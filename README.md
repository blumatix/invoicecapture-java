# invoicecapture-java
A java example application which demonstrates how our invoice capture REST API can be used to retrieve information about specific invoice details.

## Dependencies
- Java SDK: 1.8, version 1.8.0_112
- Libraries:
    - [gson 2.8.0](https://github.com/google/gson), a json library
    - [log4j2](https://logging.apache.org/log4j/2.0/download.html), a logging library

## Used IDE
- IntelliJ IDEA 2016.3.2 Community Edition: This IDE has been used as development environment
    
## Build
- Clone this repo: ```git clone https://github.com/blumatix/invoicecapture-java.git```

## Usage
 - Open a shell, e.g. from Powershell
 
```sh
    cd C:\RepoFolder\invoicefeaturedetection"

    PS> pwd

    Path
    ----
    C:\RepoFolder\invoicefeaturedetection

    PS> java -cp ".\libs\gson-2.8.0.jar;.\libs\log4j-api-2.9.0.jar;.\libs\log4j-core-2.9.0.jar;.\out\production\invoicefeaturedetection" com.blumatix.Main "C:\PathToYourInvoiceFolder\Invoices" ApiKey CaptureSDKUrl

```
    
    
PathToYourInvoiceFolder ... This shall be a valid directory containing invoices that shall be processed.    
ApiKey ... Your API key received from blumatix-consulting 

