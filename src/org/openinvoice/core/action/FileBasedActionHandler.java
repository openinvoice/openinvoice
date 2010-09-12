package org.openinvoice.core.action;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.openinvoice.core.InvoiceContextFactory;
import org.openinvoice.core.io.InvoiceWriter;
import org.openinvoice.core.io.InvoiceWriterFactory;
import org.openinvoice.repository.InvoiceRepositoryException;
import org.openinvoice.repository.InvoiceRepositoryFactory;
import org.openinvoice.util.*;
import org.openinvoice.util.ubl.InvoiceTypeWrapper;
import org.openinvoice.util.ubl.InvoiceTypeWrapperBuilder;
import org.openinvoice.util.ubl.io.UBLDocumentUnMarshaller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Author: jhe
 * Date: Aug 19, 2010
 * Time: 10:48:53 PM
 * (C) Copyright 2010 OpenInvoice.org. All rights are reserved.
 */
public class FileBasedActionHandler implements ActionHandler {

    private InvoiceTypeWrapper invoiceTypeWrapper;
    private Action action;
    private final static Logger logger = Logger.getLogger(FileBasedActionHandler.class);

    public FileBasedActionHandler() {
    }

    public void handle(Action action) throws Exception {
        this.action = action;
        if (preProcess()) {
            if (process()) {
                postProcess();
            }
        }
    }

    public boolean preProcess() {
        if (action == null || action.getArguments() == null || action.getArguments().length < 1) {
            return false;
        } else {
            if (logger.isInfoEnabled()) {
                logger.info(ResourceManager.getMessage(InvoiceTypeWrapperBuilder.class, "startProcessingOrder", new String[]{action.toString(), DateUtil.getISODateTime()}));
            }
            return true;
        }


    }

    public boolean process() throws IOException, InvoiceRepositoryException {
        if (action instanceof CreateInvoiceAction) {
            initInvoiceTypeWrapper();
            return createInvoice();
        } else if (action instanceof DeleteInvoiceAction) {
            return InvoiceRepositoryFactory.getInstance().delete(action.getArguments()[0]);
        } else if (action instanceof RenderInvoiceAction) {
            return renderInvoice() && runApplication();
        } else if (action instanceof ViewInvoiceAction) {
            return viewInvoice();
        } else {
            throw new UnsupportedOperationException();
        }
    }

    private boolean initInvoiceTypeWrapper() {
        File orderFile;
        try {
            orderFile = new File(UserProfile.getOrderInputDir(), action.getArguments()[0] + ConfigurationManager.getOrderFileFormat().getFileSuffix());
            InputStream is = new FileInputStream(orderFile);
            InvoiceTypeWrapperBuilder ib = new InvoiceTypeWrapperBuilder(UBLDocumentUnMarshaller.unMarshalOrder(is));
            this.invoiceTypeWrapper = ib.process();
        } catch (Exception e) {
            e.printStackTrace();
            if (logger.isInfoEnabled()) {
                logger.trace(e);
                return false;
            }
        }
        return true;
    }

    private boolean createInvoice() {
        try {
            if (invoiceTypeWrapper != null) {
                InvoiceWriter iw = InvoiceWriterFactory.getInstance(invoiceTypeWrapper);
                iw.setOutput(invoiceTypeWrapper.processInvoiceType(InvoiceContextFactory.getInstance().getTemplate()).removeLeftOverKeys());
                InvoiceRepositoryFactory.getInstance().create(invoiceTypeWrapper);
            } else {
                return false;
            }
        } catch (Exception e) {
            if (logger.isInfoEnabled()) {
                logger.trace(e);
                return false;
            }
        }
        return true;
    }

    private boolean renderInvoice() {
        try {
            invoiceTypeWrapper = InvoiceRepositoryFactory.getInstance().read(action.getArguments()[0]);
            InvoiceWriter iw = InvoiceWriterFactory.getInstance(invoiceTypeWrapper);
            iw.setOutput(invoiceTypeWrapper.processInvoiceType(InvoiceContextFactory.getInstance().getTemplate()).removeLeftOverKeys());
            iw.write();
        } catch (Exception e) {
            if (logger.isInfoEnabled()) {
                logger.error(e.getMessage());
            }
            return false;
        }
        return true;
    }

    private boolean viewInvoice() {
        String viewer = UserProfile.getInvoiceOutputFormat().getViewer();

        if (viewer == null) {
            logger.info(ResourceManager.getMessage(FileBasedActionHandler.class, "viewerApplicationName"));
            return false;
        }
        String viewableFileName =
                action.getArguments()[0] +
                        InvoiceContextFactory.getInstance().getTemplate().getFinalViewableFormat().getFileSuffix();

        File viewableFile = new File(UserProfile.getInvoiceOutputDir().getPath(), viewableFileName);

        if (viewableFile.exists()) {
            int exitCode;
            try {
                exitCode = Executor.execute(viewer, new String[]{viewableFile.getName()});
            } catch (IOException e) {
                exitCode = -1;
            }
            return (exitCode >= 0);
        } else {
            if (logger.isInfoEnabled()) {
                logger.info(ResourceManager.getMessage(FileBasedActionHandler.class, "viewableFileNotFound", new String[]{viewableFile.getPath()}));
            }
            return false;
        }
    }

    public void postProcess() throws IOException, InvoiceRepositoryException {
        logger.info(ResourceManager.getMessage(FileBasedActionHandler.class, "postProcessMessage", new String[]{action.toString(), DateUtil.getISODateTime()}));
    }

    private boolean runApplication() {
        String applicationName = UserProfile.getInvoiceOutputFormat().getApplication();
        int exitCode = 0;
        if (applicationName != null) {

            try {
                exitCode = Executor.execute(applicationName, new String[]{invoiceTypeWrapper.getInvoiceOutputFileName()});
            } catch (IOException e) {
                if (logger.isInfoEnabled()) {
                    logger.info(e);
                }
                return false;
            }
        }
        if (UserProfile.isInvoiceOutputFileAutoViewEnabled() && exitCode >= 0) {
            viewInvoice();
        }
        return true;
    }

}
