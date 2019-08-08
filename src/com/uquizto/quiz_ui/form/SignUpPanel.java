package com.uquizto.quiz_ui.form;

import com.uquizto.util.FormValidator;
import com.uquizto.Main;
import com.uquizto.models.User;
import com.uquizto.network.ClientUtils;
import com.uquizto.quiz_ui.LoginPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class SignUpPanel extends JPanel {

    private static final long serialVersionUID = -938192121649739530L;
    private JFrame frame;
    private Form form;
    private JPanel buttonPanel;

    private JButton backButton, submitButton;

    public SignUpPanel(JFrame frame) {
        this.frame = frame;
        this.setSize(frame.getSize());
        setLayout(new BorderLayout());
        initializeComponents();
        addComponents();
    }


    private void addComponents() {
        add(form, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        buttonPanel.add(backButton, BorderLayout.WEST);
        buttonPanel.add(submitButton, BorderLayout.EAST);
    }

    private void initializeComponents() {

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.setOpaque(false);

        form = new Form();
        form.setOpaque(false);

        backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(90, 40));
        backButton.addActionListener(back -> {
            frame.remove(SignUpPanel.this);
            frame.add(new LoginPanel(frame));
            frame.setSize(new Dimension(Main.WIDTH, Main.HEIGHT));
            frame.revalidate();
        });

        submitButton = new JButton("Submit");
        submitButton.setPreferredSize(new Dimension(90, 40));
        submitButton.addActionListener(submit -> {
            String name = form.nameField.getText();
            String uname = form.usernameField.getText();
            String country = form.countryComboBox.getItemAt(form.countryComboBox.getSelectedIndex());
            String age = (form.ageField.getText());
            String gender = form.genderComboBox.getItemAt(form.genderComboBox.getSelectedIndex());
            String password = new String(form.passwordField.getPassword());
            try {
                if (FormValidator.validate(new String[]{"name", "age", "username", "password"}
                        , name, age, uname, password)) {
                    if (Arrays.equals(form.passwordField.getPassword(), form.rePasswordField.getPassword())) {
                        User user = new User(name, uname, Integer.parseInt(age), gender, country, 0);
                        user.setPassword(password);
                        String status = ClientUtils.signUpCall(user);
                        if (status != null && status.equals("Success")) {
                            JOptionPane.showMessageDialog(frame, "Sign-Up was successful !!!");
                            frame.remove(SignUpPanel.this);
                            frame.add(new LoginPanel(frame));
                            frame.revalidate();
                            frame.repaint();
                        } else {
                            JOptionPane.showMessageDialog(SignUpPanel.this, status);
                        }
                    } else {
                        throw new FormValidator.FormException("Passwords don't match!!");
                    }
                }
            } catch (FormValidator.FormException fe) {
                JOptionPane.showMessageDialog(this, fe.getMessage());
            }
        });
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Image img = new ImageIcon("src/res/background3.png").getImage();
        g2d.drawImage(img, 0, 0, null);
    }
}


