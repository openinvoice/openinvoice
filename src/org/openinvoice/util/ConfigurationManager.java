package org.openinvoice.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;

/**
 * Author: jhe
 * Date: May 14, 2010
 * Time: 8:30:27 AM
 * (C) Copyright 2010 OpenInvoice.org. All rights are reserved.
 */
public class ConfigurationManager {

    private static Properties properties = new Properties();
    public final static String APP_NAME = "OpenInvoice";
    public final static String ISO_TS_PATTERN = "yyyy-MM-dd'T'hhmmSS";
    public final static Locale DEFAULT_LOCALE = new Locale("en", "GB");

    static {
        File sourceDir = new File(System.getProperty("user.dir"), "src");
        try {
            File cfgFile = new File(sourceDir, APP_NAME.toLowerCase() + ".properties");
            if (cfgFile.exists() && cfgFile.canRead()) {
                properties.load(new FileInputStream(cfgFile));
            } else {
                throw new IllegalArgumentException("Config. file not found");
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static String getUBLInvoiceXSDPath() {
        return properties.getProperty("ubl.invoice.xsd.file");
    }

    public static String getUBLInvoicePackage() {
        return properties.getProperty("ubl.invoice.xsd.pkg");
    }

    public static String getUBLOrderXSDPath() {
        return properties.getProperty("ubl.order.xsd.file");
    }

    public static String getUBLOrderPackage() {
        return properties.getProperty("ubl.order.xsd.pkg");
    }

    public static String getEncoding() {
        return properties.getProperty("default.encoding");
    }

    public static StorageMedia getInvoiceRepositoryStorageMedia() {
        return StorageMedia.valueOf(properties.getProperty("invoice.repository.storage.media"));
    }

    public static File getInvoiceRepositoryDir() {
        return FileManager.getDir(properties.getProperty("invoice.repository.dir"), true);
    }

    public static String getInvoiceRepositoryKeyPattern() {
        return properties.getProperty("invoice.repository.key.pattern");
    }

    public static String getInvoiceRepositoryDatePattern() {
        return properties.getProperty("invoice.repository.date.pattern");
    }

    public static OutputFormat getInvoiceRepositoryFileFormat() {
        return OutputFormat.valueOf(properties.getProperty("invoice.repository.file.format"));
    }

    public static OutputFormat getOrderFileFormat() {
        return OutputFormat.valueOf(properties.getProperty("order.file.format"));
    }

}
