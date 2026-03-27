package com.legacyminecraft.poseidon.auth.login;

import com.legacyminecraft.compat.bukkit.Plugin;

public interface LoginPauseController {
    ConnectionPause createConnectionPause(Plugin plugin, String connectionPauseName);

    void clearConnectionPause(ConnectionPause connectionPause);

    boolean hasActiveConnectionPause();

    void cancelLoginProcess(String message);
}
