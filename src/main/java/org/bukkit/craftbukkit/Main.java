package org.bukkit.craftbukkit;

import com.legacyminecraft.poseidon.runtime.CraftBukkitBootstrapSystem;

public class Main {
    public static boolean useJline = true;
    private static final CraftBukkitBootstrapSystem craftBukkitBootstrapSystem = CraftBukkitBootstrapSystem.getInstance();

    public static void main(String[] args) {
        craftBukkitBootstrapSystem.run(args, new CraftBukkitBootstrapSystem.JlineModeController() {
            @Override
            public void setUseJline(boolean nextUseJline) {
                Main.useJline = nextUseJline;
            }
        });
    }
}
