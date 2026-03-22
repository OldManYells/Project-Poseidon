package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.auth.login.AccessListAdmissionPolicyBehaviour;
import com.legacyminecraft.poseidon.auth.login.AccessListPersistence;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class AccessListAdmissionPolicyBehaviourTest {
    private final AccessListAdmissionPolicyBehaviour behaviour = AccessListAdmissionPolicyBehaviour.getInstance();
    private final AccessListPersistence persistence = AccessListPersistence.getInstance();

    @Test
    public void isWhitelistedRespectsWhitelistToggleOpsAndWhitelist() {
        Set operators = new HashSet();
        Set whitelist = new HashSet();
        operators.add("operator");
        whitelist.add("member");

        Assert.assertTrue(behaviour.isWhitelisted("AnyName", false, operators, whitelist, persistence));
        Assert.assertTrue(behaviour.isWhitelisted("Operator", true, operators, whitelist, persistence));
        Assert.assertTrue(behaviour.isWhitelisted("Member", true, operators, whitelist, persistence));
        Assert.assertFalse(behaviour.isWhitelisted("Unknown", true, operators, whitelist, persistence));
    }

    @Test
    public void isOperatorUsesNormalizedLookup() {
        Set operators = new HashSet();
        operators.add("admin");

        Assert.assertTrue(behaviour.isOperator("Admin", operators, persistence));
        Assert.assertFalse(behaviour.isOperator("Guest", operators, persistence));
    }
}
