package com.example.m2pokemonmanagement20261008.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "dresseurs")
@Data
public class DresseurEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "nom", nullable = false, length = 100)
    private String nom;

    @Column(name = "region", nullable = false, length = 100)
    private String region;

    @OneToMany(mappedBy = "dresseur", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PokemonEntity> pokemons = new ArrayList<>();
}
