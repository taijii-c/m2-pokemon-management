package com.example.m2pokemonmanagement20261008.services;

import com.example.m2pokemonmanagement20261008.dto.DresseurDto;
import com.example.m2pokemonmanagement20261008.entities.DresseurEntity;
import com.example.m2pokemonmanagement20261008.entities.PokemonEntity;
import com.example.m2pokemonmanagement20261008.enums.PokemonTypeEnum;
import com.example.m2pokemonmanagement20261008.repositories.DresseurRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DresseurServiceTest {

    @Mock
    private DresseurRepository dresseurRepository;

    @InjectMocks
    private DresseurService dresseurService;

    private PokemonEntity createPokemon(Long id, String nom, PokemonTypeEnum type, int niveau) {
        PokemonEntity pokemon = new PokemonEntity();
        pokemon.setId(id);
        pokemon.setNom(nom);
        pokemon.setType(type);
        pokemon.setNiveau(niveau);
        return pokemon;
    }

    private DresseurEntity createDresseur(Long id, String nom, String region, List<PokemonEntity> pokemons) {
        DresseurEntity dresseur = new DresseurEntity();
        dresseur.setId(id);
        dresseur.setNom(nom);
        dresseur.setRegion(region);
        dresseur.setPokemons(pokemons);
        return dresseur;
    }

    @Test
    void should_return_level_1_when_dresseur_has_no_pokemons() {
        // Arrange
        Long dresseurId = 1L;
        DresseurEntity dresseurSansPokemon = createDresseur(dresseurId, "Pierre", "Argenta", new ArrayList<>());
        when(dresseurRepository.findById(dresseurId)).thenReturn(Optional.of(dresseurSansPokemon));

        // Act
        int niveau = dresseurService.calculerNiveauDresseur(dresseurId);

        // Assert
        assertEquals(1, niveau);
    }

    @Test
    void should_calculate_exact_base_level_when_no_diversity_and_no_elite_bonus() {
        // Arrange
        Long dresseurId = 2L;
        List<PokemonEntity> pokemons = List.of(
                createPokemon(1L, "Staross", PokemonTypeEnum.EAU, 25),
                createPokemon(2L, "Psykokwak", PokemonTypeEnum.EAU, 15)
        );
        DresseurEntity dresseur = createDresseur(dresseurId, "Ondine", "Azuria", pokemons);
        when(dresseurRepository.findById(dresseurId)).thenReturn(Optional.of(dresseur));

        // Act
        int niveau = dresseurService.calculerNiveauDresseur(dresseurId);

        // Assert
        // Somme des niveaux : 25 + 15 = 40 (aucun bonus)
        assertEquals(40, niveau);
    }

    @Test
    void should_apply_diversity_bonus_when_dresseur_has_3_different_types() {
        // Arrange
        Long dresseurId = 3L;
        List<PokemonEntity> pokemons = List.of(
                createPokemon(1L, "Salameche", PokemonTypeEnum.FEU, 10),
                createPokemon(2L, "Carapuce", PokemonTypeEnum.EAU, 20),
                createPokemon(3L, "Bulbizarre", PokemonTypeEnum.PLANTE, 30)
        );
        DresseurEntity dresseur = createDresseur(dresseurId, "Régis", "Bourg Palette", pokemons);
        when(dresseurRepository.findById(dresseurId)).thenReturn(Optional.of(dresseur));

        // Act
        int niveau = dresseurService.calculerNiveauDresseur(dresseurId);

        // Assert
        // Base: 10 + 20 + 30 = 60, diversité (feu, eau, plante) = 20, total = 80
        assertEquals(80, niveau);
    }

    @Test
    void should_apply_elite_bonus_when_pokemons_have_level_greater_or_equal_to_50() {
        // Arrange
        Long dresseurId = 4L;
        List<PokemonEntity> pokemons = List.of(
                createPokemon(1L, "Dracaufeu", PokemonTypeEnum.FEU, 50),
                createPokemon(2L, "Arcanin", PokemonTypeEnum.FEU, 60)
        );
        DresseurEntity dresseur = createDresseur(dresseurId, "Auguste", "Cramois'Île", pokemons);
        when(dresseurRepository.findById(dresseurId)).thenReturn(Optional.of(dresseur));

        // Act
        int niveau = dresseurService.calculerNiveauDresseur(dresseurId);

        // Assert
        // Base: 50 + 60 = 110, élites (2 pokémons >= 50) = 2 * 10 = 20, total = 130
        assertEquals(130, niveau);
    }

    @Test
    void should_accumulate_both_bonuses_when_diversity_and_elite_conditions_are_met() {
        // Arrange
        Long dresseurId = 5L;
        List<PokemonEntity> pokemons = List.of(
                createPokemon(1L, "Pikachu", PokemonTypeEnum.ELECTRIK, 55),
                createPokemon(2L, "Dracaufeu", PokemonTypeEnum.FEU, 50),
                createPokemon(3L, "Bulbizarre", PokemonTypeEnum.PLANTE, 20)
        );
        DresseurEntity dresseur = createDresseur(dresseurId, "Sacha Ketchum", "Kanto", pokemons);
        when(dresseurRepository.findById(dresseurId)).thenReturn(Optional.of(dresseur));

        // Act
        int niveau = dresseurService.calculerNiveauDresseur(dresseurId);

        // Assert
        // Base: 55 + 50 + 20 = 125, diversité (3 types) = 20, élites (2
        // pokémons >= 50) = 20, total = 165
        assertEquals(165, niveau);
    }

    @Test
    void should_throw_exception_when_dresseur_not_found() {
        // Arrange
        Long nonExistentId = 999L;
        when(dresseurRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> dresseurService.calculerNiveauDresseur(nonExistentId));
    }

    @Test
    void should_return_correct_dresseur_dto_with_pokemons_list_when_get_dresseur_dto_called() {
        // Arrange
        Long dresseurId = 1L;
        List<PokemonEntity> pokemons = List.of(
                createPokemon(1L, "Pikachu", PokemonTypeEnum.ELECTRIK, 25)
        );
        DresseurEntity dresseur = createDresseur(dresseurId, "Sacha", "Kanto", pokemons);
        when(dresseurRepository.findById(dresseurId)).thenReturn(Optional.of(dresseur));

        // Act
        DresseurDto dto = dresseurService.getDresseurDto(dresseurId);

        // Assert
        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("Sacha", dto.getNomDresseur());
        assertEquals(25, dto.getNiveauDresseur());
        assertEquals(1, dto.getPokemons().size());
        assertEquals("Pikachu", dto.getPokemons().get(0).getNom());
    }
}
