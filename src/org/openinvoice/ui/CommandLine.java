package org.openinvoice.ui;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.openinvoice.core.action.Action;
import org.openinvoice.core.action.ActionFactory;
import org.openinvoice.core.action.FileBasedActionHandler;
import org.openinvoice.util.ResourceManager;

import java.io.PrintWriter;

/**
 * Author: jhe
 * Date: Aug 27, 2010
 * Time: 5:15:46 PM
 * (C) Copyright 2010 OpenInvoice.org. All rights are reserved.
 */
public class CommandLine {

    public static void main(String[] arguments) throws Exception {
        new CommandLine().process(arguments);
    }

    public void process(String[] arguments) throws Exception {

        Options options = new Options();
        CmdLineParser parser = new CmdLineParser(options);

        try {
            parser.parseArgument(arguments);
            Action action = null;
            if (options.createOption != null) {
                action = ActionFactory.getInstance(Action.CREATE_INV_ACTION, new String[]{options.createOption});
            }
            if (options.deleteOption != null) {
                action = ActionFactory.getInstance(Action.DELETE_INV_ACTION, new String[]{options.deleteOption});
            }
            if (options.renderOption != null) {
                action = ActionFactory.getInstance(Action.RENDER_INV_ACTION, new String[]{options.renderOption});
            }
            if (options.viewOption != null) {
                action = ActionFactory.getInstance(Action.VIEW_INV_ACTION, new String[]{options.viewOption});
            }
            if (action != null) {
                new FileBasedActionHandler().handle(action);
            }
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            parser.printUsage(new PrintWriter(System.out), ResourceManager.getMessagesResourceBundle());
            return;
        }
    }

    public class Options {

        @org.kohsuke.args4j.Option(name = Action.CREATE_INV_ACTION,
                usage = "org.openinvoice.core.action.Action.createActionOptionDescription",
                metaVar = "org.openinvoice.core.action.Action.createActionOptionArgName",
                aliases = Action.CREATE_INV_ACTION_ALIAS)
        public String createOption;

        @org.kohsuke.args4j.Option(name = Action.DELETE_INV_ACTION,
                usage = "org.openinvoice.core.action.Action.deleteActionOptionDescription",
                metaVar = "org.openinvoice.core.action.Action.deleteActionOptionArgName",
                aliases = Action.DELETE_INV_ACTION_ALIAS)
        public String deleteOption;

        @org.kohsuke.args4j.Option(name = Action.VIEW_INV_ACTION,
                usage = "org.openinvoice.core.action.Action.viewActionOptionDescription",
                metaVar = "org.openinvoice.core.action.Action.viewActionOptionArgName",
                aliases = Action.VIEW_INV_ACTION_ALIAS)
        public String viewOption;

        @org.kohsuke.args4j.Option(name = Action.RENDER_INV_ACTION,
                usage = "org.openinvoice.core.action.Action.renderActionOptionDescription",
                metaVar = "org.openinvoice.core.action.Action.renderActionOptionArgName",
                aliases = Action.RENDER_INV_ACTION_ALIAS)
        public String renderOption;
    }
}
