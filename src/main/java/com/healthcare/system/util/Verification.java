package com.healthcare.system.util;

import com.healthcare.system.exceptions.ValidationException;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Verification {
    public static void verifyPasswordWhileRegister(String password) throws ValidationException {
        if( password == null ) throw new ValidationException("Password cannot be null");
        if (password.length() < 8) {
            throw new ValidationException("Password must be at least 8 characters long");
        }
        if (!password.matches(".*\\d.*")) {
            throw new ValidationException("Password must contain at least one digit");
        }
        if (!password.matches(".*[a-z].*")) {
            throw new ValidationException("Password must contain at least one lowercase letter");
        }
        if (!password.matches(".*[A-Z].*")) {
            throw new ValidationException("Password must contain at least one uppercase letter");
        }
        if (!password.matches(".*[@#$%^&+=].*")) {
            throw new ValidationException("Password must contain at least one special character from [@#$%^&+=]");
        }
        if (password.matches(".*\\s.*")) {
            throw new ValidationException("Password cannot contain whitespace characters");
        }
    }

    public static void verifyPasswordWhileLogin(String prevPassword, String currentPassword) throws ValidationException {
        if(!Objects.equals(prevPassword, currentPassword)) {
            throw new ValidationException("Username or Password did not match");
        }
    }

    public static void verifyUserName(String userName) throws ValidationException {
         if(userName == null || userName.isEmpty())
             throw new ValidationException("Username cannot be null or empty");
    }

    public static void verifyEmailWhileRegister(List<String> emails, String email) throws ValidationException {
        if(email == null || email.isEmpty()) throw new ValidationException("Email cannot be null or empty");
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        if(!matcher.matches()) throw new ValidationException("Not a valid email");
        if(emails.stream().anyMatch(e -> e.equals(email))) {
            throw new ValidationException("Email is already in use");
        }
    }

    private static String getEmail(Object obj) {
        try {
            return (String) obj.getClass().getMethod("getEmail").invoke(obj);
        } catch (Exception e) {
            return null;
        }
    }

    public static void verifyEmailWhileLogin(List<?> objects, String currentEmail) throws ValidationException {
        if(objects.stream().noneMatch(o -> Objects.equals(getEmail(o), currentEmail))) {
            throw new ValidationException("Email: " + currentEmail + " does not exist");
        }
    }

    public static void verifyCredentials(Class<?> clazz, Object object) throws ValidationException {
        Field[] fields = clazz.getDeclaredFields();
        for(Field field: fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(object);
                if (value == null) {
                    throw new ValidationException("Field '" + field.getName() + "' cannot be null");
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Failed to access field: " + field.getName(), e);
            }
        }
    }

}
