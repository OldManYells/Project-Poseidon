package com.legacyminecraft.compat.bukkit;

import java.lang.ref.WeakReference;

/**
 * Canonical behavior for CraftChunk weak-link lifecycle operations.
 */
public final class CraftChunkWeakLinkBehaviour {
    private static final CraftChunkWeakLinkBehaviour INSTANCE = new CraftChunkWeakLinkBehaviour();

    private CraftChunkWeakLinkBehaviour() {
    }

    public static CraftChunkWeakLinkBehaviour getInstance() {
        return INSTANCE;
    }

    public void breakLink(WeakReference<net.minecraft.server.Chunk> weakChunk) {
        weakChunk.clear();
    }
}
