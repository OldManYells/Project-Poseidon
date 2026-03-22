package net.minecraft.server;

import com.legacyminecraft.poseidon.runtime.gui.PlayerListBoxBehaviour;

import javax.swing.*;

public class PlayerListBox extends JList implements IUpdatePlayerListBox {
    private static final PlayerListBoxBehaviour PLAYER_LIST_BOX_BEHAVIOUR = PlayerListBoxBehaviour.getInstance();

    private MinecraftServer a;
    private int b = 0;

    public PlayerListBox(MinecraftServer minecraftserver) {
        this.a = minecraftserver;
        minecraftserver.a((IUpdatePlayerListBox) this);
    }

    public void a() {
        this.b = PLAYER_LIST_BOX_BEHAVIOUR.updateIfDue(this.a, this.b, this);
    }
}
