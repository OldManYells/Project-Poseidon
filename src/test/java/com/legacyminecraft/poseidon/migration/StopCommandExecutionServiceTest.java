package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.runtime.StopCommandExecutionService;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class StopCommandExecutionServiceTest {
    @Test
    public void executeStopCommandRunsShutdownFlowAndSchedulesFinalStop() {
        StopCommandExecutionService service = StopCommandExecutionService.getInstance();
        RecordingActions actions = new RecordingActions();

        service.executeStopCommand(actions, "Server closing");

        Assert.assertEquals("Starting Server Shutdown, Saving Data.", actions.broadcastMessages.get(0));
        Assert.assertTrue(actions.shuttingDown);
        Assert.assertEquals("Server closing", actions.kickMessage);
        Assert.assertTrue(actions.savedWorlds);
        Assert.assertEquals(100L, actions.scheduledDelayTicks);
        Assert.assertNotNull(actions.scheduledTask);

        actions.scheduledTask.run();

        Assert.assertEquals("Stopping the server..", actions.broadcastMessages.get(1));
        Assert.assertTrue(actions.shutdownNowCalled);
    }

    private static final class RecordingActions implements StopCommandExecutionService.ShutdownActions {
        private final List broadcastMessages = new ArrayList();
        private boolean shuttingDown;
        private String kickMessage;
        private boolean savedWorlds;
        private Runnable scheduledTask;
        private long scheduledDelayTicks;
        private boolean shutdownNowCalled;

        @Override
        public void broadcast(String message) {
            broadcastMessages.add(message);
        }

        @Override
        public void setShuttingDown(boolean shuttingDown) {
            this.shuttingDown = shuttingDown;
        }

        @Override
        public void saveAndKickPlayers(String kickMessage) {
            this.kickMessage = kickMessage;
        }

        @Override
        public void saveWorlds() {
            this.savedWorlds = true;
        }

        @Override
        public void scheduleFinalStop(Runnable task, long delayTicks) {
            this.scheduledTask = task;
            this.scheduledDelayTicks = delayTicks;
        }

        @Override
        public void shutdownNow() {
            this.shutdownNowCalled = true;
        }
    }
}
