package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.auth.login.LoginAdmissionPolicy;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class LoginAdmissionPolicyTest {
    private final LoginAdmissionPolicy policy = LoginAdmissionPolicy.getInstance();

    @Test
    public void blocksBannedUsername() {
        Set<String> bannedNames = new HashSet<String>();
        bannedNames.add("playerone");

        LoginAdmissionPolicy.AdmissionResult result = policy.evaluate(
                "PlayerOne",
                "127.0.0.1",
                bannedNames,
                new HashSet<String>(),
                true,
                0,
                20,
                "banned",
                "ipbanned",
                "whitelist",
                "full"
        );

        Assert.assertEquals(LoginAdmissionPolicy.Decision.KICK_BANNED, result.getDecision());
        Assert.assertEquals("banned", result.getKickMessage());
    }

    @Test
    public void blocksBannedIp() {
        Set<String> bannedIps = new HashSet<String>();
        bannedIps.add("203.0.113.5");

        LoginAdmissionPolicy.AdmissionResult result = policy.evaluate(
                "PlayerOne",
                "203.0.113.5",
                new HashSet<String>(),
                bannedIps,
                true,
                0,
                20,
                "banned",
                "ipbanned",
                "whitelist",
                "full"
        );

        Assert.assertEquals(LoginAdmissionPolicy.Decision.KICK_BANNED_IP, result.getDecision());
        Assert.assertEquals("ipbanned", result.getKickMessage());
    }

    @Test
    public void blocksWhenNotWhitelisted() {
        LoginAdmissionPolicy.AdmissionResult result = policy.evaluate(
                "PlayerOne",
                "127.0.0.1",
                new HashSet<String>(),
                new HashSet<String>(),
                false,
                0,
                20,
                "banned",
                "ipbanned",
                "whitelist",
                "full"
        );

        Assert.assertEquals(LoginAdmissionPolicy.Decision.KICK_WHITELIST, result.getDecision());
        Assert.assertEquals("whitelist", result.getKickMessage());
    }

    @Test
    public void blocksWhenServerIsFull() {
        LoginAdmissionPolicy.AdmissionResult result = policy.evaluate(
                "PlayerOne",
                "127.0.0.1",
                new HashSet<String>(),
                new HashSet<String>(),
                true,
                20,
                20,
                "banned",
                "ipbanned",
                "whitelist",
                "full"
        );

        Assert.assertEquals(LoginAdmissionPolicy.Decision.KICK_FULL, result.getDecision());
        Assert.assertEquals("full", result.getKickMessage());
    }

    @Test
    public void allowsWhenChecksPass() {
        LoginAdmissionPolicy.AdmissionResult result = policy.evaluate(
                "PlayerOne",
                "198.51.100.10",
                new HashSet<String>(),
                new HashSet<String>(),
                true,
                10,
                20,
                "banned",
                "ipbanned",
                "whitelist",
                "full"
        );

        Assert.assertEquals(LoginAdmissionPolicy.Decision.ALLOWED, result.getDecision());
        Assert.assertEquals("198.51.100.10", result.getKickMessage());
    }
}
