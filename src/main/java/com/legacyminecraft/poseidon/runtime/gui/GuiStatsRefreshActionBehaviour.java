package com.legacyminecraft.poseidon.runtime.gui;


import java.awt.event.ActionEvent;

/**
 * Canonical action behaviour for GUI stats refresh callbacks.
 */
public final class GuiStatsRefreshActionBehaviour {
    private static final GuiStatsRefreshActionBehaviour INSTANCE = new GuiStatsRefreshActionBehaviour();

    private GuiStatsRefreshActionBehaviour() {
    }

    public static GuiStatsRefreshActionBehaviour getInstance() {
        return INSTANCE;
    }

    public GuiStatsComponent bindComponent(GuiStatsComponent guiStatsComponent) {
        return guiStatsComponent;
    }

    public void onRefreshAction(GuiStatsComponent guiStatsComponent, ActionEvent actionEvent) {
        guiStatsComponent.poseidonRefresh();
    }
}
