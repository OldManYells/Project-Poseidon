package com.legacyminecraft.compat.bukkit;


/**
 * Canonical bridge behaviour for Bukkit event dispatch routing.
 */
public final class EventDispatchBridgeBehaviour {
    private static final EventDispatchBridgeBehaviour INSTANCE = new EventDispatchBridgeBehaviour();

    private EventDispatchBridgeBehaviour() {
    }

    public static EventDispatchBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public void dispatchViaServer(CraftServer craftServer, Event event) {
        craftServer.getPluginManager().callEvent(event);
    }

    public void dispatchGlobal(Event event) {
        Bukkit.getPluginManager().callEvent(event);
    }
}
