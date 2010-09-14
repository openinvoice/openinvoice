package org.openinvoice.util;

/**
 * Author: jhe
 * Date: May 14, 2010
 * Time: 4:03:28 PM
 * (C) Copyright 2010 OpenInvoice.org. All rights are reserved.
 */
public enum OutputFormat {

    TEX, PDF, XML, HTML, UNKNOWN;

    private String fileExtension = "";
    private String format = "";

    private OutputFormat() {
        this.format = name();
        this.fileExtension = format.toLowerCase();

    }

    public String getFileSuffix() {
        return "." + fileExtension;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public String getApplication() {
        return UserProfile.getApplication(getFormat().toLowerCase());
    }

    public String getFormat() {
        return format;
    }

    public String getViewer() {
        return UserProfile.getViewer(getFormat().toLowerCase());
    }
}
