package org.openinvoice.core.field;

import org.apache.log4j.Logger;
import org.openinvoice.util.ResourceManager;

import java.util.regex.Pattern;

/**
 * Author: jhe
 * Date: Jun 16, 2010
 * Time: 3:03:57 PM
 * (C) Copyright 2010 OpenInvoice.org. All rights are reserved.
 */
public enum InvoiceFieldKey {
    id,
    issueDate,
    legalMonetaryTaxInclusiveAmount,
    legalMonetaryLineExtensionAmount,
    legalMonetaryTaxTotalAmount,
    currencySymbol,
    currencyCode,
    orderId,
    supplierParty,
    customerParty,
    itemQuantity,
    itemQuantityUnitCode,
    itemDescription,
    itemTaxTotalAmount,
    itemUnitPrice,
    itemLineExtensionAmount,
    taxSchemeCode,
    taxPercentage,
    partyName,
    partyLanguageName,
    partyLangCode,
    partyTaxSchemeCompanyId,
    partyTaxSchemeName,
    partyTaxSchemeCode,
    partyTaxCorporateSchemeRegName,
    partyTaxCorporateSchemeRegCode,
    partyPersonFamilyName,
    partyPersonFirstName,
    partyPersonMiddleName,
    partyPersonJobTitle,
    partyPersonOrgDept,
    partyAddressLineList,
    partyAddressBlockName,
    partyAddressBuildName,
    partyAddressBuildCode,
    partyAddressBuildNo,
    partyAddressDepartment,
    partyAddressCityName,
    partyAddressCountryName,
    partyAddressCountryCode,
    partyAddressFloor,
    partyAddressDistrict,
    partyAddressPostalZone,
    partyAddressPostBox,
    partyAddressRegion,
    partyAddressRoom,
    partyAddressStreetName,
    partyAddressTzOffset,
    partyWebUrl,
    partyContactName,
    partyContactEmail,
    partyContactTelephone,
    partyContactFax,
    partyLogoReference,

    paymentMeans,
    payeeFinancialAccountID,
    payeeFinancialAccountName,
    payeeFinancialInstitutionID,
    payeeFinancialInstitutionName,
    payerFinancialAccountID,
    payerFinancialAccountName,
    payerFinancialInstitutionID,
    payerFinancialInstitutionName,
    paymentDueDate,

    taxCategory,
    invoiceLineItem;

    private static Logger logger = Logger.getLogger(InvoiceFieldKey.class);

    private String prefix = "";

    public String key() {
        return name();
    }

    public String removeAssociatedKeysFromTemplate(String template) {
        if (isPartyKey()) {
            template =
                    template.replaceAll(Pattern.compile("(" + customerParty + "|" + supplierParty + ")" + "\\." + name() + "(\\.value|\\.name)").toString(), "");
        } else {
            template = removeKeyFromTemplate(getNameKey(), template);
            template = removeKeyFromTemplate(getValueKey(), template);
        }

        return template;
    }

    private String removeKeyFromTemplate(String fullKey, String template) {
        if (template.indexOf(fullKey) >= 0) {
            template = template.replaceAll(fullKey, "");
            logger.debug("removed key '" + fullKey + "'");
        }
        return template;
    }

    private String includePrefix(String key) {
        String fullKey = key;
        if (!prefix.isEmpty()) {
            fullKey = prefix.trim() + "." + fullKey;
        }
        return fullKey;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getNameKey() {
        return includePrefix(key() + ".name");
    }

    public String getValueKey() {
        return includePrefix(key() + ".value");
    }

    public boolean isPartyKey() {
        return key().startsWith("party");
    }

    public String startTag() {
        return key() + ".start";
    }

    public String endTag() {
        return key() + ".end";
    }

    public String removeTags(String template) {
        template = removeKeyFromTemplate(startTag(), template);
        template = removeKeyFromTemplate(endTag(), template);
        return template;
    }

    public String getInternationalizedText(String baseKey) {
        return ResourceManager.getLabel(baseKey);
    }

}