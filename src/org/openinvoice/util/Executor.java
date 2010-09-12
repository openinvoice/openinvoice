package org.openinvoice.util;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Author: jhe
 * Date: Aug 13, 2010
 * Time: 11:11:30 PM
 * (C) Copyright 2010 OpenInvoice.org. All rights are reserved.
 */

public class Executor {

    private static Logger logger = Logger.getLogger(Executor.class);

    public static int execute(String command, String[] arguments) throws IOException {
        if (command == null) {
            IllegalArgumentException e = new IllegalArgumentException();
            logger.error(e);
            throw e;
        }
        CommandLine commandLine = CommandLine.parse(command);
        if (arguments != null) {
            commandLine.addArguments(arguments);
        }
        DefaultExecutor executor = new DefaultExecutor();
        executor.setWorkingDirectory(UserProfile.getInvoiceOutputDir());
        File executableLogFile = new File(executor.getWorkingDirectory(), commandLine.getExecutable() + ".log");
        executor.setStreamHandler(new PumpStreamHandler(new FileOutputStream(executableLogFile)));
        int exitValue;
        try {
            exitValue = executor.execute(commandLine);
        } catch (ExecuteException e) {
            exitValue = e.getExitValue();
            logger.error(e);
        }
        logger.info(ResourceManager.getMessage(Executor.class, "executeCommand", new String[]{commandLine.toString(), UserProfile.getInvoiceOutputDir().getPath(), String.valueOf(exitValue), executableLogFile.getPath()}));
        return exitValue;
    }
}