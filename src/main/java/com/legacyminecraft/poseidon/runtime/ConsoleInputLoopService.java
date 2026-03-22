package com.legacyminecraft.poseidon.runtime;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Canonical read-loop for console command input pumping.
 */
public final class ConsoleInputLoopService {
    private static final ConsoleInputLoopService INSTANCE = new ConsoleInputLoopService();

    private ConsoleInputLoopService() {
    }

    public static ConsoleInputLoopService getInstance() {
        return INSTANCE;
    }

    public void runLoop(LineReader lineReader, RunningState runningState, CommandSink commandSink, Logger logger) {
        try {
            while (runningState.shouldContinue()) {
                String line = lineReader.readLine();
                if (line == null) {
                    // EOF or closed console stream: stop pumping commands.
                    break;
                }
                commandSink.dispatch(line);
            }
        } catch (IOException ioexception) {
            logger.log(Level.SEVERE, null, ioexception);
        }
    }

    public interface LineReader {
        String readLine() throws IOException;
    }

    public interface RunningState {
        boolean shouldContinue();
    }

    public interface CommandSink {
        void dispatch(String commandLine);
    }
}
