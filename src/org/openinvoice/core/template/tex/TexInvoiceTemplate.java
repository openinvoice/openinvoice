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

    private Map<String, String> escapeMap = new HashMap<String, String>();

    public TexInvoiceTemplate() throws IOException {
        super();
        escapeMap.put("%", "\\\\%");
        escapeMap.put("€", "\\eurologo");
        escapeMap.put("$", "\\$");
        escapeMap.put("£", "\\\\pounds");
    }

    @Override
    public OutputFormat getFinalViewableFormat() {
        return OutputFormat.PDF;
    }

    public String escape(String template) {
        Iterator keys = escapeMap.keySet().iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            if (template.indexOf(key) >= 0) {
                String value = escapeMap.get(key);
                template = template.replaceAll(key, value);
            }
        }
        return template;
    }
}
