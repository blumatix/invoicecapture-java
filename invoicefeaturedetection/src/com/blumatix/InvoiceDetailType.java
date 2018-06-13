package com.blumatix;

/**
 * Created by rudi on 1/18/2017.
 * This enum defines all invoice features that can be detected.
 */
public enum InvoiceDetailType {
    None(0),
    DeliveryDate(8),
    GrandTotalAmount(16),
    InvoiceDate(64),
    InvoiceId(1024),
    DocumentType(8192),
    VatAmount(131072),
    Iban(16384),
    TaxNo(4194304),
    UId(8388608),
    SenderOrderId(16777216),
    ReceiverOrderId(33554432),
    SenderOrderDate(67108864),
    ReceiverOrderDate(134217728),
    VatGroup(536870912);

    private final int id;

    InvoiceDetailType(int id) { this.id = id; }

    public int getValue() { return id; }

    public boolean isEmpty(){return this.equals(InvoiceDetailType.None);}

    public boolean compare(int i){return id == i;}

    public static InvoiceDetailType getValue(int _id)
    {
        InvoiceDetailType[] As = InvoiceDetailType.values();
        for(int i = 0; i < As.length; i++)
        {
            if(As[i].compare(_id))
                return As[i];
        }
        return InvoiceDetailType.None;
    }

    public static String toString(int id)
    {
        return getValue(id).name();
    }
}
