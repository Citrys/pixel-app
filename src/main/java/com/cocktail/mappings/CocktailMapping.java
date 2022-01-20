package com.cocktail.mappings;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import java.util.List;

@Document(indexName = "cocktail")
@AllArgsConstructor
@Builder
@Data
public class CocktailMapping {
    @Id
    private String id;

    String name;

    String description;

    List<String> ingredients;

    String created;

    String modified;
}
