package com.app.adoteaiv2.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.adoteaiv2.model.Pet;
import com.app.adoteaiv2.repository.PetRepository;

@RestController
@RequestMapping("/pets")
public class PetController {

    @Autowired
    private PetRepository petRepository;

    @GetMapping
    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pet> getPetById(@PathVariable Long id) {
        Optional<Pet> pet = petRepository.findById(id);
        if (pet.isPresent()) {
            return new ResponseEntity<>(pet.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public Pet createPet(@RequestBody Pet pet) {
        return petRepository.save(pet);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pet> updatePet(@PathVariable Long id, @RequestBody Pet petDetails) {
        Optional<Pet> pet = petRepository.findById(id);
        if (pet.isPresent()) {
            Pet updatedPet = pet.get();
            if (petDetails.getName() != null) {
                updatedPet.setName(petDetails.getName());
            }
            if (petDetails.getType() != null) {
                updatedPet.setType(petDetails.getType());
            }
            if (petDetails.getBreed() != null) {
                updatedPet.setBreed(petDetails.getBreed());
            }
            if (petDetails.getSize() != null) {
                updatedPet.setSize(petDetails.getSize());
            }
            if (petDetails.getAge() != null) {
                updatedPet.setAge(petDetails.getAge());
            }
            if (petDetails.getContactNumber() != null) {
                updatedPet.setContactNumber(petDetails.getContactNumber());
            }
            updatedPet.setAdopted(petDetails.isAdopted());
            return new ResponseEntity<>(petRepository.save(updatedPet), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePet(@PathVariable Long id) {
        try {
            petRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Pet> adoptPet(@PathVariable Long id) {
        Optional<Pet> pet = petRepository.findById(id);
        if (pet.isPresent()) {
            Pet adoptedPet = pet.get();
            adoptedPet.setAdopted(true);
            return new ResponseEntity<>(petRepository.save(adoptedPet), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
