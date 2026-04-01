package com.legacyminecraft.poseidon.map;
import com.legacyminecraft.poseidon.api.*;
import com.legacyminecraft.poseidon.world.entity.data.*;
import com.legacyminecraft.poseidon.statistics.*;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;
import com.legacyminecraft.poseidon.world.core.*;

import org.bukkit.World;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import java.util.List;

/**
 * Represents a map item.
 */
public interface IMapView {

    /**
     * Get the ID of this map item. Corresponds to the damage value of a map
     * in an inventory.
     * @return The ID of the map.
     */
    public short getId();

    /**
     * Check whether this map is virtual. A map is virtual if its lowermost
     * MapRenderer is plugin-provided.
     * @return Whether the map is virtual.
     */
    public boolean isVirtual();

    /**
     * Get the scale of this map.
     * @return The scale of the map.
     */
    public MapView.Scale getScale();

    /**
     * Set the scale of this map.
     * @param scale The scale to set.
     */
    public void setScale(MapView.Scale scale);

    /**
     * Get the center X position of this map.
     * @return The center X position.
     */
    public int getCenterX();

    /**
     * Get the center Z position of this map.
     * @return The center Z position.
     */
    public int getCenterZ();

    /**
     * Set the center X position of this map.
     * @param x The center X position.
     */
    public void setCenterX(int x);

    /**
     * Set the center Z position of this map.
     * @param z The center Z position.
     */
    public void setCenterZ(int z);

    /**
     * Get the world that this map is associated with. Primarily used by the
     * internal renderer, but may be used by external renderers. May return
     * null if the world the map is associated with is not loaded.
     * @return The World this map is associated with.
     */
    public World getWorld();

    /**
     * Set the world that this map is associated with. The world is used by
     * the internal renderer, and may also be used by external renderers.
     * @param world The World to associate this map with.
     */
    public void setWorld(World world);

    /**
     * Get a list of MapRenderers currently in effect.
     * @return A List<MapRenderer> containing each map renderer.
     */
    public List<MapRenderer> getRenderers();

    /**
     * Add a renderer to this map.
     * @param renderer The MapRenderer to add.
     */
    public void addRenderer(MapRenderer renderer);

    /**
     * Remove a renderer from this map.
     * @param renderer The MapRenderer to remove.
     * @return True if the renderer was successfully removed.
     */
    public boolean removeRenderer(MapRenderer renderer);

}
