package com.backend.recipeManagement.service;

import com.backend.recipeManagement.dto.AddRecipeDTO;
import com.backend.recipeManagement.dto.DeleteRecipeDTO;
import com.backend.recipeManagement.dto.RecipeDetailsDTO;
import com.backend.recipeManagement.dto.RecipeListDTO;
import com.backend.recipeManagement.dto.RecipeListRequestDTO;
import com.backend.recipeManagement.paging.PaginationRequestDTO;
import com.backend.recipeManagement.paging.PaginationResponseDTO;
import java.util.List;

public interface IRecipeManagementService {
  List<RecipeListDTO> getRecipeList(
      RecipeListRequestDTO requestDTO, PaginationRequestDTO paginationRequestDTO);

  PaginationResponseDTO getRecipeListPages(
      RecipeListRequestDTO requestDTO, PaginationRequestDTO paginationRequestDTO);

  RecipeDetailsDTO getRecipeDetails(Long recipeId);

  void save(AddRecipeDTO addRecipeDTO);

  void delete(Long recipeId, DeleteRecipeDTO dto);
}
