package com.example.m2pokemonmanagement20261008.controllers;

import com.example.m2pokemonmanagement20261008.dto.DresseurDto;
import com.example.m2pokemonmanagement20261008.services.IDresseurService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dresseurs")
@RequiredArgsConstructor
public class DresseurController {

    private final IDresseurService dresseurService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getDresseur(@PathVariable Long id) {
        if (!dresseurService.exist(id)) {
            return new ResponseEntity<>("Le dresseur n'existe pas", HttpStatusCode.valueOf(201));
        }

        DresseurDto dto = dresseurService.getDresseurDto(id);
        return new ResponseEntity<>(dto, HttpStatusCode.valueOf(200));
    }
}
