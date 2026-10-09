package com.example.m2pokemonmanagement20261008.enums;

import lombok.Getter;

@Getter
public enum PokemonTypeEnum {

    FEU("Feu"),
    EAU("Eau"),
    PLANTE("Plante"),
    ELECTRIK("Électrik"),
    NORMAL("Normal"),
    GLACE("Glace"),
    COMBAT("Combat"),
    POISON("Poison"),
    SOL("Sol"),
    VOL("Vol"),
    PSY("Psy"),
    INSECTE("Insecte"),
    ROCHE("Roche"),
    SPECTRE("Spectre"),
    DRAGON("Dragon"),
    ACIER("Acier"),
    TENEBRES("Ténèbres"),
    FEE("Fée");

    private final String label;

    PokemonTypeEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }

    public static PokemonTypeEnum toType(String label) {
        if (label == null || label.trim().isEmpty()) {
            return null;
        }
        for (PokemonTypeEnum type : values()) {
            if (type.label.equalsIgnoreCase(label.trim()) || type.name().equalsIgnoreCase(label.trim())) {
                return type;
            }
        }
        throw new IllegalArgumentException("Type Pokémon inconnu : " + label);
    }
}
