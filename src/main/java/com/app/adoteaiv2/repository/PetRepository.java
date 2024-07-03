package com.app.adoteaiv2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.adoteaiv2.model.Pet;

public interface PetRepository extends JpaRepository<Pet, Long> {
}