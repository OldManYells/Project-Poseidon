package net.minecraft.server;

import com.legacyminecraft.poseidon.runtime.PlayerListTickContract;

public interface IUpdatePlayerListBox extends PlayerListTickContract {

    void a();

    default void tick() {
        this.a();
    }
}
