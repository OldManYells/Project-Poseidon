package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftSchedulerSupportWrappersThinnessTest {
    private static final Path CRAFT_TASK_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/scheduler/CraftTask.java");
    private static final Path CRAFT_FUTURE_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/scheduler/CraftFuture.java");
    private static final Path CRAFT_THREAD_MANAGER_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/scheduler/CraftThreadManager.java");
    private static final Path CRAFT_WORKER_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/scheduler/CraftWorker.java");
    private static final Path OBJECT_CONTAINER_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/scheduler/ObjectContainer.java");

    @Test
    public void craftTaskDelegatesIdentityAndOrderingRulesToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_TASK_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("SchedulerTaskIdentityBehaviour"));
        Assert.assertTrue(text.contains("schedulerTaskIdentityBehaviour"));
        Assert.assertTrue(text.contains("schedulerTaskIdentityBehaviour.nextTaskId()"));
        Assert.assertTrue(text.contains("schedulerTaskIdentityBehaviour.compareByExecutionThenId("));
        Assert.assertFalse(text.contains("private static Integer idCounter"));
        Assert.assertFalse(text.contains("idCounterSync"));
        Assert.assertFalse(text.contains("idCounter++"));
    }

    @Test
    public void craftFutureDelegatesLifecycleStateToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_FUTURE_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("SchedulerFutureLifecycleBehaviour"));
        Assert.assertTrue(text.contains("schedulerFutureLifecycleBehaviour"));
        Assert.assertTrue(text.contains("FutureState"));
        Assert.assertTrue(text.contains("schedulerFutureLifecycleBehaviour.runCallable("));
        Assert.assertTrue(text.contains("schedulerFutureLifecycleBehaviour.awaitResult("));
        Assert.assertTrue(text.contains("schedulerFutureLifecycleBehaviour.cancel("));
        Assert.assertFalse(text.contains("private boolean done = false;"));
        Assert.assertFalse(text.contains("private boolean running = false;"));
        Assert.assertFalse(text.contains("private boolean cancelled = false;"));
        Assert.assertFalse(text.contains("private Exception e = null;"));
        Assert.assertFalse(text.contains("private int taskId = -1;"));
        Assert.assertFalse(text.contains("this.wait(TimeUnit.MILLISECONDS.convert(timeout, unit));"));
    }

    @Test
    public void craftThreadManagerDelegatesWorkerRegistryOperationsToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_THREAD_MANAGER_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("SchedulerWorkerRegistryBehaviour"));
        Assert.assertTrue(text.contains("schedulerWorkerRegistryBehaviour"));
        Assert.assertTrue(text.contains("schedulerWorkerRegistryBehaviour.registerWorker("));
        Assert.assertTrue(text.contains("schedulerWorkerRegistryBehaviour.interruptTask("));
        Assert.assertTrue(text.contains("schedulerWorkerRegistryBehaviour.interruptTasks("));
        Assert.assertTrue(text.contains("schedulerWorkerRegistryBehaviour.interruptAllTasks("));
        Assert.assertTrue(text.contains("schedulerWorkerRegistryBehaviour.isAlive("));
        Assert.assertFalse(text.contains("Iterator<CraftWorker>"));
        Assert.assertFalse(text.contains("while (itr.hasNext())"));
    }

    @Test
    public void craftWorkerDelegatesLifecycleAndIdentityRulesToCanonicalBehaviours() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_WORKER_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("SchedulerWorkerIdentityBehaviour"));
        Assert.assertTrue(text.contains("SchedulerWorkerLifecycleBehaviour"));
        Assert.assertTrue(text.contains("schedulerWorkerIdentityBehaviour"));
        Assert.assertTrue(text.contains("schedulerWorkerLifecycleBehaviour"));
        Assert.assertTrue(text.contains("schedulerWorkerLifecycleBehaviour.startWorkerThread(this)"));
        Assert.assertTrue(text.contains("schedulerWorkerLifecycleBehaviour.executeWithCleanup("));
        Assert.assertFalse(text.contains("new Thread(this);"));
        Assert.assertFalse(text.contains("e.printStackTrace();"));
        Assert.assertFalse(text.contains("parent.workers.remove(this);"));
        Assert.assertFalse(text.contains("hashIdCounterSync"));
    }

    @Test
    public void objectContainerDelegatesStorageToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(OBJECT_CONTAINER_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("SchedulerObjectContainerBehaviour"));
        Assert.assertTrue(text.contains("schedulerObjectContainerBehaviour"));
        Assert.assertTrue(text.contains("AtomicReference"));
        Assert.assertTrue(text.contains("schedulerObjectContainerBehaviour.setObject("));
        Assert.assertTrue(text.contains("schedulerObjectContainerBehaviour.getObject("));
        Assert.assertFalse(text.contains("T object;"));
        Assert.assertFalse(text.contains("return object;"));
        Assert.assertFalse(text.contains("this.object = object;"));
    }
}

