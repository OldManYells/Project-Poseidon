package com.legacyminecraft.poseidon.world;

/**
 * World-local progress callback alias.
 */
public interface IProgressUpdate extends com.legacyminecraft.compat.bukkit.IProgressUpdate {
    void a(String message);

    void b(String message);

    void a(int progressPercent);
}
