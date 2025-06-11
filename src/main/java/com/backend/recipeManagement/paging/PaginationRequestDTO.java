package com.backend.recipeManagement.paging;

public record PaginationRequestDTO(String sort, String sortDirection, Long page, Long size) {}
