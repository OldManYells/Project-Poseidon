package com.legacyminecraft.poseidon.entity;

import net.minecraft.server.PlayerListEntry;

/**
 * Canonical behaviour for player-list entry equality and string formatting.
 */
public final class PlayerListEntryStateBehaviour {
    private static final PlayerListEntryStateBehaviour INSTANCE = new PlayerListEntryStateBehaviour();

    private PlayerListEntryStateBehaviour() {
    }

    public static PlayerListEntryStateBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean equalsEntry(PlayerListEntry self, Object other) {
        if (!(other instanceof PlayerListEntry)) {
            return false;
        }

        PlayerListEntry otherEntry = (PlayerListEntry) other;
        Long selfKey = Long.valueOf(self.a());
        Long otherKey = Long.valueOf(otherEntry.a());

        if (selfKey != otherKey && (selfKey == null || !selfKey.equals(otherKey))) {
            return false;
        }

        Object selfValue = self.b();
        Object otherValue = otherEntry.b();
        return selfValue == otherValue || (selfValue != null && selfValue.equals(otherValue));
    }

    public String toEntryString(PlayerListEntry entry) {
        return entry.a() + "=" + entry.b();
    }
}
