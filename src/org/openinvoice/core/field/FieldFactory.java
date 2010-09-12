package org.openinvoice.core.field;

import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IssueDateType;
import org.openinvoice.util.UserProfile;

/**
 * Author: jhe
 * Date: Jun 15, 2010
 * Time: 4:31:49 PM
 * (C) Copyright 2010 OpenInvoice.org. All rights are reserved.
 */
public class FieldFactory {

    private static CurrencyField createCurrencyField(String key, Object value) {
        CurrencyField cf = new CurrencyField(key, value);
        if (UserProfile.isInvoiceOutputCurrencyFormatWithSymbol()) {
            cf.setFormatWithCurrencySign(true);
        }
        return cf;
    }

    public static Field getInstance(InvoiceFieldKey key, Object o) {
        Field f;
        switch (key) {
            case id:
                f = new TextField(InvoiceFieldKey.id.key(), ((IDType) o).getValue());
                break;
            case orderId:
                f = new TextField(InvoiceFieldKey.orderId.key(), ((IDType) o).getValue());
                break;
            case issueDate:
                f = new DateField(InvoiceFieldKey.issueDate.key(), ((IssueDateType) o).getValue());
                break;
            case legalMonetaryTaxInclusiveAmount:
                f = createCurrencyField(InvoiceFieldKey.legalMonetaryTaxInclusiveAmount.key(), o);
                break;
            case legalMonetaryLineExtensionAmount:
                f = createCurrencyField(InvoiceFieldKey.legalMonetaryLineExtensionAmount.key(), o);
                break;
            case legalMonetaryTaxTotalAmount:
                f = createCurrencyField(InvoiceFieldKey.legalMonetaryTaxTotalAmount.key(), o);
                break;
            case currencySymbol:
                f = new TextField(InvoiceFieldKey.currencySymbol.key(), o);
                break;
            case currencyCode:
                f = new TextField(InvoiceFieldKey.currencyCode.key(), o);
                break;
            case customerParty:
                f = new TextField(InvoiceFieldKey.customerParty.key(), o);
                break;
            case supplierParty:
                f = new TextField(InvoiceFieldKey.supplierParty.key(), o);
                break;
            case itemQuantity:
                f = new NumberField(InvoiceFieldKey.itemQuantity.key(), o);
                break;
            case itemQuantityUnitCode:
                f = new TextField(InvoiceFieldKey.itemQuantityUnitCode.key(), o);
                break;
            case itemDescription:
                f = new TextField(InvoiceFieldKey.itemDescription.key(), o);
                break;
            case itemTaxTotalAmount:
                f = createCurrencyField(InvoiceFieldKey.itemTaxTotalAmount.key(), o);
                break;
            case itemUnitPrice:
                f = createCurrencyField(InvoiceFieldKey.itemUnitPrice.key(), o);
                break;
            case itemLineExtensionAmount:
                f = createCurrencyField(InvoiceFieldKey.itemLineExtensionAmount.key(), o);
                break;
            case taxSchemeCode:
                f = new TextField(InvoiceFieldKey.taxSchemeCode.key(), o);
                break;
            case taxPercentage:
                f = new PercentField(InvoiceFieldKey.taxPercentage.key(), o);
                break;
            default:
                throw new UnsupportedOperationException(key.name());
        }
        return f;
    }

    public static TextField getTextInstance(InvoiceFieldKey key, Object o) {
        return new TextField(key.key(), o);
    }
}
