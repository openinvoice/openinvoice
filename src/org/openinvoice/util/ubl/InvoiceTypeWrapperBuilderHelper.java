package org.openinvoice.util.ubl;

import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.MonetaryTotalType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.OrderReferenceType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.PaymentTermsType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.TaxTotalType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.*;
import org.joda.time.DateTime;
import un.unece.uncefact.codelist.specification._54217._2001.CurrencyCodeContentType;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.math.BigDecimal;

/**
 * Author: jhe
 * Date: Jun 2, 2010
 * Time: 11:43:34 PM
 * (C) Copyright 2010 OpenInvoice.org. All rights are reserved.
 */
public final class InvoiceTypeWrapperBuilderHelper {

    public static TaxTotalType createTaxTotalType(BigDecimal value, CurrencyCodeContentType currencyCode) {
        TaxTotalType taxTotal = new TaxTotalType();
        TaxAmountType taxAmount = new TaxAmountType();
        taxAmount.setValue(value);
        taxAmount.setCurrencyID(currencyCode);
        taxTotal.setTaxAmount(taxAmount);
        return taxTotal;
    }

    public static LineExtensionAmountType createLineExtensionAmountType(BigDecimal value, CurrencyCodeContentType currencyCode) {
        LineExtensionAmountType lineExtensionAmount = new LineExtensionAmountType();
        lineExtensionAmount.setCurrencyID(currencyCode);
        lineExtensionAmount.setValue(value);
        return lineExtensionAmount;
    }

    public static MonetaryTotalType createMonetaryTotalType(BigDecimal taxInclusiveAmountValue, BigDecimal lineExtensionAmountValue, CurrencyCodeContentType currencyCode) {
        MonetaryTotalType monetaryTotal = new MonetaryTotalType();
        PayableAmountType payableAmount = new PayableAmountType();
        payableAmount.setCurrencyID(currencyCode);
        payableAmount.setValue(taxInclusiveAmountValue);
        monetaryTotal.setPayableAmount(payableAmount);

        LineExtensionAmountType lineExtensionAmountType = new LineExtensionAmountType();
        lineExtensionAmountType.setCurrencyID(currencyCode);
        lineExtensionAmountType.setValue(lineExtensionAmountValue);
        monetaryTotal.setLineExtensionAmount(lineExtensionAmountType);

        return monetaryTotal;
    }

    private MonetaryTotalType createMonetaryTotal() {
        MonetaryTotalType monetaryTotal = new MonetaryTotalType();
        monetaryTotal.setLineExtensionAmount(new LineExtensionAmountType());
        monetaryTotal.setTaxExclusiveAmount(new TaxExclusiveAmountType());
        monetaryTotal.setTaxInclusiveAmount(new TaxInclusiveAmountType());
        monetaryTotal.setAllowanceTotalAmount(new AllowanceTotalAmountType());
        return monetaryTotal;
    }

    public static IssueTimeType createIssueTimeType() {
        IssueTimeType currentIssueTime = new IssueTimeType();
        DateTime dateTime = new DateTime();
        DatatypeFactory dataTypeFactory;
        try {
            dataTypeFactory = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }
        if (dataTypeFactory != null) {
            currentIssueTime.setValue(dataTypeFactory.newXMLGregorianCalendar(dateTime.toGregorianCalendar()));
        }
        return currentIssueTime;
    }


    public static TaxAmountType addTaxAmount(TaxAmountType a1, TaxAmountType a2) {
        TaxAmountType newAmount = new TaxAmountType();
        newAmount.setValue(a1.getValue().add(a2.getValue()));
        newAmount.setCurrencyID(a1.getCurrencyID());
        return newAmount;
    }

    public static OrderReferenceType createOrderReferenceType(IDType id, SalesOrderIDType salesOrderId) {
        OrderReferenceType orderRef = new OrderReferenceType();
        if (id != null) {
            orderRef.setID(id);
        }
        if (salesOrderId != null) {
            orderRef.setSalesOrderID(salesOrderId);
        }
        return orderRef;
    }

    public static CurrencyCodeContentType createCurrencyCodeContentType(String iso2Code) {
        return CurrencyCodeContentType.valueOf(iso2Code);
    }

    public static PaymentTermsType createPaymentTermsType() {
        return new PaymentTermsType();
    }
}
