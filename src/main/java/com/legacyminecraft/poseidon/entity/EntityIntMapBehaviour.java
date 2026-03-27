package com.legacyminecraft.poseidon.entity;


/**
 * Canonical behaviour for legacy int-keyed entity list table operations.
 */
public final class EntityIntMapBehaviour {
    private static final EntityIntMapBehaviour INSTANCE = new EntityIntMapBehaviour();
    private static final int MAXIMUM_CAPACITY = 1073741824;

    private EntityIntMapBehaviour() {
    }

    public static EntityIntMapBehaviour getInstance() {
        return INSTANCE;
    }

    public Object get(EntityList map, int key) {
        int hash = hashKey(key);
        EntityListEntry[] buckets = map.poseidonGetBuckets();

        for (EntityListEntry entry = buckets[bucketIndex(hash, buckets.length)];
             entry != null;
             entry = entry.poseidonGetNext()) {
            if (entry.poseidonGetSlot() == key) {
                return entry.poseidonGetValue();
            }
        }

        return null;
    }

    public boolean containsKey(EntityList map, int key) {
        return this.findEntry(map, key) != null;
    }

    public EntityListEntry findEntry(EntityList map, int key) {
        int hash = hashKey(key);
        EntityListEntry[] buckets = map.poseidonGetBuckets();

        for (EntityListEntry entry = buckets[bucketIndex(hash, buckets.length)];
             entry != null;
             entry = entry.poseidonGetNext()) {
            if (entry.poseidonGetSlot() == key) {
                return entry;
            }
        }

        return null;
    }

    public void put(EntityList map, int key, Object value) {
        int hash = hashKey(key);
        EntityListEntry[] buckets = map.poseidonGetBuckets();
        int targetBucket = bucketIndex(hash, buckets.length);

        for (EntityListEntry entry = buckets[targetBucket]; entry != null; entry = entry.poseidonGetNext()) {
            if (entry.poseidonGetSlot() == key) {
                entry.poseidonSetValue(value);
            }
        }

        map.poseidonSetModCount(map.poseidonGetModCount() + 1);
        this.addEntry(map, hash, key, value, targetBucket);
    }

    public Object remove(EntityList map, int key) {
        EntityListEntry removed = this.removeEntry(map, key);

        return removed == null ? null : removed.poseidonGetValue();
    }

    public EntityListEntry removeEntry(EntityList map, int key) {
        int hash = hashKey(key);
        EntityListEntry[] buckets = map.poseidonGetBuckets();
        int bucket = bucketIndex(hash, buckets.length);
        EntityListEntry previous = buckets[bucket];

        EntityListEntry current;
        EntityListEntry next;
        for (current = previous; current != null; current = next) {
            next = current.poseidonGetNext();
            if (current.poseidonGetSlot() == key) {
                map.poseidonSetModCount(map.poseidonGetModCount() + 1);
                map.poseidonSetSize(map.poseidonGetSize() - 1);
                if (previous == current) {
                    buckets[bucket] = next;
                } else {
                    previous.poseidonSetNext(next);
                }

                return current;
            }

            previous = current;
        }

        return current;
    }

    public void clear(EntityList map) {
        map.poseidonSetModCount(map.poseidonGetModCount() + 1);
        EntityListEntry[] buckets = map.poseidonGetBuckets();

        for (int index = 0; index < buckets.length; ++index) {
            buckets[index] = null;
        }

        map.poseidonSetSize(0);
    }

    public int hashKey(int key) {
        key ^= key >>> 20 ^ key >>> 12;
        return key ^ key >>> 7 ^ key >>> 4;
    }

    public int bucketIndex(int hash, int length) {
        return hash & length - 1;
    }

    private void addEntry(EntityList map, int hash, int key, Object value, int bucket) {
        EntityListEntry[] buckets = map.poseidonGetBuckets();
        EntityListEntry previousHead = buckets[bucket];

        buckets[bucket] = map.poseidonCreateEntry(hash, key, value, previousHead);
        int size = map.poseidonGetSize();
        map.poseidonSetSize(size + 1);
        if (size >= map.poseidonGetThreshold()) {
            this.resize(map, 2 * buckets.length);
        }
    }

    private void resize(EntityList map, int newCapacity) {
        EntityListEntry[] currentBuckets = map.poseidonGetBuckets();
        int currentCapacity = currentBuckets.length;

        if (currentCapacity == MAXIMUM_CAPACITY) {
            map.poseidonSetThreshold(Integer.MAX_VALUE);
            return;
        }

        EntityListEntry[] resizedBuckets = new EntityListEntry[newCapacity];

        this.transfer(map, resizedBuckets);
        map.poseidonSetBuckets(resizedBuckets);
        map.poseidonSetThreshold((int) ((float) newCapacity * map.poseidonGetLoadFactor()));
    }

    private void transfer(EntityList map, EntityListEntry[] targetBuckets) {
        EntityListEntry[] currentBuckets = map.poseidonGetBuckets();
        int targetLength = targetBuckets.length;

        for (int bucketIndex = 0; bucketIndex < currentBuckets.length; ++bucketIndex) {
            EntityListEntry entry = currentBuckets[bucketIndex];

            if (entry != null) {
                currentBuckets[bucketIndex] = null;

                EntityListEntry nextEntry;
                do {
                    nextEntry = entry.poseidonGetNext();
                    int targetBucket = bucketIndex(entry.poseidonGetHash(), targetLength);

                    entry.poseidonSetNext(targetBuckets[targetBucket]);
                    targetBuckets[targetBucket] = entry;
                    entry = nextEntry;
                } while (nextEntry != null);
            }
        }
    }
}
