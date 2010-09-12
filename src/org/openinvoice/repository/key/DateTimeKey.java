package org.openinvoice.repository.key;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.openinvoice.core.field.InvoiceFieldKey;
import org.openinvoice.util.ConfigurationManager;
import org.openinvoice.util.DateUtil;

/**
 * Author: jhe
 * Date: Aug 12, 2010
 * Time: 4:30:27 PM
 * (C) Copyright 2010 OpenInvoice.org. All rights are reserved.
 */
public class DateTimeKey extends AbstractRepositoryKey {

    private String id;
    private DateTime dateTime;

    public DateTimeKey() {
        super(ConfigurationManager.getInvoiceRepositoryKeyPattern());
    }

    public DateTimeKey(String id, DateTime dateTime) {
        super(ConfigurationManager.getInvoiceRepositoryKeyPattern());
        this.id = id;
        this.dateTime = dateTime;
    }

    public void initKeyComponents() {
        if (dateTime != null) {
            String pattern = dateTime.toString(DateTimeFormat.forPattern(ConfigurationManager.getInvoiceRepositoryDatePattern()));
            getKeyComponents().add(new KeyComponent(dateTime.getClass().getSimpleName(), DateUtil.formatDateTime(dateTime, pattern)));
        }
        if (id != null) {
            getKeyComponents().add(new KeyComponent(InvoiceFieldKey.id.name().toUpperCase(), id));
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }
}
