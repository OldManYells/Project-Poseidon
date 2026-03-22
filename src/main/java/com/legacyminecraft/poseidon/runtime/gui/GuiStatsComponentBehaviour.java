package com.legacyminecraft.poseidon.runtime.gui;

import net.minecraft.server.NetworkManager;

import javax.swing.JComponent;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionListener;

public final class GuiStatsComponentBehaviour {
    private static final GuiStatsComponentBehaviour INSTANCE = new GuiStatsComponentBehaviour();

    private GuiStatsComponentBehaviour() {
    }

    public static GuiStatsComponentBehaviour getInstance() {
        return INSTANCE;
    }

    public void initialize(JComponent component, ActionListener refreshListener) {
        component.setPreferredSize(new Dimension(256, 196));
        component.setMinimumSize(new Dimension(256, 196));
        component.setMaximumSize(new Dimension(256, 196));
        (new Timer(500, refreshListener)).start();
        component.setBackground(Color.BLACK);
    }

    public int refresh(int[] memoryUseSamples, String[] statusLines, int cursor, JComponent component) {
        long memoryInUse = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        System.gc();
        statusLines[0] = "Memory use: " + memoryInUse / 1024L / 1024L + " mb (" + Runtime.getRuntime().freeMemory() * 100L / Runtime.getRuntime().maxMemory() + "% free)";
        statusLines[1] = "Threads: " + NetworkManager.b + " + " + NetworkManager.c;
        memoryUseSamples[cursor & 255] = (int) (memoryInUse * 100L / Runtime.getRuntime().maxMemory());
        component.repaint();
        return cursor + 1;
    }

    public void paint(Graphics graphics, int[] memoryUseSamples, int cursor, String[] statusLines) {
        graphics.setColor(new Color(16777215));
        graphics.fillRect(0, 0, 256, 192);

        int i;

        for (i = 0; i < 256; ++i) {
            int value = memoryUseSamples[i + cursor & 255];

            graphics.setColor(new Color(value + 28 << 16));
            graphics.fillRect(i, 100 - value, 1, value);
        }

        graphics.setColor(Color.BLACK);

        for (i = 0; i < statusLines.length; ++i) {
            String line = statusLines[i];

            if (line != null) {
                graphics.drawString(line, 32, 116 + i * 16);
            }
        }
    }
}
