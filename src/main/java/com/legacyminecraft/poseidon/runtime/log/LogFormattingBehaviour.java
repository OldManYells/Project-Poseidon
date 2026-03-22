package com.legacyminecraft.poseidon.runtime.log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public final class LogFormattingBehaviour {
    private static final LogFormattingBehaviour INSTANCE = new LogFormattingBehaviour();

    private LogFormattingBehaviour() {
    }

    public static LogFormattingBehaviour getInstance() {
        return INSTANCE;
    }

    public String formatConsole(LogRecord logrecord, SimpleDateFormat dateFormat) {
        StringBuilder stringbuilder = new StringBuilder();

        stringbuilder.append(dateFormat.format(Long.valueOf(logrecord.getMillis())));
        appendLevelLabel(stringbuilder, logrecord.getLevel(), true);
        stringbuilder.append(logrecord.getMessage());
        stringbuilder.append('\n');
        appendThrowable(stringbuilder, logrecord.getThrown());

        return stringbuilder.toString();
    }

    public String formatGui(LogRecord logrecord) {
        StringBuilder stringbuilder = new StringBuilder();

        appendLevelLabel(stringbuilder, logrecord.getLevel(), false);
        stringbuilder.append(logrecord.getMessage());
        stringbuilder.append('\n');
        appendThrowable(stringbuilder, logrecord.getThrown());

        return stringbuilder.toString();
    }

    private static void appendLevelLabel(StringBuilder out, Level level, boolean withLeadingSpace) {
        String prefix = withLeadingSpace ? " [" : "[";

        if (level == Level.FINEST) {
            out.append(prefix).append("FINEST] ");
        } else if (level == Level.FINER) {
            out.append(prefix).append("FINER] ");
        } else if (level == Level.FINE) {
            out.append(prefix).append("FINE] ");
        } else if (level == Level.INFO) {
            out.append(prefix).append("INFO] ");
        } else if (level == Level.WARNING) {
            out.append(prefix).append("WARNING] ");
        } else if (level == Level.SEVERE) {
            out.append(prefix).append("SEVERE] ");
        } else {
            out.append(prefix).append(level.getLocalizedName()).append("] ");
        }
    }

    private static void appendThrowable(StringBuilder out, Throwable throwable) {
        if (throwable != null) {
            StringWriter stringwriter = new StringWriter();
            throwable.printStackTrace(new PrintWriter(stringwriter));
            out.append(stringwriter.toString());
        }
    }
}
