package com.legacyminecraft.compat.bukkit.craftbukkit.map;

import com.legacyminecraft.compat.bukkit.CraftMapCanvas;
import com.legacyminecraft.compat.bukkit.CraftMapRendererBehaviour;
import com.legacyminecraft.compat.bukkit.CraftMapView;
import com.legacyminecraft.compat.bukkit.MapRenderer;
import com.legacyminecraft.compat.bukkit.Player;
import com.legacyminecraft.compat.bukkit.WorldMap;

public class CraftMapRenderer extends MapRenderer {
    private final CraftMapView mapView;
    private final WorldMap worldMap;

    public CraftMapRenderer(CraftMapView mapView, WorldMap worldMap) {
        this.mapView = mapView;
        this.worldMap = worldMap;
    }

    @Override
    public void render(CraftMapView mapView, CraftMapCanvas canvas, Player player) {
        CraftMapRendererBehaviour.getInstance().render(worldMap, canvas);
    }

    public CraftMapView getMapView() {
        return mapView;
    }
}
