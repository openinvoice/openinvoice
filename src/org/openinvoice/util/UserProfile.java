package org.openinvoice.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Author: jhe
 * Date: May 13, 2010
 * Time: 11:52:37 AM
 * (C) Copyright 2010 OpenInvoice.org. All rights are reserved.
 */
public class UserProfile {

    public final static String LINE_SEP = System.getProperty("line.separator");
    public final static File USER_HOME = new File(System.getProperty("user.home"));
    public final static String USER_NAME = System.getProperty("user.name");
    private static Properties properties = new Properties();

    static {
        try {
            File userProfileFile = getProfileFile();
            if (userProfileFile.exists() && userProfileFile.canRead()) {
                properties.load(new FileInputStream(userProfileFile));
            } else {
                throw new IllegalArgumentException(ResourceManager.getMessage(UserProfile.class, "fileNotFound"));
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static File getProfileFile() throws IOException {
        String fileName = USER_NAME + "_" + ConfigurationManager.APP_NAME.toLowerCase() + ".properties";
        return FileManager.getFile(new File(getUserHome(), fileName).getPath());
    }

    public static File getUserHome() {
        return USER_HOME;
    }

    public static String getCountryCode() {
        return properties.getProperty("locale.countryCode");
    }

    public static String getLanguageCode() {
        return properties.getProperty("locale.languageCode");
    }


    public static File getInvoiceTemplateDir() {
        return FileManager.getDir(properties.getProperty("invoice.template.dir"));
    }

    public static File getInvoiceTemplateFile() throws IOException {
        String fileName = properties.getProperty("invoice.template.file") + getInvoiceOutputFormat().getFileSuffix();
        File f = new File(getInvoiceTemplateDir(), fileName);
        return FileManager.getFile(f);
    }

    public static OutputFormat getInvoiceOutputFormat() {
        return OutputFormat.valueOf(properties.getProperty("invoice.output.format"));
    }

    public static File getInvoiceOutputDir() {
        return FileManager.getDir(properties.getProperty("invoice.output.dir"));
    }

    public static File getOrderInputDir() {
        return FileManager.getDir(properties.getProperty("order.input.dir"));
    }

    public static StorageMedia getInvoiceOutputFileStorageMedia() {
        return StorageMedia.valueOf(properties.getProperty("invoice.output.file.storage.media"));
    }

    public static boolean isInvoiceOutputCurrencyFormatWithSymbol() {
        return Boolean.parseBoolean(properties.getProperty("invoice.output.currency.formatWithSymbol"));
    }

    public static boolean isInvoiceOutputFilesAutoDeleteEnabled() {
        return Boolean.parseBoolean(properties.getProperty("invoice.output.files.autoDelete"));
    }

    public static boolean isInvoiceOutputFileAutoViewEnabled() {
        return Boolean.parseBoolean(properties.getProperty("invoice.output.file.autoView"));
    }

    public static String getApplication(String format) {
        return properties.getProperty("invoice.output.format." + format + ".application");
    }

    public static String getViewer(String format) {
        return properties.getProperty("invoice.output.format." + format + ".viewer");
    }

}