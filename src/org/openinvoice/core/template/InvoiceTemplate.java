package org.openinvoice.core.template;

import org.openinvoice.core.field.InvoiceFieldKey;
import org.openinvoice.util.OutputFormat;
import org.openinvoice.util.UserProfile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Author: jhe
 * Date: Apr 29, 2010
 * Time: 9:34:31 PM
 * (C) Copyright 2010 OpenInvoice.org. All rights are reserved.
 */
public class InvoiceTemplate {

  private String templateText;

  public Map<String, String> getEscapeMap() {
    return escapeMap;
  }

  private Map<String, String> escapeMap = new HashMap<String, String>();

  public InvoiceTemplate(String templateText) {
    this.templateText = templateText;
  }

  public InvoiceTemplate() throws IOException {
    File f = UserProfile.getInvoiceTemplateFile();
    if (f != null) {
      this.templateText = new TemplateFile(f).toString();
    }
  }

  public String getTemplateText() {
    return templateText;
  }

  public OutputFormat getFinalViewableFormat() {
    return OutputFormat.UNKNOWN;
  }

  public void setTemplateText(String templateText) {
    this.templateText = templateText;
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

  private String removeLeftOverKeys(String template) {
    for (InvoiceFieldKey key : InvoiceFieldKey.values()) {
      template = key.removeAssociatedKeysFromTemplate(template);
    }
    return template;
  }

  public String removeLeftOverKeys() {
    templateText = removeLeftOverKeys(templateText);
    templateText = templateText.replaceAll(",\\s*,", "");  // remove left-over commas
    templateText = templateText.replaceAll("\\s+:\\s+", ""); // remove left-over colons
    return templateText;
  }

  @Override
  public String toString() {
    return templateText;
  }

  /**
   * Given a file, it reads (as byte stream) the file content into a string to be used
   * as a <em>template text</em>. The template text contains place holders to be
   * replaced with dynamic values.
   * <p/>
   * Author: jhe
   * Date: Apr 29, 2010
   * Time: 4:15:17 PM
   * (C) Copyright 2010 OpenInvoice.org. All rights are reserved.
   */
  public static class TemplateFile {

    private String content;

    public TemplateFile(File f) throws IOException {
      this(new FileInputStream(f));
    }

    public TemplateFile(InputStream is) throws IOException {
      StringBuffer sb = new StringBuffer();
      byte[] buffer = new byte[is.available()];
      while (is.read(buffer) != -1) {
        sb.append(new String(buffer));
      }
      this.content = sb.toString();
    }

    public String toString() {
      return content;
    }
  }
}