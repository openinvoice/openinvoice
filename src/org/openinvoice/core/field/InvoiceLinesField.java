package org.openinvoice.core.field;

import org.openinvoice.core.template.InvoiceTemplate;
import org.openinvoice.core.template.InvoiceTemplateFactory;
import org.openinvoice.util.UserProfile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Author: jhe
 * Date: Jun 22, 2010
 * Time: 10:29:58 PM
 * (C) Copyright 2010 OpenInvoice.org. All rights are reserved.
 */
public class InvoiceLinesField implements Replaceable {

    private Set<InvoiceLineField> invoiceLineFields = new HashSet<InvoiceLineField>();

    public boolean add(InvoiceLineField invoiceLineField) {
        return invoiceLineFields.add(invoiceLineField);
    }

    private InvoiceTemplate replaceInvoiceLinesItemHeader(InvoiceTemplate invoiceTemplate) {
        List<TextField> headerFields = new ArrayList<TextField>();
        headerFields.add(new TextField(InvoiceFieldKey.itemDescription.key()));
        headerFields.add(new TextField(InvoiceFieldKey.itemQuantityUnitCode.key()));
        headerFields.add(new TextField(InvoiceFieldKey.itemQuantity.key()));
        headerFields.add(new TextField(InvoiceFieldKey.itemUnitPrice.key()));
        headerFields.add(new TextField(InvoiceFieldKey.itemLineExtensionAmount.key()));
        headerFields.add(new TextField(InvoiceFieldKey.itemTaxTotalAmount.key()));
        headerFields.add(new TextField(InvoiceFieldKey.taxPercentage.key()));
        for (TextField headerField : headerFields) {
            invoiceTemplate = headerField.replace(invoiceTemplate);
        }
        return invoiceTemplate;
    }

    public InvoiceTemplate replace(InvoiceTemplate invoiceTemplate) {

        String commentStartTag = invoiceTemplate.getStartCommentTag();
        String commentEndTag = invoiceTemplate.getEndCommentTag();

        String invoiceLineItemStartTag = commentStartTag
            + InvoiceFieldKey.invoiceLineItem.startTag() + commentEndTag;

        String invoiceLineItemEndTag = commentStartTag
            + InvoiceFieldKey.invoiceLineItem.endTag() + commentEndTag;

        String template = replaceInvoiceLinesItemHeader(invoiceTemplate).toString().trim();

        int invoiceLineItemStartTagIndex = template.indexOf(invoiceLineItemStartTag);
        int invoiceLineItemEndTagIndex = template.indexOf(invoiceLineItemEndTag);

        if (invoiceLineItemStartTagIndex >= 0 && invoiceLineItemEndTagIndex > 0) {
            int endTemplateIndex = invoiceLineItemEndTagIndex + invoiceLineItemEndTag.length();
            String itemTemplateStr = template.substring(invoiceLineItemStartTagIndex + invoiceLineItemStartTag.length(), invoiceLineItemEndTagIndex);
            String itemTemplateLineFeed = template.substring(endTemplateIndex, template.indexOf(UserProfile.LINE_SEP,
                    endTemplateIndex));

            String textBeforeBeginLabelIndex = template.substring(0, invoiceLineItemStartTagIndex);
            String textAfterEndLabelIndex = template.substring(invoiceLineItemEndTagIndex + invoiceLineItemEndTag.length(), template.length());

            StringBuffer itemsBuffer = new StringBuffer();
            InvoiceTemplate itemLineTemplate = InvoiceTemplateFactory.getInstance(itemTemplateStr);

            for (InvoiceLineField invoiceLineField : invoiceLineFields) {
                String invoiceLineItem = invoiceLineField.replace(itemLineTemplate).toString();
                itemsBuffer.append(invoiceLineItem).append(itemTemplateLineFeed);
                itemLineTemplate = InvoiceTemplateFactory.getInstance(itemTemplateStr);
            }
            return InvoiceTemplateFactory.getInstance(textBeforeBeginLabelIndex + itemsBuffer.toString() + textAfterEndLabelIndex);
        } else {
            return invoiceTemplate;
        }
    }
}
