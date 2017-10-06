package com.blumatix;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Main {

    private static Logger logger = LogManager.getLogger(Main.class);


    public static void main(String[] args) throws Exception {
        String invoiceFolder;
        String apiKey;
        String url;

        if (args.length != 3)
        {
            System.out.println( "Usage: java -cp \".\\libs\\gson-2.8.0.jar;.\\out\\production\\invoicefeaturedetection\" com.blumatix.Main invoiceFolder apiKey url");
            return;
        }
        else
        {
            invoiceFolder = args[0];
            apiKey = args[1];
            url = args[2];

            System.out.println( "Invoice folder: " + invoiceFolder);
            System.out.println( "ApiKey: " + apiKey);
            System.out.println( "Url: " + url);
        }

        Main main = new Main();

        Main.logger.info("Test client started");

        // InvoiceFeatures that shall be detected: DocumentType, GrandTotalAmount, InvoiceId, InvoiceDate
        int invoiceFeatures =
                    //InvoiceDetailType.DocumentType.getValue()     |
                    InvoiceDetailType.GrandTotalAmount.getValue()   |
                    InvoiceDetailType.InvoiceId.getValue()          |
                    InvoiceDetailType.InvoiceDate.getValue()        |
                    InvoiceDetailType.Iban.getValue()               |
                    InvoiceDetailType.TaxNo.getValue()              |
                    InvoiceDetailType.UId.getValue();


        Path path = Paths.get(invoiceFolder);
        if (!Files.exists(path))
        {
            Main.logger.info("Invoice folder does not exist");
            return;
        }

        File folder = new File(invoiceFolder);
        File[] invoices = folder.listFiles();

        int totalInvoices = invoices.length;
        int invoiceCounter = 0;
        Main.logger.info("Total number of invoices: " + invoices.length);

        for (File file : invoices) {
            String filename = file.getAbsolutePath();
            Main.logger.info( "Sending " + ++invoiceCounter + "/" + totalInvoices + " invoice request " + filename );
            main.requestInvoiceDetails(filename, apiKey, invoiceFeatures, url);
        }

        Main.logger.info("Test client finished");
    }

    private void requestInvoiceDetails(String filename, String apiKey, int invoiceFeatures, String url_ ) throws Exception {
        // REST query
        String urlString = "http://" + url_ + "/invoicedetail/detect";

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
        InvoiceDetailsRequest invoiceRequest = new InvoiceDetailsRequest( invoiceFeatures, filename);

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

            return;
        }

        // Show invoice feature detection result
        InvoiceDetailsResponse invoiceResponse = InvoiceDetailsResponse.CreateResponse(response);
        Main.logger.info( "Received invoice features of invoice: " + filename );
        Main.logger.info( invoiceResponse.toString() );
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
}
