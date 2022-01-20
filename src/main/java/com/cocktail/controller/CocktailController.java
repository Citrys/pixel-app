package com.cocktail.controller;

import com.cocktail.dto.CocktailDto;
import com.cocktail.errors.EntityNotFound;
import com.cocktail.model.Cocktail;
import com.cocktail.model.Response;
import com.cocktail.services.CocktailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/service")
public class CocktailController {

    private final CocktailService cocktailService;

    @Autowired
    CocktailController(CocktailService cocktailService) {
        this.cocktailService = cocktailService;
    }

    @GetMapping("/cocktails")
    public List<Cocktail> getAllCocktails() {
        log.info("Retrieving all cocktails from the database");
        return cocktailService.getAllCocktails();
    }

    @GetMapping("/cocktail/{id}")
    public Cocktail getCocktailById(@PathVariable String id) throws EntityNotFound {
        log.info("Retrieving single cocktail by id = {} from the database", id);
        return cocktailService.getCocktailById(id);
    }

    @PostMapping("/cocktail")
    @ResponseStatus(HttpStatus.CREATED)
    public Cocktail createCocktail(@RequestBody CocktailDto cocktailDto) {
        log.info("Saving new cocktail = {} into the db", cocktailDto.toString());
        return cocktailService.save(cocktailDto);
    }

    @ResponseBody
    @PutMapping("/cocktail/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Cocktail updateCocktail(@PathVariable String id, @RequestBody CocktailDto cocktailDto) throws EntityNotFound {
        log.info("Updating cocktail with id={}, updateBody = {}", id, cocktailDto.toString());
        return cocktailService.update(id, cocktailDto);
    }

    @ResponseBody
    @DeleteMapping("/cocktail/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response deleteCocktail(@PathVariable String id) {
        log.info("Delete cocktail by id = {}", id);
        return cocktailService.delete(id);
    }
}
