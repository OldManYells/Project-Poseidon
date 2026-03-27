package com.legacyminecraft.poseidon.entity;

/**
 * Legacy int-keyed entity index map used by tracker systems.
 */
public class EntityList {
    private static final int DEFAULT_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75F;

    private transient EntityListEntry[] buckets = new EntityListEntry[DEFAULT_CAPACITY];
    private transient int size;
    private int threshold = (int) (DEFAULT_CAPACITY * DEFAULT_LOAD_FACTOR);
    private final float loadFactor = DEFAULT_LOAD_FACTOR;
    private transient int modCount;

    public static int poseidonHash(int key) {
        return EntityIntMapBehaviour.getInstance().hashKey(key);
    }

    public Object a(int key) {
        return EntityIntMapBehaviour.getInstance().get(this, key);
    }

    public void a(int key, Object value) {
        EntityIntMapBehaviour.getInstance().put(this, key, value);
    }

    public boolean b(int key) {
        return EntityIntMapBehaviour.getInstance().containsKey(this, key);
    }

    public Object d(int key) {
        return EntityIntMapBehaviour.getInstance().remove(this, key);
    }

    public void a() {
        EntityIntMapBehaviour.getInstance().clear(this);
    }

    EntityListEntry[] poseidonGetBuckets() {
        return buckets;
    }

    void poseidonSetBuckets(EntityListEntry[] buckets) {
        this.buckets = buckets;
    }

    int poseidonGetSize() {
        return size;
    }

    void poseidonSetSize(int size) {
        this.size = size;
    }

    int poseidonGetThreshold() {
        return threshold;
    }

    void poseidonSetThreshold(int threshold) {
        this.threshold = threshold;
    }

    float poseidonGetLoadFactor() {
        return loadFactor;
    }

    int poseidonGetModCount() {
        return modCount;
    }

    void poseidonSetModCount(int modCount) {
        this.modCount = modCount;
    }

    EntityListEntry poseidonCreateEntry(int hash, int slot, Object value, EntityListEntry next) {
        return new EntityListEntry(hash, slot, value, next);
    }
}
