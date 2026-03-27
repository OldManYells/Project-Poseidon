package org.bukkit.craftbukkit.map;

import com.legacyminecraft.compat.bukkit.CraftMapRenderDataFactoryBehaviour;
import org.bukkit.map.MapCursor;

import java.util.ArrayList;

public class RenderData {
    private static final CraftMapRenderDataFactoryBehaviour CRAFT_MAP_RENDER_DATA_FACTORY_BEHAVIOUR =
            CraftMapRenderDataFactoryBehaviour.getInstance();

    public final byte[] buffer;
    public final ArrayList<MapCursor> cursors;

    public RenderData() {
        this.buffer = CRAFT_MAP_RENDER_DATA_FACTORY_BEHAVIOUR.createBuffer();
        this.cursors = CRAFT_MAP_RENDER_DATA_FACTORY_BEHAVIOUR.createCursors();
    }
}
