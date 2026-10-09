package com.example.m2pokemonmanagement20261008.entities;

import com.example.m2pokemonmanagement20261008.enums.PokemonTypeEnum;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "pokemons")
@Data
public class PokemonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "nom", nullable = false, length = 100)
    private String nom;

    @Column(name = "type", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private PokemonTypeEnum type;

    @Column(name = "niveau", nullable = false)
    private int niveau;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dresseur_id")
    private DresseurEntity dresseur;
}
