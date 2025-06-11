package com.backend.recipeManagement.mapper;

public class RecipeManagementListMapper extends ColumnMapper {
  public RecipeManagementListMapper() {
    COLUMN_MAP.put("title", "title");
    COLUMN_MAP.put("description", "description");
    COLUMN_MAP.put("cookingTime", "cookingTime");
    COLUMN_MAP.put("status", "status");
    COLUMN_MAP.put("createdDate", "createdDate");
  }
}
