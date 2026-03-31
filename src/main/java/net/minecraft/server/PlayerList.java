package net.minecraft.server;

public class PlayerList {

    private transient PlayerListEntry[] entries = new PlayerListEntry[16];
    private transient int size;
    private int threshold = 12;
    private final float loadFactor = 0.75F;
    private transient volatile int modCount;

    public PlayerList() {}

    private static int computeHash(long key) {
        return hash((int) (key ^ key >>> 32));
    }

    private static int hash(int value) {
        value ^= value >>> 20 ^ value >>> 12;
        return value ^ value >>> 7 ^ value >>> 4;
    }

    private static int indexFor(int hash, int length) {
        return hash & length - 1;
    }

    public Object get(long key) {
        int hash = computeHash(key);

        for (PlayerListEntry entry = this.entries[indexFor(hash, this.entries.length)]; entry != null; entry = entry.c) {
            if (entry.a == key) {
                return entry.b;
            }
        }

        return null;
    }

    public void put(long key, Object value) {
        int hash = computeHash(key);
        int bucketIndex = indexFor(hash, this.entries.length);

        for (PlayerListEntry entry = this.entries[bucketIndex]; entry != null; entry = entry.c) {
            if (entry.a == key) {
                entry.b = value;
            }
        }

        ++this.modCount;
        this.addEntry(hash, key, value, bucketIndex);
    }

    private void resize(int newCapacity) {
        PlayerListEntry[] oldEntries = this.entries;
        int oldCapacity = oldEntries.length;

        if (oldCapacity == 1073741824) {
            this.threshold = Integer.MAX_VALUE;
        } else {
            PlayerListEntry[] newEntries = new PlayerListEntry[newCapacity];
            this.transferEntries(newEntries);
            this.entries = newEntries;
            this.threshold = (int) ((float) newCapacity * this.loadFactor);
        }
    }

    private void transferEntries(PlayerListEntry[] newEntries) {
        PlayerListEntry[] oldEntries = this.entries;
        int newLength = newEntries.length;

        for (int bucket = 0; bucket < oldEntries.length; ++bucket) {
            PlayerListEntry entry = oldEntries[bucket];

            if (entry != null) {
                oldEntries[bucket] = null;

                PlayerListEntry nextEntry;
                do {
                    nextEntry = entry.c;
                    int newBucketIndex = indexFor(entry.d, newLength);
                    entry.c = newEntries[newBucketIndex];
                    newEntries[newBucketIndex] = entry;
                    entry = nextEntry;
                } while (nextEntry != null);
            }
        }
    }

    public Object remove(long key) {
        PlayerListEntry entry = this.removeEntry(key);
        return entry == null ? null : entry.b;
    }

    final PlayerListEntry removeEntry(long key) {
        int hash = computeHash(key);
        int bucketIndex = indexFor(hash, this.entries.length);
        PlayerListEntry previous = this.entries[bucketIndex];

        PlayerListEntry current;
        PlayerListEntry next;
        for (current = previous; current != null; current = next) {
            next = current.c;
            if (current.a == key) {
                ++this.modCount;
                --this.size;
                if (previous == current) {
                    this.entries[bucketIndex] = next;
                } else {
                    previous.c = next;
                }

                return current;
            }

            previous = current;
        }

        return current;
    }

    private void addEntry(int hash, long key, Object value, int bucketIndex) {
        PlayerListEntry existingHead = this.entries[bucketIndex];
        this.entries[bucketIndex] = new PlayerListEntry(hash, key, value, existingHead);
        if (this.size++ >= this.threshold) {
            this.resize(2 * this.entries.length);
        }
    }

    @Deprecated
    private static int e(long key) {
        return computeHash(key);
    }

    @Deprecated
    private static int a(int value) {
        return hash(value);
    }

    @Deprecated
    private static int a(int hash, int length) {
        return indexFor(hash, length);
    }

    @Deprecated
    public Object a(long key) {
        return this.get(key);
    }

    @Deprecated
    public void a(long key, Object value) {
        this.put(key, value);
    }

    @Deprecated
    private void b(int newCapacity) {
        this.resize(newCapacity);
    }

    @Deprecated
    private void a(PlayerListEntry[] newEntries) {
        this.transferEntries(newEntries);
    }

    @Deprecated
    public Object b(long key) {
        return this.remove(key);
    }

    @Deprecated
    final PlayerListEntry c(long key) {
        return this.removeEntry(key);
    }

    @Deprecated
    private void a(int hash, long key, Object value, int bucketIndex) {
        this.addEntry(hash, key, value, bucketIndex);
    }

    @Deprecated
    static int d(long key) {
        return computeHash(key);
    }
}
