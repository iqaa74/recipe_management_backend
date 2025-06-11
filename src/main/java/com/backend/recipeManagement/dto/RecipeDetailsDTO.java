package com.backend.recipeManagement.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDetailsDTO {
  Long recipeId;
  String title;
  String description;
  String cookingTime;
  String category;
  List<RecipeIngredientListDTO> ingredients;
}
