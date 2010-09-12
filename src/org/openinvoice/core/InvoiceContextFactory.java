package org.openinvoice.core;

import org.openinvoice.core.template.InvoiceTemplateFactory;
import org.openinvoice.util.StorageMedia;
import org.openinvoice.util.UserProfile;

import java.util.Locale;

/**
 * Author: jhe
 * Date: May 13, 2010
 * Time: 11:11:22 AM
 * (C) Copyright 2010 OpenInvoice.org. All rights are reserved.
 */
public class InvoiceContextFactory {

    private static InvoiceContext invoiceContext = null;

    public static InvoiceContext getInstance() {
        return getInstance(null);
    }

    public static InvoiceContext getInstance(Locale locale) {

        if (invoiceContext == null) {
            invoiceContext = new InvoiceContext();
        }
        initialize(invoiceContext, locale);
        return invoiceContext;
    }

    private static void initialize(InvoiceContext context, Locale locale) {
        initLocale(context, locale);
        initInvoiceTemplate(context);
        initUserName(context);
        initInvoiceOutputFileStorageMedia(context);
    }

    private static void initUserName(InvoiceContext context) {
        context.setUserName(UserProfile.USER_NAME);
    }

    private static void initLocale(InvoiceContext context, Locale l) {
        Locale locale = l;
        String countryCode = UserProfile.getCountryCode();
        String langCode = UserProfile.getLanguageCode();
        if (countryCode != null && langCode != null) {
            locale = new Locale(langCode, countryCode);
        }
        context.setLocale(locale);
    }

    public static void initInvoiceTemplate(InvoiceContext context) {

        context.setTemplate(InvoiceTemplateFactory.getInstance());
    }

    public static void initInvoiceOutputFileStorageMedia(InvoiceContext context) {
        StorageMedia sm = UserProfile.getInvoiceOutputFileStorageMedia();
        context.setOutputFileStorageMedia(sm);
    }
}
