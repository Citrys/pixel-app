package com.cocktail.services;
import com.cocktail.dto.CocktailDto;
import com.cocktail.errors.EntityNotFound;
import com.cocktail.mappings.CocktailMapping;
import com.cocktail.model.Cocktail;
import com.cocktail.model.Response;
import com.cocktail.repository.CocktailRepository;
import com.cocktail.searchrepo.CocktailSearchRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Log4j2
@Service
public class CocktailService {
    @Autowired
    private CocktailRepository repository;

    @Autowired
    private CocktailSearchRepository esRepository;

    public Cocktail save(CocktailDto cocktailDto) {
        Cocktail cock = createBookBuilder(cocktailDto).build();
        log.info("Cocktail item to save is:" + cock.toString());
        Cocktail savedItem = repository.save(cock);
        CocktailMapping esItem = createEsDocument(savedItem).build();

        log.info("Cocktail ES item to save is:" + esItem.toString());
        esRepository.save(createEsDocument(cock).build());
        return savedItem;
    }

    public Cocktail update(String id, CocktailDto cocktailDto) throws EntityNotFound {
        Optional<Cocktail> item = repository.findById(id);
        if (item.isPresent()) {
            Cocktail toUpdate = createBookBuilder(cocktailDto).id(id)
                    .created(item.get().getCreated()).build();
            esRepository.save(createEsDocument(toUpdate).build());
            return repository.save(toUpdate);
        } else {
            throw new EntityNotFound();
        }
    }

    public Response delete(String id) {
        Optional<Cocktail> cocktailToDelete = repository.findById(id);
        if (cocktailToDelete.isPresent()) {
            esRepository.deleteById(id);
            repository.deleteById(id);
            return new Response("success", "deleted");
        } else {
            return new Response("failed", "Item Not Found");
        }
    }

    public Cocktail getCocktailById(String id) throws EntityNotFound {
        return repository.findById(id).orElseThrow(EntityNotFound::new);
    }

    public List<Cocktail> getAllCocktails() {
        final Iterable<Cocktail> allBooks = repository.findAll();
        return StreamSupport.stream(allBooks.spliterator(), false).collect(Collectors.toList());
    }

    private Cocktail.CocktailBuilder createBookBuilder(CocktailDto dto) {
        return Cocktail.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .ingredients(dto.getIngredients())
                .created(new Date().toString())
                .modified(new Date().toString());
    }

    private CocktailMapping.CocktailMappingBuilder createEsDocument(Cocktail cocktail) {
        return CocktailMapping.builder()
                .id(cocktail.getId())
                .name(cocktail.getName())
                .description(cocktail.getDescription())
                .ingredients(cocktail.getIngredients())
                .created(cocktail.getCreated())
                .modified(cocktail.getModified());
    }
}
