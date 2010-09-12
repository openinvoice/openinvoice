package org.openinvoice.core.field;

import java.text.NumberFormat;

/**
 * Author: jhe
 * Date: Jun 16, 2010
 * Time: 9:37:23 PM
 * (C) Copyright 2010 OpenInvoice.org. All rights are reserved.
 */
public class PercentField extends Field {

    public PercentField(String baseKey, Object value) {
        super(baseKey, value);
    }

    public PercentField(String baseKey) {
        super(baseKey);
    }

    public String formatValue() {
        NumberFormat nf = NumberFormat.getPercentInstance(super.getLocale());
        return nf.format(getValue());
    }

}