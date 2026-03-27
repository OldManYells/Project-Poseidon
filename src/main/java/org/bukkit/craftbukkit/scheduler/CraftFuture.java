package org.bukkit.craftbukkit.scheduler;

import com.legacyminecraft.compat.bukkit.SchedulerFutureLifecycleBehaviour;

import java.util.concurrent.*;

public class CraftFuture<T> implements Runnable, Future<T> {

    private final SchedulerFutureLifecycleBehaviour schedulerFutureLifecycleBehaviour =
            SchedulerFutureLifecycleBehaviour.getInstance();
    private final CraftScheduler craftScheduler;
    private final Callable<T> callable;
    private final ObjectContainer<T> returnStore = new ObjectContainer<T>();
    private final SchedulerFutureLifecycleBehaviour.FutureState futureState =
            new SchedulerFutureLifecycleBehaviour.FutureState();

    CraftFuture(CraftScheduler craftScheduler, Callable callable) {
        this.callable = callable;
        this.craftScheduler = craftScheduler;
    }

    public void run() {
        schedulerFutureLifecycleBehaviour.runCallable(this, futureState, callable, returnStore);
    }

    public T get() throws InterruptedException, ExecutionException {
        try {
            return get(0L, TimeUnit.MILLISECONDS);
        } catch (TimeoutException te) {}
        return null;
    }

    public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return schedulerFutureLifecycleBehaviour.awaitResult(this, futureState, returnStore, timeout, unit);
    }

    public T getResult() throws ExecutionException {
        return schedulerFutureLifecycleBehaviour.getResult(futureState, returnStore);
    }

    public boolean isDone() {
        return schedulerFutureLifecycleBehaviour.isDone(this, futureState);
    }

    public boolean isCancelled() {
        return schedulerFutureLifecycleBehaviour.isCancelled(this, futureState);
    }

    public boolean cancel(boolean mayInterruptIfRunning) {
        return schedulerFutureLifecycleBehaviour.cancel(
                this,
                futureState,
                mayInterruptIfRunning,
                craftScheduler::cancelTask
        );
    }

    public void setTaskId(int taskId) {
        schedulerFutureLifecycleBehaviour.setTaskId(this, futureState, taskId);
    }
}
