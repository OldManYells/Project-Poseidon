package com.legacyminecraft.poseidon.auth.login;

import java.util.Set;

/**
 * Canonical admission policy for legacy whitelist/operator checks.
 */
public final class AccessListAdmissionPolicyBehaviour {
    private static final AccessListAdmissionPolicyBehaviour INSTANCE = new AccessListAdmissionPolicyBehaviour();

    private AccessListAdmissionPolicyBehaviour() {
    }

    public static AccessListAdmissionPolicyBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean isWhitelisted(String playerName,
                                 boolean whitelistEnabled,
                                 Set operatorNames,
                                 Set whitelistedNames,
                                 AccessListPersistence accessListPersistence) {
        String normalizedName = accessListPersistence.normalize(playerName);
        return !whitelistEnabled
                || operatorNames.contains(normalizedName)
                || whitelistedNames.contains(normalizedName);
    }

    public boolean isOperator(String playerName, Set operatorNames, AccessListPersistence accessListPersistence) {
        return operatorNames.contains(accessListPersistence.normalize(playerName));
    }
}
