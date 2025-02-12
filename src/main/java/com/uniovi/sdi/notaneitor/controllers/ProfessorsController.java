package com.uniovi.sdi.notaneitor.controllers;

import com.uniovi.sdi.notaneitor.entities.Professor;
import com.uniovi.sdi.notaneitor.services.ProfessorsService;
import com.uniovi.sdi.notaneitor.validators.AddProfessorFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;

@Controller
@RequestMapping("/professor")
public class ProfessorsController {

    private final AddProfessorFormValidator addProfessorFormValidator;
    @Autowired
    private ProfessorsService professorsService;

    public ProfessorsController(AddProfessorFormValidator addProfessorFormValidator) {
        this.addProfessorFormValidator = addProfessorFormValidator;
    }

    @RequestMapping("/list")
    public String getList(Model professorModel) {
        professorModel.addAttribute("professorsList", professorsService.getProfessors());
        return "professor/list";
    }

    @RequestMapping("/add")
    public String getAdd(Model model) {
        model.addAttribute("professor", new Professor());
        return "professor/add";
    }

    @RequestMapping("/details")
    public String getDetails(Model professorModel, @RequestParam("id") Long id) {
        professorModel.addAttribute("professor", professorsService.detailsProfessor(id));
        return "professor/details";
    }

    @RequestMapping("/edit/{id}")
    public String getEdit(Model model, @PathVariable Long id) {
        model.addAttribute("professor", professorsService.detailsProfessor(id));
        return "professor/edit";
    }

    @RequestMapping("/delete/{id}")
    public String getDelete(@PathVariable Long id) {
        professorsService.removeProfessor(id);
        return "redirect:/professor/list";
    }

    @RequestMapping(value="/add", method = RequestMethod.POST)
    public String postAdd(@Validated Professor professor, BindingResult result) {
        addProfessorFormValidator.validate(professor, result);
        if (result.hasErrors()) {
            return "professor/add";
        }


        professorsService.addProfessor(professor);
        return "redirect:/professor/list";
    }

    @RequestMapping(value="/edit/{id}", method = RequestMethod.POST)
    public String postEdit(@ModelAttribute Professor professor, @PathVariable Long id) {
        professor.setId(id);
        professorsService.addProfessor(professor);
        return "redirect:/professor/details?id="+id;
    }
}
