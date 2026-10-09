package com.example.m2pokemonmanagement20261008.dto;

import lombok.Data;

import java.util.List;

@Data
public class DresseurDto {
    private Long id;
    private String nomDresseur;
    private int niveauDresseur;
    private List<PokemonDto> pokemons;
}
