package org.openinvoice.core.field;

import org.openinvoice.core.InvoiceContextFactory;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Author: jhe
 * Date: Jun 15, 2010
 * Time: 3:50:45 PM
 * (C) Copyright 2010 OpenInvoice.org. All rights are reserved.
 */
public class CurrencyField extends Field {

    private boolean formatWithCurrencySign;

    public CurrencyField(String baseKey, Object value) {
        super(baseKey, value);
    }

    public boolean isFormatWithCurrencySign() {
        return formatWithCurrencySign;
    }

    public void setFormatWithCurrencySign(boolean formatWithCurrencySign) {
        this.formatWithCurrencySign = formatWithCurrencySign;
    }

    public CurrencyField(String baseKey) {
        super(baseKey, BigDecimal.ZERO);
    }

    public BigDecimal add(BigDecimal amount) {
        return ((BigDecimal) getValue()).add(amount);
    }

    public BigDecimal subtract(BigDecimal amount) {
        return ((BigDecimal) getValue()).subtract(amount);
    }

    public BigDecimal multiply(BigDecimal amount) {
        return ((BigDecimal) getValue()).multiply(amount);
    }

    public BigDecimal divide(BigDecimal amount) {
        return ((BigDecimal) getValue()).divide(amount);
    }

    public java.util.Currency getCurrency() {
        return java.util.Currency.getInstance(getLocale());
    }

    public static String getCurrencySymbol() {
        Locale l = InvoiceContextFactory.getInstance().getLocale();
        return java.util.Currency.getInstance(l).getSymbol(l);
    }

    public static String getCurrencyCode() {
        return java.util.Currency.getInstance(InvoiceContextFactory.getInstance().getLocale()).getCurrencyCode();
    }

    public String formatValue() {
        return format((BigDecimal) getValue());
    }

    private String format(BigDecimal amount) {
        DecimalFormat df = (DecimalFormat) NumberFormat.getCurrencyInstance(super.getLocale());
        if (!isFormatWithCurrencySign()) {
            df.applyPattern("#,##0.00;(#,##0.00)"); // drop the currency symbol
        }
        return df.format(amount);
    }


}