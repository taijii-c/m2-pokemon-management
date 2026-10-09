package com.example.m2pokemonmanagement20261008.repositories;

import com.example.m2pokemonmanagement20261008.entities.PokemonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PokemonRepository extends JpaRepository<PokemonEntity, Long> {
}
