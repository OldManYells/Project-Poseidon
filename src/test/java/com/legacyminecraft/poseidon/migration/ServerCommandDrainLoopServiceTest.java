package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.runtime.ServerCommandDrainLoopService;
import org.junit.Assert;
import org.junit.Test;

public class ServerCommandDrainLoopServiceTest {
    @Test
    public void drainLoopInvokesDrainUntilControlStops() {
        ServerCommandDrainLoopService service = ServerCommandDrainLoopService.getInstance();
        CountingLoopControl loopControl = new CountingLoopControl(3);
        CountingSleeper sleeper = new CountingSleeper();

        service.drainUntilStopped(loopControl, sleeper, 10L);

        Assert.assertEquals(3, loopControl.getDrainCount());
        Assert.assertEquals(3, sleeper.getSleepCount());
        Assert.assertEquals(0, loopControl.getInterruptedCount());
    }

    @Test
    public void interruptedSleepIsReportedAndLoopContinues() {
        ServerCommandDrainLoopService service = ServerCommandDrainLoopService.getInstance();
        CountingLoopControl loopControl = new CountingLoopControl(2);
        InterruptingSleeper sleeper = new InterruptingSleeper();

        service.drainUntilStopped(loopControl, sleeper, 10L);

        Assert.assertEquals(2, loopControl.getDrainCount());
        Assert.assertEquals(1, loopControl.getInterruptedCount());
        Assert.assertEquals(2, sleeper.getSleepCount());
    }

    private static final class CountingLoopControl implements ServerCommandDrainLoopService.LoopControl {
        private final int drainIterationsBeforeStop;
        private int drainCount;
        private int interruptedCount;

        private CountingLoopControl(int drainIterationsBeforeStop) {
            this.drainIterationsBeforeStop = drainIterationsBeforeStop;
        }

        @Override
        public boolean isRunning() {
            return drainCount < drainIterationsBeforeStop;
        }

        @Override
        public void drainCommands() {
            drainCount++;
        }

        @Override
        public void handleInterruptedSleep(InterruptedException interruptedException) {
            interruptedCount++;
        }

        public int getDrainCount() {
            return drainCount;
        }

        public int getInterruptedCount() {
            return interruptedCount;
        }
    }

    private static final class CountingSleeper implements ServerCommandDrainLoopService.Sleeper {
        private int sleepCount;

        @Override
        public void sleep(long millis) {
            sleepCount++;
        }

        public int getSleepCount() {
            return sleepCount;
        }
    }

    private static final class InterruptingSleeper implements ServerCommandDrainLoopService.Sleeper {
        private int sleepCount;

        @Override
        public void sleep(long millis) throws InterruptedException {
            sleepCount++;
            if (sleepCount == 1) {
                throw new InterruptedException("test");
            }
        }

        public int getSleepCount() {
            return sleepCount;
        }
    }
}
