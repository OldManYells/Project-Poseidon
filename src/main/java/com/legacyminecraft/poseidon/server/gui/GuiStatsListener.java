package com.legacyminecraft.poseidon.server.gui;
import com.legacyminecraft.poseidon.api.*;
import com.legacyminecraft.poseidon.world.entity.data.*;
import com.legacyminecraft.poseidon.statistics.*;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class GuiStatsListener implements ActionListener {

    final GuiStatsComponent a;

    GuiStatsListener(GuiStatsComponent guistatscomponent) {
        this.a = guistatscomponent;
    }

    public void actionPerformed(ActionEvent actionevent) {
        GuiStatsComponent.a(this.a);
    }
}
