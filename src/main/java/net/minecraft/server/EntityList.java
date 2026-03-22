package net.minecraft.server;

import com.legacyminecraft.poseidon.entity.EntityIntMapBehaviour;

public class EntityList {
    private static final EntityIntMapBehaviour ENTITY_INT_MAP_BEHAVIOUR = EntityIntMapBehaviour.getInstance();

    private transient EntityListEntry[] a = new EntityListEntry[16];
    private transient int b;
    private int c = 12;
    private final float d = 0.75F;
    private transient volatile int e;

    public EntityList() {
    }

    private static int g(int i) {
        return ENTITY_INT_MAP_BEHAVIOUR.hashKey(i);
    }

    private static int a(int i, int j) {
        return ENTITY_INT_MAP_BEHAVIOUR.bucketIndex(i, j);
    }

    public Object a(int i) {
        return ENTITY_INT_MAP_BEHAVIOUR.get(this, i);
    }

    public boolean b(int i) {
        return ENTITY_INT_MAP_BEHAVIOUR.containsKey(this, i);
    }

    final EntityListEntry c(int i) {
        return ENTITY_INT_MAP_BEHAVIOUR.findEntry(this, i);
    }

    public void a(int i, Object object) {
        ENTITY_INT_MAP_BEHAVIOUR.put(this, i, object);
    }

    public Object d(int i) {
        return ENTITY_INT_MAP_BEHAVIOUR.remove(this, i);
    }

    final EntityListEntry e(int i) {
        return ENTITY_INT_MAP_BEHAVIOUR.removeEntry(this, i);
    }

    public void a() {
        ENTITY_INT_MAP_BEHAVIOUR.clear(this);
    }

    public static int poseidonHash(int i) {
        return f(i);
    }

    static int f(int i) {
        return g(i);
    }

    public EntityListEntry[] poseidonGetBuckets() {
        return this.a;
    }

    public void poseidonSetBuckets(EntityListEntry[] buckets) {
        this.a = buckets;
    }

    public int poseidonGetSize() {
        return this.b;
    }

    public void poseidonSetSize(int size) {
        this.b = size;
    }

    public int poseidonGetThreshold() {
        return this.c;
    }

    public void poseidonSetThreshold(int threshold) {
        this.c = threshold;
    }

    public float poseidonGetLoadFactor() {
        return this.d;
    }

    public int poseidonGetModCount() {
        return this.e;
    }

    public void poseidonSetModCount(int modCount) {
        this.e = modCount;
    }

    public EntityListEntry poseidonCreateEntry(int hash, int slot, Object value, EntityListEntry next) {
        return new EntityListEntry(hash, slot, value, next);
    }
}
