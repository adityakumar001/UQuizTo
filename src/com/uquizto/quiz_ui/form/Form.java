package com.uquizto.quiz_ui.form;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.util.Locale;

class Form extends JPanel {

    private static final long serialVersionUID = 7754346111849647930L;
    JTextField usernameField;
    NumberField ageField;
    NameField nameField;
    JPasswordField passwordField, rePasswordField;
    JComboBox<String> genderComboBox, countryComboBox;
    private JPanel generalInfoPanel, passwordPanel, emptyPanel, inputPanel;

    Form() {
        repaint();
        setLayout(new GridLayout(1, 2, 20, 20));
        initializePanels();
        setPanelLayouts();
        String[] genders = {"Male", "Female", "Other"};
        String name = "Name : ", age = "Your Age : ", gender = "Gender : ", username = "Username : ",
                country = "Your Country Name : ", password = "Password : ",
                repassword = "Re-Enter Password : ";

        JLabel nameLabel = new JLabel(name);
        JLabel ageLabel = new JLabel(age);
        JLabel countryLabel = new JLabel(country);
        JLabel usernameLabel = new JLabel(username);
        JLabel genderLabel = new JLabel(gender);
        JLabel passwordLabel = new JLabel(password);
        JLabel repasswordLabel = new JLabel(repassword);

        nameField = new NameField();
        ageField = new NumberField();
        usernameField = new JTextField();

        passwordField = new JPasswordField();
        rePasswordField = new JPasswordField();

        genderComboBox = new JComboBox<>(genders);
        String[] countries = getCountries();
        countryComboBox = new JComboBox<>(countries);

        addComponents(generalInfoPanel, nameLabel, nameField, ageLabel, ageField, genderLabel, genderComboBox,
                usernameLabel, usernameField, countryLabel, countryComboBox);

        addComponents(passwordPanel, passwordLabel, passwordField, repasswordLabel, rePasswordField);

        setOpacity();
        addPanels();

    }

    private String[] getCountries() {
        String[] countries = Locale.getISOCountries();
        for (int i = 0; i < countries.length; i++) {
            Locale locale = new Locale("", countries[i]);
            countries[i] = locale.getDisplayCountry();
        }
        return countries;
    }

    private void addComponents(JPanel panel, JComponent... components) {
        for (JComponent component : components) {
            panel.add(component);
        }
    }

    private void setOpacity() {
        emptyPanel.setOpaque(false);
        inputPanel.setOpaque(false);
        passwordPanel.setOpaque(false);
        generalInfoPanel.setOpaque(false);

    }

    private void initializePanels() {

        emptyPanel = new JPanel();
        inputPanel = new JPanel();

        generalInfoPanel = new JPanel();
        generalInfoPanel.setBorder(BorderFactory.createTitledBorder("General iNFO"));

        passwordPanel = new JPanel();
        passwordPanel.setBorder(BorderFactory.createTitledBorder("setUpPassword()"));

    }

    private void setPanelLayouts() {
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        generalInfoPanel.setLayout(new GridLayout(5, 2, 0, 20));
        passwordPanel.setLayout(new GridLayout(2, 2, 30, 20));
    }

    private void addPanels() {
        inputPanel.add(generalInfoPanel);
        emptyPanel.add(passwordPanel);
        inputPanel.add(Box.createVerticalStrut(100));
        add(inputPanel);
        add(emptyPanel);
    }

    public class NumberField extends JTextField {

        @Override
        protected Document createDefaultModel() {
            return new NumberDocument();
        }

        class NumberDocument extends PlainDocument {
            String numbers = "1234567890";

            @Override
            public void insertString(int offs, String str, AttributeSet a)
                    throws BadLocationException {
                if (numbers.contains(str))
                    super.insertString(offs, str, a);
            }
        }
    }

    public class NameField extends JTextField {

        @Override
        protected Document createDefaultModel() {
            return new AlphabetDocument();
        }

        class AlphabetDocument extends PlainDocument {
            String alphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz. ";

            @Override
            public void insertString(int offs, String str, AttributeSet a)
                    throws BadLocationException {
                if (alphabets.contains(str))
                    super.insertString(offs, str, a);
            }
        }
    }

}