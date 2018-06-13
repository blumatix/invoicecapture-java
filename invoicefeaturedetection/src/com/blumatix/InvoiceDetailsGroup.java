package com.blumatix;

import java.util.List;

public class InvoiceDetailsGroup {
    private InvoiceDetailType Type;
    private String TypeName;
    private List<InvoiceFeatureDetectionResponse> InvoiceDetailTypePredictions;

    public InvoiceDetailType getType() {
        return Type;
    }

    public void setType(InvoiceDetailType type) {
        Type = type;
    }

    public String getTypeName() {
        return TypeName;
    }

    public void setTypeName(String typeName) {
        TypeName = typeName;
    }

    public List<InvoiceFeatureDetectionResponse> getInvoiceDetailTypePredictions() {
        return InvoiceDetailTypePredictions;
    }

    public void setInvoiceDetailTypePredictions(List<InvoiceFeatureDetectionResponse> invoiceDetailPredictions) {
        InvoiceDetailTypePredictions = invoiceDetailPredictions;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        String header = String.format("GroupName: %s", TypeName);

        builder.append(header).append("\n");

        for (InvoiceFeatureDetectionResponse p : InvoiceDetailTypePredictions) {
            builder.append('\t').append(p.toString()).append('\n');
        }

        return builder.toString();
    }
}