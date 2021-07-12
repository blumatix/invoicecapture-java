package com.blumatix;

/**
 * Created by rudi on 1/18/2017.
 * This enum defines all invoice features that can be detected.
 */
public enum InvoiceDetailType {
    None(0L),
    Sender(2L),
    DeliveryDate(8L),
    GrandTotalAmount(16L),
    VatRate(32L),
    InvoiceDate(64L),
    Receiver(128L),
    NetTotalAmount(256L),
    InvoiceId(1024L),
    DocumentType(8192L),
    Iban(16384L),
    Bic(32768L),
    LineItem(65536L),
    VatAmount(131072L),
    InvoiceCurrency(524288L),
    DeliveryNoteId(1048576L),
    CustomerId(2097152L),
    TaxNumber(4194304L),
    UId(8388608L),
    SenderOrderId(16777216L),
    ReceiverOrderId(33554432L),
    SenderOrderDate(67108864L),
    ReceiverOrderDate(134217728L),
    NetAmount(268435456L),
    VatGroup(536870912L),
    VatTotalAmount(1073741824L),
    BankCode(4294967296L),
	BankAccount(8589934592L),
	BankGroup(17179869184L),
    IsrReference(34359738368L),
    DiscountDate(68719476736L),
    DiscountStart(137438953472L),
    DiscountDuration(274877906944L),
    DiscountPercent(549755813888L),
    DiscountGroup(1099511627776L),
    DueDateDate(2199023255552L),
    DueDateStart(4398046511104L),
    DueDateDuration(8796093022208L),
    DueDateGroup(17592186044416L),
    IsrSubscriber(35184372088832L),
    KId(70368744177664L),
    CompanyRegistrationNumber(140737488355328L),
    Contacts(281474976710656L);

    private final long id;

    InvoiceDetailType(long id) { this.id = id; }

    public long getValue() { return id; }

    public boolean isEmpty(){return this.equals(InvoiceDetailType.None);}

    public boolean compare(long i){return id == i;}

    public static InvoiceDetailType getValue(long _id)
    {
        InvoiceDetailType[] As = InvoiceDetailType.values();
        for(int i = 0; i < As.length; i++)
        {
            if(As[i].compare(_id))
                return As[i];
        }
        return InvoiceDetailType.None;
    }

    public static String toString(long id)
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
