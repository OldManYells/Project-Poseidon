
package org.bukkit.event.player;
import com.legacyminecraft.poseidon.world.core.*;

import org.bukkit.World;
import org.bukkit.entity.Player;

public class PlayerChangedWorldEvent extends PlayerEvent {

    private final World from;

    public PlayerChangedWorldEvent(Player player, World from) {
        super(Type.PLAYER_CHANGED_WORLD, player);
        this.from = from;
    }

    public World getFrom() {
        return from;
    }
}