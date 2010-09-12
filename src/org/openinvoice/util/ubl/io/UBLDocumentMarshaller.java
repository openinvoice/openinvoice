package org.openinvoice.util.ubl.io;

import oasis.names.specification.ubl.schema.xsd.invoice_2.ObjectFactory;
import org.openinvoice.util.ConfigurationManager;
import org.openinvoice.util.ubl.InvoiceTypeWrapper;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
//import javax.xml.validation.Validator;

/**
 * Author: jhe
 * Date: May 13, 2010
 * Time: 8:44:46 PM
 * (C) Copyright 2010 OpenInvoice.org. All rights are reserved.
 */
public class UBLDocumentMarshaller {

    public static void marshalInvoice(InvoiceTypeWrapper invoiceTypeWrapper, OutputStream outputStream) throws JAXBException, SAXException, IOException {
        JAXBContext jc = JAXBContext.newInstance(ConfigurationManager.getUBLInvoicePackage());
        ObjectFactory of = new ObjectFactory();
        of.createInvoice(invoiceTypeWrapper.getInvoiceType());
        javax.xml.bind.Marshaller m = jc.createMarshaller();
        m.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING, ConfigurationManager.getEncoding());
        m.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = sf.newSchema(new File(ConfigurationManager.getUBLInvoiceXSDPath()));
        m.setSchema(schema);
        m.marshal(of.createInvoice(invoiceTypeWrapper.getInvoiceType()), outputStream);
    }


}
