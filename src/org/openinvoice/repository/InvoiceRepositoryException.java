package org.openinvoice.repository;

/**
 * Author: jhe
 * Date: May 15, 2010
 * Time: 11:29:37 PM
 * (C) Copyright 2010 OpenInvoice.org. All rights are reserved.
 */
public class InvoiceRepositoryException extends Exception {

    public InvoiceRepositoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvoiceRepositoryException(Throwable cause) {
        super(cause);
    }

    public InvoiceRepositoryException(String message) {
        super(message);
    }
}
