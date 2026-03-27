package com.legacyminecraft.poseidon.entity;

/**
 * Backing entry node for {@link EntityList}.
 */
public class EntityListEntry {
    private final int hash;
    private final int slot;
    private Object value;
    private EntityListEntry next;

    public EntityListEntry(int hash, int slot, Object value, EntityListEntry next) {
        this.hash = hash;
        this.slot = slot;
        this.value = value;
        this.next = next;
    }

    public int poseidonGetHash() {
        return hash;
    }

    public int poseidonGetSlot() {
        return slot;
    }

    public Object poseidonGetValue() {
        return value;
    }

    public void poseidonSetValue(Object value) {
        this.value = value;
    }

    public EntityListEntry poseidonGetNext() {
        return next;
    }

    public void poseidonSetNext(EntityListEntry next) {
        this.next = next;
    }

    @Override
    public boolean equals(Object other) {
        return EntityListEntryBehaviour.getInstance().equalsEntry(this, other);
    }

    @Override
    public int hashCode() {
        return EntityListEntryBehaviour.getInstance().hashCode(this);
    }

    @Override
    public String toString() {
        return EntityListEntryBehaviour.getInstance().stringify(this);
    }
}
