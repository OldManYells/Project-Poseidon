package com.legacyminecraft.poseidon.entity;

import net.minecraft.server.PlayerListEntry;

public final class PlayerListBehaviour {
    private static final PlayerListBehaviour INSTANCE = new PlayerListBehaviour();

    private PlayerListBehaviour() {
    }

    public static PlayerListBehaviour getInstance() {
        return INSTANCE;
    }

    public int hashLong(long key) {
        return this.hashInt((int) (key ^ key >>> 32));
    }

    public int hashInt(int value) {
        value ^= value >>> 20 ^ value >>> 12;
        return value ^ value >>> 7 ^ value >>> 4;
    }

    public int indexFor(int hash, int length) {
        return hash & length - 1;
    }

    public Object get(PlayerListEntry[] table, long key) {
        int hash = this.hashLong(key);

        for (PlayerListEntry entry = table[this.indexFor(hash, table.length)]; entry != null; entry = entry.d()) {
            if (entry.a() == key) {
                return entry.b();
            }
        }

        return null;
    }

    public void updateExistingValues(PlayerListEntry[] table, long key, Object value) {
        int hash = this.hashLong(key);
        int bucket = this.indexFor(hash, table.length);

        for (PlayerListEntry entry = table[bucket]; entry != null; entry = entry.d()) {
            if (entry.a() == key) {
                entry.a(value);
            }
        }
    }

    public PutState put(PlayerListEntry[] table, int size, int threshold, int modCount, float loadFactor, long key, Object value) {
        int hash = this.hashLong(key);
        int bucket = this.indexFor(hash, table.length);

        table[bucket] = new PlayerListEntry(hash, key, value, table[bucket]);

        if (size++ >= threshold) {
            ResizeState resizeState = this.resize(table, 2 * table.length, loadFactor, threshold);
            table = resizeState.table;
            threshold = resizeState.threshold;
        }

        return new PutState(table, size, threshold, modCount + 1);
    }

    public RemoveState remove(PlayerListEntry[] table, int size, int modCount, long key) {
        int hash = this.hashLong(key);
        int bucket = this.indexFor(hash, table.length);
        PlayerListEntry previous = table[bucket];

        for (PlayerListEntry current = previous; current != null; current = current.d()) {
            PlayerListEntry next = current.d();
            if (current.a() == key) {
                if (previous == current) {
                    table[bucket] = next;
                } else {
                    previous.a(next);
                }

                return new RemoveState(table, size - 1, modCount + 1, current);
            }

            previous = current;
        }

        return new RemoveState(table, size, modCount, null);
    }

    private ResizeState resize(PlayerListEntry[] table, int newCapacity, float loadFactor, int currentThreshold) {
        int oldCapacity = table.length;

        if (oldCapacity == 1073741824) {
            return new ResizeState(table, Integer.MAX_VALUE);
        }

        PlayerListEntry[] resized = new PlayerListEntry[newCapacity];
        this.transfer(table, resized);
        return new ResizeState(resized, (int) ((float) newCapacity * loadFactor));
    }

    private void transfer(PlayerListEntry[] source, PlayerListEntry[] target) {
        int newLength = target.length;

        for (int i = 0; i < source.length; ++i) {
            PlayerListEntry entry = source[i];

            if (entry != null) {
                source[i] = null;

                do {
                    PlayerListEntry next = entry.d();
                    int bucket = this.indexFor(entry.c(), newLength);

                    entry.a(target[bucket]);
                    target[bucket] = entry;
                    entry = next;
                } while (entry != null);
            }
        }
    }

    public static final class PutState {
        public final PlayerListEntry[] table;
        public final int size;
        public final int threshold;
        public final int modCount;

        PutState(PlayerListEntry[] table, int size, int threshold, int modCount) {
            this.table = table;
            this.size = size;
            this.threshold = threshold;
            this.modCount = modCount;
        }
    }

    public static final class RemoveState {
        public final PlayerListEntry[] table;
        public final int size;
        public final int modCount;
        public final PlayerListEntry removedEntry;

        RemoveState(PlayerListEntry[] table, int size, int modCount, PlayerListEntry removedEntry) {
            this.table = table;
            this.size = size;
            this.modCount = modCount;
            this.removedEntry = removedEntry;
        }
    }

    private static final class ResizeState {
        private final PlayerListEntry[] table;
        private final int threshold;

        private ResizeState(PlayerListEntry[] table, int threshold) {
            this.table = table;
            this.threshold = threshold;
        }
    }
}
