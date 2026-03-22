package com.legacyminecraft.poseidon.auth.login;

import net.minecraft.server.EntityPlayer;

import java.util.List;

/**
 * Canonical duplicate-session guard used during login admission.
 */
public final class DuplicateLoginGuard {
    private DuplicateLoginGuard() {
    }

    public static void disconnectDuplicateUsernameSessions(List players, String username) {
        for (int i = 0; i < players.size(); ++i) {
            EntityPlayer onlinePlayer = (EntityPlayer) players.get(i);
            if (onlinePlayer.name.equalsIgnoreCase(username)) {
                onlinePlayer.netServerHandler.disconnect("You logged in from another location");
            }
        }
    }
}
