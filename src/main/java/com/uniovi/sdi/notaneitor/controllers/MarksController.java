package com.uniovi.sdi.notaneitor.controllers;

import com.uniovi.sdi.notaneitor.entities.Mark;
import com.uniovi.sdi.notaneitor.services.MarksService;
import com.uniovi.sdi.notaneitor.services.UsersService;
import com.uniovi.sdi.notaneitor.validators.AddMarkFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

@Controller
public class MarksController {
    private final AddMarkFormValidator addMarkFormValidator;
    private final MarksService marksService;
    private final UsersService usersService;
    private final HttpSession httpSession;

    public MarksController(MarksService marksService, UsersService usersService, AddMarkFormValidator addMarkFormValidator, HttpSession httpSession) {
        this.marksService = marksService;
        this.usersService = usersService;
        this.addMarkFormValidator = addMarkFormValidator;
        this.httpSession = httpSession;
    }

    @RequestMapping("/mark/list")
    public String getList(Model model) {
        model.addAttribute("markList", marksService.getMarks());
        return "mark/list";
    }

    @RequestMapping(value="/mark/add", method = RequestMethod.POST)
    public String setMark(@Validated Mark mark, BindingResult result, Model model) {
        addMarkFormValidator.validate(mark, result);
        if (result.hasErrors()) {
            model.addAttribute("usersList", usersService.getUsers());
            return "mark/add";
        }

        marksService.addMark(mark);
        return "redirect:/mark/list";
    }

    @RequestMapping("/mark/details/{id}")
    public String getDetail(Model model, @PathVariable Long id){
        model.addAttribute("mark", marksService.getMark(id));
        return "mark/details";
    }

    @RequestMapping("/mark/delete/{id}")
    public String deleteMark(@PathVariable Long id){
        marksService.deleteMark(id);
        return "redirect:/mark/list";
    }

    @RequestMapping(value="/mark/add")
    public String getMark(Model model) {
        model.addAttribute("usersList", usersService.getUsers());
        model.addAttribute("mark", new Mark());
        return "mark/add";
    }

    @RequestMapping(value="/mark/edit/{id}")
    public String getEdit(Model model, @PathVariable Long id){
        model.addAttribute("mark", marksService.getMark(id));
        model.addAttribute("usersList", usersService.getUsers());
        return "mark/edit";
    }

    @RequestMapping(value="/mark/edit/{id}", method=RequestMethod.POST)
    public String setEdit(@ModelAttribute Mark mark, @PathVariable Long id){
        Mark originalMark = marksService.getMark(id);
// modificar solo score y description
        originalMark.setScore(mark.getScore());
        originalMark.setDescription(mark.getDescription());
        marksService.addMark(originalMark);
        return "redirect:/mark/details/" + id;
    }

    @RequestMapping("/mark/list/update")
    public String updateList(Model model){
        model.addAttribute("markList", marksService.getMarks() );
        return "mark/list :: marksTable";
    }
}
