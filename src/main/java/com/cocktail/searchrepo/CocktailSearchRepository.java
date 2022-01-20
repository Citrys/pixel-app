package com.cocktail.searchrepo;

import com.cocktail.mappings.CocktailMapping;
import com.cocktail.model.Cocktail;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

@EnableScan
public interface CocktailSearchRepository extends ElasticsearchRepository<CocktailMapping, String> {
    @Query("{\"bool\": {\"must\": [{\"match\": {\"name\": \"?0\"}}]}}")
    Page<Cocktail> findByName(String name, Pageable pageable);

    @Query("{\"bool\": {\"must\": [{\"match\": {\"ingredients\": \"?0\"}}]}}")
    Page<Cocktail> findByIngredient(String name, Pageable ingredient);
}
