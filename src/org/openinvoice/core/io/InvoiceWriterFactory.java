package org.openinvoice.core.io;


import org.openinvoice.core.InvoiceContextFactory;
import org.openinvoice.util.ResourceManager;
import org.openinvoice.util.StorageMedia;
import org.openinvoice.util.ubl.InvoiceTypeWrapper;

import java.io.IOException;

/**
 * Author: jhe
 * Date: Apr 29, 2010
 * Time: 10:29:29 PM
 * (C) Copyright 2010 OpenInvoice.org. All rights are reserved.
 */
public class InvoiceWriterFactory {
    public static InvoiceWriter getInstance(InvoiceTypeWrapper invoiceTypeWrapper) throws IOException {
        StorageMedia media = InvoiceContextFactory.getInstance().getOutputFileStorageMedia();
        InvoiceWriter writer;
        switch (media) {
            case FILE_SYSTEM:
                writer = new InvoiceFileWriter(invoiceTypeWrapper);
                break;
            default:
                throw new UnsupportedOperationException(ResourceManager.getMessage(InvoiceWriterFactory.class, "unsupportedOutputMedia", new String[]{media.toString()}));
        }
        return writer;
    }

}
