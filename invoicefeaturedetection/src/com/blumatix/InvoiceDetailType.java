package com.blumatix;

/**
 * Created by rudi on 1/18/2017.
 * This enum defines all invoice features that can be detected.
 */
public enum InvoiceDetailType {
    None(0),
    Sender(2),
    DeliveryDate(8),
    GrandTotalAmount(16),
    InvoiceDate(64),
    NetTotalAmount(256),
    InvoiceId(1024),
    DocumentType(8192),
    Iban(16384),
    Bic(32768),
    LineItems(65536),
    InvoiceCurrency(524288),
    DeliveryNoteId(1048576),
    CustomerId(2097152),
    TaxNumber(4194304),
    UId(8388608),
    SenderOrderId(16777216),
    ReceiverOrderId(33554432),
    SenderOrderDate(67108864),
    ReceiverOrderDate(134217728),
    VatGroup(536870912),
    VatTotalAmount(1073741824),
    CustomInvoiceDetail(-2147483648);

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

    public static InvoiceDetailType fromString(String text) {
        for (InvoiceDetailType d : InvoiceDetailType.values()) {
            if (d.name().equalsIgnoreCase(text)) {
                return d;
            }
        }

        return null;
    }
}
