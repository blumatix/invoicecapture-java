# invoicecapture-java
Contains sample code to access our capture client.

### More information and quick test capability: [BLU DELTA AI invoice capture - KI Rechnungserfassung](https://www.bludelta.de)
### Request free API key here: [Capturing - Rechungserfassung Get Started](https://www.bludelta.de/en/get-started/)
### Access URL (latest version): https://api.bludelta.ai/v1-17

## Dependencies
- Java SDK: 1.8, version 1.8.0_112
- Libraries:
    - [commons-cli-1.4.jar](https://commons.apache.org/proper/commons-cli/)
    - [gson 2.8.0](https://github.com/google/gson), a json library
    - [log4j2](https://logging.apache.org/log4j/2.0/download.html), a logging library


## Used IDE
- IntelliJ IDEA 2016.3.2 Community Edition: This IDE has been used as development environment
    
## Build
- Clone this repo: ```git clone https://github.com/blumatix/invoicecapture-java.git```
- ```cd invoicecapture-java\invoicefeaturedetection```
- ```javac -cp ".\libs\gson-2.8.0.jar;.\libs\log4j-api-2.9.0.jar;.\libs\log4j-core-2.9.0.jar;.\libs\commons-cli-1.4.jar;.\out\production\invoicefeaturedetection" .\src\com\blumatix\*.java -d .\out\production\invoicefeaturedetection\```

## Usage
 - Open a shell, e.g. from Powershell
 
```sh
	PS> cd C:\RepoFolder\invoicefeaturedetection"
	PS> $env:CLASSPATH = "$pwd\libs\*;$pwd\out\production\invoicefeaturedetection"
	PS> java -cp $env:CLASSPATH com.blumatix.Main -folderPath INVOICE_FOLDER -apiKey APIKEY -url CAPTURESDK_URL -version CAPTURESDK_VERSION -outputPath OUTPUT_FOLDER
```
You get detailed help:
```sh
	PS> cd C:\RepoFolder\invoicefeaturedetection"
	PS> $env:CLASSPATH = "$pwd\libs\*;$pwd\out\production\invoicefeaturedetection"
	PS> java -cp $env:CLASSPATH com.blumatix.Main -help
    
    usage: -cp $env:CLASSPATH com.blumatix.Main -folderPath
           PATH_TO_YOUR_INVOICE -apiKey YOUR_API_KEY -url
           CAPTURE_SDK_BASEURL -version VERSION -resultPdf
     -a,--apiKey <YOUR_API_KEY>             Your APIKey needed for
                                            authentication and authorization
     -d,--invoiceDetails <InvoiceDetails>   A list of invoice details that
                                            shall be returned.
     -f,--folderPath <arg>                  Path to an invoice folder
     -help                                  Help for this application
     -i,--filename <arg>                    Path to an invoice
     -o,--outputPath <arg>                  Path to an output folder to which
                                            all result will be written to.
     -resultPdf                             Requests and creates a ResultPdf
     -u,--url <CAPTURESDK_URL>              CaptureSdk base url
     -v,--version <CAPUTURESDK_VERSION>     CaptureSdk version
```


- INVOICE_FOLDER: This shall be a valid directory containing invoices that shall be processed.
- APIKEY: Your API key received from blumatix-consulting
- CAPTURESDK_URL: Base url to our capture service
- CAPTURESDK_VERSION: A specific capture sdk version
- OUTPUT_FOLDER: A valid folder used to store results

__NOTE__ All paths must be properly escaped, e.g. "C:\\tmp\\invoicefolder"

### 5x8 Support: bludelta-support@blumatix.com
