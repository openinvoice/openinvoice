package org.openinvoice.core;

import org.openinvoice.core.template.InvoiceTemplate;
import org.openinvoice.util.OutputFormat;
import org.openinvoice.util.StorageMedia;

import java.io.File;
import java.util.Currency;
import java.util.Locale;

/**
 * Author: jhe
 * Date: May 14, 2010
 * Time: 9:34:09 AM
 * (C) Copyright 2010 OpenInvoice.org. All rights are reserved.
 */
public class InvoiceContext {

    private Locale locale;
    private File templateFile;
    private InvoiceTemplate template;
    private StorageMedia outputFileStorageMedia;
    private OutputFormat outputFormat;
    private String userName;

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Currency getCurrency() {
        return java.util.Currency.getInstance(getLocale());
    }

    public String getCurrencyCode() {
        return getCurrency().getCurrencyCode();
    }

    public String getCountryCode() {
        return getLocale().getCountry();
    }

    public String getLanguageCode() {
        return getLocale().getLanguage();
    }

    public File getTemplateFile() {
        return templateFile;
    }

    public void setTemplateFile(File templateFile) {
        this.templateFile = templateFile;
    }

    public InvoiceTemplate getTemplate() {
        return template;
    }

    public void setTemplate(InvoiceTemplate template) {
        this.template = template;
    }

    public StorageMedia getOutputFileStorageMedia() {
        return outputFileStorageMedia;
    }

    public void setOutputFileStorageMedia(StorageMedia outputFileStorageMedia) {
        this.outputFileStorageMedia = outputFileStorageMedia;
    }

    public OutputFormat getOutputFormat() {
        return outputFormat;
    }

    public void setOutputFormat(OutputFormat outputFormat) {
        this.outputFormat = outputFormat;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
