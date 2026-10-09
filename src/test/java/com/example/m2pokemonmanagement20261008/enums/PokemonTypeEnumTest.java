package com.example.m2pokemonmanagement20261008.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PokemonTypeEnumTest {

    @Test
    void should_return_correct_label_when_get_label_called() {
        // Arrange
        PokemonTypeEnum type = PokemonTypeEnum.FEU;

        // Act
        String label = type.getLabel();

        // Assert
        assertEquals("Feu", label);
    }

    @Test
    void should_return_enum_when_valid_label_provided() {
        // Arrange
        String inputLabel = "Électrik";

        // Act
        PokemonTypeEnum result = PokemonTypeEnum.toType(inputLabel);

        // Assert
        assertEquals(PokemonTypeEnum.ELECTRIK, result);
    }

    @Test
    void should_return_enum_case_insensitively_when_valid_label_provided() {
        // Arrange
        String inputLabel = "eau";

        // Act
        PokemonTypeEnum result = PokemonTypeEnum.toType(inputLabel);

        // Assert
        assertEquals(PokemonTypeEnum.EAU, result);
    }

    @Test
    void should_throw_exception_when_unknown_type_provided() {
        // Arrange
        String unknown = "Inconnu";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> PokemonTypeEnum.toType(unknown));
    }
}
