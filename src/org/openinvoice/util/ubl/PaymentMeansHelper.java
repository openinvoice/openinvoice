package org.openinvoice.util.ubl;

import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.BranchType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.FinancialAccountType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.FinancialInstitutionType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.PaymentMeansType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.NameType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PaymentDueDateType;
import org.openinvoice.core.field.InvoiceFieldKey;
import org.openinvoice.core.field.PaymentMeansField;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Author: jhe
 * Date: Jul 2, 2010
 * Time: 4:34:15 PM
 * (C) Copyright 2010 OpenInvoice.org. All rights are reserved.
 */
public class PaymentMeansHelper {

    private InvoiceTypeWrapper invoiceTypeWrapper;

    public PaymentMeansHelper(InvoiceTypeWrapper invoiceTypeWrapper) {
        this.invoiceTypeWrapper = invoiceTypeWrapper;
    }

    public void process() {
        if (invoiceTypeWrapper != null) {
            if (!invoiceTypeWrapper.getInvoiceType().getPaymentMeans().isEmpty()) {
                PaymentMeansType paymentMeans = invoiceTypeWrapper.getInvoiceType().getPaymentMeans().get(0);
                PaymentMeansField paymentMeansField = new PaymentMeansField(InvoiceFieldKey.paymentMeans.key());
                processFinancialAccountType(paymentMeans.getPayeeFinancialAccount(), true, paymentMeansField);
                processPaymentDueDateType(paymentMeans.getPaymentDueDate(), paymentMeansField);
                invoiceTypeWrapper.getPrintableFields().add(paymentMeansField);
            }
        }
    }

    public void processPaymentDueDateType(PaymentDueDateType paymentDueDateType, PaymentMeansField paymentMeansField) {
        XMLGregorianCalendar date = paymentDueDateType.getValue();
        if (date != null) {
            paymentMeansField.setPaymentDueDate(date);
        }
    }

    public void processFinancialAccountType(FinancialAccountType financialAccountType, boolean payee, PaymentMeansField paymentMeansField) {
        // Account Account Identifier. SWIFT (BIC) and IBAN are dfined is ISO 9362 and ISO 13616
        IDType financialAccountID = financialAccountType.getID();
        if (financialAccountID != null) {
            String value = financialAccountID.getValue();
            if (value != null) {
                if (payee) {
                    paymentMeansField.setPayeeFinancialAccountID(value);
                } else {
                    paymentMeansField.setPayerFinancialAccountID(value);
                }
            }
        }

        // Account Name
        NameType financialAccountName = financialAccountType.getName();
        if (financialAccountName != null) {
            String value = financialAccountName.getValue();
            if (value != null) {
                if (payee) {
                    paymentMeansField.setPayeeFinancialAccountName(value);
                } else {
                    paymentMeansField.setPayerFinancialAccountID(value);
                }
            }
        }

        // Account type (e.g. Current)
        financialAccountType.getAccountTypeCode();

        // Currency Code
        financialAccountType.getCurrencyCode();


        BranchType branchType = financialAccountType.getFinancialInstitutionBranch();

        // Branch ID
        branchType.getID();

        // Branch name (e.g. Open Bank LTD, Bridge Branch)
        branchType.getName();

        // Branch address
        branchType.getAddress();

        FinancialInstitutionType financialInstitutionType = branchType.getFinancialInstitution();

        // Financial Institution, The identifier of the financial institution expressed as a code.
        // ISO 9362 BIC is recommended.
        IDType financialInstitutionID = financialInstitutionType.getID();
        if (financialInstitutionID != null) {
            String value = financialInstitutionID.getValue();
            if (value != null) {
                if (payee) {
                    paymentMeansField.setPayeeFinancialInstitutionID(value);
                } else {
                    paymentMeansField.setPayerFinancialInstitutionID(value);
                }
            }
        }

        // Financial Institution Name (Open Bank LTD)
        NameType financialInstitutionName = financialInstitutionType.getName();
        if (financialInstitutionName != null) {
            String value = financialInstitutionName.getValue();
            if (value != null) {
                if (payee) {
                    paymentMeansField.setPayeeFinancialInstitutionName(value);
                } else {
                    paymentMeansField.setPayerFinancialInstitutionName(value);
                }
            }
        }

        // Financial Institution Address
        financialInstitutionType.getAddress();
    }
}


