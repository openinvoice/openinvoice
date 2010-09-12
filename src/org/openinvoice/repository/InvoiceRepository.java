package org.openinvoice.repository;

import org.openinvoice.util.ubl.InvoiceTypeWrapper;

/**
 * Author: jhe
 * Date: May 15, 2010
 * Time: 10:50:51 PM
 * (C) Copyright 2010 OpenInvoice.org. All rights are reserved.
 */
public interface InvoiceRepository {
    void create(InvoiceTypeWrapper iw) throws InvoiceRepositoryException;

    InvoiceTypeWrapper read(String invoiceId) throws InvoiceRepositoryException;

    boolean delete(String invoiceId) throws InvoiceRepositoryException;
}
