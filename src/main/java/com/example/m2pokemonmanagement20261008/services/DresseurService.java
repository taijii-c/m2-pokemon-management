package com.example.m2pokemonmanagement20261008.services;

import com.example.m2pokemonmanagement20261008.dto.DresseurDto;
import com.example.m2pokemonmanagement20261008.dto.PokemonDto;
import com.example.m2pokemonmanagement20261008.entities.DresseurEntity;
import com.example.m2pokemonmanagement20261008.entities.PokemonEntity;
import com.example.m2pokemonmanagement20261008.repositories.DresseurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DresseurService implements IDresseurService {

    private final DresseurRepository dresseurRepository;

    @Override
    public boolean exist(Long id) {
        return dresseurRepository.existsById(id);
    }

    @Override
    public DresseurEntity get(Long id) {
        return dresseurRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Dresseur non trouvé avec l'id : " + id));
    }

    @Override
    public int calculerNiveauDresseur(Long dresseurId) {
        DresseurEntity dresseur = get(dresseurId);
        List<PokemonEntity> pokemons = dresseur.getPokemons();

        // Cas limite
        if (pokemons == null || pokemons.isEmpty()) {
            return 1;
        }

        // Base de calcul: somme exacte des niveaux
        int baseNiveau = pokemons.stream()
                .mapToInt(PokemonEntity::getNiveau)
                .sum();

        // Bonus diversité des types
        long distinctTypesCount = pokemons.stream()
                .map(PokemonEntity::getType)
                .filter(Objects::nonNull)
                .map(String::trim)
                .map(String::toLowerCase)
                .distinct()
                .count();

        int bonusDiversite = (distinctTypesCount >= 3) ? 20 : 0;

        // Bonus pokémon >= niveau 50: +10 points
        long eliteCount = pokemons.stream()
                .filter(p -> p.getNiveau() >= 50)
                .count();

        int bonusElite = (int) eliteCount * 10;

        return baseNiveau + bonusDiversite + bonusElite;
    }

    @Override
    public DresseurDto getDresseurDto(Long dresseurId) {
        DresseurEntity dresseur = get(dresseurId);
        List<PokemonEntity> pokemons = (dresseur.getPokemons() != null)
                ? dresseur.getPokemons()
                : new ArrayList<>();
        int niveauCalcule = calculerNiveauDresseur(dresseurId);

        List<PokemonDto> pokemonDtos = pokemons.stream()
                .map(p -> {
                    PokemonDto pDto = new PokemonDto();
                    pDto.setId(p.getId());
                    pDto.setNom(p.getNom());
                    pDto.setType(p.getType());
                    pDto.setNiveau(p.getNiveau());
                    return pDto;
                })
                .collect(Collectors.toList());

        DresseurDto dto = new DresseurDto();
        dto.setId(dresseur.getId());
        dto.setNomDresseur(dresseur.getNom());
        dto.setNiveauDresseur(niveauCalcule);
        dto.setPokemons(pokemonDtos);

        return dto;
    }
}
