package com.legacyminecraft.compat.bukkit;


import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.IntConsumer;

/**
 * Canonical behavior for CraftBukkit scheduler future lifecycle state transitions.
 */
public final class SchedulerFutureLifecycleBehaviour {
    private static final SchedulerFutureLifecycleBehaviour INSTANCE = new SchedulerFutureLifecycleBehaviour();

    private SchedulerFutureLifecycleBehaviour() {
    }

    public static SchedulerFutureLifecycleBehaviour getInstance() {
        return INSTANCE;
    }

    public <T> void runCallable(
            Object monitor,
            FutureState state,
            Callable<T> callable,
            ObjectContainer<T> returnStore
    ) {
        synchronized (monitor) {
            if (state.isCancelled()) {
                return;
            }
            state.setRunning(true);
        }

        try {
            returnStore.setObject(callable.call());
        } catch (Exception exception) {
            state.setException(exception);
        }

        synchronized (monitor) {
            state.setRunning(false);
            state.setDone(true);
            monitor.notify();
        }
    }

    public <T> T getResult(FutureState state, ObjectContainer<T> returnStore) throws ExecutionException {
        if (state.isCancelled()) {
            throw new CancellationException();
        }
        if (state.getException() != null) {
            throw new ExecutionException(state.getException());
        }
        return returnStore.getObject();
    }

    public <T> T awaitResult(
            Object monitor,
            FutureState state,
            ObjectContainer<T> returnStore,
            long timeout,
            TimeUnit unit
    ) throws InterruptedException, ExecutionException, TimeoutException {
        synchronized (monitor) {
            if (state.isDone()) {
                return getResult(state, returnStore);
            }
            monitor.wait(TimeUnit.MILLISECONDS.convert(timeout, unit));
            return getResult(state, returnStore);
        }
    }

    public boolean cancel(
            Object monitor,
            FutureState state,
            boolean mayInterruptIfRunning,
            IntConsumer taskCanceller
    ) {
        synchronized (monitor) {
            if (state.isCancelled()) {
                return false;
            }
            state.setCancelled(true);
            if (state.getTaskId() != -1) {
                taskCanceller.accept(state.getTaskId());
            }
            return !state.isRunning() && !state.isDone();
        }
    }

    public boolean isDone(Object monitor, FutureState state) {
        synchronized (monitor) {
            return state.isDone();
        }
    }

    public boolean isCancelled(Object monitor, FutureState state) {
        synchronized (monitor) {
            return state.isCancelled();
        }
    }

    public void setTaskId(Object monitor, FutureState state, int taskId) {
        synchronized (monitor) {
            state.setTaskId(taskId);
        }
    }

    public static final class FutureState {
        private boolean done;
        private boolean running;
        private boolean cancelled;
        private Exception exception;
        private int taskId = -1;

        public boolean isDone() {
            return done;
        }

        public void setDone(boolean done) {
            this.done = done;
        }

        public boolean isRunning() {
            return running;
        }

        public void setRunning(boolean running) {
            this.running = running;
        }

        public boolean isCancelled() {
            return cancelled;
        }

        public void setCancelled(boolean cancelled) {
            this.cancelled = cancelled;
        }

        public Exception getException() {
            return exception;
        }

        public void setException(Exception exception) {
            this.exception = exception;
        }

        public int getTaskId() {
            return taskId;
        }

        public void setTaskId(int taskId) {
            this.taskId = taskId;
        }
    }
}

