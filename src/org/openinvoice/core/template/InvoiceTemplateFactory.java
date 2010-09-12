package org.openinvoice.core.template;

import org.apache.log4j.Logger;
import org.openinvoice.core.io.InvoiceWriterFactory;
import org.openinvoice.core.template.tex.TexInvoiceTemplate;
import org.openinvoice.util.OutputFormat;
import org.openinvoice.util.ResourceManager;
import org.openinvoice.util.UserProfile;

import java.io.IOException;

/**
 * Author: jhe
 * Date: May 9, 2010
 * Time: 10:36:55 PM
 * (C) Copyright 2010 OpenInvoice.org. All rights are reserved.
 */
public class InvoiceTemplateFactory {

    private static Logger logger = Logger.getLogger(InvoiceTemplateFactory.class);
    private static InvoiceTemplate invoiceTemplate;

    public static InvoiceTemplate getInstance(String templateText) {
        invoiceTemplate = getInstance();
        invoiceTemplate.setTemplateText(templateText);
        return invoiceTemplate;
    }

    public static InvoiceTemplate getInstance() {
        if (invoiceTemplate == null) {
            OutputFormat format = UserProfile.getInvoiceOutputFormat();
            switch (format) {
                case TEX:
                    try {
                        invoiceTemplate = new TexInvoiceTemplate();
                    } catch (IOException e) {
                        logger.fatal(e.getMessage());
                        throw new IllegalArgumentException(e.getMessage());
                    }
                    break;
                default:
                    throw new UnsupportedOperationException(ResourceManager.getMessage(InvoiceWriterFactory.class, "unsupportedOutputFormat", new String[]{format.toString()}));
            }
        }
        return invoiceTemplate;
    }
}
