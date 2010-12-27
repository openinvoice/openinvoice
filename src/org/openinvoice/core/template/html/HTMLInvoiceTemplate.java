package org.openinvoice.core.template.html;

import org.openinvoice.core.template.InvoiceTemplate;
import org.openinvoice.util.OrderedPair;
import org.openinvoice.util.OutputFormat;

import java.io.IOException;

/**
 * Author: Sina K. Heshmati
 * Created on: Sep 13, 2010
 */

public class HTMLInvoiceTemplate extends InvoiceTemplate {

  private static final String START_COMMENT_BLOCK = "<!--";
  private static final String END_COMMENT_BLOCK = "-->";
  private static final OrderedPair<String, String> ITEM_DELIMITER_WRAPPER;

  static {
    ITEM_DELIMITER_WRAPPER = new OrderedPair(START_COMMENT_BLOCK, END_COMMENT_BLOCK);
  }

  public HTMLInvoiceTemplate() throws IOException {
    super();
    getEscapeMap().put("&", "&amp;");
    getEscapeMap().put("<", "&lt;");
    getEscapeMap().put(">", "&gt;");
    getEscapeMap().put("\"", "&quot;");
  }

  @Override
  public OrderedPair<String, String> getItemDelimiterWrapper() {
    return ITEM_DELIMITER_WRAPPER;
  }

  @Override
  public OutputFormat getFinalViewableFormat() {
    return OutputFormat.HTML;
  }
}