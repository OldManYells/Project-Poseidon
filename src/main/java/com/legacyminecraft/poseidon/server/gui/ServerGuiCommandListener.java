package com.legacyminecraft.poseidon.server.gui;
import com.legacyminecraft.poseidon.world.entity.data.*;
import com.legacyminecraft.poseidon.statistics.*;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class ServerGuiCommandListener implements ActionListener {

    final JTextField a;

    final ServerGUI b;

    ServerGuiCommandListener(ServerGUI servergui, JTextField jtextfield) {
        this.b = servergui;
        this.a = jtextfield;
    }

    public void actionPerformed(ActionEvent actionevent) {
        String s = this.a.getText().trim();

        if (s.length() > 0) {
            ServerGUI.a(this.b).issueCommand(s, this.b);
        }

        this.a.setText("");
    }
}
