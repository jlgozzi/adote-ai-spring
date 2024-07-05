package com.app.adoteaiv2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.adoteaiv2.model.Pet;
import com.app.adoteaiv2.repository.PetRepository;

@Controller
@RequestMapping("/pets")
public class PetViewController {

    @Autowired
    private PetRepository petRepository;

    @GetMapping
    public String listPets(Model model) {
        List<Pet> pets = petRepository.findAll();
        model.addAttribute("pets", pets);
        return "list-pets";
    }

    @GetMapping("/{id}")
    public String viewPet(@PathVariable Long id, Model model) {
        Pet pet = petRepository.findById(id).orElse(null);
        model.addAttribute("pet", pet);
        return "view-pet";
    }

    @GetMapping("/add")
    public String addPetForm(Model model) {
        model.addAttribute("pet", new Pet());
        return "add-pet";
    }

    @PostMapping("/add")
    public String addPet(@ModelAttribute Pet pet) {
        petRepository.save(pet);
        return "redirect:/pets";
    }

    @PostMapping("/adopt/{id}")
    public String adoptPet(@PathVariable Long id) {
        Pet pet = petRepository.findById(id).orElse(null);
        if (pet != null) {
            pet.setAdopted(true);
            petRepository.save(pet);
        }
        return "redirect:/pets";
    }

    @PostMapping("/delete/{id}")
    public String deletePet(@PathVariable Long id) {
        petRepository.deleteById(id);
        return "redirect:/pets";
    }
}
