package com.blumatix;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.cli.*;

public class Main {
    private static Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        String invoiceFolder = "";
        String filename = "";
        String apiKey="";
        String url="";
        String[] invoiceDetails;
        boolean createResultPdf = false;
        String outputPath=".";
        int invoiceFeatures = -1;

        CommandLineParser parser = new DefaultParser();

        try {
            // parse the command line arguments
            Options options = createOptions();
            CommandLine cmd = parser.parse(options, args);

            if (cmd.hasOption("help")) {
                // automatically generate the help statement
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp( "-cp $env:CLASSPATH com.blumatix.Main -folderPath PATH_TO_YOUR_INVOICE -apiKey YOUR_API_KEY -url CAPTURE_SDK_BASEURL -version VERSION -resultPdf", options );
                return;
            }

            if (cmd.hasOption("folderPath")) {
                invoiceFolder = cmd.getOptionValue("folderPath");
            }

            // will only be taken into account if folderPath is not set
            if (cmd.hasOption("filename") && invoiceFolder == "") {
                filename = cmd.getOptionValue("filename");
            }

            if (cmd.hasOption("apiKey")) {
                apiKey = cmd.getOptionValue("apiKey");
            }

            if (cmd.hasOption("url")) {
                url = cmd.getOptionValue("url");
            }

            if (cmd.hasOption("invoiceDetails")) {
                invoiceDetails = cmd.getOptionValues("invoiceDetails");
                invoiceFeatures = getInvoiceDetailsFilterMask(invoiceDetails);
            }
            else {
                invoiceFeatures = getInvoiceDetailsFilterMask(null);
            }

            createResultPdf = cmd.hasOption("resultPdf");

            if (cmd.hasOption("outputPath")) {
                outputPath = cmd.getOptionValue("outputPath");
            }
        }
        catch( ParseException exp ) {
            // oops, something went wrong
            System.err.println( "Parsing failed.  Reason: " + exp.getMessage() );
        }

        Main main = new Main();
        File[] invoices = null;

        if (invoiceFolder != "" && Files.exists(Paths.get(invoiceFolder))) {
            File folder = new File(invoiceFolder);
            invoices = folder.listFiles();
        }
        else if (filename != "" && Files.exists(Paths.get(filename))) {
            File invoiceFile = new File(filename);
            invoices = new File[] {invoiceFile};
        }
        else {
            Main.logger.error("Invalid invoice folder or invoice file");
            return;
        }

        int totalInvoices = invoices.length;
        int invoiceCounter = 0;
        Main.logger.info("Total number of invoices: " + invoices.length);

        for (File file : invoices) {
            String invoicename = file.getAbsolutePath();
            Main.logger.info( "Sending " + ++invoiceCounter + "/" + totalInvoices + " invoice request " + invoicename );
            String response = main.requestInvoiceDetails(invoicename, apiKey, invoiceFeatures, url, createResultPdf, outputPath);
            Main.logger.info( "Received invoice features of invoice: " + invoicename );
            String invoiceNameWithoutExtension = getNameWithoutExtension(Paths.get(invoicename));
            writeResultFile(response, outputPath, invoiceNameWithoutExtension);

            if (createResultPdf) {
                writeResultPdf(response, outputPath, invoiceNameWithoutExtension);
            }
        }

