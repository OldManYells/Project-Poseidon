package com.legacyminecraft.poseidon.runtime.log;

import javax.swing.JTextArea;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public final class GuiLogOutputBehaviour {
    private static final GuiLogOutputBehaviour INSTANCE = new GuiLogOutputBehaviour();

    private GuiLogOutputBehaviour() {
    }

    public static GuiLogOutputBehaviour getInstance() {
        return INSTANCE;
    }

    public int publish(JTextArea textArea, Formatter formatter, LogRecord record, int[] lengths, int index) {
        int startLength = textArea.getDocument().getLength();

        textArea.append(formatter.format(record));
        textArea.setCaretPosition(textArea.getDocument().getLength());
        int appendedLength = textArea.getDocument().getLength() - startLength;

        if (lengths[index] != 0) {
            textArea.replaceRange("", 0, lengths[index]);
        }

        lengths[index] = appendedLength;
        return (index + 1) % lengths.length;
    }
}
