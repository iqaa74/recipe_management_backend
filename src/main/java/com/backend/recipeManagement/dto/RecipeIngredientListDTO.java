package com.backend.recipeManagement.dto;

public record RecipeIngredientListDTO(
    Long ingredientId, String ingredientName, String quantity, String unit) {}
