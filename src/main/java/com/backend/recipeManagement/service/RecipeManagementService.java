package com.backend.recipeManagement.service;

import com.backend.recipeManagement.dto.AddRecipeDTO;
import com.backend.recipeManagement.dto.DeleteRecipeDTO;
import com.backend.recipeManagement.dto.RecipeDetailsDTO;
import com.backend.recipeManagement.dto.RecipeListDTO;
import com.backend.recipeManagement.dto.RecipeListRequestDTO;
import com.backend.recipeManagement.jooq.RecipeManagementJooq;
import com.backend.recipeManagement.mapper.RecipeManagementListMapper;
import com.backend.recipeManagement.model.Recipe;
import com.backend.recipeManagement.paging.PaginationRequestDTO;
import com.backend.recipeManagement.paging.PaginationResponseDTO;
import com.backend.recipeManagement.repository.RecipeRepository;
import com.backend.recipeManagement.util.PaginationUtil;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class RecipeManagementService implements IRecipeManagementService {
  private final RecipeManagementJooq recipeManagementJooq;
  private final RecipeRepository recipeRepository;

  @Override
  public List<RecipeListDTO> getRecipeList(
      RecipeListRequestDTO requestDTO, PaginationRequestDTO paginationRequestDTO) {

    PaginationRequestDTO pg =
        PaginationUtil.pageSorting(paginationRequestDTO, new RecipeManagementListMapper(), false);
    return recipeManagementJooq.getRecipeList(requestDTO, pg);
  }

  @Override
  public PaginationResponseDTO getRecipeListPages(
      RecipeListRequestDTO requestDTO, PaginationRequestDTO paginationRequestDTO) {
    Long total = recipeManagementJooq.getRecipeListPages(requestDTO, paginationRequestDTO);
    return PaginationUtil.pagination(paginationRequestDTO.size(), total);
  }

  @Override
  public RecipeDetailsDTO getRecipeDetails(Long recipeId) {
    RecipeDetailsDTO recipeDetailsDTO = recipeManagementJooq.getRecipeDetails(recipeId);
    recipeDetailsDTO.setIngredients(recipeManagementJooq.getRecipeIngredientList(recipeId));

    return recipeDetailsDTO;
  }

  @Override
  @Transactional
  public void save(AddRecipeDTO dto) {
    Recipe recipe = new Recipe();
    if (dto.recipeId() != null) {
      recipe = recipeRepository.getReferenceById(dto.recipeId());
      recipe.setUpdatedBy(1L);
      recipe.setUpdatedDate(LocalDateTime.now());
    } else {
      recipe.setCreatedBy(1L);
      recipe.setCreatedDate(LocalDateTime.now());
    }
    recipe.setTitle(dto.title());
    recipe.setDescription(dto.description());
    recipe.setCookingTime(dto.cookingTime());
    recipe.setActiveFlag("A");
    recipeRepository.save(recipe);
  }

  @Override
  @Transactional
  public void delete(Long recipeId, DeleteRecipeDTO dto) {

    Recipe recipe = recipeRepository.getReferenceById(recipeId);
    recipe.setUpdatedBy(1L);
    recipe.setUpdatedDate(LocalDateTime.now());
    recipe.setActiveFlag("I");
    if (dto.reason() == null) {
      if (dto.reason() == null || dto.reason().isBlank()) {
        throw new IllegalArgumentException("Please enter reason");
      }
      recipe.setReason(dto.reason());
    }

    recipeRepository.save(recipe);
  }
}
