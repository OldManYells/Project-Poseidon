package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftSchedulerWrapperThinnessTest {
    private static final Path CRAFT_SCHEDULER_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/scheduler/CraftScheduler.java");
    private static final Path CRAFT_SCHEDULER_MAIN_THREAD_HEARTBEAT_SYSTEM_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftSchedulerMainThreadHeartbeatSystem.java");
    private static final Path CRAFT_SCHEDULER_RUN_LOOP_SYSTEM_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftSchedulerRunLoopSystem.java");
    private static final Path CRAFT_SCHEDULER_TASK_CANCELLATION_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/SchedulerTaskCancellationBehaviour.java");
    private static final Path CRAFT_SCHEDULER_TASK_INSPECTION_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/SchedulerTaskInspectionBehaviour.java");

    @Test
    public void craftSchedulerDelegatesRunLoopOrchestrationToCanonicalSystem() throws IOException {
        String craftSchedulerText = read(CRAFT_SCHEDULER_PATH);
        String heartbeatSystemText = read(CRAFT_SCHEDULER_MAIN_THREAD_HEARTBEAT_SYSTEM_PATH);
        String runLoopSystemText = read(CRAFT_SCHEDULER_RUN_LOOP_SYSTEM_PATH);
        String cancellationBehaviourText = read(CRAFT_SCHEDULER_TASK_CANCELLATION_BEHAVIOUR_PATH);
        String inspectionBehaviourText = read(CRAFT_SCHEDULER_TASK_INSPECTION_BEHAVIOUR_PATH);

        Assert.assertTrue(craftSchedulerText.contains("CraftSchedulerRunLoopSystem"));
        Assert.assertTrue(craftSchedulerText.contains("CraftSchedulerMainThreadHeartbeatSystem"));
        Assert.assertTrue(craftSchedulerText.contains("SchedulerTaskCancellationBehaviour"));
        Assert.assertTrue(craftSchedulerText.contains("SchedulerTaskInspectionBehaviour"));
        Assert.assertTrue(craftSchedulerText.contains("craftSchedulerRunLoopSystem.runLoop"));
        Assert.assertTrue(craftSchedulerText.contains("craftSchedulerMainThreadHeartbeatSystem.runHeartbeat"));
        Assert.assertTrue(craftSchedulerText.contains("schedulerTaskCancellationBehaviour.cancelTask("));
        Assert.assertTrue(craftSchedulerText.contains("schedulerTaskCancellationBehaviour.cancelTasks("));
        Assert.assertTrue(craftSchedulerText.contains("schedulerTaskCancellationBehaviour.cancelAllTasks("));
        Assert.assertTrue(craftSchedulerText.contains("schedulerTaskInspectionBehaviour.isCurrentlyRunning("));
        Assert.assertTrue(craftSchedulerText.contains("schedulerTaskInspectionBehaviour.isQueued("));
        Assert.assertTrue(craftSchedulerText.contains("schedulerTaskInspectionBehaviour.getActiveWorkers("));
        Assert.assertTrue(craftSchedulerText.contains("schedulerTaskInspectionBehaviour.getPendingTasks("));
        Assert.assertFalse(craftSchedulerText.contains("Iterator<CraftTask> itr = schedulerQueue.keySet().iterator();"));
        Assert.assertFalse(craftSchedulerText.contains("current.getIdNumber() == taskId"));
        Assert.assertFalse(craftSchedulerText.contains("current.getOwner().equals(plugin)"));
        Assert.assertFalse(craftSchedulerText.contains("craftThreadManager.isAlive(taskId)"));
        Assert.assertFalse(craftSchedulerText.contains("workerList.add((BukkitWorker) itr.next())"));
        Assert.assertFalse(craftSchedulerText.contains("taskList = new ArrayList<CraftTask>"));

        Assert.assertTrue(heartbeatSystemText.contains("MainThreadHeartbeatAccess"));
        Assert.assertTrue(heartbeatSystemText.contains("while (!access.isSyncedTasksEmpty()"));
        Assert.assertTrue(heartbeatSystemText.contains("access.transferMainThreadQueueToSyncedTasks();"));
        Assert.assertTrue(heartbeatSystemText.contains("access.runSyncedTask(task);"));
        Assert.assertTrue(heartbeatSystemText.contains("taskPerformance.computeIfAbsent"));
        Assert.assertTrue(heartbeatSystemText.contains("access.getServerLogger().log(Level.WARNING"));
        Assert.assertTrue(heartbeatSystemText.contains("access.getTaskLogger().log("));

        Assert.assertTrue(runLoopSystemText.contains("SchedulerRunLoopAccess"));
        Assert.assertTrue(runLoopSystemText.contains("while (true)"));
        Assert.assertTrue(runLoopSystemText.contains("access.removeScheduledTask(first);"));
        Assert.assertTrue(runLoopSystemText.contains("access.processTask(first);"));
        Assert.assertTrue(runLoopSystemText.contains("access.enqueueScheduledTask(first);"));
        Assert.assertTrue(runLoopSystemText.contains("access.getSchedulerQueueMonitor().wait(sleepTime);"));

        Assert.assertTrue(cancellationBehaviourText.contains("removeMatchingTasks"));
        Assert.assertTrue(cancellationBehaviourText.contains("interruptMatchingWorkers"));
        Assert.assertTrue(cancellationBehaviourText.contains("worker.getTaskId() == taskId"));
        Assert.assertTrue(cancellationBehaviourText.contains("worker.getOwner().equals(plugin)"));
        Assert.assertTrue(cancellationBehaviourText.contains("worker.interrupt();"));
        Assert.assertFalse(cancellationBehaviourText.contains("CraftScheduler"));
        Assert.assertFalse(cancellationBehaviourText.contains("while (itr.hasNext())"));

        Assert.assertTrue(inspectionBehaviourText.contains("isCurrentlyRunning(Set<CraftWorker> workers, int taskId)"));
        Assert.assertTrue(inspectionBehaviourText.contains("getActiveWorkers(Set<CraftWorker> workers)"));
        Assert.assertTrue(inspectionBehaviourText.contains("getPendingTasks("));
        Assert.assertTrue(inspectionBehaviourText.contains("worker.isAlive()"));
        Assert.assertFalse(inspectionBehaviourText.contains("CraftScheduler"));
        Assert.assertFalse(inspectionBehaviourText.contains("Iterator<CraftTask> itr"));
    }

    private static String read(Path path) throws IOException {
        return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
    }
}
