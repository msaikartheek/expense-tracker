package com.expense.tracker.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "invoiceDetails")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class InvoiceDetails {
    @Id
    String id;
    String clientId;
    String invoiceNumber;
    String invoiceType;
    String invoiceDate;
    String invoiceAmount;
    String invoiceCurrency;
    String invoiceDescription;
    String invoiceStatus;
}
