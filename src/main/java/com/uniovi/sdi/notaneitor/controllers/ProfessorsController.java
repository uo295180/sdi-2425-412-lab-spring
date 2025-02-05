package com.uniovi.sdi.notaneitor.controllers;

import com.uniovi.sdi.notaneitor.entities.Professor;
import com.uniovi.sdi.notaneitor.services.ProfessorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;

@RestController
@RequestMapping("/professor")
public class ProfessorsController {

    @Autowired
    private ProfessorsService professorsService;

    @RequestMapping("/list")
    public String getList() {
        return professorsService.getProfessors().toString();
    }

    @RequestMapping("/add")
    public String getAdd() {
        return "Añadir professor";
    }

    @RequestMapping("/details")
    public String getDetails(@RequestParam("id") Long id) {
        return professorsService.detailsProfessor(id);
    }

    @RequestMapping("/edit/{id}")
    public String getEdit(@PathVariable Long id) {
        return "Editando profesor";
    }

    @RequestMapping("/delete/{id}")
    public String getDelete(@PathVariable Long id) {
        int numprof = professorsService.getProfessors().size();
        professorsService.removeProfessor(id);
        return (numprof-1 == professorsService.getProfessors().size() ? "Ok" : "Id no encontrado") + " ->\n" + professorsService.getProfessors().toString();
    }

    @RequestMapping(value="/add", method = RequestMethod.POST)
    public String postAdd(@ModelAttribute Professor professor) {
        professorsService.addProfessor(professor);
        return "Ok";
    }

    @RequestMapping(value="/edit", method = RequestMethod.POST)
    public String postEdit(@ModelAttribute Professor professor) {
        professorsService.addProfessor(professor);
        return "redirect:/details/"+professor.getId();
    }
}
