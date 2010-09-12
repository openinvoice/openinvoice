package org.openinvoice.core.field;

/**
 * Author: jhe
 * Date: Jun 29, 2010
 * Time: 11:04:57 PM
 * (C) Copyright 2010 OpenInvoice.org. All rights are reserved.
 */
public class CustomerPartyField extends PartyField {

    public CustomerPartyField() {
        super(InvoiceFieldKey.customerParty.key());
        super.setBaseKeyPrefix(InvoiceFieldKey.customerParty.key());
    }

}
