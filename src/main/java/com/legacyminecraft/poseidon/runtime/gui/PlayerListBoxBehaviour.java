package com.legacyminecraft.poseidon.runtime.gui;

import net.minecraft.server.EntityPlayer;
import net.minecraft.server.MinecraftServer;

import javax.swing.JList;
import java.util.Vector;

public final class PlayerListBoxBehaviour {
    private static final PlayerListBoxBehaviour INSTANCE = new PlayerListBoxBehaviour();

    private PlayerListBoxBehaviour() {
    }

    public static PlayerListBoxBehaviour getInstance() {
        return INSTANCE;
    }

    public int updateIfDue(MinecraftServer minecraftServer, int tickCounter, JList listBox) {
        if (tickCounter % 20 == 0) {
            Vector vector = new Vector();

            for (int i = 0; i < minecraftServer.serverConfigurationManager.players.size(); ++i) {
                vector.add(((EntityPlayer) minecraftServer.serverConfigurationManager.players.get(i)).name);
            }

            listBox.setListData(vector);
        }

        return tickCounter + 1;
    }
}
