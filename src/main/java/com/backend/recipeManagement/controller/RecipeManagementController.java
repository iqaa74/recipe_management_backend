package com.backend.recipeManagement.controller;

import com.backend.recipeManagement.dto.AddRecipeDTO;
import com.backend.recipeManagement.dto.DeleteRecipeDTO;
import com.backend.recipeManagement.dto.RecipeDetailsDTO;
import com.backend.recipeManagement.dto.RecipeListDTO;
import com.backend.recipeManagement.dto.RecipeListRequestDTO;
import com.backend.recipeManagement.paging.PaginationRequestDTO;
import com.backend.recipeManagement.paging.PaginationResponseDTO;
import com.backend.recipeManagement.service.RecipeManagementService;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/v1/recipe")
@AllArgsConstructor
public class RecipeManagementController {
  private final RecipeManagementService recipeManagementService;

  @GetMapping("/list")
  public List<RecipeListDTO> getRecipeList(
      @RequestParam(required = false) String title,
      @RequestParam(required = false) String description,
      @RequestParam(required = false) String cookingTime,
      @RequestParam(required = false) String status,
      @RequestParam(required = false) String category,
      @RequestParam(required = false) String sort,
      @RequestParam(required = false) String sortDirection,
      @RequestParam(required = false) Long page,
      @RequestParam(required = false) Long size) {
    RecipeListRequestDTO reqDTO =
        new RecipeListRequestDTO(title, description, cookingTime, status, category);
    PaginationRequestDTO pg = new PaginationRequestDTO(sort, sortDirection, page, size);
    return recipeManagementService.getRecipeList(reqDTO, pg);
  }

  @GetMapping("/list/page")
  public PaginationResponseDTO getBrandListPages(
      @RequestParam(required = false) String title,
      @RequestParam(required = false) String description,
      @RequestParam(required = false) String cookingTime,
      @RequestParam(required = false) String status,
      @RequestParam(required = false) String category,
      @RequestParam(required = false) Long size) {
    PaginationRequestDTO paginationRequestDTO = new PaginationRequestDTO(null, null, null, size);
    RecipeListRequestDTO requestDTO =
        new RecipeListRequestDTO(title, description, cookingTime, status, category);
    return recipeManagementService.getRecipeListPages(requestDTO, paginationRequestDTO);
  }

  @GetMapping("/{recipeId}")
  public RecipeDetailsDTO getRecipeDetails(@PathVariable("recipeId") Long recipeId) {

    RecipeDetailsDTO recipeDetailsDTO = recipeManagementService.getRecipeDetails(recipeId);

    return recipeDetailsDTO;
  }

  @PostMapping("/save")
  public void save(@RequestBody AddRecipeDTO addRecipeDTO) {
    recipeManagementService.save(addRecipeDTO);
  }

  @PostMapping("/delete/{recipeId}")
  public void delete(@PathVariable("recipeId") Long recipeId, @RequestBody DeleteRecipeDTO dto) {
    recipeManagementService.delete(recipeId, dto);
  }
}
