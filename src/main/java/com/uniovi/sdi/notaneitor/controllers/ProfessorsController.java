package com.uniovi.sdi.notaneitor.controllers;

import com.uniovi.sdi.notaneitor.entities.Professor;
import com.uniovi.sdi.notaneitor.services.ProfessorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;

@Controller
@RequestMapping("/professor")
public class ProfessorsController {

    @Autowired
    private ProfessorsService professorsService;

    @RequestMapping("/list")
    public String getList(Model professorModel) {
        professorModel.addAttribute("professorsList", professorsService.getProfessors());
        return "professor/list";
    }

    @RequestMapping("/add")
    public String getAdd() {
        return "professor/add";
    }

    @RequestMapping("/details")
    public String getDetails(Model professorModel, @RequestParam("id") Long id) {
        professorModel.addAttribute("professor", professorsService.detailsProfessor(id));
        return "professor/details";
    }

    @RequestMapping("/edit/{id}")
    public String getEdit(@PathVariable Long id) {
        return "professor/edit";
    }

    @RequestMapping("/delete/{id}")
    public String getDelete(@PathVariable Long id) {
        professorsService.removeProfessor(id);
        return "redirect:/professor/list";
    }

    @RequestMapping(value="/add", method = RequestMethod.POST)
    public String postAdd(@ModelAttribute Professor professor) {
        professorsService.addProfessor(professor);
        return "redirect:/professor/list";
    }

    @RequestMapping(value="/edit", method = RequestMethod.POST)
    public String postEdit(@ModelAttribute Professor professor) {
        professorsService.addProfessor(professor);
        return "redirect:/professor/details/"+professor.getId();
    }
}
