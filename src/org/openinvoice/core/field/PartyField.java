package org.openinvoice.core.field;

import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.FirstNameType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MiddleNameType;

import java.util.List;

/**
 * Author: jhe
 * Date: Jun 28, 2010
 * Time: 10:09:53 PM
 * (C) Copyright 2010 OpenInvoice.org. All rights are reserved.
 */
public class PartyField extends FieldCollection {

    public PartyField(String baseKey) {
        super(baseKey);
    }

    public void addName(String value) {
        add(FieldFactory.getTextInstance(InvoiceFieldKey.partyName, value));
    }

    public void addLanguageName(String value) {
        add(FieldFactory.getTextInstance(InvoiceFieldKey.partyLanguageName, value));
    }

    public void addLanguageCode(String value) {
        add(FieldFactory.getTextInstance(InvoiceFieldKey.partyLangCode, value));
    }

    public void setTaxSchemeCompanyID(String value) {
        add(FieldFactory.getTextInstance(InvoiceFieldKey.partyTaxSchemeCompanyId, value));
    }

    public void setTaxSchemeName(String value) {
        add(FieldFactory.getTextInstance(InvoiceFieldKey.partyTaxSchemeName, value));
    }

    public void setTaxSchemeCode(String value) {
        add(FieldFactory.getTextInstance(InvoiceFieldKey.partyTaxSchemeCode, value));
    }

    public void setTaxCorporateSchemeRegistrationName(String value) {
        add(FieldFactory.getTextInstance(InvoiceFieldKey.partyTaxCorporateSchemeRegName, value));
    }

    public void setTaxCorporateSchemeRegistrationCode(String value) {
        add(FieldFactory.getTextInstance(InvoiceFieldKey.partyTaxCorporateSchemeRegCode, value));
    }

    public void setPersonOrganizationDepartment(String value) {
        add(FieldFactory.getTextInstance(InvoiceFieldKey.partyPersonOrgDept, value));
    }

    public void setPersonJobTitle(String value) {
        add(FieldFactory.getTextInstance(InvoiceFieldKey.partyPersonJobTitle, value));
    }

    public void setPersonMiddleName(MiddleNameType value) {
        add(FieldFactory.getTextInstance(InvoiceFieldKey.partyPersonMiddleName, value));
    }

    public void setPersonFirstName(FirstNameType value) {
        add(FieldFactory.getTextInstance(InvoiceFieldKey.partyPersonFirstName, value));
    }

    public void setPersonFamilyName(String value) {
        add(FieldFactory.getTextInstance(InvoiceFieldKey.partyPersonFamilyName, value));
    }

    public void setAddressTimeZoneOffset(String value) {
        add(FieldFactory.getTextInstance(InvoiceFieldKey.partyAddressTzOffset, value));
    }

    public void setAddressLineList(List<String> value) {
        //add(FieldFactory.getTextInstance(InvoiceFieldKey.partyAddressLineList, value));
    }

    public void setAddressBlockName(String value) {
        add(FieldFactory.getTextInstance(InvoiceFieldKey.partyAddressBlockName, value));
    }

    public void setAddressBuildingName(String value) {
        add(FieldFactory.getTextInstance(InvoiceFieldKey.partyAddressBuildName, value));
    }

    public void setAddressBuildingNumber(String value) {
        add(FieldFactory.getTextInstance(InvoiceFieldKey.partyAddressBuildNo, value));
    }

    public void setAddressCityName(String value) {
        add(FieldFactory.getTextInstance(InvoiceFieldKey.partyAddressCityName, value));
    }

    public void setAddressCountryName(String value) {
        add(FieldFactory.getTextInstance(InvoiceFieldKey.partyAddressCountryName, value));
    }

    public void setAddressCountryCode(String value) {
        add(FieldFactory.getTextInstance(InvoiceFieldKey.partyAddressCountryCode, value));
    }

    public void setAddressDepartment(String value) {
        add(FieldFactory.getTextInstance(InvoiceFieldKey.partyAddressDepartment, value));
    }

    public void setAddressFloor(String value) {
        add(FieldFactory.getTextInstance(InvoiceFieldKey.partyAddressFloor, value));
    }

    public void setAddressDistrict(String value) {
        add(FieldFactory.getTextInstance(InvoiceFieldKey.partyAddressDistrict, value));
    }

    public void setAddressPostalZone(String value) {
        add(FieldFactory.getTextInstance(InvoiceFieldKey.partyAddressPostalZone, value));
    }

    public void setAddressPostBox(String value) {
        add(FieldFactory.getTextInstance(InvoiceFieldKey.partyAddressPostBox, value));
    }

    public void setAddressRegion(String value) {
        add(FieldFactory.getTextInstance(InvoiceFieldKey.partyAddressRegion, value));
    }

    public void setAddressRoom(String value) {
        add(FieldFactory.getTextInstance(InvoiceFieldKey.partyAddressRoom, value));
    }

    public void setAddressStreetName(String value) {
        add(FieldFactory.getTextInstance(InvoiceFieldKey.partyAddressStreetName, value));
    }

    public void setContactTelephone(String value) {
        add(FieldFactory.getTextInstance(InvoiceFieldKey.partyContactTelephone, value));
    }

    public void setContactTelefax(String value) {
        add(FieldFactory.getTextInstance(InvoiceFieldKey.partyContactFax, value));
    }

    public void setContactEmail(String value) {
        add(FieldFactory.getTextInstance(InvoiceFieldKey.partyContactEmail, value));
    }

    public void setContactName(String value) {
        add(FieldFactory.getTextInstance(InvoiceFieldKey.partyContactName, value));
    }

    public void setWebURL(String value) {
        add(FieldFactory.getTextInstance(InvoiceFieldKey.partyWebUrl, value));
    }

    public void setLogoReference(String value) {
        add(FieldFactory.getTextInstance(InvoiceFieldKey.partyLogoReference, value));
    }

}