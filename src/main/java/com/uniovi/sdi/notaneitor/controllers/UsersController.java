package com.uniovi.sdi.notaneitor.controllers;

import com.uniovi.sdi.notaneitor.entities.Mark;
import com.uniovi.sdi.notaneitor.entities.User;
import com.uniovi.sdi.notaneitor.services.MarksService;
import com.uniovi.sdi.notaneitor.services.RolesService;
import com.uniovi.sdi.notaneitor.services.SecurityService;
import com.uniovi.sdi.notaneitor.services.UsersService;
import com.uniovi.sdi.notaneitor.validators.SignUpFormValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
public class UsersController {

    private final SignUpFormValidator signUpFormValidator;
    private final UsersService usersService;
    private final SecurityService securityService;
    private final RolesService rolesService;
    private final MarksService marksService;

    public UsersController(UsersService usersService, SecurityService securityService,
                           SignUpFormValidator signUpFormValidator, RolesService rolesService,
                           MarksService marksService) {
        this.usersService = usersService;
        this.securityService = securityService;
        this.signUpFormValidator = signUpFormValidator;
        this.rolesService = rolesService;
        this.marksService = marksService;
    }

    @RequestMapping("/user/list")
    public String getListado(Model model) {
        model.addAttribute("usersList", usersService.getUsers());
        return "user/list";
    }

    @RequestMapping("/user/add")
    public String getUser(Model model) {
        model.addAttribute("rolesList", rolesService.getRoles());
        model.addAttribute("usersList", usersService.getUsers());
        return "user/add";
    }

    @RequestMapping(value="/user/add", method = RequestMethod.POST)
    public String setUser( @ModelAttribute User user){
        usersService.addUser(user);
        return "redirect:/user/list";
    }

    @RequestMapping("/user/details/{id}")
    public String getDetail(Model model, @PathVariable Long id) {
        model.addAttribute("user", usersService.getUser(id));
        return "user/details";
    }

    @RequestMapping("/user/delete/{id}")
    public String delete(@PathVariable Long id) {
        usersService.deleteUser(id);
        return "redirect:/user/list";
    }
    @RequestMapping(value = "/user/edit/{id}")
    public String getEdit(Model model, @PathVariable Long id) {
        User user = usersService.getUser(id);
        model.addAttribute("user", user);
        return "user/edit";
    }
    @RequestMapping(value = "/user/edit/{id}", method = RequestMethod.POST)
    public String setEdit(@PathVariable Long id, @ModelAttribute User user) {
        usersService.updateUser(user);
        return "redirect:/user/details/" + id;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signup(@Validated User user, BindingResult result) {
        signUpFormValidator.validate(user, result);
        if (result.hasErrors()) {
            return "signup";
        }
        user.setRole(rolesService.getRoles()[0]);
        usersService.addUser(user);
        securityService.autoLogin(user.getDni(), user.getPasswordConfirm());
        return "redirect:home";
    }
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = { "/home" }, method = RequestMethod.GET)
    public String home(Model model, Pageable pageable, Principal principal) {
        String dni = principal.getName();
        User user = usersService.getUserByDni(dni);
        Page<Mark> marks = marksService.getMarksForUser(pageable, user);
        model.addAttribute("markList", marks.getContent() );
        model.addAttribute("page", marks);
        return "home";
    }

    @RequestMapping(value="/signup", method = RequestMethod.GET)
    public String signup(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @RequestMapping("/user/list/update")
    public String updateList(Model model) {
        model.addAttribute("usersList", usersService.getUsers());
        return "user/list :: usersTable";
    }

    @RequestMapping("/logout")
    public String logout() { return "login";}
}
