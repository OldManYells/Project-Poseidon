package net.minecraft.server;

import com.legacyminecraft.poseidon.runtime.gui.GuiStatsRefreshActionBehaviour;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class GuiStatsListener implements ActionListener {
    private static final GuiStatsRefreshActionBehaviour GUI_STATS_REFRESH_ACTION_BEHAVIOUR = GuiStatsRefreshActionBehaviour.getInstance();

    final GuiStatsComponent a;

    GuiStatsListener(GuiStatsComponent guistatscomponent) {
        this.a = GUI_STATS_REFRESH_ACTION_BEHAVIOUR.bindComponent(guistatscomponent);
    }

    public void actionPerformed(ActionEvent actionevent) {
        GUI_STATS_REFRESH_ACTION_BEHAVIOUR.onRefreshAction(this.a, actionevent);
    }
}
