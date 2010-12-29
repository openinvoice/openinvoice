package org.openinvoice.core.template.html;

import org.openinvoice.core.template.InvoiceTemplate;
import org.openinvoice.util.OutputFormat;

import java.io.IOException;

/**
 * Author: Sina K. Heshmati
 * Created on: Sep 13, 2010
 */

public class HTMLInvoiceTemplate extends InvoiceTemplate {

  private static final String START_COMMENT_TAG = "<!--";
  private static final String END_COMMENT_TAG = "-->";

  public HTMLInvoiceTemplate() throws IOException {
    super();
    getEscapeMap().put("&", "&amp;");
    getEscapeMap().put("<", "&lt;");
    getEscapeMap().put(">", "&gt;");
    getEscapeMap().put("\"", "&quot;");
  }

  public String getStartCommentTag() {
    return START_COMMENT_TAG;
  }

  public String getEndCommentTag() {
    return END_COMMENT_TAG;
  }

  @Override
  public OutputFormat getFinalViewableFormat() {
    return OutputFormat.HTML;
  }
}