package com.legacyminecraft.poseidon.compat.bukkit;

import java.util.LinkedList;

/**
 * Canonical behavior for promoting values into the strong-reference queue used by soft-map wrappers.
 */
public final class SoftMapStrongReferenceQueueBehaviour {
    private static final SoftMapStrongReferenceQueueBehaviour INSTANCE = new SoftMapStrongReferenceQueueBehaviour();

    private SoftMapStrongReferenceQueueBehaviour() {
    }

    public static SoftMapStrongReferenceQueueBehaviour getInstance() {
        return INSTANCE;
    }

    public <V> void promote(LinkedList<V> strongReferenceQueue, V value, int maxStrongReferences) {
        strongReferenceQueue.addFirst(value);
        if (strongReferenceQueue.size() > maxStrongReferences) {
            strongReferenceQueue.removeLast();
        }
    }
}

