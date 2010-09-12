package org.openinvoice.repository.provider.fs;

import oasis.names.specification.ubl.schema.xsd.invoice_2.InvoiceType;
import org.apache.log4j.Logger;
import org.openinvoice.core.InvoiceContextFactory;
import org.openinvoice.repository.InvoiceRepository;
import org.openinvoice.repository.InvoiceRepositoryException;
import org.openinvoice.util.ConfigurationManager;
import org.openinvoice.util.FileManager;
import org.openinvoice.util.ResourceManager;
import org.openinvoice.util.UserProfile;
import org.openinvoice.util.ubl.InvoiceTypeWrapper;
import org.openinvoice.util.ubl.io.UBLDocumentMarshaller;
import org.openinvoice.util.ubl.io.UBLDocumentUnMarshaller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Author: jhe
 * Date: May 15, 2010
 * Time: 10:42:40 PM
 * (C) Copyright 2010 OpenInvoice.org. All rights are reserved.
 */
public class FileBasedInvoiceRepository implements InvoiceRepository {

    private static File repositoryDir = ConfigurationManager.getInvoiceRepositoryDir();
    private static final Logger logger = Logger.getLogger(FileBasedInvoiceRepository.class);

    private String makeFileNameByInvoiceKey(String invoiceKey) {
        return invoiceKey + ConfigurationManager.getInvoiceRepositoryFileFormat().getFileSuffix();
    }

    public void create(InvoiceTypeWrapper iw) throws InvoiceRepositoryException {
        OutputStream out = System.out;
        String fileName = makeFileNameByInvoiceKey(iw.getInvoiceKey());
        if (invoiceFileExist(fileName)) {
            InvoiceRepositoryException ire = new InvoiceRepositoryException(ResourceManager.getMessage(FileBasedInvoiceRepository.class, "duplicateInvoice", new String[]{iw.getInvoiceKey(), repositoryDir.getPath()}));
            logger.error(ire);
            throw ire;
        }
        try {
            File f = FileManager.createFile(getInvoiceFile(fileName).getPath());
            out = new FileOutputStream(f);
            UBLDocumentMarshaller.marshalInvoice(iw, out);
            logger.info(ResourceManager.getMessage(FileBasedInvoiceRepository.class, "created", new String[]{f.getName(), repositoryDir.getPath()}));
        } catch (Exception e) {
            InvoiceRepositoryException ire = new InvoiceRepositoryException(ResourceManager.getMessage(FileBasedInvoiceRepository.class, "failedToCreate", new String[]{e.getMessage()}), e);
            logger.error(ire);
            throw ire;
        }
    }

    public InvoiceTypeWrapper read(String invoiceKey) throws InvoiceRepositoryException {
        InvoiceType invoiceType = null;
        String fileName = makeFileNameByInvoiceKey(invoiceKey);
        try {
            if (invoiceFileExist(fileName)) {
                String fullPath = getInvoiceFile(fileName).getPath();
                invoiceType = UBLDocumentUnMarshaller.unMarshalInvoice(new FileInputStream(fullPath));
                return new InvoiceTypeWrapper(invoiceType);
            } else {
                InvoiceRepositoryException ire = new InvoiceRepositoryException(ResourceManager.getMessage(FileBasedInvoiceRepository.class, "unableToFind", new String[]{fileName, repositoryDir.getPath()}));
                if (logger.isDebugEnabled()) {
                    logger.debug(ire);
                }
                throw ire;
            }
        } catch (Exception e) {
            InvoiceRepositoryException ire = new InvoiceRepositoryException(ResourceManager.getMessage(FileBasedInvoiceRepository.class, "failedToRead", new String[]{fileName, repositoryDir.getPath()}));
            if (logger.isDebugEnabled()) {
                logger.debug(ire);
            }
            throw ire;
        }
    }

    public boolean delete(String invoiceKey) throws InvoiceRepositoryException {
        File f = null;

        String fileName = makeFileNameByInvoiceKey(invoiceKey);
        if (invoiceFileExist(fileName)) {
            f = getInvoiceFile(fileName);
        } else {
            logger.warn(ResourceManager.getMessage(FileBasedInvoiceRepository.class, "unableToFind", new String[]{fileName, repositoryDir.getPath()}));
            return false;
        }
        InvoiceTypeWrapper iw = read(invoiceKey);
        if (iw == null) {
            return false;
        }
        if (f.delete()) {
            logger.info(ResourceManager.getMessage(FileBasedInvoiceRepository.class, "deleted", new String[]{f.getName(), repositoryDir.getPath()}));
            if (UserProfile.isInvoiceOutputFilesAutoDeleteEnabled()) {
                if (iw.deleteInvoiceOutputFiles()) {
                    logger.info(ResourceManager.getMessage(FileBasedInvoiceRepository.class, "deleted", new String[]{iw.getInvoiceOutputFileName(), UserProfile.getInvoiceOutputDir().getPath()}));
                }
            }
            return true;
        } else {
            logger.info(ResourceManager.getMessage(FileBasedInvoiceRepository.class, "failedToDelete", new String[]{f.getPath()}));
            return false;
        }
    }

    private boolean invoiceFileExist(String fileName) {
        return getInvoiceFile(fileName).exists();
    }

    private File getInvoiceFile(String fileName) {
        return new File(repositoryDir, fileName);
    }

    public static void main(String[] args) throws Exception {
        FileBasedInvoiceRepository rep = new FileBasedInvoiceRepository();
        rep.delete(args[0]);
    }
}
