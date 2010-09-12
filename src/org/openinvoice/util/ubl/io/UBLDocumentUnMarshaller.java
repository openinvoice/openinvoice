package org.openinvoice.util.ubl.io;

import oasis.names.specification.ubl.schema.xsd.invoice_2.InvoiceType;
import oasis.names.specification.ubl.schema.xsd.order_2.OrderType;
import org.openinvoice.util.ConfigurationManager;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Author: jhe
 * Date: May 13, 2010
 * Time: 10:36:14 AM
 * (C) Copyright 2010 OpenInvoice.org. All rights are reserved.
 */
public class UBLDocumentUnMarshaller {

    public static OrderType unMarshalOrder(InputStream is) throws JAXBException, FileNotFoundException, SAXException {

        JAXBContext jc = JAXBContext.newInstance(ConfigurationManager.getUBLOrderPackage());
        Unmarshaller u = jc.createUnmarshaller();

        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = sf.newSchema(new File(ConfigurationManager.getUBLOrderXSDPath()));
        u.setSchema(schema);

        return (OrderType) ((JAXBElement) u.unmarshal(is)).getValue();
    }

    public static InvoiceType unMarshalInvoice(InputStream is) throws JAXBException, SAXException, FileNotFoundException {
        JAXBContext jc = JAXBContext.newInstance(ConfigurationManager.getUBLInvoicePackage());
        Unmarshaller u = jc.createUnmarshaller();

        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = sf.newSchema(new File(ConfigurationManager.getUBLInvoiceXSDPath()));
        u.setSchema(schema);

        return (InvoiceType) ((JAXBElement) u.unmarshal(is)).getValue();
    }


}
