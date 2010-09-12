package org.openinvoice.core.field;

import org.openinvoice.util.DateUtil;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Author: jhe
 * Date: Jun 15, 2010
 * Time: 4:24:17 PM
 * (C) Copyright 2010 OpenInvoice.org. All rights are reserved.
 */
public class DateField extends Field {

    public DateField(String baseKey, Object value) {
        super(baseKey, value);
    }

    public DateField(String baseKey) {
        super(baseKey);
    }

    public String formatValue() {
        Object dateValue = getValue();
        if (dateValue instanceof XMLGregorianCalendar) {
            return DateUtil.formatAsDateTime((XMLGregorianCalendar) dateValue);
        } else {
            throw new UnsupportedOperationException();
        }
    }
}
