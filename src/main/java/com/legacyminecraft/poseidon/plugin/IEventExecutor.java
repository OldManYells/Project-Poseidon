package com.legacyminecraft.poseidon.plugin;

import org.bukkit.event.Event;
import org.bukkit.event.Listener;

/**
 * Interface which defines the class for event call backs to plugins
 */
public interface IEventExecutor {
    public void execute(Listener listener, Event event);
}
