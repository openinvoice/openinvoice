package org.openinvoice.util;

/**
 * Author: jhe
 * Date: May 14, 2010
 * Time: 3:46:25 PM
 * (C) Copyright 2010 OpenInvoice.org. All rights are reserved.
 */
public enum StorageMedia {

    FILE_SYSTEM("FILE_SYSTEM"),
    DATABASE("DATABASE");

    private String storageMedia = "";

    StorageMedia(String storageMedia) {
        this.storageMedia = storageMedia;
    }


    @Override
    public String toString() {
        return storageMedia;
    }
}