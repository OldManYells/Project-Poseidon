package com.legacyminecraft.poseidon.scheduler;
import com.legacyminecraft.poseidon.api.*;
import com.legacyminecraft.poseidon.world.entity.data.*;
import com.legacyminecraft.poseidon.statistics.*;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;

import org.bukkit.plugin.Plugin;

/**
 * Represents a task being executed by the scheduler
 */

public interface ITask {

    /**
     * Returns the taskId for the task
     *
     * @return Task id number
     */
    public int getTaskId();

    /**
     * Returns the Plugin that owns this task
     *
     * @return The Plugin that owns the task
     */
    public Plugin getOwner();

    /**
     * Returns true if the Task is a sync task
     *
     * @return true if the task is run by main thread
     */
    public boolean isSync();
}
