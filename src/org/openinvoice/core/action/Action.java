package org.openinvoice.core.action;

import java.util.Arrays;

/**
 * Author: jhe
 * Date: Aug 22, 2010
 * Time: 2:17:35 PM
 * (C) Copyright 2010 OpenInvoice.org. All rights are reserved.
 */
public abstract class Action {

    public final static String RENDER_INV_ACTION = "--render-invoice";
    public final static String RENDER_INV_ACTION_ALIAS = "-r";
    public final static String VIEW_INV_ACTION = "--view-invoice";
    public final static String VIEW_INV_ACTION_ALIAS = "-v";
    public final static String CREATE_INV_ACTION = "--create-invoice";
    public final static String CREATE_INV_ACTION_ALIAS = "-c";
    public final static String DELETE_INV_ACTION = "--delete-invoice";
    public final static String DELETE_INV_ACTION_ALIAS = "-d";


    private String name;
    private String alias;
    private String[] arguments;

    public Action(String name, String alias) {
        this.name = name;
        this.alias = alias;
    }

    public String[] getArguments() {
        return arguments;
    }

    public void setArguments(String[] arguments) {
        this.arguments = arguments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public boolean isPreviousActionPerformed() {
        return false;
    }

    @Override
    public String toString() {
        return
                "'" + name + '\'' +
                ", arguments = " + (arguments == null ? null : Arrays.asList(arguments));
    }
}
