package org.openinvoice.core.field;

import org.apache.log4j.Logger;
import org.openinvoice.core.InvoiceContextFactory;
import org.openinvoice.core.template.InvoiceTemplate;
import org.openinvoice.core.template.InvoiceTemplateFactory;
import org.openinvoice.util.ResourceManager;

import java.util.Locale;

/**
 * Author: jhe
 * Date: Jun 15, 2010
 * Time: 3:45:02 PM
 * (C) Copyright 2010 OpenInvoice.org. All rights are reserved.
 */
public abstract class Field implements Replaceable {

    private static final Logger logger = Logger.getLogger(Field.class);

    private Object value;
    private Locale locale = InvoiceContextFactory.getInstance().getLocale();
    private InvoiceFieldKey fieldKey;

    public Field(String baseKey, Object value) {
        this.fieldKey = InvoiceFieldKey.valueOf(baseKey);
        this.value = value;
    }

    public Field(String baseKey) {
        this(baseKey, null);
    }

    public Locale getLocale() {
        return locale;
    }

    public String getName() {
        return ResourceManager.getLabel(fieldKey.key());
    }

    public String getNameKey() {
        return fieldKey.getNameKey();
    }

    public String getValueKey() {
        return fieldKey.getValueKey();
    }

    public String formatValue() {
        return value.toString();
    }

    public String getBaseKey() {
        return fieldKey.key();
    }

    public Object getValue() {
        return value;
    }

    public String getBaseKeyPrefix() {
        return fieldKey.getPrefix();
    }

    public void setBaseKeyPrefix(String baseKeyPrefix) {
        fieldKey.setPrefix(baseKeyPrefix);
    }

    public InvoiceTemplate replace(InvoiceTemplate template) {
        String s = template.toString();

        String nameKey = getNameKey();
        String valueKey = getValueKey();

        int nameKeyIndex = s.indexOf(nameKey);
        int valueKeyIndex = s.indexOf(valueKey);
        if (nameKeyIndex >= 0) {
            String escapedName = template.escape(getName());
            s = s.replace(nameKey, escapedName);
            if (logger.isDebugEnabled()) {
                logger.debug("replacing '" + nameKey + "' with '" + escapedName + "'");
            }
        }
        if (valueKeyIndex >= 0) {
            if (value != null) {
                String escapedValue = template.escape(formatValue());
                s = s.replace(valueKey, escapedValue);
                if (logger.isDebugEnabled()) {
                    logger.debug("replacing '" + valueKey + "' with '" + escapedValue + "'");
                }
            }
        }
        return InvoiceTemplateFactory.getInstance(s);
    }
}