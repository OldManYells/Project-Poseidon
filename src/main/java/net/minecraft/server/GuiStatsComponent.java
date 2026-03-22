package net.minecraft.server;

import com.legacyminecraft.poseidon.runtime.gui.GuiStatsComponentBehaviour;

import javax.swing.*;
import java.awt.*;

public class GuiStatsComponent extends JComponent {
    private static final GuiStatsComponentBehaviour GUI_STATS_COMPONENT_BEHAVIOUR = GuiStatsComponentBehaviour.getInstance();

    private int[] a = new int[256];
    private int b = 0;
    private String[] c = new String[10];

    public GuiStatsComponent() {
        GUI_STATS_COMPONENT_BEHAVIOUR.initialize(this, new GuiStatsListener(this));
    }

    private void a() {
        this.b = GUI_STATS_COMPONENT_BEHAVIOUR.refresh(this.a, this.c, this.b, this);
    }

    public void paint(Graphics graphics) {
        GUI_STATS_COMPONENT_BEHAVIOUR.paint(graphics, this.a, this.b, this.c);
    }

    static void a(GuiStatsComponent guistatscomponent) {
        guistatscomponent.a();
    }

    public void poseidonRefresh() {
        this.a();
    }
}
