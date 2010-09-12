package org.openinvoice.util;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import java.io.File;
import java.io.IOException;

/**
 * Author: jhe
 * Date: May 14, 2010
 * Time: 1:32:25 PM
 * (C) Copyright 2010 OpenInvoice.org. All rights are reserved.
 */
public class FileManager {

    private final static Logger logger = Logger.getLogger(FileManager.class);

    public static File getDir(String path) {
        return getDir(path, false);
    }

    public static File getDir(String path, boolean create) {
        File dir;
        if (path != null) {
            dir = new File(path);
            if (create) {
                if (!dir.exists()) {
                    boolean created = dir.mkdirs();
                    if (!created) {
                        return null;
                    }
                }
            }
            if (!dir.isDirectory() || !dir.canRead()) {
                throw new IllegalArgumentException(ResourceManager.getMessage(FileManager.class, "dirNotReadable", new String[]{path}));
            }
        } else {
            throw new IllegalArgumentException(ResourceManager.getMessage(FileManager.class, "dirNotFound", new String[]{path}));
        }
        return dir;
    }

    public static File createFile(File dir, String fileName) throws IOException {
        return createFile(new File(dir, fileName).getPath());
    }

    public static File createFile(String path) throws IOException {
        File file = new File(path);
        if (file.createNewFile()) {
            return file;
        }
        return null;
    }

    public static File getFile(String path) throws IOException {
        File file;
        if (path != null) {
            file = new File(path);
            if (!file.exists() || !file.canRead()) {
                throw new IOException(ResourceManager.getMessage(FileManager.class, "fileNotFound", new String[]{path}));
            }
        } else {
            throw new IOException(ResourceManager.getMessage(FileManager.class, "fileNotFound", new String[]{path}));
        }
        return file;
    }

    public static File getFile(File file) throws IOException {
        return getFile(file.getPath());
    }

    private static String createTimeStampedFileName(String path) {
        StringBuffer name = new StringBuffer(getBaseName(path));
        name.append("_");
        name.append(new DateTime().toDateTimeISO().toString(ConfigurationManager.ISO_TS_PATTERN));
        name.append(getSuffix(path));
        return name.toString();
    }

    public static String getBaseName(String name) {
        return name.substring(0, name.lastIndexOf("."));
    }

    public static String getSuffix(String name) {
        return name.substring(name.lastIndexOf("."), name.length());
    }

    public static File timeStampFile(File f) {
        if (f != null) {
            File timeStampedFile = new File(createTimeStampedFileName(f.getPath()));
            boolean renamed = f.renameTo(timeStampedFile);
            return renamed ? timeStampedFile : null;
        } else {
            throw new IllegalArgumentException(ResourceManager.getMessage(FileManager.class, "fileNotFound", new String[]{null}));
        }
    }

    public static File backupAndCreateFile(String fileName, File dir) throws IOException {
        File file = new File(dir, fileName);
        File backupFile = FileManager.timeStampFile(file);
        if (file.exists()) {
            if (backupFile == null) {
                throw new IOException(ResourceManager.getMessage(FileManager.class, "fileCreationFailed", new String[]{file.getPath()}));
            }
        } else {
            file = FileManager.createFile(dir, fileName);
        }
        if (logger.isInfoEnabled()) {
            logger.info(ResourceManager.getMessage(FileManager.class, "fileBackUpSucceeded", new String[]{file.getPath(), backupFile.getPath()}));
        }
        return file;
    }

    public static boolean delete(File dir, String fullFileName) {
        return new File(dir, fullFileName).delete();
    }

    public static boolean delete(String fileName) {
        return new File(fileName).delete();
    }

    public static boolean delete(File[] files) {
        boolean deletedFiles = true;
        for (File file : files) {
            if (!file.delete()) {
                deletedFiles = false;
                break;
            }
        }
        return deletedFiles;
    }

}
