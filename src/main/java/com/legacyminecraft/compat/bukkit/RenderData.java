package com.legacyminecraft.compat.bukkit;

import java.util.ArrayList;
import java.util.List;

/**
 * Canonical map render-data scaffold.
 */
public class RenderData {
    public final byte[] buffer = new byte[128 * 128];
    public final List<MapCursor> cursors = new ArrayList<MapCursor>();
}
