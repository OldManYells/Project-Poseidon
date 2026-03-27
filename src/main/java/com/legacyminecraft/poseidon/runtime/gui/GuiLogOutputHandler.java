package com.legacyminecraft.poseidon.runtime.gui;

import javax.swing.JTextArea;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * GUI-local log handler writing to the server text area.
 */
public class GuiLogOutputHandler extends Handler {
    private final JTextArea output;

    public GuiLogOutputHandler(JTextArea output) {
        this.output = output;
    }

    @Override
    public void publish(LogRecord record) {
        if (record == null || output == null) {
            return;
        }
        output.append(record.getMessage());
        output.append("\n");
    }

    @Override
    public void flush() {
    }

    @Override
    public void close() {
    }
}
