package org.openinvoice.core.field;

import java.math.BigDecimal;

/**
 * Author: jhe
 * Date: Jun 21, 2010
 * Time: 5:31:09 PM
 * (C) Copyright 2010 OpenInvoice.org. All rights are reserved.
 */
public class TaxCategoryField extends FieldCollection {

    private PercentField percentField;
    private TextField taxSchemeCode;

    public TaxCategoryField(String taxSchemeCode, BigDecimal percentage) {
        super(InvoiceFieldKey.taxCategory.key());
        this.percentField = (PercentField) FieldFactory.getInstance(InvoiceFieldKey.taxPercentage, percentage);
        this.taxSchemeCode = (TextField) FieldFactory.getInstance(InvoiceFieldKey.taxSchemeCode, taxSchemeCode);
    }

    public PercentField getPercentField() {
        return percentField;
    }

    public TextField getTaxSchemeCode() {
        return taxSchemeCode;
    }
}

