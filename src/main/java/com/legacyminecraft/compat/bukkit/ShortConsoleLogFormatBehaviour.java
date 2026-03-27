package com.legacyminecraft.compat.bukkit;

import joptsimple.OptionException;
import joptsimple.OptionSet;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.logging.LogRecord;

/**
 * Canonical behavior for CraftBukkit short-console log date selection and line formatting.
 */
public final class ShortConsoleLogFormatBehaviour {
    private static final ShortConsoleLogFormatBehaviour INSTANCE = new ShortConsoleLogFormatBehaviour();

    private ShortConsoleLogFormatBehaviour() {
    }

    public static ShortConsoleLogFormatBehaviour getInstance() {
        return INSTANCE;
    }

    public SimpleDateFormat resolveDateFormat(OptionSet options) {
        SimpleDateFormat configuredDateFormat = null;

        if (options.has("date-format")) {
            try {
                Object rawDateFormatOption = options.valueOf("date-format");
                if (rawDateFormatOption instanceof SimpleDateFormat) {
                    configuredDateFormat = (SimpleDateFormat) rawDateFormatOption;
                }
            } catch (OptionException optionException) {
                System.err.println("Given date format is not valid. Falling back to default.");
            }
        } else if (options.has("nojline")) {
            configuredDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }

        if (configuredDateFormat == null) {
            configuredDateFormat = new SimpleDateFormat("HH:mm:ss");
        }

        return configuredDateFormat;
    }

    public String formatRecord(LogRecord logRecord, SimpleDateFormat dateFormat) {
        StringBuilder formattedMessage = new StringBuilder();
        Throwable thrownException = logRecord.getThrown();

        formattedMessage.append(dateFormat.format(logRecord.getMillis()));
        formattedMessage.append(" [");
        formattedMessage.append(logRecord.getLevel().getLocalizedName().toUpperCase());
        formattedMessage.append("] ");
        formattedMessage.append(logRecord.getMessage());
        formattedMessage.append('\n');

        if (thrownException != null) {
            StringWriter stackTraceWriter = new StringWriter();
            thrownException.printStackTrace(new PrintWriter(stackTraceWriter));
            formattedMessage.append(stackTraceWriter);
        }

        return formattedMessage.toString();
    }
}

