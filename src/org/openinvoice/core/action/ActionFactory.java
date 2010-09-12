package org.openinvoice.core.action;

/**
 * Author: jhe
 * Date: Aug 31, 2010
 * Time: 12:41:11 AM
 * (C) Copyright 2010 OpenInvoice.org. All rights are reserved.
 */
public class ActionFactory {

    private static Action action = null;

    public static Action getInstance(String actionName, String[] arguments) {

        if (actionName.equals(Action.RENDER_INV_ACTION)) {
            action = new RenderInvoiceAction();
        } else if (actionName.equals(Action.VIEW_INV_ACTION)) {
            action = new ViewInvoiceAction();
        } else if (actionName.equals(Action.CREATE_INV_ACTION)) {
            action = new CreateInvoiceAction();
        } else if (actionName.equals(Action.DELETE_INV_ACTION)) {
            action = new DeleteInvoiceAction();
        } else {
            throw new IllegalArgumentException(actionName);
        }
        if (arguments != null) {
            action.setArguments(arguments);
        }
        return action;
    }
}
