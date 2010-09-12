package org.openinvoice.core.field;

import java.text.NumberFormat;

/**
 * Author: jhe
 * Date: Jun 16, 2010
 * Time: 9:37:23 PM
 * (C) Copyright 2010 OpenInvoice.org. All rights are reserved.
 */
public class NumberField extends Field {

    public NumberField(String baseKey, Object value) {
        super(baseKey, value);
    }

    public NumberField(String baseKey) {
        super(baseKey);
    }

    public String formatValue() {
        NumberFormat nf = NumberFormat.getInstance(super.getLocale());
        return nf.format(getValue());
    }

}
