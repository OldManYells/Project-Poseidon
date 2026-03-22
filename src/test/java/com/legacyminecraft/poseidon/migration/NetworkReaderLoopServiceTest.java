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
                }
        );

        Assert.assertEquals(2, readCalls[0]);
        Assert.assertEquals(1, sleepCalls[0]);
        Assert.assertEquals(0, threadCounter[0]);
    }
}
