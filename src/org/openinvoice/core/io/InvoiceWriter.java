package org.openinvoice.core.io;

import org.openinvoice.core.InvoiceContextFactory;
import org.openinvoice.core.template.InvoiceTemplate;
import org.openinvoice.util.ubl.InvoiceTypeWrapper;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Author: jhe
 * Date: Apr 29, 2010
 * Time: 4:21:24 PM
 * (C) Copyright 2010 OpenInvoice.org. All rights are reserved.
 */
public class InvoiceWriter {

    private String output;
    private InvoiceTypeWrapper invoiceTypeWrapper;
    private InvoiceTemplate template;

    public InvoiceWriter(InvoiceTypeWrapper invoiceTypeWrapper) {
        this.invoiceTypeWrapper = invoiceTypeWrapper;
        this.template = InvoiceContextFactory.getInstance().getTemplate();
    }

    public void write() throws IOException {
        this.write(System.out);
    }

    public void write(OutputStream os) throws IOException {
        os.write(output.getBytes());
        os.close();
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public void setTemplate(InvoiceTemplate template) {
        this.template = template;
    }

    public void setInvoiceTypeWrapper(InvoiceTypeWrapper invoiceTypeWrapper) {
        this.invoiceTypeWrapper = invoiceTypeWrapper;
    }

    public InvoiceTypeWrapper getInvoiceTypeWrapper() {
        return invoiceTypeWrapper;
    }

    public InvoiceTemplate getTemplate() {
        return template;
    }
}