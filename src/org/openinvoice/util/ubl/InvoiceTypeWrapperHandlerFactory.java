package org.openinvoice.util.ubl;

import org.openinvoice.core.action.ActionHandler;
import org.openinvoice.core.action.FileBasedActionHandler;

/**
 * Author: jhe
 * Date: Aug 19, 2010
 * Time: 11:15:33 PM
 * (C) Copyright 2010 OpenInvoice.org. All rights are reserved.
 */
public class InvoiceTypeWrapperHandlerFactory {

    private static ActionHandler handler;

    public static ActionHandler getInstance() {
        if (handler == null) {
            handler = new FileBasedActionHandler();
        }
        return handler;
    }

}
