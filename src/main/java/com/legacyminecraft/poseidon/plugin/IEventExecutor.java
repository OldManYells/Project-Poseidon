package com.legacyminecraft.poseidon.plugin;
import com.legacyminecraft.poseidon.world.entity.data.*;
import com.legacyminecraft.poseidon.statistics.*;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;

import org.bukkit.event.Event;
import org.bukkit.event.Listener;

/**
 * Interface which defines the class for event call backs to plugins
 */
public interface IEventExecutor {
    public void execute(Listener listener, Event event);
}
