package com.blumatix;

import java.util.List;
import java.util.Base64;

import com.google.gson.Gson;

/**
 * Created by rudi on 1/18/2017.
 * This is the response that is returned as a json object from the capturesdk service. It contains
 * a list of predicted invoice features. Each predicted invoice feature is encapsulated in an
 * InvoiceFeatureDetectionResponse object.
 */
public class InvoiceDetailsResponse {

    private int DocumentResolution;
    private List<InvoiceFeatureDetectionResponse> InvoiceDetailTypePredictions;
    private List<InvoiceDetailsGroup> PredictionGroups;
    private String ResultPdf;

    public int getDocumentResolution()
    {
        return DocumentResolution;
    }

    public void setDocumentResolution(int documentResolution)
    {
        DocumentResolution = documentResolution;
    }

    public List<InvoiceFeatureDetectionResponse> getInvoiceDetailTypePredictions()
    {
        return InvoiceDetailTypePredictions;
    }

    public void setInvoiceDetailTypePredictions(List<InvoiceFeatureDetectionResponse> invoiceFeaturePredictions)
    {
        InvoiceDetailTypePredictions = invoiceFeaturePredictions;
    }

    public List<InvoiceDetailsGroup> getPredictionGroups()
    {
        return PredictionGroups;
    }

    public void setPredictionGroups(List<InvoiceDetailsGroup> predictionGroups)
    {
        PredictionGroups = predictionGroups;
    }

    public byte[] getResultPdf() { return Base64.getDecoder().decode(ResultPdf); }

    public static InvoiceDetailsResponse CreateResponse(String responseString )
    {
        InvoiceDetailsResponse response = new Gson().fromJson(responseString, InvoiceDetailsResponse.class);

        return response;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (InvoiceDetailTypePredictions != null)
        {
            sb.append("DocumentResolution: ").append(DocumentResolution).append('\n');

            for (InvoiceFeatureDetectionResponse p: InvoiceDetailTypePredictions) {
                sb.append(p.toString()).append('\n');
            }

            for (InvoiceDetailsGroup group : PredictionGroups) {
                sb.append(group.toString()).append('\n');
            }
        }

        return sb.toString();
    }
}
