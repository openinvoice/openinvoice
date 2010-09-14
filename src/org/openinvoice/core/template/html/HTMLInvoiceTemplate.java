package org.openinvoice.core.template.html;

import org.openinvoice.core.template.InvoiceTemplate;
import org.openinvoice.util.OutputFormat;

import java.io.IOException;

/**
 * Author: Sina K. Heshmati
 * Created on: Sep 13, 2010
 */

public class HTMLInvoiceTemplate extends InvoiceTemplate {

    public HTMLInvoiceTemplate() throws IOException {
        super();
        getEscapeMap().put("&", "&amp;");
        getEscapeMap().put("<", "&lt;");
        getEscapeMap().put(">", "&gt;");
        getEscapeMap().put("\"", "&quot;");
    }

    @Override
    public OutputFormat getFinalViewableFormat() {
        return OutputFormat.HTML;
    }
}