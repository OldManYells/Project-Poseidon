package com.legacyminecraft.poseidon.runtime;

/**
 * Role-aligned canonical system for CraftBukkit wrapper bootstrap launch.
 */
public final class CraftBukkitBootstrapSystem {
    private static final CraftBukkitBootstrapSystem INSTANCE = new CraftBukkitBootstrapSystem();
    private final CraftBukkitBootstrapService delegate = CraftBukkitBootstrapService.getInstance();

    private CraftBukkitBootstrapSystem() {
    }

    public static CraftBukkitBootstrapSystem getInstance() {
        return INSTANCE;
    }

    public void run(String[] args, final JlineModeController jlineModeController) {
        delegate.run(args, new CraftBukkitBootstrapService.JlineModeController() {
            @Override
            public void setUseJline(boolean useJline) {
                jlineModeController.setUseJline(useJline);
            }
        });
    }

    public interface JlineModeController {
        void setUseJline(boolean useJline);
    }
}

