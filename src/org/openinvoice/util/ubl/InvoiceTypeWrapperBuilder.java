package org.openinvoice.util.ubl;

import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.*;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.*;
import oasis.names.specification.ubl.schema.xsd.invoice_2.InvoiceType;
import oasis.names.specification.ubl.schema.xsd.order_2.OrderType;
import org.apache.log4j.Logger;
import org.openinvoice.util.ResourceManager;
import un.unece.uncefact.codelist.specification._54217._2001.CurrencyCodeContentType;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.List;

/**
 * Author: jhe
 * Date: May 12, 2010
 * Time: 11:05:35 PM
 * (C) Copyright 2010 OpenInvoice.org. All rights are reserved.
 */
public class InvoiceTypeWrapperBuilder {

    private Logger logger = Logger.getLogger(InvoiceTypeWrapperBuilder.class);
    private OrderType orderType;
    private InvoiceType invoiceType;

    public InvoiceTypeWrapperBuilder(OrderType orderType) throws JAXBException, FileNotFoundException {
        this.orderType = orderType;
        this.invoiceType = new InvoiceType();
    }

    public InvoiceTypeWrapper process() {
        CurrencyCodeContentType currencyCode;

        invoiceType.setOrderReference(InvoiceTypeWrapperBuilderHelper.createOrderReferenceType(orderType.getID(), orderType.getSalesOrderID()));
        invoiceType.setID(orderType.getID()==null?new IDType():orderType.getID());
        invoiceType.setIssueDate(orderType.getIssueDate());
        //TODO: invoiceType.setIssueTime(orderType.getIssueTime()==null?InvoiceTypeWrapperBuilderHelper.createIssueTimeType():orderType.getIssueTime());
        invoiceType.setAccountingCustomerParty(orderType.getBuyerCustomerParty());
        invoiceType.setAccountingSupplierParty(orderType.getSellerSupplierParty());
        List<OrderLineType> orderLineList = orderType.getOrderLine();
        if (orderLineList != null) {
            for (OrderLineType orderLine : orderLineList) {
                InvoiceLineType invoiceLine = new InvoiceLineType();
                if (orderLine != null) {
                    LineItemType orderLineItem = orderLine.getLineItem();
                    if (orderLineItem != null) {
                        if (orderLineItem != null) {
                            PriceType price = orderLineItem.getPrice();
                            currencyCode = price.getPriceAmount().getCurrencyID();
                            if (price != null) {
                                invoiceLine.setPrice(price);
                            }
                            IDType lineID = orderLineItem.getID();
                            if (lineID != null) {
                                invoiceLine.setID(lineID);
                            }
                            QuantityType quantity = orderLineItem.getQuantity();
                            if (quantity != null) {
                                InvoicedQuantityType invoicedQuantity = new InvoicedQuantityType();
                                invoicedQuantity.setUnitCode(quantity.getUnitCode());
                                invoicedQuantity.setValue(quantity.getValue());
                                invoiceLine.setInvoicedQuantity(invoicedQuantity);
                            }
                            TotalTaxAmountType totalTaxAmount = orderLineItem.getTotalTaxAmount();
                            if (totalTaxAmount != null) {
                                InvoiceTypeWrapperBuilderHelper.createTaxTotalType(totalTaxAmount.getValue(), currencyCode);
                            }
                            invoiceLine.setLineExtensionAmount(InvoiceTypeWrapperBuilderHelper.createLineExtensionAmountType(BigDecimal.ZERO, currencyCode));
                            invoiceLine.setItem(orderLineItem.getItem());
                        }
                        invoiceType.getInvoiceLine().add(invoiceLine);
                    }
                }
            }
        }
        PaymentMeansType paymentMeans = orderType.getPaymentMeans();
        if (paymentMeans != null) {
            invoiceType.getPaymentMeans().add(paymentMeans);
        }
        invoiceType.getPaymentTerms().add(InvoiceTypeWrapperBuilderHelper.createPaymentTermsType());
        return new InvoiceTypeWrapper(invoiceType);
    }
}



