package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.NetworkWriterLoopSystem;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class NetworkWriterLoopServiceTest {
    @Test
    public void exitsImmediatelyWhenConnectionIsClosed() {
        final int[] threadCounter = new int[]{0};

        NetworkWriterLoopSystem.getInstance().runLoop(
                true,
                new NetworkWriterLoopSystem.WriterLoopOperations() {
                    @Override
                    public void incrementWriterThreadCount() {
                        threadCounter[0]++;
                    }

                    @Override
                    public void decrementWriterThreadCount() {
                        threadCounter[0]--;
                    }

                    @Override
                    public boolean isConnectionOpen() {
                        return false;
                    }

                    @Override
                    public boolean writeNextPacket() {
                        return false;
                    }

                    @Override
                    public void sleepQuietly(long millis) {
                    }

                    @Override
                    public void flushOutput() {
                    }

                    @Override
                    public boolean isShuttingDown() {
                        return false;
                    }

                    @Override
                    public void handleException(Exception exception) {
                    }
                }
        );

        Assert.assertEquals(0, threadCounter[0]);
    }

    @Test
    public void processesOneIterationAndFlushesInFastMode() {
        final int[] threadCounter = new int[]{0};
        final int[] openChecks = new int[]{0};
        final int[] writeCalls = new int[]{0};
        final int[] sleepCalls = new int[]{0};
        final int[] flushCalls = new int[]{0};

        NetworkWriterLoopSystem.getInstance().runLoop(
                true,
                new NetworkWriterLoopSystem.WriterLoopOperations() {
                    @Override
                    public void incrementWriterThreadCount() {
                        threadCounter[0]++;
                    }

                    @Override
                    public void decrementWriterThreadCount() {
                        threadCounter[0]--;
                    }

                    @Override
                    public boolean isConnectionOpen() {
                        return openChecks[0]++ == 0;
                    }

                    @Override
                    public boolean writeNextPacket() {
                        return writeCalls[0]++ == 0;
                    }

                    @Override
                    public void sleepQuietly(long millis) {
                        sleepCalls[0]++;
                        Assert.assertEquals(2L, millis);
                    }

                    @Override
                    public void flushOutput() {
                        flushCalls[0]++;
                    }

                    @Override
                    public boolean isShuttingDown() {
                        return false;
                    }

                    @Override
                    public void handleException(Exception exception) {
                    }
                }
        );

        Assert.assertEquals(2, writeCalls[0]);
        Assert.assertEquals(1, sleepCalls[0]);
        Assert.assertEquals(1, flushCalls[0]);
        Assert.assertEquals(0, threadCounter[0]);
    }

    @Test
    public void reportsFlushErrorsWhenNotShuttingDown() {
        final int[] exceptionReports = new int[]{0};

        NetworkWriterLoopSystem.getInstance().runLoop(
                true,
                new NetworkWriterLoopSystem.WriterLoopOperations() {
                    private boolean firstLoop = true;

                    @Override
                    public void incrementWriterThreadCount() {
                    }

                    @Override
                    public void decrementWriterThreadCount() {
                    }

                    @Override
                    public boolean isConnectionOpen() {
                        if (firstLoop) {
                            firstLoop = false;
                            return true;
                        }
                        return false;
                    }

                    @Override
                    public boolean writeNextPacket() {
                        return false;
                    }

                    @Override
                    public void sleepQuietly(long millis) {
                    }

                    @Override
                    public void flushOutput() throws IOException {
                        throw new IOException("flush failed");
                    }

                    @Override
                    public boolean isShuttingDown() {
                        return false;
                    }

                    @Override
                    public void handleException(Exception exception) {
                        exceptionReports[0]++;
                    }
                }
        );

        Assert.assertEquals(1, exceptionReports[0]);
    }
}
