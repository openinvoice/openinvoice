package org.openinvoice.core.template.tex;

import org.openinvoice.core.template.InvoiceTemplate;
import org.openinvoice.util.OutputFormat;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Author: jhe
 * Date: May 9, 2010
 * Time: 10:28:29 PM
 * (C) Copyright 2010 OpenInvoice.org. All rights are reserved.
 */
public class TexInvoiceTemplate extends InvoiceTemplate {

    public TexInvoiceTemplate() throws IOException {
        super();
        getEscapeMap().put("%", "\\\\%");
        getEscapeMap().put("€", "\\eurologo");
        getEscapeMap().put("$", "\\$");
        getEscapeMap().put("£", "\\\\pounds");
    }

    @Override
    public OutputFormat getFinalViewableFormat() {
        return OutputFormat.PDF;
    }
}