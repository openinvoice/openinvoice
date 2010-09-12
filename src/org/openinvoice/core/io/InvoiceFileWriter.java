package org.openinvoice.core.io;

import org.apache.log4j.Logger;
import org.openinvoice.util.ResourceManager;
import org.openinvoice.util.UserProfile;
import org.openinvoice.util.ubl.InvoiceTypeWrapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Author: jhe
 * Date: Apr 30, 2010
 * Time: 4:47:33 PM
 * (C) Copyright 2010 OpenInvoice.org. All rights are reserved.
 */
public class InvoiceFileWriter extends InvoiceWriter {

    private Logger logger = Logger.getLogger(InvoiceFileWriter.class);

    public InvoiceFileWriter(InvoiceTypeWrapper invoiceTypeWrapper) throws IOException {
        super(invoiceTypeWrapper);
    }

    public void write(FileOutputStream os) throws IOException {
        super.write(os);
    }

    public void write() throws IOException {
        File outputFile = new File(UserProfile.getInvoiceOutputDir(), getInvoiceTypeWrapper().getInvoiceOutputFileName());
        if (outputFile.exists()) {
            String message = ResourceManager.getMessage(InvoiceFileWriter.class, "duplicateFile", new String[]{outputFile.getName(), UserProfile.getInvoiceOutputDir().getPath()});
            IOException ioe = new IOException(message);
            throw ioe;
        } else {
            super.write(new FileOutputStream(outputFile));
            if (logger.isInfoEnabled()) {
                logger.info(ResourceManager.getMessage(InvoiceFileWriter.class, "created", new String[]{outputFile.getName(), UserProfile.getInvoiceOutputDir().getPath()}));
            }
        }
    }
}
