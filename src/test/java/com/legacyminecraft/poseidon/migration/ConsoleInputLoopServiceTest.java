package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.runtime.ConsoleInputLoopService;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsoleInputLoopServiceTest {
    private static final Logger LOGGER = Logger.getLogger("ConsoleInputLoopServiceTest");

    static {
        LOGGER.setUseParentHandlers(false);
        LOGGER.setLevel(Level.OFF);
    }

    @Test
    public void dispatchesOnlyNonNullCommands() {
        final String[] lines = new String[]{"say test", null};
        final int[] index = new int[]{0};
        final List<String> dispatched = new ArrayList<String>();

        ConsoleInputLoopService.getInstance().runLoop(
                new ConsoleInputLoopService.LineReader() {
                    @Override
                    public String readLine() {
                        return lines[index[0]++];
                    }
                },
                new ConsoleInputLoopService.RunningState() {
                    @Override
                    public boolean shouldContinue() {
                        return index[0] < lines.length;
                    }
                },
                new ConsoleInputLoopService.CommandSink() {
                    @Override
                    public void dispatch(String commandLine) {
                        dispatched.add(commandLine);
                    }
                },
                LOGGER
        );

        Assert.assertEquals(1, dispatched.size());
        Assert.assertEquals("say test", dispatched.get(0));
    }

    @Test
    public void absorbsIoExceptionsWithoutThrowing() {
        final boolean[] reachedSink = new boolean[]{false};

        ConsoleInputLoopService.getInstance().runLoop(
                new ConsoleInputLoopService.LineReader() {
                    @Override
                    public String readLine() throws IOException {
                        throw new IOException("boom");
                    }
                },
                new ConsoleInputLoopService.RunningState() {
                    @Override
                    public boolean shouldContinue() {
                        return true;
                    }
                },
                new ConsoleInputLoopService.CommandSink() {
                    @Override
                    public void dispatch(String commandLine) {
                        reachedSink[0] = true;
                    }
                },
                LOGGER
        );

        Assert.assertFalse(reachedSink[0]);
    }

    @Test
    public void stopsLoopWhenReaderReturnsNullEvenIfRunningStateIsTrue() {
        final int[] readCount = new int[]{0};
        final int[] dispatchCount = new int[]{0};

        ConsoleInputLoopService.getInstance().runLoop(
                new ConsoleInputLoopService.LineReader() {
                    @Override
                    public String readLine() {
                        readCount[0]++;
                        return null;
                    }
                },
                new ConsoleInputLoopService.RunningState() {
                    @Override
                    public boolean shouldContinue() {
                        return true;
                    }
                },
                new ConsoleInputLoopService.CommandSink() {
                    @Override
                    public void dispatch(String commandLine) {
                        dispatchCount[0]++;
                    }
                },
                LOGGER
        );

        Assert.assertEquals(1, readCount[0]);
        Assert.assertEquals(0, dispatchCount[0]);
    }
}
