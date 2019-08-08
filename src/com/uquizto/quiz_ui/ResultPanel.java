package com.uquizto.quiz_ui;


import com.uquizto.Main;
import com.uquizto.models.Question;
import com.uquizto.models.Result;
import com.uquizto.models.User;
import com.uquizto.network.ClientUtils;

import javax.swing.*;
import java.awt.*;
import java.util.List;

@SuppressWarnings("serial")
public class ResultPanel extends JPanel {
    ResultPanel(JFrame frame, Result result, User user) {
        setLayout(new BorderLayout(10, 10));

        Box labelBox = Box.createVerticalBox();
        JLabel resultHeading = new JLabel("<html><h1>Your Result</h1></html>");
        JLabel nameLabel = new JLabel("Name : " + user.getName());
        nameLabel.getInsets(new Insets(0, 10, 0, 10));
        JLabel userNameLabel = new JLabel("Username : " + user.getUname());
        JLabel ageLabel = new JLabel("Age : " + user.getAge());
        JLabel countryLabel = new JLabel("Country : " + user.getCountry());
        JLabel correctLabel = new JLabel("Correct : " + result.correct);
        JLabel incorrectLabel = new JLabel("Incorrect : " + result.incorrect);
        JLabel highScoreLabel;

        if (user.getHigh_score() > result.correct) {
            highScoreLabel = new JLabel("Your High Score : " + user.getHigh_score());
        } else {
            user.setHigh_score(result.correct);
            highScoreLabel = new JLabel("Your High Score : " + user.getHigh_score());
            ClientUtils.syncResults(user);
        }
        JLabel noAttemptLabel = new JLabel("Not Attempted : " + result.nonAttempts);
        JLabel timeTakenLabel = new JLabel("Time Taken : " + (result.getEndTime() - result.getStartTime()) / 1000 + " seconds");

        Box buttonBox = Box.createHorizontalBox();
        JButton doneButton = new JButton("Done");
        JButton resetButton = new JButton("Retest");
        doneButton.addActionListener(done -> {
            frame.dispose();
        });

        resetButton.addActionListener(reset -> {
            List<Question> questions = ClientUtils.getQuestions();
            frame.remove(this);
            frame.add(new QuestionsPanel(frame, user, questions));
            frame.setSize(new Dimension(Main.WIDTH, Main.HEIGHT));
            frame.revalidate();
        });
        setPadding(resultHeading, nameLabel, userNameLabel, ageLabel, countryLabel, correctLabel, incorrectLabel, noAttemptLabel,
                highScoreLabel, timeTakenLabel);
        setPadding(resetButton, doneButton);
        addComponents(labelBox, resultHeading, nameLabel, userNameLabel, ageLabel, countryLabel, correctLabel, incorrectLabel, noAttemptLabel,
                highScoreLabel, timeTakenLabel);

        addComponents(buttonBox, resetButton, doneButton);

        add(labelBox, BorderLayout.CENTER);
        add(buttonBox, BorderLayout.SOUTH);

    }

    private void setPadding(JButton... buttons) {
        for (JButton button : buttons) {
            button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        }
    }

    private void setPadding(JLabel... labels) {
        for (JLabel label : labels) {
            label.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        }
    }

    private void addComponents(JComponent container, JComponent... components) {
        for (JComponent component : components) {
            container.add(component);
            container.add(Box.createGlue());
        }
    }

    public void paintComponent(Graphics g) {
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Graphics2D g2d = (Graphics2D) g;
        g2d.addRenderingHints(rh);
        g2d.drawImage(new ImageIcon("src/res/background1.png").getImage(), 0, 0, null);
    }
}
