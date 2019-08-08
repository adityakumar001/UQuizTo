package com.uquizto.quiz_ui;

import com.uquizto.Main;
import com.uquizto.models.Question;
import com.uquizto.models.Result;
import com.uquizto.models.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

/**
 * This class handles the main QnA section of the program.
 */

@SuppressWarnings("serial")
public class QuestionsPanel extends JPanel {


    private Timer autoQuesSwitcher;
    private ButtonGroup buttonGroup;
    private boolean quizRunning;
    private JLabel quesTimerLabel;
    private JRadioButton[] optionRadioButtons = new JRadioButton[4];
    private List<Question> questions;
    private Iterator<Question> questionIterator;
    private Question currentQues;
    private JLabel questionLabel;
    private Result result = new Result();
    private StopWatch questionStopWatch = new StopWatch();
    private User user;

    QuestionsPanel(JFrame frame, User user, List<Question> questions) {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        this.questions = questions;
        this.questionIterator = questions.iterator();
        Box buttonBox = Box.createHorizontalBox();
        Box optionsBox = Box.createVerticalBox();
        questionLabel = new JLabel();
        quesTimerLabel = new JLabel(questionStopWatch.timer + "s/30s");
        JButton nextButton = new JButton("Next");
        buttonGroup = new ButtonGroup();
        for (int i = 0; i < optionRadioButtons.length; i++) {
            optionRadioButtons[i] = new JRadioButton();
            buttonGroup.add(optionRadioButtons[i]);
        }

        ActionListener taskPerformer = task -> {
            checkQuestion();
            if (questionIterator.hasNext()) {
                nextQuestion();
                autoQuesSwitcher.restart();
            } else {
                autoQuesSwitcher.stop();
                quizRunning = false;
                displayResult(frame);
            }
        };

        autoQuesSwitcher = new Timer(30000, taskPerformer);

        questionLabel.setFont(new Font("helvetica", Font.ITALIC, 16));
        nextButton.addActionListener(taskPerformer);

        for (JRadioButton radioButton : optionRadioButtons) {
            optionsBox.add(Box.createVerticalGlue());
            optionsBox.add(radioButton);
            optionsBox.add(Box.createVerticalGlue());
        }
        buttonBox.add(quesTimerLabel);
        buttonBox.add(Box.createHorizontalGlue());
        buttonBox.add(nextButton);


        add(questionLabel, BorderLayout.NORTH);
        add(optionsBox, BorderLayout.CENTER);
        add(buttonBox, BorderLayout.SOUTH);

        result.setStartTime(System.currentTimeMillis());
        autoQuesSwitcher.start();
        quizRunning = true;
        questionStopWatch.start();
        nextQuestion();
        this.user = user;
    }


    private void checkQuestion() {

        boolean noneSelected = true;
        for (int i = 0; i < optionRadioButtons.length; i++) {
            if (optionRadioButtons[i].isSelected()) {
                noneSelected = false;
                if ((int) (currentQues.getAnswer().charAt(0)) - 65 == i)
                    result.correct++;
                else
                    result.incorrect++;
                break;
            }
        }
        if (noneSelected) {
            result.nonAttempts++;
        }
    }

    private void nextQuestion() {
        currentQues = questionIterator.next();
        questionLabel.setText("<html><p>Q-" + (questions.indexOf(currentQues) + 1) + ") " + currentQues.getQuestion() + "</p></html>");
        for (int i = 0; i < optionRadioButtons.length; i++) {
            optionRadioButtons[i].setText("<html><p>" + currentQues.getOptions()[i] + "</p></html>");
            buttonGroup.clearSelection();
        }
        questionStopWatch.reset();
    }

    private void displayResult(JFrame frame) {

        result.setEndTime(System.currentTimeMillis());
        frame.remove(QuestionsPanel.this);
        frame.add(new ResultPanel(frame, result, user));
        frame.setSize(new Dimension(Main.WIDTH, Main.HEIGHT));
        frame.revalidate();

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        Graphics2D g2d = (Graphics2D) g;
        g2d.addRenderingHints(rh);
        g2d.drawImage(new ImageIcon("src/res/background2.png").getImage(), 0, 0, null);
        g2d.setFont(new Font("Bradley Handwriting", Font.ITALIC, 16));

    }

    class StopWatch extends Thread {

        private int timer = 0;
        private long prevTime;

        @Override
        public void run() {
            prevTime = System.nanoTime();
            while (quizRunning) {
                if ((System.nanoTime() - prevTime) / Math.pow(10, 9) >= 1) {
                    quesTimerLabel.setText(++timer + "s/30s");
                    prevTime = System.nanoTime();
                }
            }
        }

        private void reset() {
            timer = 0;
            prevTime = System.nanoTime();
        }
    }
}