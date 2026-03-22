package com.legacyminecraft.poseidon.compat.bukkit;

import jline.ConsoleReader;

import java.io.IOException;

/**
 * Canonical behavior for CraftBukkit terminal console flush flow with optional JLine redraw.
 */
public final class TerminalConsoleFlushBehaviour {
    private static final TerminalConsoleFlushBehaviour INSTANCE = new TerminalConsoleFlushBehaviour();

    private TerminalConsoleFlushBehaviour() {
    }

    public static TerminalConsoleFlushBehaviour getInstance() {
        return INSTANCE;
    }

    public void flush(ConsoleReader reader, boolean useJline, FlushCallbacks flushCallbacks) throws IOException {
        if (useJline) {
            reader.printString(ConsoleReader.RESET_LINE + "");
            reader.flushConsole();
            flushCallbacks.flushParent();
            redrawLineOrClear(reader);
            reader.flushConsole();
            return;
        }

        flushCallbacks.flushParent();
    }

    private void redrawLineOrClear(ConsoleReader reader) {
        try {
            reader.drawLine();
        } catch (Throwable throwable) {
            reader.getCursorBuffer().clearBuffer();
        }
    }

    public interface FlushCallbacks {
        void flushParent();
    }
}

