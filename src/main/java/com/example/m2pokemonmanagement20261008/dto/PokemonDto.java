package com.example.m2pokemonmanagement20261008.dto;

import lombok.Data;

@Data
public class PokemonDto {
    private Long id;
    private String nom;
    private String type;
    private int niveau;
}
