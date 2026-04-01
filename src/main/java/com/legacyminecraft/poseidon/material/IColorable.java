package com.legacyminecraft.poseidon.material;
import com.legacyminecraft.poseidon.world.entity.data.*;
import com.legacyminecraft.poseidon.statistics.*;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;

import org.bukkit.DyeColor;

/**
 * An object that can be colored.
 *
 * @author Cogito
 *
 */
public interface IColorable {

    /**
     * Gets the color of this object.
     *
     * @return The DyeColor of this object.
     */
    public DyeColor getColor();

    /**
     * Sets the color of this object to the specified DyeColor.
     *
     * @param color The color of the object, as a DyeColor.
     */
    public void setColor(DyeColor color);

}
