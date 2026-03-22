package net.minecraft.server;

import com.legacyminecraft.poseidon.entity.EntityListEntryBehaviour;

public class EntityListEntry {
    private static final EntityListEntryBehaviour ENTITY_LIST_ENTRY_BEHAVIOUR = EntityListEntryBehaviour.getInstance();

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
        return ENTITY_LIST_ENTRY_BEHAVIOUR.equalsEntry(this, object);
    }

    public final int hashCode() {
        return ENTITY_LIST_ENTRY_BEHAVIOUR.hashCode(this);
    }

    public final String toString() {
        return ENTITY_LIST_ENTRY_BEHAVIOUR.stringify(this);
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
