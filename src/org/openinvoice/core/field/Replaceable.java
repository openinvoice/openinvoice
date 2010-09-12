package org.openinvoice.core.field;

import org.openinvoice.core.template.InvoiceTemplate;

/**
 * Author: jhe
 * Date: Jun 19, 2010
 * Time: 7:36:59 PM
 * (C) Copyright 2010 OpenInvoice.org. All rights are reserved.
 */
public interface Replaceable {
    InvoiceTemplate replace(InvoiceTemplate placeHolderText);
}
