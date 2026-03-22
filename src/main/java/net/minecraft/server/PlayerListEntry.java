package net.minecraft.server;

import com.legacyminecraft.poseidon.entity.PlayerListEntryStateBehaviour;

public class PlayerListEntry {
    private static final PlayerListEntryStateBehaviour PLAYER_LIST_ENTRY_STATE_BEHAVIOUR = PlayerListEntryStateBehaviour.getInstance();

    final long a;
    Object b;
    PlayerListEntry c;
    final int d;

    public PlayerListEntry(int i, long j, Object object, PlayerListEntry playerlistentry) {
        this.b = object;
        this.c = playerlistentry;
        this.a = j;
        this.d = i;
    }

    public final long a() {
        return this.a;
    }

    public final Object b() {
        return this.b;
    }

    public final int c() {
        return this.d;
    }

    public final PlayerListEntry d() {
        return this.c;
    }

    public final void a(Object object) {
        this.b = object;
    }

    public final void a(PlayerListEntry playerlistentry) {
        this.c = playerlistentry;
    }

    public final boolean equals(Object object) {
        return PLAYER_LIST_ENTRY_STATE_BEHAVIOUR.equalsEntry(this, object);
    }

    public final int hashCode() {
        return PlayerList.d(this.a);
    }

    public final String toString() {
        return PLAYER_LIST_ENTRY_STATE_BEHAVIOUR.toEntryString(this);
    }
}
