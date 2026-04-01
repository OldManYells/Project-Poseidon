package com.legacyminecraft.poseidon.server.gui;
import com.legacyminecraft.poseidon.api.*;
import com.legacyminecraft.poseidon.world.entity.data.*;
import com.legacyminecraft.poseidon.statistics.*;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

class ServerGuiFocusAdapter extends FocusAdapter {

    final ServerGUI a;

    ServerGuiFocusAdapter(ServerGUI servergui) {
        this.a = servergui;
    }

    public void focusGained(FocusEvent focusevent) {}
}
