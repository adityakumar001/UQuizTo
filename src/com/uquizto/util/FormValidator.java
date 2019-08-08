package com.uquizto.util;

public class FormValidator {
    private static final String NAME_REGEX = "^\\pL+[\\pL\\pZ\\pP]{0,}$";
    private static final String UNAME_REGEX = "^\\p{Alnum}[\\p{Alnum}._%+\\-$&]{5,25}$";
    private static final String PASSWORD_REGEX = "^[a-zA-Z\\d!@#$%^&*() ]{8,32}";

    public static boolean validate(String[] types, String... attributes) throws FormException {
        boolean valid = true;
        for (int i = 0; i < attributes.length; i++) {
            valid = valid && validate(types[i], attributes[i]);
        }
        return valid;
    }

    public static boolean validate(String type, String attribute) throws FormException {
        switch (type) {
            case "name":
                if (attribute.matches(NAME_REGEX)) return true;
                else {
                    throw new FormException("Enter a valid name!!");
                }
            case "age":
                if (Integer.parseInt(attribute) >= 5 || Integer.parseInt(attribute) <= 100) {
                    return true;
                } else {
                    throw new FormException("Enter a valid age between 5 & 100");
                }
            case "username":
                if (attribute.matches(UNAME_REGEX)) {
                    return true;
                } else {
                    throw new FormException("Username can only contain alphabet,digits and the following special characters:._%+-$&");
                }
            case "password":
                if (attribute.length() >= 8 && attribute.matches(PASSWORD_REGEX)) {
                    return true;
                } else {
                    throw new FormException("Password must contain 8-32 of the following characters : " + "!@#$%^&*() digits and alphabet");
                }
        }
        return true;
    }

    public static class FormException extends Exception {

        public FormException(String message) {
            super(message);
        }
    }
}


