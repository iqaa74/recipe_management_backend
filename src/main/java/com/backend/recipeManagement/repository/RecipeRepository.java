package com.backend.recipeManagement.repository;

import com.backend.recipeManagement.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {}
