package com.legacyminecraft.poseidon.runtime.gui;

import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * GUI-local stats panel widget.
 */
public class GuiStatsComponent extends JComponent implements ActionListener {
    private static final long serialVersionUID = 1L;
    private final int[] memoryUseSamples = new int[256];
    private final String[] statusLines = new String[11];
    private int cursor;

    public GuiStatsComponent() {
        GuiStatsComponentBehaviour.getInstance().initialize(this, this);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        cursor = GuiStatsComponentBehaviour.getInstance().refresh(memoryUseSamples, statusLines, cursor, this);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        GuiStatsComponentBehaviour.getInstance().paint(graphics, memoryUseSamples, cursor, statusLines);
    }
}
