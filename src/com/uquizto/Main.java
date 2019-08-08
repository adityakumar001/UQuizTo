package com.uquizto;

import com.uquizto.quiz_ui.LoginPanel;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import java.awt.*;

public class Main {

    public static final int WIDTH = 600, HEIGHT = 450;

    public static void main(String[] args) {
        new Main().setStartFrame();
    }

    private void setStartFrame() {
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame();
        frame.setSize(new Dimension(WIDTH, HEIGHT));
        frame.setTitle("UQuizTo");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.add(new LoginPanel(frame), BorderLayout.CENTER);
        frame.setVisible(true);

    }

}

