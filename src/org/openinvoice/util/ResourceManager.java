package org.openinvoice.util;

import org.openinvoice.core.InvoiceContextFactory;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Author: jhe
 * Date: May 14, 2010
 * Time: 1:14:54 PM
 * (C) Copyright 2010 OpenInvoice.org. All rights are reserved.
 */
public class ResourceManager {


    static Locale locale = InvoiceContextFactory.getInstance().getLocale();

    public static String getLabel(String key) {
        java.util.ResourceBundle labelResources =
                java.util.ResourceBundle.getBundle("invoice-labels", locale == null ? ConfigurationManager.DEFAULT_LOCALE : locale);

        return labelResources.getString(key);
    }

    public static String getLabel(Class c, String key) {
        return getLabel(c.getName() + "." + key);
    }

    public static ResourceBundle getMessagesResourceBundle() {
        return
                java.util.ResourceBundle.getBundle("messages", locale == null ? ConfigurationManager.DEFAULT_LOCALE : locale);
    }

    public static String getMessage(String key) {
        java.util.ResourceBundle messagesResources = getMessagesResourceBundle();
        String message = messagesResources.getString(key);
        return message != null ? message : "";
    }

    public static String getMessage(Class c, String key) {
        return getMessage(c.getName() + "." + key);
    }

    public static String getMessage(Class c, String key, Object[] arguments) {
        MessageFormat mf = new MessageFormat(getMessage(c, key));
        return mf.format(arguments);
    }
}
