package com.uniovi.sdi.notaneitor.validators;

import com.uniovi.sdi.notaneitor.entities.Professor;
import com.uniovi.sdi.notaneitor.entities.User;
import com.uniovi.sdi.notaneitor.services.ProfessorsService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class AddProfessorFormValidator implements Validator {

    private final ProfessorsService professorsService;

    public AddProfessorFormValidator(ProfessorsService professorsService) {
        this.professorsService = professorsService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Professor professor = (Professor) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dni", "Error.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "Error.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "surname", "Error.empty");

        if (professor.getDni().length() != 9) {
            errors.rejectValue("dni", "Error.professor.dni.length");
        }

        if(!Character.isLetter(professor.getDni().toCharArray()[professor.getDni().length()-1])){
            errors.rejectValue("dni", "Error.professor.dni.lastCharacter");
        }

        if (professorsService.getProfessorsByDni(professor.getDni()) != null) {
            errors.rejectValue("dni", "Error.professor.dni.duplicate");}


    }

}
