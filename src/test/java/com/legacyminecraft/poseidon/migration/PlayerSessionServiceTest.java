package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.world.player.PlayerSessionSystem;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PlayerSessionServiceTest {
    private final PlayerSessionSystem service = PlayerSessionSystem.getInstance();

    @Test
    public void emptyPlayerListHasNoNames() {
        List players = new ArrayList();
        Assert.assertEquals("", service.buildPlayerNameList(players));
    }

    @Test
    public void sendPacketToMissingPlayerReturnsFalse() {
        List players = new ArrayList();
        Assert.assertFalse(service.sendPacketToPlayer(players, "Ghost", null));
    }

    @Test
    public void operatorBroadcastWithNoPlayersIsNoop() {
        List players = new ArrayList();
        Set operators = new HashSet();
        operators.add("admin");
        service.sendPacketToOperators(players, operators, "test");
        Assert.assertTrue(true);
    }
}
