package com.blumatix;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

/**
 * Created by rudi on 9/18/2017.
 */
public class DetectInvoiceRequestAsync
{
    private int Flags;
    private String Invoice;
    private String Version;

    public DetectInvoiceRequestAsync(int flags, String filename, String version) throws IOException
    {
        Flags = flags;
        setInvoice(filename);
        Version = version;
    }

    /**
     * Gets the invoice feature bitmask.
     * @return Bitmask representing the invoice features to be detected.
     */
    public int getFlags() {
        return Flags;
    }

    /**
     * Gets the base64 encoded invoice as a byte array
     * @return The invoice as a byte array.
     */
    public byte[] getInvoice() {
        return Base64.getDecoder().decode(Invoice);
    }

    /**
     * Gets the service version.
     * @return The service version.
     */
    public String getVersion() { return Version; }

    /**
     * Serializes this PredictInvoiceRequest instance into a json string as it is required by the capturesdk
     * REST api
     * @return The serialized json string
     */
    public String toJson() { return new Gson().toJson(this); }

    // Set the Invoice field. The file is read into a byte array which is then converted into a base64 string
    // that is required by the capturesdk service REST api
    private void setInvoice(String filename) throws IOException
    {
        Path path = Paths.get(filename);

        byte[] data = Files.readAllBytes(path);

        Invoice = Base64.getEncoder().encodeToString(data);
    }
}
