package com.example.demo.validation;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.passay.LengthRule;
import org.passay.MessageResolver;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.PropertiesMessageResolver;
import org.passay.RuleResult;
import org.passay.EnglishSequenceData;
import org.passay.IllegalSequenceRule;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public void initialize(final ValidPassword arg0) {

    }

    @Override
    public boolean isValid(final String password, final ConstraintValidatorContext context) {
        // customization of validation messages
        Properties props = new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("passay.properties");
        try {
            props.load(inputStream);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        MessageResolver resolver = new PropertiesMessageResolver(props);
        
        PasswordValidator validator = new PasswordValidator(resolver, Arrays.asList(
            // password length between 8 and 50 characters
            new LengthRule(8, 30),

            // password must contain at least one upper-case character
            new CharacterRule(EnglishCharacterData.UpperCase, 1),

            // password must contain at least one lower-case character
            new CharacterRule(EnglishCharacterData.LowerCase, 1),

            // password must contain at least one digit character
            new CharacterRule(EnglishCharacterData.Digit, 1),

            // at least one symbol (special character)
            new CharacterRule(EnglishCharacterData.Special, 1),

            // rejects passwords that contain a sequence of >= 5 characters alphabetical  (e.g. abcdef)
            new IllegalSequenceRule(EnglishSequenceData.Alphabetical, 5, false),

            // rejects passwords that contain a sequence of >= 5 characters numerical   (e.g. 12345)
            new IllegalSequenceRule(EnglishSequenceData.Numerical, 5, false), 
            
            // rejects passwords that contain a sequence of >= 6 characters qwerty   (e.g. qwerty)
            new IllegalSequenceRule(EnglishSequenceData.USQwerty, 6, false)));
        
        RuleResult result = validator.validate(new PasswordData(password));
        
        if (result.isValid()) {
            return true;
        }

        List<String> messages = validator.getMessages(result);
        String messageTemplate = String.join(",", messages);
        context.buildConstraintViolationWithTemplate(messageTemplate).addConstraintViolation().disableDefaultConstraintViolation();

        return false;

    }
}