        Main.logger.info("Test client finished");
    }

    private static Options createOptions() {
        //  [[-folderPath] <String>] [[-filename] <String>] [[-apiKey] <String>] [[-url] <String>] [[-version] <String>] [-resultPdf] [-csv] [-mergeCsv] [[-invoiceDetails] <String[]>] [[-outputPath] <String>]
        Options options = new Options();

        options.addOption("f", "folderPath", true, "Path to an invoice folder");
        options.addOption("i", "filename", true, "Path to an invoice");

        Option apiKey = OptionBuilder
                .withLongOpt("apiKey")
                .withArgName("YOUR_API_KEY")
                .hasArgs(1)
                .withDescription("Your APIKey needed for authentication and authorization")
                .create("a");
        options.addOption(apiKey);

        Option url = OptionBuilder
                .withLongOpt("url")
                .withArgName("CAPTURESDK_URL")
                .hasArgs(1)
                .withDescription("CaptureSdk base url")
                .create("u");
        options.addOption(url);

        options.addOption("resultPdf", "Requests and creates a ResultPdf");

        Option invoiceDetails = OptionBuilder
                .withLongOpt("invoiceDetails")
                .withArgName("InvoiceDetails")
                .hasArgs()
                .withDescription("A list of invoice details that shall be returned.")
                .create("d");

        options.addOption(invoiceDetails);

        options.addOption("o", "outputPath", true, "Path to an output folder to which all result will be written to.");
        options.addOption("help", "Help for this application");

        return options;
    }

    private static int getInvoiceDetailsFilterMask(String[] invoiceDetails) throws Exception {
        int invoiceFeatures = 0;
        if (invoiceDetails == null)
        {
            // Request all available InvoiceDetails
            invoiceFeatures = InvoiceDetailType.None.getValue();
        }
        else
        {
            // Request only a subset of available InvoiceDetails
            for (String detail : invoiceDetails) {
                InvoiceDetailType detailType = InvoiceDetailType.fromString(detail);
                if (detailType == null) {
                    throw new Exception("Invalid InvoiceDetailType " + detail);
                }

                invoiceFeatures |= detailType.getValue();
            }
        }

        return invoiceFeatures;
    }

    private String requestInvoiceDetails(String filename, String apiKey, int invoiceFeatures, String baseUrl,
                                       boolean createResultPdf, String outputPath ) throws Exception {
        // REST query
        String urlString = baseUrl + "/invoicedetail/detect";
        Main.logger.info("CaptureSdk Url: " + urlString);

        URL url = new URL(urlString);

        // If you're behind a proxy then use the following code
        //String proxyServer = "PROXYSERVERNAME";
        //String port = "PROXYPORT";
        //Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyServer, Integer.parseInt(port)));
        //HttpURLConnection connection = (HttpURLConnection)url.openConnection( proxy );

        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("POST");

        // We use json as content type and accept json as response format
        connection.setRequestProperty( "Content-Type", "application/json" );
        connection.setRequestProperty("Accept", "application/json");

        // Add ApiKeyToken to header
        connection.setRequestProperty("X-ApiKey", apiKey);

        // Send POST
        connection.setDoOutput(true);
        DataOutputStream writeStream = new DataOutputStream(connection.getOutputStream());

        // Instantiate a new request object that shall be sent as a json string to the capturesdk service
        InvoiceDetailsRequest invoiceRequest = new InvoiceDetailsRequest( invoiceFeatures, filename, createResultPdf);

        // Convert InvoiceRequest object to a json string
        // This string is then sent in the POST request to the capturesdk service
        String jsonString = invoiceRequest.toJson();

        writeStream.writeBytes(jsonString);
        writeStream.flush();
        writeStream.close();

        // Get Response and check response code
        int responseCode = connection.getResponseCode();
        String response = getResponse(connection);

        if (responseCode != 200)
        {
            Main.logger.error(
                    "Response error:" +
                    "\n\tResponseCode " + responseCode +
                    "\n\t" + connection.getResponseMessage() +
                    "\n\t" + response);

            return null;
        }

        return response;
    }

    private String getResponse(HttpURLConnection connection) throws IOException {
        InputStream is = connection.getResponseCode() == 200 ? connection.getInputStream() : connection.getErrorStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(is));

        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null)
        {
            response.append(inputLine);
        }

        in.close();

        return response.toString();
    }

    public static String getNameWithoutExtension(Path path) {
        Path name = path.getFileName();
        // null for empty paths and root-only paths
        if (name == null) {
            return "";
        }
        String fileName = name.toString();
        int dotIndex = fileName.lastIndexOf('.');
        return dotIndex == -1 ? fileName : fileName.substring(0, dotIndex);
    }

    private static void writeResultFile(String response, String outputPath, String invoiceName) throws IOException {
        // Write response into a json file
        String jsonResultPath = Paths.get(outputPath, invoiceName + ".json").toString();

        Main.logger.info("Writing to " + jsonResultPath);

        FileOutputStream outputStream = new FileOutputStream(jsonResultPath);
        byte[] strToBytes = response.getBytes();
        outputStream.write(strToBytes);
        outputStream.close();
    }

    private static void writeResultPdf(String response, String outputPath, String invoiceName) throws IOException {
        // Write response into a json file
        String pdfResultPath = Paths.get(outputPath, invoiceName + ".pdf").toString();

        Main.logger.info("Writing to " + pdfResultPath);

        FileOutputStream outputStream = new FileOutputStream(pdfResultPath);
        byte[] strToBytes = InvoiceDetailsResponse.CreateResponse(response).getResultPdf();
        outputStream.write(strToBytes);
        outputStream.close();
    }
}
