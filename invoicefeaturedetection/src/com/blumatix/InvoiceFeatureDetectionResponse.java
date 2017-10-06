package com.blumatix;

/**
 * Created by rudi on 1/18/2017.
 * Contains a single detected invoice feature
 */
public class InvoiceFeatureDetectionResponse {
    private int Type;
    private String Value;
    private double Score;
    private int X;
    private int Y;
    private int Width;
    private int Height;

    @Override
    public String toString() {
        return String.format("%s,Value:%s,Score:%f,[X:%d,Y:%d,Width:%d,Height:%d]",
                toFeatureTypeString(Type), Value.toString(), Score, X, Y, Width, Height);
    }

    public int getFeatureType() {
        return Type;
    }

    public void setFeatureType(int featureType) {
        Type = featureType;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public double getScore() {
        return Score;
    }

    public void setScore(double score) {
        Score = score;
    }

    public int getX() {
        return X;
    }

    public void setX(int x) {
        X = x;
    }

    public int getY() {
        return Y;
    }

    public void setY(int y) {
        Y = y;
    }

    public int getWidth() {
        return Width;
    }

    public void setWidth(int width) {
        Width = width;
    }

    public int getHeight() {
        return Height;
    }

    public void setHeight(int height) {
        Height = height;
    }

    private String toFeatureTypeString( int type )
    {
        if (type == InvoiceDetailType.GrandTotalAmount.getValue())
        {
            return InvoiceDetailType.GrandTotalAmount.toString();
        }
        else if (type == InvoiceDetailType.InvoiceDate.getValue())
        {
            return InvoiceDetailType.InvoiceDate.toString();
        }
        else if (type == InvoiceDetailType.InvoiceId.getValue())
        {
            return InvoiceDetailType.InvoiceId.toString();
        }
        else if (type == InvoiceDetailType.DocumentType.getValue())
        {
            return InvoiceDetailType.DocumentType.toString();
        }
        else if (type == InvoiceDetailType.Iban.getValue())
        {
            return InvoiceDetailType.Iban.toString();
        }
        else if (type == InvoiceDetailType.TaxNo.getValue())
        {
            return InvoiceDetailType.TaxNo.toString();
        }
        else if (type == InvoiceDetailType.UId.getValue())
        {
            return InvoiceDetailType.UId.toString();
        }
        else
        {
            return "Unkown InvoiceFeature";
        }
    }
}
