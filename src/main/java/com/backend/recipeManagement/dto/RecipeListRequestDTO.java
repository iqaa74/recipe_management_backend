package com.backend.recipeManagement.dto;

public record RecipeListRequestDTO(
    String title, String description, String cookingTime, String status, String category) {}
