package org.openinvoice.repository;

import org.openinvoice.core.io.InvoiceWriterFactory;
import org.openinvoice.repository.provider.fs.FileBasedInvoiceRepository;
import org.openinvoice.util.ConfigurationManager;
import org.openinvoice.util.ResourceManager;
import org.openinvoice.util.StorageMedia;

import java.io.IOException;

/**
 * Author: jhe
 * Date: May 9, 2010
 * Time: 10:36:55 PM
 * (C) Copyright 2010 OpenInvoice.org. All rights are reserved.
 */
public class InvoiceRepositoryFactory {

    public static InvoiceRepository getInstance() throws IOException {

        StorageMedia storageMedia = ConfigurationManager.getInvoiceRepositoryStorageMedia();
        InvoiceRepository repository;

        switch (storageMedia) {
            case FILE_SYSTEM:
                repository = new FileBasedInvoiceRepository();
                break;
            case DATABASE:
                throw new UnsupportedOperationException(ResourceManager.getMessage(InvoiceWriterFactory.class, "unsupportedOutputFormat", new String[]{storageMedia.toString()}));
            default:
                throw new UnsupportedOperationException(ResourceManager.getMessage(InvoiceWriterFactory.class, "unsupportedOutputFormat", new String[]{storageMedia.toString()}));
        }
        return repository;
    }
}