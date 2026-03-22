package org.bukkit.craftbukkit.util;

import com.legacyminecraft.poseidon.compat.bukkit.TerminalConsoleFlushBehaviour;
import jline.ConsoleReader;
import org.bukkit.craftbukkit.Main;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TerminalConsoleHandler extends ConsoleHandler {
    private final ConsoleReader reader;
    private final TerminalConsoleFlushBehaviour terminalConsoleFlushBehaviour = TerminalConsoleFlushBehaviour.getInstance();

    public TerminalConsoleHandler(ConsoleReader reader) {
        super();
        this.reader = reader;
    }

    @Override
    public synchronized void flush() {
        try {
            terminalConsoleFlushBehaviour.flush(this.reader, Main.useJline, new TerminalConsoleFlushBehaviour.FlushCallbacks() {
                @Override
                public void flushParent() {
                    TerminalConsoleHandler.super.flush();
                }
            });
        } catch (IOException ex) {
            Logger.getLogger(TerminalConsoleHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
