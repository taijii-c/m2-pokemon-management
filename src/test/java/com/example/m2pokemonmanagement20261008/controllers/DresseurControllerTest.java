package com.example.m2pokemonmanagement20261008.controllers;

import com.example.m2pokemonmanagement20261008.dto.DresseurDto;
import com.example.m2pokemonmanagement20261008.dto.PokemonDto;
import com.example.m2pokemonmanagement20261008.services.IDresseurService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DresseurController.class)
class DresseurControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IDresseurService dresseurService;

    private PokemonDto createPokemonDto(Long id, String nom, String type, int niveau) {
        PokemonDto dto = new PokemonDto();
        dto.setId(id);
        dto.setNom(nom);
        dto.setType(type);
        dto.setNiveau(niveau);
        return dto;
    }

    @Test
    void should_return_200_and_dresseur_dto_with_pokemons_array_when_dresseur_exists() throws Exception {
        // Arrange
        Long dresseurId = 1L;
        List<PokemonDto> pokemons = List.of(
                createPokemonDto(1L, "Pikachu", "Électrik", 55),
                createPokemonDto(2L, "Dracaufeu", "Feu", 50),
                createPokemonDto(3L, "Bulbizarre", "Plante", 20)
        );

        DresseurDto dto = new DresseurDto();
        dto.setId(dresseurId);
        dto.setNomDresseur("Sacha Ketchum");
        dto.setNiveauDresseur(165);
        dto.setPokemons(pokemons);

        when(dresseurService.exist(dresseurId)).thenReturn(true);
        when(dresseurService.getDresseurDto(dresseurId)).thenReturn(dto);

        // Act & Assert
        mockMvc.perform(get("/dresseurs/" + dresseurId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nomDresseur").value("Sacha Ketchum"))
                .andExpect(jsonPath("$.niveauDresseur").value(165))
                .andExpect(jsonPath("$.pokemons.length()").value(3))
                .andExpect(jsonPath("$.pokemons[0].nom").value("Pikachu"))
                .andExpect(jsonPath("$.pokemons[1].nom").value("Dracaufeu"))
                .andExpect(jsonPath("$.pokemons[2].nom").value("Bulbizarre"));
    }

    @Test
    void should_return_201_and_error_message_when_dresseur_does_not_exist() throws Exception {
        // Arrange
        Long nonExistentId = 99L;
        when(dresseurService.exist(nonExistentId)).thenReturn(false);

        // Act & Assert
        mockMvc.perform(get("/dresseurs/" + nonExistentId))
                .andExpect(status().is(201))
                .andExpect(content().string("Le dresseur n'existe pas"));
    }
}
