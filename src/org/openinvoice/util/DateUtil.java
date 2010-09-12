package org.openinvoice.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.openinvoice.core.InvoiceContextFactory;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Author: jhe
 * Date: May 31, 2010
 * Time: 5:48:48 PM
 * (C) Copyright 2010 OpenInvoice.org. All rights are reserved.
 */
public final class DateUtil {

    public final static String DEFAULT_DATE_PATTERN = "dd-MM-yyyy";
    public final static String DEFAULT_DATE_TIME_PATTERN = "dd-MM-yyyy 'Time' hh:mm:ss";

    public static DateTime toDateTime(XMLGregorianCalendar cal) {
        return new DateTime().withDate(cal.getYear(), cal.getMonth(), cal.getDay());
    }

    public static String getISODateTime() {
        return new DateTime().toDateTimeISO().toString(DateUtil.DEFAULT_DATE_TIME_PATTERN);
    }

    public static String formatDateTime(DateTime dt) {
        return formatDateTime(dt, DEFAULT_DATE_PATTERN);
    }

    public static String formatDateTime(DateTime dt, String pattern) {
        return dt.toString(DateTimeFormat.forPattern(pattern).withLocale(InvoiceContextFactory.getInstance().getLocale()));
    }

    public static String formatAsDateTime(XMLGregorianCalendar cal) {
        return formatDateTime(toDateTime(cal));
    }

}
