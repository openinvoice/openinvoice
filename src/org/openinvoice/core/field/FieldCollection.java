package org.openinvoice.core.field;

import org.openinvoice.core.template.InvoiceTemplate;
import org.openinvoice.util.ResourceManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Author: jhe
 * Date: Jun 17, 2010
 * Time: 9:49:20 PM
 * (C) Copyright 2010 OpenInvoice.org. All rights are reserved.
 */
public class FieldCollection implements Collection<Field>, Replaceable {

    private Collection<Field> fields = new ArrayList<Field>();
    private InvoiceFieldKey fieldKey;

    public FieldCollection(String baseKey) {
        this.fieldKey = InvoiceFieldKey.valueOf(baseKey);
    }

    public String getBaseKey() {
        return fieldKey.key();
    }

    public String getName() {
        return ResourceManager.getLabel(fieldKey.key());
    }

    public int size() {
        return fields.size();
    }

    public boolean isEmpty() {
        return fields.isEmpty();
    }

    public boolean contains(Object o) {
        return fields.contains(o);
    }

    public Iterator<Field> iterator() {
        return fields.iterator();
    }

    public Object[] toArray() {
        return fields.toArray();
    }

    public <T> T[] toArray(T[] a) {
        return fields.toArray(a);
    }

    public boolean add(Field f) {
        return fields.add(f);
    }

    public boolean remove(Object o) {
        return fields.remove(o);
    }

    public boolean containsAll(Collection<?> c) {
        return fields.containsAll(c);
    }

    public boolean addAll(Collection<? extends Field> c) {
        return fields.addAll(c);
    }

    public boolean removeAll(Collection<?> c) {
        return fields.removeAll(c);
    }

    public boolean retainAll(Collection<?> c) {
        return fields.containsAll(c);
    }

    public void clear() {
        fields.clear();
    }

    public String getBaseKeyPrefix() {
        return fieldKey.getPrefix();
    }

    public void setBaseKeyPrefix(String baseKeyPrefix) {
        fieldKey.setPrefix(baseKeyPrefix);
    }

    public InvoiceTemplate replace(InvoiceTemplate template) {
        for (Field f : this) {
            if (getBaseKeyPrefix() != null && (!getBaseKeyPrefix().isEmpty())) {
                f.setBaseKeyPrefix(getBaseKeyPrefix());
            }
            template = f.replace(template);
        }
        return template;
    }

    public boolean add(InvoiceFieldKey key, Object o) {
        return this.add(FieldFactory.getInstance(key, o));
    }
}