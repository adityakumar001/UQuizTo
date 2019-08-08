package com.uquizto.quiz_ui;

import com.uquizto.models.Question;
import com.uquizto.models.User;
import com.uquizto.network.ClientUtils;
import com.uquizto.quiz_ui.form.SignUpPanel;
import com.uquizto.util.FormValidator;

import javax.swing.*;
import java.awt.*;
import java.util.List;

@SuppressWarnings("serial")
public class LoginPanel extends JPanel {

    private JFrame quizFrame;
    private JPasswordField passwordField;
    private JTextField usernameField;

    public LoginPanel(JFrame frame) {

        this.quizFrame = frame;
        setLayout(new GridLayout(2, 1, 0, 0));

        Logo logo = new Logo(0, 0);
        logo.setOpaque(false);

        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(1, 2, 0, 0));
        loginPanel.setOpaque(false);

        Box textFieldBox = Box.createVerticalBox();
        Box labelBox = Box.createVerticalBox();

        JLabel usernameLabel = new JLabel("Enter Username   :  ");
        setUpComponent(usernameLabel, new Font("onyx", Font.PLAIN, 25), RIGHT_ALIGNMENT, 150, 30);

        JLabel passwordLabel = new JLabel("Enter passwordLabel   :  ");
        setUpComponent(passwordLabel, new Font("onyx", Font.PLAIN, 25), RIGHT_ALIGNMENT, 150, 30);

        usernameField = new JTextField();
        setUpComponent(usernameField, new Font(Font.DIALOG, Font.ITALIC, 18), LEFT_ALIGNMENT, 150, 30);

        passwordField = new JPasswordField();
        setUpComponent(passwordField, LEFT_ALIGNMENT, 150, 30);

        JButton loginButton = new JButton("Login");
        setUpComponent(loginButton, LEFT_ALIGNMENT, 80, 30);

        JButton signupButton = new JButton("Sign-Up");
        setUpComponent(signupButton, RIGHT_ALIGNMENT, 80, 30);

        addComponentsToBox(textFieldBox, usernameField, passwordField, loginButton);
        addComponentsToBox(labelBox, usernameLabel, passwordLabel, signupButton);

        loginButton.addActionListener(login -> {
            String username = usernameField.getText().trim();
            char[] password = passwordField.getPassword();
            if (username.equals("") || password.length == 0) {
                JOptionPane.showMessageDialog(quizFrame, "Please enter username and password!!");
                loginButton.setEnabled(true);
            } else {
                try {
                    if (FormValidator.validate("username", username)) {
                        User user = ClientUtils.login(username, new String(password));
                        if (user != null) {

                            if (user.getHigh_score() < 0) {
                                JOptionPane.showMessageDialog(LoginPanel.this, user.getName());
                                return;
                            }

                            List<Question> questions = ClientUtils.getQuestions();
                            if (questions.size() == 1) {
                                JOptionPane.showMessageDialog(LoginPanel.this,
                                        questions.get(0).getQuestion());
                                return;
                            } else {
                                QuestionsPanel questionsPanel = new QuestionsPanel(frame, user, questions);
                                quizFrame.remove(LoginPanel.this);
                                quizFrame.add(questionsPanel);
                                quizFrame.revalidate();
                            }
                        }
                        loginButton.setEnabled(true);
                    }
                } catch (FormValidator.FormException fe) {
                    JOptionPane.showMessageDialog(this, fe.getMessage());
                }
            }
        });

        signupButton.addActionListener(signup -> {
            quizFrame.remove(LoginPanel.this);
            quizFrame.add(new SignUpPanel(quizFrame));
            quizFrame.setTitle("Sign-Up");
            quizFrame.revalidate();
            quizFrame.repaint();
        });

        loginPanel.add(labelBox);
        loginPanel.add(textFieldBox);

        add(logo, BorderLayout.NORTH);
        add(loginPanel, BorderLayout.SOUTH);
    }

    private void setUpComponent(JComponent component, float alignment, int width, int height) {
        component.setAlignmentX(alignment);
        component.setMaximumSize(new Dimension(width, height));
        component.setPreferredSize(new Dimension(width, height));

    }

    private void setUpComponent(JComponent component, Font font, float alignment, int width, int height) {
        component.setAlignmentX(alignment);
        component.setFont(font);
        component.setMaximumSize(new Dimension(width, height));
        component.setPreferredSize(new Dimension(width, height));

    }

    private void addComponentsToBox(Box parent, JComponent... components) {
        for (JComponent component : components) {
            parent.add(Box.createVerticalStrut(20));
            parent.add(component);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        rh.add(new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON));
        g2d.setRenderingHints(rh);
        g2d.setColor(Color.CYAN);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.setColor(Color.BLACK);
        g2d.drawImage(new ImageIcon("src/res/background2.png").getImage(), 0, 0, null);
    }

}
