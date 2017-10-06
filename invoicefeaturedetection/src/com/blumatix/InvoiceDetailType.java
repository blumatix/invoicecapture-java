package com.blumatix;

/**
 * Created by rudi on 1/18/2017.
 * This enum defines all invoice features that can be detected.
 */
public enum InvoiceDetailType {

    GrandTotalAmount(16),
    InvoiceDate(64),
    InvoiceId(1024),
    DocumentType(8192),
    Iban(16384),
    TaxNo(4194304),
    UId(8388608);


    private final int id;

    InvoiceDetailType(int id) { this.id = id; }

    public int getValue() { return id; }
}
