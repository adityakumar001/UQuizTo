package com.uquizto.quiz_ui;

import javax.swing.*;
import java.awt.*;

public class Logo extends JPanel {

    private int xOffset, yOffset;

    public Logo(int xOffset, int yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        Graphics2D g2d = (Graphics2D) g;

        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        rh.add(new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON));
        g2d.setRenderingHints(rh);

        int diff = 10;
        int iniX = 455-xOffset, iniY = 235, endX = 245-xOffset, endY = 235, midY = 0, midX = 0;

        for (int j = 0; j < 5; j++) {
            for (int i = 1; i <= 3; i++) {
                if (i == 2) {
                    g2d.setColor(Color.YELLOW);
                    midY = (int) Math.sqrt(3 * (iniX - endX) * (iniX - endX) / 4);
                    midX = (iniX + endX) / 2;
                    g2d.drawLine(endX, endY, midX, iniY - midY);
                } else if (i == 3) {
                    g2d.setColor(Color.RED);
                    g2d.drawLine(midX, iniY - midY, iniX - diff, iniY - diff);
                } else {
                    g2d.setColor(Color.BLUE);
                    g2d.drawLine(iniX, iniY, endX, endY);
                }
            }
            iniX = iniX - diff;
            endX = endX + diff;
            iniY = iniY - diff;
            endY = endY - 3 * diff;
        }
        g2d.setColor(Color.BLACK);
        g2d.fillOval(midX - 30, midY - 15, 40, 40);
        g2d.setColor(Color.DARK_GRAY);
    }

}
