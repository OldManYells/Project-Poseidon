package com.legacyminecraft.poseidon.event;
import com.legacyminecraft.poseidon.packets.*;

import com.legacyminecraft.poseidon.packets.Packet;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerEvent;

public class PlayerPacketEvent extends Event implements Cancellable {
    private boolean cancel;
    private Packet packet;
    private String username;

    public PlayerPacketEvent(Type type, String username, Packet packet) {
        super(type);
        this.cancel = false;
        this.packet = packet;
        this.username = username;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    public int getPacketID() {
        return packet.b();
    }

    public Packet getPacket() {
        return packet;
    }

    public void setPacket(Packet packet) {
        this.packet = packet;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }

    public String getUsername() {
        return username;
    }
}
