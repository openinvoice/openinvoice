package org.openinvoice.util.ubl;

import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.*;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.*;
import org.openinvoice.core.InvoiceContextFactory;
import org.openinvoice.core.field.CustomerPartyField;
import org.openinvoice.core.field.PartyField;
import org.openinvoice.core.field.SupplierPartyField;
import un.unece.uncefact.data.specification.unqualifieddatatypesschemamodule._2.CodeType;
import un.unece.uncefact.data.specification.unqualifieddatatypesschemamodule._2.IdentifierType;
import un.unece.uncefact.data.specification.unqualifieddatatypesschemamodule._2.NameType;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;


/**
 * Author: jhe
 * Date: May 12, 2010
 * Time: 11:05:35 PM
 * (C) Copyright 2010 OpenInvoice.org. All rights are reserved.
 */
public class PartyTypeHelper {

    private InvoiceTypeWrapper invoiceTypeWrapper;

    public PartyTypeHelper(InvoiceTypeWrapper invoiceTypeWrapper) {
        this.invoiceTypeWrapper = invoiceTypeWrapper;
    }

    public void process() {
        if (invoiceTypeWrapper != null) {
            SupplierPartyType spt = invoiceTypeWrapper.getInvoiceType().getAccountingSupplierParty();
            CustomerPartyType cpt = invoiceTypeWrapper.getInvoiceType().getAccountingCustomerParty();
            invoiceTypeWrapper.getPrintableFields().add(parsePartyType(cpt.getParty(), new CustomerPartyField()));
            invoiceTypeWrapper.getPrintableFields().add(parsePartyType(spt.getParty(), new SupplierPartyField()));
        }
    }

    public PartyField parsePartyType(PartyType pt, PartyField pf) {
        List<PartyNameType> partyNameList = pt.getPartyName();
        if (partyNameList.size() > 0) {
            PartyNameType partyName = partyNameList.get(0);
            if (partyName != null) {
                NameType name = partyName.getName();
                if (name != null) {
                    pf.addName(name.getValue());
                }
            }
        }
        parseLanguage(pt.getLanguage(), pf);
        parsePartyTaxScheme(pt.getPartyTaxScheme(), pf);
        parsePartyLegalEntity(pt.getPartyLegalEntity(), pf);
        parsePerson(pt.getPerson(), pf);
        parseAddressType(pt.getPostalAddress(), pf);
        parseWebsiteURI(pt.getWebsiteURI(), pf);
        parseLogoReference(pt.getLogoReferenceID(), pf);
        parseContactType(pt.getContact(), pf);
        return pf;
    }

    public void parseLanguage(LanguageType lt, PartyField pf) {
        if (lt != null) {
            IdentifierType identifier = lt.getID();
            NameType name = lt.getName();
            if (name != null) {
                pf.addLanguageName(name.getValue());
            }
            LocaleCodeType localeCode = lt.getLocaleCode();
            if (localeCode != null) {
                pf.addLanguageCode(localeCode.getLanguageID());
            }
        }
    }

    public void parsePartyTaxScheme(List<PartyTaxSchemeType> ptstList, PartyField pf) {
        if (ptstList != null && ptstList.size() > 0) {
            PartyTaxSchemeType partyTaxScheme = ptstList.get(0);
            if (partyTaxScheme != null) {
                IdentifierType companyID = partyTaxScheme.getCompanyID();
                if (companyID != null) {
                    pf.setTaxSchemeCompanyID(companyID.getValue());
                }
                TaxSchemeType taxScheme = partyTaxScheme.getTaxScheme();
                if (taxScheme != null) {
                    NameType name = taxScheme.getName();
                    if (name != null) {
                        pf.setTaxSchemeName(name.getValue());
                    }
                    TaxTypeCodeType taxTypeCode = taxScheme.getTaxTypeCode();
                    if (taxTypeCode != null) {
                        CodeType taxSchemeTypeCode = taxScheme.getTaxTypeCode();
                        if (taxSchemeTypeCode != null) {
                            pf.setTaxSchemeCode(taxSchemeTypeCode.getValue());
                        }
                    }
                }
            }
        }
    }

    public void parsePartyLegalEntity(List<PartyLegalEntityType> pletList, PartyField pf) {
        if (pletList != null && pletList.size() > 0) {
            PartyLegalEntityType plet = pletList.get(0);
            CorporateRegistrationSchemeType corporateRegistrationScheme =
                    plet.getCorporateRegistrationScheme();
            if (corporateRegistrationScheme != null) {
                NameType name = corporateRegistrationScheme.getName();
                if (name != null) {
                    pf.setTaxCorporateSchemeRegistrationName(name.getValue());
                }
                CorporateRegistrationTypeCodeType corporateRegistrationTypeCode =
                        corporateRegistrationScheme.getCorporateRegistrationTypeCode();
                if (corporateRegistrationTypeCode != null) {
                    pf.setTaxCorporateSchemeRegistrationCode(corporateRegistrationTypeCode.getValue());
                }
            }
        }
    }

