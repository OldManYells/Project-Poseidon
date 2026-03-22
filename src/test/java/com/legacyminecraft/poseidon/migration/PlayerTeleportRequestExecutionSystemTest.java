package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.PlayerTeleportRequestExecutionSystem;
import org.bukkit.Location;
import org.junit.Assert;
import org.junit.Test;

public class PlayerTeleportRequestExecutionSystemTest {
    private final PlayerTeleportRequestExecutionSystem playerTeleportRequestExecutionSystem =
            PlayerTeleportRequestExecutionSystem.getInstance();

    @Test
    public void executeResolvesDestinationAndTeleports() {
        ResolverCapture resolverCapture = new ResolverCapture();
        ActionCapture actionCapture = new ActionCapture();

        playerTeleportRequestExecutionSystem.execute(resolverCapture, actionCapture);

        Assert.assertTrue(resolverCapture.called);
        Assert.assertSame(resolverCapture.destination, actionCapture.destination);
    }

    private static final class ResolverCapture implements PlayerTeleportRequestExecutionSystem.TeleportDestinationResolver {
        private boolean called;
        private final Location destination = new Location(null, 1.0D, 2.0D, 3.0D, 4.0F, 5.0F);

        @Override
        public Location resolveDestination() {
            this.called = true;
            return destination;
        }
    }

    private static final class ActionCapture implements PlayerTeleportRequestExecutionSystem.TeleportActions {
        private Location destination;

        @Override
        public void teleport(Location destination) {
            this.destination = destination;
        }
    }
}
