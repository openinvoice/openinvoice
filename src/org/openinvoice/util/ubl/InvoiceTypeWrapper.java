package org.openinvoice.util.ubl;

import oasis.names.specification.ubl.schema.xsd.invoice_2.InvoiceType;
import org.openinvoice.core.InvoiceContext;
import org.openinvoice.core.InvoiceContextFactory;
import org.openinvoice.core.field.*;
import org.openinvoice.core.template.InvoiceTemplate;
import org.openinvoice.repository.key.DateTimeKey;
import org.openinvoice.util.DateUtil;
import org.openinvoice.util.FileManager;
import org.openinvoice.util.ResourceManager;
import org.openinvoice.util.UserProfile;
import un.unece.uncefact.codelist.specification._54217._2001.CurrencyCodeContentType;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * Author: jhe
 * Date: May 14, 2010
 * Time: 2:35:13 PM
 * (C) Copyright 2010 OpenInvoice.org. All rights are reserved.
 */
public class InvoiceTypeWrapper {

    private InvoiceType invoiceType;
    private CurrencyCodeContentType currencyCodeType;
    private DateTimeKey invoiceRepositoryKey = new DateTimeKey();

    private Set<Replaceable> printableFields = new HashSet<Replaceable>();
    private InvoiceLinesField invoiceLinesField = new InvoiceLinesField();

    public InvoiceTypeWrapper(InvoiceType invoiceType) {
        printableFields.add(invoiceLinesField);
        setInvoiceType(invoiceType);
        invoiceRepositoryKey = new DateTimeKey(invoiceType.getID().getValue(), DateUtil.toDateTime(invoiceType.getIssueDate().getValue()));
        invoiceRepositoryKey.compose();
        this.currencyCodeType =
                InvoiceTypeWrapperBuilderHelper.createCurrencyCodeContentType(InvoiceContextFactory.getInstance().getCurrencyCode());
    }

    public InvoiceTemplate processInvoiceType(InvoiceTemplate template) {
        printableFields.add(FieldFactory.getInstance(InvoiceFieldKey.id, invoiceType.getID()));
        printableFields.add(FieldFactory.getInstance(InvoiceFieldKey.issueDate, invoiceType.getIssueDate()));
        printableFields.add(FieldFactory.getInstance(InvoiceFieldKey.orderId, invoiceType.getOrderReference().getID()));
        new InvoiceLineTypeHelper(this).process();
        new PartyTypeHelper(this).process();
        new PaymentMeansHelper(this).process();
        new PaymentTermsHelper(this).process();
        printableFields.add(FieldFactory.getInstance(InvoiceFieldKey.currencySymbol, CurrencyField.getCurrencySymbol()));
        printableFields.add(FieldFactory.getInstance(InvoiceFieldKey.currencyCode, CurrencyField.getCurrencyCode()));
        for (Replaceable replaceable : printableFields) {
            template = replaceable.replace(template);
        }
        return template;
    }

    public CurrencyCodeContentType getCurrencyCodeType() {
        return currencyCodeType;
    }

    public Set<Replaceable> getPrintableFields() {
        return printableFields;
    }

    public InvoiceLinesField getInvoiceLinesField() {
        return invoiceLinesField;
    }

    public String getInvoiceKey() {
        return invoiceRepositoryKey.toString();
    }

    public String getInvoiceOutputFileName() {
        String fileNameSuffix = UserProfile.getInvoiceOutputFormat().getFileSuffix();
        if (fileNameSuffix == null) {
            throw new NullPointerException(ResourceManager.getMessage(InvoiceTypeWrapper.class, "invalidFileNameSuffix"));
        }
        return getInvoiceKey() + fileNameSuffix;
    }

    public InvoiceType getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(InvoiceType invoiceType) {
        this.invoiceType = invoiceType;
    }

    public boolean deleteInvoiceOutputFiles() {
        File[] filesToBeDeleted = UserProfile.getInvoiceOutputDir().listFiles(new InvoiceOutputFilesFileFilter());
        return FileManager.delete(filesToBeDeleted);
    }

    private class InvoiceOutputFilesFileFilter implements java.io.FileFilter {
        public boolean accept(File f) {
            return f.getName().startsWith(getInvoiceKey());
        }
    }
}
