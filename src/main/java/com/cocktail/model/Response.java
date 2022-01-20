package com.cocktail.model;

import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    String result;
    String status;
}
