package com.legacyminecraft.poseidon.runtime.gui;

import java.awt.event.FocusEvent;

/**
 * Canonical bridge behaviour for legacy server GUI focus events.
 */
public final class ServerGuiFocusBridgeBehaviour {
    private static final ServerGuiFocusBridgeBehaviour INSTANCE = new ServerGuiFocusBridgeBehaviour();

    private ServerGuiFocusBridgeBehaviour() {
    }

    public static ServerGuiFocusBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public void onFocusGained(FocusEvent event) {
        // Intentionally no-op to preserve legacy behavior.
    }
}
