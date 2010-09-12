package org.openinvoice.core.field;

import org.openinvoice.core.template.InvoiceTemplate;
import org.openinvoice.core.template.InvoiceTemplateFactory;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 * Author: jhe
 * Date: Jun 16, 2010
 * Time: 9:24:11 PM
 * (C) Copyright 2010 OpenInvoice.org. All rights are reserved.
 */
public class InvoiceLineField implements Replaceable {

    private CurrencyField itemLineExtensionAmountField;
    private CurrencyField itemTaxInclusiveAmountField;
    private CurrencyField itemTaxTotalAmountField;
    private Item item = new Item();

    public void setLegalMonetaryLineExtensionAmountValue(BigDecimal value) {
        this.itemLineExtensionAmountField =
                (CurrencyField) FieldFactory.getInstance(InvoiceFieldKey.itemLineExtensionAmount, value);
    }

    public void setTaxTotalAmountValue(BigDecimal value) {
        this.itemTaxTotalAmountField =
                (CurrencyField) FieldFactory.getInstance(InvoiceFieldKey.itemTaxTotalAmount, value);
    }

    public void setLegalMonetaryTaxInclusiveAmountValue(BigDecimal value) {
        this.itemTaxInclusiveAmountField =
                (CurrencyField) FieldFactory.getInstance(InvoiceFieldKey.itemTaxTotalAmount, value);
    }

    public CurrencyField getItemLineExtensionAmountField() {
        return itemLineExtensionAmountField;
    }

    public CurrencyField getItemTaxInclusiveAmountField() {
        return itemTaxInclusiveAmountField;
    }

    public CurrencyField getItemTaxTotalAmountField() {
        return itemTaxTotalAmountField;
    }

    public InvoiceLineField() {
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public InvoiceTemplate replace(InvoiceTemplate template) {
        String s = template.toString();
        if (itemLineExtensionAmountField != null) {
            s = itemLineExtensionAmountField.replace(template).toString();
        }
        if (itemTaxInclusiveAmountField != null) {
            s = itemTaxInclusiveAmountField.replace(InvoiceTemplateFactory.getInstance(s)).toString();
        }
        if (itemTaxTotalAmountField != null) {
            s = itemTaxTotalAmountField.replace(InvoiceTemplateFactory.getInstance(s)).toString();
        }
        return item.replace(InvoiceTemplateFactory.getInstance(s));
    }

    public class Item extends FieldCollection {


        private List<TaxCategoryField> taxCategories = new LinkedList<TaxCategoryField>();

        public Item() {
            super(InvoiceFieldKey.invoiceLineItem.key());
        }

        public List getTaxCategories() {
            return taxCategories;
        }

        public CurrencyField getLineExtensionAmount() {
            return (CurrencyField) FieldFactory.getInstance(InvoiceFieldKey.itemLineExtensionAmount,
                    getUnitPriceValue().multiply(getQuantityValue()));
        }

        public void setQuantityValue(BigDecimal value) {
            super.add(FieldFactory.getInstance(InvoiceFieldKey.itemQuantity, value));
        }

        public void setQuantityUnitCode(String value) {
            super.add(FieldFactory.getInstance(InvoiceFieldKey.itemQuantityUnitCode, value));
        }

        public void setUnitPriceValue(BigDecimal value) {
            super.add(FieldFactory.getInstance(InvoiceFieldKey.itemUnitPrice, value));
        }

        public void setDescriptionValue(String value) {
            super.add(FieldFactory.getInstance(InvoiceFieldKey.itemDescription, value));
        }

        private BigDecimal getQuantityValue() {
            Field quantity = find(InvoiceFieldKey.itemQuantity);
            return quantity != null ? (BigDecimal) quantity.getValue() : null;
        }

        private BigDecimal getUnitPriceValue() {
            Field price = find(InvoiceFieldKey.itemUnitPrice);
            return price != null ? (BigDecimal) price.getValue() : null;
        }

        public InvoiceTemplate replace(InvoiceTemplate template) {
            for (TaxCategoryField tcf : taxCategories) {
                PercentField pf = tcf.getPercentField();
                TextField tf = tcf.getTaxSchemeCode();
                template = pf.replace(template);
                template = tf.replace(template);
            }
            return super.replace(template);
        }

        private Field find(InvoiceFieldKey key) {
            for (Object o : this) {
                Field field = (Field) o;
                if (field.getBaseKey().equals(key.name())) {
                    return field;
                }
            }
            return null;
        }

    }
}
