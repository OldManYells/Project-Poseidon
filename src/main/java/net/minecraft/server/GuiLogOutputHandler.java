package net.minecraft.server;

import com.legacyminecraft.poseidon.runtime.log.GuiLogOutputBehaviour;

import javax.swing.*;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class GuiLogOutputHandler extends Handler {
    private static final GuiLogOutputBehaviour GUI_LOG_OUTPUT_BEHAVIOUR = GuiLogOutputBehaviour.getInstance();

    private int[] b = new int[1024];
    private int c = 0;
    Formatter a = new GuiLogFormatter(this);
    private JTextArea d;

    public GuiLogOutputHandler(JTextArea jtextarea) {
        this.setFormatter(this.a);
        this.d = jtextarea;
    }

    public void close() {}

    public void flush() {}

    public void publish(LogRecord logrecord) {
        this.c = GUI_LOG_OUTPUT_BEHAVIOUR.publish(this.d, this.a, logrecord, this.b, this.c);
    }
}
