package net.minecraft.server;

public class EntityList {
    private transient EntityListEntry[] a = new EntityListEntry[16];
    private transient int b;
    private int c = 12;
    private final float d = 0.75F;
    private transient volatile int e;

    public EntityList() {
    }

    private static int g(int i) {
        return hashKey(i);
    }

    private static int a(int i, int j) {
        return bucketIndex(i, j);
    }

    public Object a(int i) {
        EntityListEntry entitylistentry = this.c(i);
        return entitylistentry == null ? null : entitylistentry.b;
    }

    public boolean b(int i) {
        return this.c(i) != null;
    }

    final EntityListEntry c(int i) {
        int j = g(i);
        EntityListEntry[] aentitylistentry = this.a;

        for (EntityListEntry entitylistentry = aentitylistentry[a(j, aentitylistentry.length)]; entitylistentry != null; entitylistentry = entitylistentry.c) {
            if (entitylistentry.a == i) {
                return entitylistentry;
            }
        }

        return null;
    }

    public void a(int i, Object object) {
        int j = g(i);
        EntityListEntry[] aentitylistentry = this.a;
        int k = a(j, aentitylistentry.length);

        for (EntityListEntry entitylistentry = aentitylistentry[k]; entitylistentry != null; entitylistentry = entitylistentry.c) {
            if (entitylistentry.a == i) {
                entitylistentry.b = object;
            }
        }

        ++this.e;
        this.a(k, j, i, object);
    }

    public Object d(int i) {
        EntityListEntry entitylistentry = this.e(i);
        return entitylistentry == null ? null : entitylistentry.b;
    }

    final EntityListEntry e(int i) {
        int j = g(i);
        EntityListEntry[] aentitylistentry = this.a;
        int k = a(j, aentitylistentry.length);
        EntityListEntry entitylistentry = aentitylistentry[k];
        EntityListEntry entitylistentry1;

        for (entitylistentry1 = entitylistentry; entitylistentry1 != null; entitylistentry1 = entitylistentry1.c) {
            if (entitylistentry1.a == i) {
                ++this.e;
                --this.b;
                if (entitylistentry == entitylistentry1) {
                    aentitylistentry[k] = entitylistentry1.c;
                } else {
                    entitylistentry.c = entitylistentry1.c;
                }

                return entitylistentry1;
            }

            entitylistentry = entitylistentry1;
        }

        return entitylistentry1;
    }

    public void a() {
        ++this.e;
        EntityListEntry[] aentitylistentry = this.a;

        for (int i = 0; i < aentitylistentry.length; ++i) {
            aentitylistentry[i] = null;
        }

        this.b = 0;
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

    private void a(int i, int j, int k, Object object) {
        EntityListEntry[] aentitylistentry = this.a;
        aentitylistentry[i] = new EntityListEntry(j, k, object, aentitylistentry[i]);
        int l = this.b++;
        if (l >= this.c) {
            int i1 = 2 * aentitylistentry.length;
            if (aentitylistentry.length == 1073741824) {
                this.c = Integer.MAX_VALUE;
            } else {
                EntityListEntry[] aentitylistentry1 = new EntityListEntry[i1];
                this.a(aentitylistentry1);
                this.a = aentitylistentry1;
                this.c = (int)((float)i1 * this.d);
            }
        }
    }

    private void a(EntityListEntry[] aentitylistentry) {
        EntityListEntry[] aentitylistentry1 = this.a;

        for (int i = 0; i < aentitylistentry1.length; ++i) {
            EntityListEntry entitylistentry = aentitylistentry1[i];
            if (entitylistentry != null) {
                aentitylistentry1[i] = null;
                do {
                    EntityListEntry entitylistentry1 = entitylistentry.c;
                    int j = a(entitylistentry.d, aentitylistentry.length);
                    entitylistentry.c = aentitylistentry[j];
                    aentitylistentry[j] = entitylistentry;
                    entitylistentry = entitylistentry1;
                } while (entitylistentry != null);
            }
        }
    }

    private static int bucketIndex(int hash, int length) {
        return hash & length - 1;
    }

    private static int hashKey(int key) {
        key ^= key >>> 20 ^ key >>> 12;
        return key ^ key >>> 7 ^ key >>> 4;
    }
}
