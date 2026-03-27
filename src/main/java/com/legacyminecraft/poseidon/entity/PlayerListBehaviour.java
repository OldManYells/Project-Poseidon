package com.legacyminecraft.poseidon.entity;

import com.legacyminecraft.poseidon.compat.LegacyCompatGatewayRegistry;

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

    public Object get(Object[] table, long key) {
        int hash = this.hashLong(key);

        for (Object entry = table[this.indexFor(hash, table.length)]; entry != null; entry = Bridge.next(entry)) {
            if (Bridge.key(entry) == key) {
                return Bridge.value(entry);
            }
        }

        return null;
    }

    public void updateExistingValues(Object[] table, long key, Object value) {
        int hash = this.hashLong(key);
        int bucket = this.indexFor(hash, table.length);

        for (Object entry = table[bucket]; entry != null; entry = Bridge.next(entry)) {
            if (Bridge.key(entry) == key) {
                Bridge.setValue(entry, value);
            }
        }
    }

    public PutState put(Object[] table, int size, int threshold, int modCount, float loadFactor, long key, Object value) {
        int hash = this.hashLong(key);
        int bucket = this.indexFor(hash, table.length);

        table[bucket] = Bridge.newEntry(hash, key, value, table[bucket]);

        if (size++ >= threshold) {
            ResizeState resizeState = this.resize(table, 2 * table.length, loadFactor, threshold);
            table = resizeState.table;
            threshold = resizeState.threshold;
        }

        return new PutState(table, size, threshold, modCount + 1);
    }

    public RemoveState remove(Object[] table, int size, int modCount, long key) {
        int hash = this.hashLong(key);
        int bucket = this.indexFor(hash, table.length);
        Object previous = table[bucket];

        for (Object current = previous; current != null; current = Bridge.next(current)) {
            Object next = Bridge.next(current);
            if (Bridge.key(current) == key) {
                if (previous == current) {
                    table[bucket] = next;
                } else {
                    Bridge.setNext(previous, next);
                }

                return new RemoveState(table, size - 1, modCount + 1, current);
            }

            previous = current;
        }

        return new RemoveState(table, size, modCount, null);
    }

    private ResizeState resize(Object[] table, int newCapacity, float loadFactor, int currentThreshold) {
        int oldCapacity = table.length;

        if (oldCapacity == 1073741824) {
            return new ResizeState(table, Integer.MAX_VALUE);
        }

        Object[] resized = new Object[newCapacity];
        this.transfer(table, resized);
        return new ResizeState(resized, (int) ((float) newCapacity * loadFactor));
    }

    private void transfer(Object[] source, Object[] target) {
        int newLength = target.length;

        for (int i = 0; i < source.length; ++i) {
            Object entry = source[i];

            if (entry != null) {
                source[i] = null;

                do {
                    Object next = Bridge.next(entry);
                    int bucket = this.indexFor(Bridge.hash(entry), newLength);

                    Bridge.setNext(entry, target[bucket]);
                    target[bucket] = entry;
                    entry = next;
                } while (entry != null);
            }
        }
    }

    public static final class PutState {
        public final Object[] table;
        public final int size;
        public final int threshold;
        public final int modCount;

        PutState(Object[] table, int size, int threshold, int modCount) {
            this.table = table;
            this.size = size;
            this.threshold = threshold;
            this.modCount = modCount;
        }
    }

    public static final class RemoveState {
        public final Object[] table;
        public final int size;
        public final int modCount;
        public final Object removedEntry;

        RemoveState(Object[] table, int size, int modCount, Object removedEntry) {
            this.table = table;
            this.size = size;
            this.modCount = modCount;
            this.removedEntry = removedEntry;
        }
    }

    private static final class ResizeState {
        private final Object[] table;
        private final int threshold;

        private ResizeState(Object[] table, int threshold) {
            this.table = table;
            this.threshold = threshold;
        }
    }

    private static final class Bridge {
        private static Object newEntry(int hash, long key, Object value, Object next) {
            return LegacyCompatGatewayRegistry.gateway().createPlayerListEntry(hash, key, value, next);
        }

        private static long key(Object entry) {
            return ((Long) invoke(entry, "a")).longValue();
        }

        private static Object value(Object entry) {
            return invoke(entry, "b");
        }

        private static int hash(Object entry) {
            return ((Integer) invoke(entry, "c")).intValue();
        }

        private static Object next(Object entry) {
            return invoke(entry, "d");
        }

        private static void setValue(Object entry, Object value) {
            invoke(entry, "a", value);
        }

        private static void setNext(Object entry, Object next) {
            invoke(entry, "a", next);
        }

        private static Object invoke(Object target, String methodName, Object... args) {
            try {
                for (java.lang.reflect.Method method : target.getClass().getMethods()) {
                    if (method.getName().equals(methodName) && method.getParameterTypes().length == args.length) {
                        method.setAccessible(true);
                        return method.invoke(target, args);
                    }
                }
                throw new IllegalStateException("Method not found: " + methodName);
            } catch (Exception exception) {
                throw new IllegalStateException(exception);
            }
        }
    }
}
