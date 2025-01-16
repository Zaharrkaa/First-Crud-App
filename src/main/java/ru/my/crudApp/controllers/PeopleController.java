package ru.my.crudApp.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.my.crudApp.dao.PersonDAO;
import ru.my.crudApp.models.Person;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDAO personDAO;

    @Autowired
    public PeopleController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", personDAO.index());
        return "people/index";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        model.addAttribute("person", personDAO.show(id));
        return "people/show";
    }
    @GetMapping("/new")
    public String create(Model model){
        model.addAttribute("person", new Person());
        return "people/create";
    }
    @PostMapping()
    public String newPerson(@ModelAttribute @Valid Person person, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "people/create";
        }
        personDAO.addPerson(person);
        return "redirect:/people";
    }
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, @Valid Model model){
        model.addAttribute("person", personDAO.show(id));
        return "people/edit";
    }
    @PatchMapping("/{id}")
    public String editPerson(@PathVariable("id") int id, @ModelAttribute @Valid Person person, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "people/edit";
        }
        personDAO.editPerson(id, person);
        return "redirect:/people";
    }
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id, Model model){
        model.addAttribute("person", personDAO.show(id));
        return "people/delete";
    }
    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int id, @ModelAttribute Person person){
        personDAO.deletePerson(id);
        return "redirect:/people";
    }
}
