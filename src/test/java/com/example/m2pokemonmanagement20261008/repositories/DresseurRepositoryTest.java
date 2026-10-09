package com.example.m2pokemonmanagement20261008.repositories;

import com.example.m2pokemonmanagement20261008.entities.DresseurEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class DresseurRepositoryTest {

    @Autowired
    private DresseurRepository dresseurRepository;

    @Test
    void should_find_dresseur_by_id_when_exists_in_database() {
        // Arrange
        Long dresseurId = 1L;

        // Act
        Optional<DresseurEntity> result = dresseurRepository.findById(dresseurId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Sacha Ketchum", result.get().getNom());
        assertEquals("Kanto", result.get().getRegion());
    }

    @Test
    void should_retrieve_exact_pokemon_list_size_from_data_sql_when_dresseur_queried() {
        // Arrange
        Long dresseurSachaId = 1L;
        Long dresseurOndineId = 2L;
        Long dresseurPierreId = 3L;

        // Act
        DresseurEntity sacha = dresseurRepository.findById(dresseurSachaId).orElseThrow();
        DresseurEntity ondine = dresseurRepository.findById(dresseurOndineId).orElseThrow();
        DresseurEntity pierre = dresseurRepository.findById(dresseurPierreId).orElseThrow();

        // Assert
        assertEquals(3, sacha.getPokemons().size());
        assertEquals(2, ondine.getPokemons().size());
        assertEquals(0, pierre.getPokemons().size());
    }

    @Test
    void should_save_and_retrieve_new_dresseur_when_valid_entity() {
        // Arrange
        DresseurEntity newDresseur = new DresseurEntity();
        newDresseur.setNom("Cynthia");
        newDresseur.setRegion("Sinnoh");

        // Act
        DresseurEntity saved = dresseurRepository.save(newDresseur);
        Optional<DresseurEntity> retrieved = dresseurRepository.findById(saved.getId());

        // Assert
        assertTrue(retrieved.isPresent());
        assertEquals("Cynthia", retrieved.get().getNom());
        assertEquals("Sinnoh", retrieved.get().getRegion());
        assertEquals(0, retrieved.get().getPokemons().size());
    }

    @Test
    void should_return_empty_optional_when_dresseur_not_found() {
        // Arrange
        Long nonExistentId = 999L;

        // Act
        Optional<DresseurEntity> result = dresseurRepository.findById(nonExistentId);

        // Assert
        assertFalse(result.isPresent());
    }
}
