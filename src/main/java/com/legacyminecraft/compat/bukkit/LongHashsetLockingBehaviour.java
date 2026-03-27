package com.legacyminecraft.compat.bukkit;

import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

/**
 * Canonical behaviour for LongHashset read/write lock orchestration.
 */
public final class LongHashsetLockingBehaviour {
    private static final LongHashsetLockingBehaviour INSTANCE = new LongHashsetLockingBehaviour();

    private LongHashsetLockingBehaviour() {
    }

    public static LongHashsetLockingBehaviour getInstance() {
        return INSTANCE;
    }

    public <T> T withReadLock(ReadLock readLock, ValueOperation<T> operation) {
        readLock.lock();
        try {
            return operation.execute();
        } finally {
            readLock.unlock();
        }
    }

    public <T> T withWriteLock(WriteLock writeLock, ValueOperation<T> operation) {
        writeLock.lock();
        try {
            return operation.execute();
        } finally {
            writeLock.unlock();
        }
    }

    public interface ValueOperation<T> {
        T execute();
    }
}
