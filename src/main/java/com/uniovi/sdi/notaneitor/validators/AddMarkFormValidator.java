package com.uniovi.sdi.notaneitor.validators;

import com.uniovi.sdi.notaneitor.entities.Mark;
import com.uniovi.sdi.notaneitor.entities.User;
import com.uniovi.sdi.notaneitor.services.MarksService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class AddMarkFormValidator implements Validator {

    private final MarksService marksService;

    public AddMarkFormValidator(MarksService marksService) {
        this.marksService = marksService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Mark mark = (Mark) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "Error.empty");

        if(mark.getScore()<0||mark.getScore()>10){
            errors.rejectValue("score", "Error.mark.scoreOut");
        }
        if(mark.getDescription().length()<20){
            errors.rejectValue("description", "Error.mark.description.length");
        }

    }
}
