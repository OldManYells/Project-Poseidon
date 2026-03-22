package net.minecraft.server;

import com.legacyminecraft.poseidon.runtime.log.LogFormattingBehaviour;

import java.text.SimpleDateFormat;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

final class ConsoleLogFormatter extends Formatter {
    private static final LogFormattingBehaviour LOG_FORMATTING_BEHAVIOUR = LogFormattingBehaviour.getInstance();

    private SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    ConsoleLogFormatter() {}

    public String format(LogRecord logrecord) {
        return LOG_FORMATTING_BEHAVIOUR.formatConsole(logrecord, this.a);
    }
}
