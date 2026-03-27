package org.bukkit.craftbukkit.map;

import com.legacyminecraft.compat.bukkit.CraftMapRendererBehaviour;
import net.minecraft.server.WorldMap;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

public class CraftMapRenderer extends MapRenderer {
    private static final CraftMapRendererBehaviour CRAFT_MAP_RENDERER_BEHAVIOUR =
            CraftMapRendererBehaviour.getInstance();

    private final CraftMapView mapView;
    private final WorldMap worldMap;

    public CraftMapRenderer(CraftMapView mapView, WorldMap worldMap) {
        super(false);
        this.mapView = mapView;
        this.worldMap = worldMap;
    }

    @Override
    public void render(MapView map, MapCanvas canvas, Player player) {
        CRAFT_MAP_RENDERER_BEHAVIOUR.render(worldMap, canvas);
    }
}
