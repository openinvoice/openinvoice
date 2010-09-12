package org.openinvoice.core.field;

import org.openinvoice.core.template.InvoiceTemplate;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Author: jhe
 * Date: Jun 28, 2010
 * Time: 10:09:53 PM
 * (C) Copyright 2010 OpenInvoice.org. All rights are reserved.
 */
public class PaymentMeansField extends FieldCollection {

    public PaymentMeansField(String baseKey) {
        super(baseKey);
    }

    public InvoiceTemplate replace(InvoiceTemplate template) {
        return super.replace(template);
    }

    public void setPaymentDueDate(XMLGregorianCalendar value) {
        add(new DateField(InvoiceFieldKey.paymentDueDate.key(), value));
    }

    public void setPayeeFinancialAccountID(String value) {
        add(new TextField(InvoiceFieldKey.payeeFinancialAccountID.key(), value));
    }

    public void setPayeeFinancialAccountName(String value) {
        add(new TextField(InvoiceFieldKey.payeeFinancialAccountName.key(), value));
    }

    public void setPayeeFinancialInstitutionID(String value) {
        add(new TextField(InvoiceFieldKey.payeeFinancialInstitutionID.key(), value));
    }

    public void setPayeeFinancialInstitutionName(String value) {
        add(new TextField(InvoiceFieldKey.payeeFinancialInstitutionName.key(), value));
    }

    public void setPayerFinancialAccountID(String value) {
        add(new TextField(InvoiceFieldKey.payerFinancialAccountID.key(), value));
    }

    public void setPayerFinancialAccountName(String value) {
        add(new TextField(InvoiceFieldKey.payerFinancialAccountName.key(), value));
    }

    public void setPayerFinancialInstitutionID(String value) {
        add(new TextField(InvoiceFieldKey.payerFinancialInstitutionID.key(), value));
    }

    public void setPayerFinancialInstitutionName(String value) {
        add(new TextField(InvoiceFieldKey.payerFinancialInstitutionName.key(), value));
    }

}