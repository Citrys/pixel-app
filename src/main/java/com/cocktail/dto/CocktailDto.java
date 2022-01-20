package com.cocktail.dto;

import lombok.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data

public class CocktailDto {
    @NonNull
    private String name;
    @NonNull
    private String description;
    @NonNull
    private List<String> ingredients;
}
