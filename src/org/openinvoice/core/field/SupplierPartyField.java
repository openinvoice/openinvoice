package org.openinvoice.core.field;

/**
 * Author: jhe
 * Date: Jun 29, 2010
 * Time: 11:04:57 PM
 * (C) Copyright 2010 OpenInvoice.org. All rights are reserved.
 */
public class SupplierPartyField extends PartyField {

    public SupplierPartyField() {
        super(InvoiceFieldKey.supplierParty.key());
        super.setBaseKeyPrefix(InvoiceFieldKey.supplierParty.key());
    }

}