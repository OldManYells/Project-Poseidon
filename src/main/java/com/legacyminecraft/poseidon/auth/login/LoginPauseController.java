package com.legacyminecraft.poseidon.auth.login;

import org.bukkit.plugin.Plugin;

public interface LoginPauseController {
    ConnectionPause createConnectionPause(Plugin plugin, String connectionPauseName);

    void clearConnectionPause(ConnectionPause connectionPause);

    boolean hasActiveConnectionPause();

    void cancelLoginProcess(String message);
}
