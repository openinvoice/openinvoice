package org.openinvoice.util.ubl;

import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.*;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.*;
import org.openinvoice.core.field.FieldFactory;
import org.openinvoice.core.field.InvoiceFieldKey;
import org.openinvoice.core.field.InvoiceLineField;
import org.openinvoice.core.field.TaxCategoryField;
import un.unece.uncefact.data.specification.unqualifieddatatypesschemamodule._2.CodeType;

import java.math.BigDecimal;
import java.util.Iterator;

/**
 * Author: jhe
 * Date: Jun 28, 2010
 * Time: 7:09:35 PM
 * (C) Copyright 2010 OpenInvoice.org. All rights are reserved.
 */
public class InvoiceLineTypeHelper {

    private InvoiceTypeWrapper invoiceTypeWrapper;

    public InvoiceLineTypeHelper(InvoiceTypeWrapper invoiceTypeWrapper) {
        this.invoiceTypeWrapper = invoiceTypeWrapper;
    }

    public InvoiceTypeWrapper process() {
        processInvoiceLineType();
        return invoiceTypeWrapper;
    }

    /*
     * Given an <code>InvoiceLineField</code>, it calculates the <code>TaxTotalType</code>
     * subtotals.
     */
    private InvoiceLineType calculateInvoiceLineTaxSubtotalsBasedOnItemTaxCategories(InvoiceLineType invoiceLineType,
                                                                                     InvoiceLineField invoiceLineField) {

        BigDecimal taxableAmount = invoiceLineType.getLineExtensionAmount().getValue();
        ItemType item = invoiceLineType.getItem();

        TaxTotalType taxTotal = InvoiceTypeWrapperBuilderHelper.createTaxTotalType(BigDecimal.ZERO, invoiceTypeWrapper.getCurrencyCodeType());
        invoiceLineType.getTaxTotal().add(taxTotal);

        for (TaxCategoryType taxCategory : item.getClassifiedTaxCategory()) {
            String taxSchemeCodeValue = "";
            if (taxCategory != null) {
                TaxSchemeType taxScheme = taxCategory.getTaxScheme();
                if (taxScheme != null) {
                    CodeType code = taxScheme.getTaxTypeCode();
                    if (code != null) {
                        taxSchemeCodeValue = code.getValue();
                    }
                }

                PercentType taxPercent = taxCategory.getPercent();

                TaxAmountType calculatedTaxAmount = new TaxAmountType();
                calculatedTaxAmount.setValue(taxableAmount.multiply(taxPercent.getValue()));
                calculatedTaxAmount.setCurrencyID(invoiceTypeWrapper.getCurrencyCodeType());


                TaxableAmountType taxableAmountType = new TaxableAmountType();
                taxableAmountType.setCurrencyID(invoiceTypeWrapper.getCurrencyCodeType());
                taxableAmountType.setValue(taxableAmount);

                TaxSubtotalType taxSubtotal = new TaxSubtotalType();
                taxSubtotal.setTaxAmount(calculatedTaxAmount);
                taxSubtotal.setPercent(taxCategory.getPercent());
                taxSubtotal.setTaxableAmount(taxableAmountType);
                taxSubtotal.setTaxCategory(taxCategory);
                invoiceLineType.getTaxTotal().get(0).getTaxSubtotal().add(taxSubtotal);
                if (invoiceLineField != null) {
                    TaxCategoryField taxCategoryField = new TaxCategoryField(taxSchemeCodeValue, taxPercent.getValue());
                    invoiceLineField.getItem().getTaxCategories().add(taxCategoryField);
                    invoiceLineField.setTaxTotalAmountValue(taxSubtotal.getTaxAmount().getValue());
                }
            }

        }
        invoiceLineType.getTaxTotal().get(0).getTaxAmount().setValue((BigDecimal)invoiceLineField.getItemTaxTotalAmountField().getValue());
        return invoiceLineType;
    }

    // TODO: Look into this method - and refactor it.

