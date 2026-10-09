package com.example.m2pokemonmanagement20261008.services;

import com.example.m2pokemonmanagement20261008.dto.DresseurDto;
import com.example.m2pokemonmanagement20261008.entities.DresseurEntity;

public interface IDresseurService {
    int calculerNiveauDresseur(Long dresseurId);
    DresseurDto getDresseurDto(Long dresseurId);
    boolean exist(Long id);
    DresseurEntity get(Long id);
}
