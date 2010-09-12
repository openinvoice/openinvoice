package org.openinvoice.core.action;

/**
 * Author: jhe
 * Date: Aug 19, 2010
 * Time: 10:42:18 PM
 * (C) Copyright 2010 OpenInvoice.org. All rights are reserved.
 */
public interface ActionHandler {
    void handle(Action action) throws Exception;
}
