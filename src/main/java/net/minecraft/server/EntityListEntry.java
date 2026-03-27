package net.minecraft.server;

public class EntityListEntry {

    final int a;
    Object b;
    EntityListEntry c;
    final int d;

    EntityListEntry(int i, int j, Object object, EntityListEntry entitylistentry) {
        this.b = object;
        this.c = entitylistentry;
        this.a = j;
        this.d = i;
    }

    public final int a() {
        return this.a;
    }

    public final Object b() {
        return this.b;
    }

    public final boolean equals(Object object) {
        if (!(object instanceof EntityListEntry)) {
            return false;
        }

        EntityListEntry entitylistentry = (EntityListEntry)object;
        if (this.a != entitylistentry.a) {
            return false;
        }

        return this.b == entitylistentry.b || this.b != null && this.b.equals(entitylistentry.b);
    }

    public final int hashCode() {
        return EntityList.poseidonHash(this.a);
    }

    public final String toString() {
        return this.a + "=" + this.b;
    }

    public final int poseidonGetSlot() {
        return this.a;
    }

    public final Object poseidonGetValue() {
        return this.b;
    }

    public final void poseidonSetValue(Object value) {
        this.b = value;
    }

    public final EntityListEntry poseidonGetNext() {
        return this.c;
    }

    public final void poseidonSetNext(EntityListEntry next) {
        this.c = next;
    }

    public final int poseidonGetHash() {
        return this.d;
    }
}
