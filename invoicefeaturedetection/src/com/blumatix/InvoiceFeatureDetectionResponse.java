package com.blumatix;

/**
 * Created by rudi on 1/18/2017.
 * Contains a single detected invoice feature
 */
public class InvoiceFeatureDetectionResponse {
    private int Type;
    private String TypeName;
    private String Value;
    private double Score;
    private int X;
    private int Y;
    private int Width;
    private int Height;

    public int getFeatureType() {
        return Type;
    }

    public void setFeatureType(int featureType) {
        Type = featureType;
    }

    public String getTypeName() {return TypeName;}

    public void setTypeName(String typeName) { TypeName = typeName; }

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

    @Override
    public String toString() {
        return String.format("InvoiceDetail: %s, Type: %d,Value:%s,Score:%f,[X:%d,Y:%d,Width:%d,Height:%d]",
                TypeName, Type, Value.toString(), Score, X, Y, Width, Height);
    }
}
