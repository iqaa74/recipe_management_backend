package com.backend.recipeManagement.paging;

public record PaginationResponseDTO(Long totalPages, Long total, Long size) {}