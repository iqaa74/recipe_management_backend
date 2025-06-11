package com.backend.recipeManagement.dto;

import java.time.LocalDateTime;

public record RecipeListDTO(
    Long recipeID,
    String title,
    String description,
    String cookingTime,
    String status,
    String category,
    LocalDateTime createdDate) {}
