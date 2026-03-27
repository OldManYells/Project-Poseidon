package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.NetworkReaderLoopSystem;
import org.junit.Assert;
import org.junit.Test;

public class NetworkReaderLoopServiceTest {
    @Test
    public void exitsImmediatelyWhenConnectionIsClosed() {
        final int[] threadCounter = new int[]{0};

        NetworkReaderLoopSystem.getInstance().runLoop(
                true,
                new NetworkReaderLoopSystem.ReaderLoopOperations() {
                    @Override
                    public void incrementReaderThreadCount() {
                        threadCounter[0]++;
                    }

                    @Override
                    public void decrementReaderThreadCount() {
                        threadCounter[0]--;
                    }

                    @Override
                    public boolean isConnectionOpen() {
                        return false;
                    }

                    @Override
                    public boolean isShuttingDown() {
                        return false;
                    }

                    @Override
                    public boolean readNextPacket() {
                        return false;
                    }

                    @Override
                    public void sleepQuietly(long millis) {
                    }

                    @Override
                    public void handleException(Exception exception) {
                    }
                }
        );

        Assert.assertEquals(0, threadCounter[0]);
    }

    @Test
    public void processesOneIterationThenCloses() {
        final int[] threadCounter = new int[]{0};
        final int[] openChecks = new int[]{0};
        final int[] readCalls = new int[]{0};
        final int[] sleepCalls = new int[]{0};

        NetworkReaderLoopSystem.getInstance().runLoop(
                false,
                new NetworkReaderLoopSystem.ReaderLoopOperations() {
                    @Override
                    public void incrementReaderThreadCount() {
                        threadCounter[0]++;
                    }

                    @Override
                    public void decrementReaderThreadCount() {
                        threadCounter[0]--;
                    }

                    @Override
                    public boolean isConnectionOpen() {
                        return openChecks[0]++ == 0;
                    }

                    @Override
                    public boolean isShuttingDown() {
                        return false;
                    }

                    @Override
                    public boolean readNextPacket() {
                        return readCalls[0]++ == 0;
                    }

                    @Override
                    public void sleepQuietly(long millis) {
                        sleepCalls[0]++;
                        Assert.assertEquals(100L, millis);
                    }

                    @Override
                    public void handleException(Exception exception) {
                    }
                }
        );

        Assert.assertEquals(2, readCalls[0]);
        Assert.assertEquals(1, sleepCalls[0]);
        Assert.assertEquals(0, threadCounter[0]);
    }

    @Test
    public void reportsReadErrorsWhenNotShuttingDown() {
        final int[] exceptionReports = new int[]{0};

        NetworkReaderLoopSystem.getInstance().runLoop(
                true,
                new NetworkReaderLoopSystem.ReaderLoopOperations() {
                    private boolean firstLoop = true;

                    @Override
                    public void incrementReaderThreadCount() {
                    }

                    @Override
                    public void decrementReaderThreadCount() {
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
                    public boolean isShuttingDown() {
                        return false;
                    }

                    @Override
                    public boolean readNextPacket() {
                        throw new IllegalStateException("reader failed");
                    }

                    @Override
                    public void sleepQuietly(long millis) {
                    }

                    @Override
                    public void handleException(Exception exception) {
                        exceptionReports[0]++;
                    }
                }
        );

        Assert.assertEquals(1, exceptionReports[0]);
    }

    @Test
    public void suppressesReadErrorsDuringShutdown() {
        final int[] exceptionReports = new int[]{0};
        final int[] shutdownChecks = new int[]{0};

        NetworkReaderLoopSystem.getInstance().runLoop(
                true,
                new NetworkReaderLoopSystem.ReaderLoopOperations() {
                    private boolean firstLoop = true;

                    @Override
                    public void incrementReaderThreadCount() {
                    }

                    @Override
                    public void decrementReaderThreadCount() {
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
                    public boolean isShuttingDown() {
                        return shutdownChecks[0]++ > 0;
                    }

                    @Override
                    public boolean readNextPacket() {
                        throw new IllegalStateException("reader failed");
                    }

                    @Override
                    public void sleepQuietly(long millis) {
                    }

                    @Override
                    public void handleException(Exception exception) {
                        exceptionReports[0]++;
                    }
                }
        );

        Assert.assertEquals(0, exceptionReports[0]);
    }
}
