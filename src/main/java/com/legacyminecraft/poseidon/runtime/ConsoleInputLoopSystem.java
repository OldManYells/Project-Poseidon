package com.legacyminecraft.poseidon.runtime;

import java.util.logging.Logger;

/**
 * Role-aligned canonical facade for console input loop orchestration.
 */
public final class ConsoleInputLoopSystem {
    private static final ConsoleInputLoopSystem INSTANCE = new ConsoleInputLoopSystem();
    private final ConsoleInputLoopService delegate = ConsoleInputLoopService.getInstance();

    private ConsoleInputLoopSystem() {
    }

    public static ConsoleInputLoopSystem getInstance() {
        return INSTANCE;
    }

    public void runLoop(
            final LineReader lineReader,
            final RunningState runningState,
            final CommandSink commandSink,
            Logger logger
    ) {
        delegate.runLoop(
                new ConsoleInputLoopService.LineReader() {
                    @Override
                    public String readLine() throws java.io.IOException {
                        return lineReader.readLine();
                    }
                },
                new ConsoleInputLoopService.RunningState() {
                    @Override
                    public boolean shouldContinue() {
                        return runningState.shouldContinue();
                    }
                },
                new ConsoleInputLoopService.CommandSink() {
                    @Override
                    public void dispatch(String commandLine) {
                        commandSink.dispatch(commandLine);
                    }
                },
                logger
        );
    }

    public interface LineReader {
        String readLine() throws java.io.IOException;
    }

    public interface RunningState {
        boolean shouldContinue();
    }

    public interface CommandSink {
        void dispatch(String commandLine);
    }
}