    public void parsePerson(PersonType pt, PartyField pf) {
        if (pt != null) {
            FamilyNameType familyName = pt.getFamilyName();
            if (familyName != null) {
                pf.setPersonFamilyName(familyName.getValue());
            }
            FirstNameType firstName = pt.getFirstName();
            if (firstName != null) {
                pf.setPersonFirstName(pt.getFirstName());
            }
            MiddleNameType middleName = pt.getMiddleName();
            if (middleName != null) {
                pf.setPersonMiddleName(pt.getMiddleName());
            }
            JobTitleType jobTitle = pt.getJobTitle();
            if (jobTitle != null) {
                pf.setPersonJobTitle(jobTitle.getValue());
            }
            OrganizationDepartmentType organizationDepartment = pt.getOrganizationDepartment();
            if (organizationDepartment != null) {
                pf.setPersonOrganizationDepartment(organizationDepartment.getValue());
            }
        }
    }

    public void parseAddressType(AddressType at, PartyField pf) {
        if (at != null) {
            List<AddressLineType> addressLineList = at.getAddressLine();
            if (addressLineList != null) {
                List<String> addressLineStringList = new LinkedList<String>();
                for (AddressLineType addressLine : addressLineList) {
                    LineType line = addressLine.getLine();
                    if (line != null) {
                        addressLineStringList.add(line.getValue());
                    }
                }
                pf.setAddressLineList(addressLineStringList);
            }
            BlockNameType blockName = at.getBlockName();
            if (blockName != null) {
                pf.setAddressBlockName(blockName.getValue());
            }
            NameType buildingName = at.getBuildingName();
            if (buildingName != null) {
                pf.setAddressBuildingName(buildingName.getValue());
            }
            BuildingNumberType buildingNumber = at.getBuildingNumber();
            if (buildingNumber != null) {
                pf.setAddressBuildingNumber(buildingNumber.getValue());
            }
            CityNameType cityName = at.getCityName();
            if (cityName != null) {
                pf.setAddressCityName(cityName.getValue());
            }
            CountryType country = at.getCountry();
            if (country != null) {
                NameType countryName = country.getName();
                if (countryName != null) {
                    pf.setAddressCountryName(countryName.getValue());
                }
                IdentificationCodeType identificationCode = country.getIdentificationCode();
                if (identificationCode != null) {
                    pf.setAddressCountryCode(identificationCode.getValue());
                    if (countryName == null) {
                        pf.setAddressCountryName(
                                new Locale(InvoiceContextFactory.getInstance().getLanguageCode(),
                                        identificationCode.getValue()).getDisplayCountry());
                    }
                }
            }
            DepartmentType department = at.getDepartment();
            if (department != null) {
                pf.setAddressDepartment(department.getValue());
            }
            FloorType floor = at.getFloor();
            if (floor != null) {
                pf.setAddressFloor(floor.getValue());
            }
            DistrictType district = at.getDistrict();
            if (district != null) {
                pf.setAddressDistrict(district.getValue());
            }
            PostalZoneType postalZone = at.getPostalZone();
            if (postalZone != null) {
                pf.setAddressPostalZone(postalZone.getValue());
            }
            PostboxType postbox = at.getPostbox();
            if (postbox != null) {
                pf.setAddressPostBox(postbox.getValue());
            }
            RegionType region = at.getRegion();
            if (region != null) {
                pf.setAddressRegion(region.getValue());
            }
            RoomType room = at.getRoom();
            if (room != null) {
                pf.setAddressRoom(room.getValue());
            }
            StreetNameType streetName = at.getStreetName();
            if (streetName != null) {
                pf.setAddressStreetName(streetName.getValue());
            }
            TimezoneOffsetType timezoneOffset = at.getTimezoneOffset();
            if (timezoneOffset != null) {
                pf.setAddressTimeZoneOffset(timezoneOffset.getValue());
            }
        }
    }

    public void parseWebsiteURI(WebsiteURIType wut, PartyField pf) {
        if (wut != null) {
            pf.setWebURL(wut.getValue());
        }
    }

    public void parseLogoReference(LogoReferenceIDType lr, PartyField pf) {
        if (lr != null) {
            pf.setLogoReference(lr.getValue());
        }
    }

    public void parseContactType(ContactType ct, PartyField pf) {
        if (ct != null) {
            NameType name = ct.getName();
            if (name != null) {
                pf.setContactName(name.getValue());
            }
            ElectronicMailType electronicMail = ct.getElectronicMail();
            if (electronicMail != null) {
                pf.setContactEmail(electronicMail.getValue());
            }
            TelefaxType telefax = ct.getTelefax();
            if (telefax != null) {
                pf.setContactTelefax(telefax.getValue());
            }
            TelephoneType telephone = ct.getTelephone();
            if (telephone != null) {
                pf.setContactTelephone(telephone.getValue());
            }
        }
    }
}