    private void processInvoiceLineType() {

        for (InvoiceLineType invoiceLineType : invoiceTypeWrapper.getInvoiceType().getInvoiceLine()) {
            InvoiceLineField invoiceLineField = new InvoiceLineField();

            // Item Quantity
            InvoicedQuantityType quantity = invoiceLineType.getInvoicedQuantity();
            if (quantity != null) {
                invoiceLineField.getItem().setQuantityValue(quantity.getValue());
                invoiceLineField.getItem().setQuantityUnitCode(quantity.getUnitCode());
            }
            // Item Price
            PriceType price = invoiceLineType.getPrice();
            PriceAmountType priceAmount = null;
            if (price != null) {
                priceAmount = price.getPriceAmount();
                if (priceAmount != null) {
                    invoiceLineField.getItem().setUnitPriceValue(priceAmount.getValue());
                }
            }
            // Item Description
            if (invoiceLineType.getItem() != null) {
                Iterator<DescriptionType> descriptions = invoiceLineType.getItem().getDescription().iterator();
                StringBuffer sb = new StringBuffer();
                while (descriptions.hasNext()) {
                    sb.append(descriptions.next().getValue());
                }
                invoiceLineField.getItem().setDescriptionValue(sb.toString());
            }
            // Calculate and set line ext. amount
            BigDecimal priceAmountValue = null;
            if (priceAmount != null) {
                priceAmountValue = priceAmount.getValue();
            }
            BigDecimal quantityValue = null;
            if (quantity != null) {
                quantityValue = quantity.getValue();
            }
            if (priceAmountValue != null && quantityValue != null) {
                BigDecimal calculatedLineExtensionAmountValue = priceAmountValue.multiply(quantityValue);
                invoiceLineType.setLineExtensionAmount(InvoiceTypeWrapperBuilderHelper.createLineExtensionAmountType(calculatedLineExtensionAmountValue, invoiceTypeWrapper.getCurrencyCodeType()));

                // calculate and set tax total
                invoiceLineType = calculateInvoiceLineTaxSubtotalsBasedOnItemTaxCategories(invoiceLineType, invoiceLineField);
                invoiceLineField.setLegalMonetaryLineExtensionAmountValue(calculatedLineExtensionAmountValue);
                //invoiceLineField.setTaxTotalAmountValue(taxTotal.getTaxAmount().getValue());
                invoiceTypeWrapper.getInvoiceLinesField().add(invoiceLineField);
            }
        }
        invoiceTypeWrapper.getInvoiceType().setLegalMonetaryTotal(createMonetaryTotalType());
    }

    private MonetaryTotalType createMonetaryTotalType() {
        BigDecimal totalLineExtension = BigDecimal.ZERO;
        BigDecimal totalTax = BigDecimal.ZERO;
        for (InvoiceLineType invoiceLineType : invoiceTypeWrapper.getInvoiceType().getInvoiceLine()) {
            totalLineExtension = totalLineExtension.add(invoiceLineType.getLineExtensionAmount().getValue());
            for (TaxTotalType taxTotalType : invoiceLineType.getTaxTotal()) {
                totalTax = totalTax.add(taxTotalType.getTaxAmount().getValue());
            }
        }
        invoiceTypeWrapper.getPrintableFields().add(FieldFactory.getInstance(InvoiceFieldKey.legalMonetaryLineExtensionAmount, totalLineExtension));
        invoiceTypeWrapper.getPrintableFields().add(FieldFactory.getInstance(InvoiceFieldKey.legalMonetaryTaxTotalAmount, totalTax));
        invoiceTypeWrapper.getPrintableFields().add(FieldFactory.getInstance(InvoiceFieldKey.legalMonetaryTaxInclusiveAmount, totalTax.add(totalLineExtension)));
        return InvoiceTypeWrapperBuilderHelper.createMonetaryTotalType(totalLineExtension.add(totalTax), totalLineExtension, invoiceTypeWrapper.getCurrencyCodeType());
    }


}
