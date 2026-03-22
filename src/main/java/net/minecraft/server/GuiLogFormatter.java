package net.minecraft.server;

import com.legacyminecraft.poseidon.runtime.log.LogFormattingBehaviour;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

class GuiLogFormatter extends Formatter {
    private static final LogFormattingBehaviour LOG_FORMATTING_BEHAVIOUR = LogFormattingBehaviour.getInstance();

    final GuiLogOutputHandler a;

    GuiLogFormatter(GuiLogOutputHandler guilogoutputhandler) {
        this.a = guilogoutputhandler;
    }

    public String format(LogRecord logrecord) {
        return LOG_FORMATTING_BEHAVIOUR.formatGui(logrecord);
    }
}
