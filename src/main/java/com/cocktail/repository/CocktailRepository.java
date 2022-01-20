package com.cocktail.repository;

import com.cocktail.model.Cocktail;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;


@EnableScan
public interface CocktailRepository extends CrudRepository<Cocktail, String> {
}

