package com.legacyminecraft.poseidon.entity;

/**
 * Backing entry node for long-keyed player list maps.
 */
public class PlayerListEntry {
    private final int hash;
    private final long key;
    private Object value;
    private PlayerListEntry next;

    public PlayerListEntry(int hash, long key, Object value, PlayerListEntry next) {
        this.hash = hash;
        this.key = key;
        this.value = value;
        this.next = next;
    }

    public long a() {
        return key;
    }

    public Object b() {
        return value;
    }

    public int c() {
        return hash;
    }

    public PlayerListEntry d() {
        return next;
    }

    public void a(Object value) {
        this.value = value;
    }

    public void a(PlayerListEntry next) {
        this.next = next;
    }

    @Override
    public boolean equals(Object other) {
        return PlayerListEntryStateBehaviour.getInstance().equalsEntry(this, other);
    }

    @Override
    public String toString() {
        return PlayerListEntryStateBehaviour.getInstance().toEntryString(this);
    }
}
