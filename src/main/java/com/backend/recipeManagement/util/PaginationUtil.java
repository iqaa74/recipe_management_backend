package com.backend.recipeManagement.util;

import com.backend.recipeManagement.mapper.ColumnMapper;
import com.backend.recipeManagement.paging.PaginationRequestDTO;
import com.backend.recipeManagement.paging.PaginationResponseDTO;
import java.util.Optional;

public class PaginationUtil {
  public static PaginationRequestDTO pageSorting(
      PaginationRequestDTO pageRq, ColumnMapper mapper, Boolean multiDefaultSort) {

    String sort = mapper.getColumnName(pageRq.sort());

    if (multiDefaultSort) {
      sort = mapper.getMultipleColumnName(pageRq.sort());
    }

    Long page = pageRq.page() != null ? pageRq.page() : 1L;
    Long size = pageRq.size() != null ? pageRq.size() : 10L;

    String sortDirection = "asc";
    if (pageRq.sortDirection() != null && pageRq.sortDirection().contains("desc")) {
      sortDirection = "desc";
    }
    return new PaginationRequestDTO(sort, sortDirection, page, size);
  }

  public static PaginationRequestDTO pageSorting(
      PaginationRequestDTO pageRq,
      ColumnMapper mapper,
      String defaultSortDirection,
      Boolean multiDefaultSort) {

    String sort = mapper.getColumnName(pageRq.sort());

    if (multiDefaultSort) {
      sort = mapper.getMultipleColumnName(pageRq.sort());
    }

    Long page = pageRq.page() != null ? pageRq.page() : 1L;
    Long size = pageRq.size() != null ? pageRq.size() : 10L;

    String sortDirection =
        Optional.ofNullable(pageRq.sortDirection())
                .orElse(Optional.ofNullable(defaultSortDirection).orElse("asc"))
                .contains("desc")
            ? "desc"
            : "asc";

    return new PaginationRequestDTO(sort, sortDirection, page, size);
  }

  /** Retrieves pagination without sorting, only page and size */
  public static PaginationRequestDTO pageSorting(PaginationRequestDTO pageRq) {

    Long page = pageRq.page() != null ? pageRq.page() : 1L;
    Long size = pageRq.size() != null ? pageRq.size() : 10L;

    return new PaginationRequestDTO(null, null, page, size);
  }

  public static PaginationResponseDTO pagination(Long reqSize, Long total) {
    Long size = reqSize != null ? reqSize : 10L;
    Long totalPages = total / size;
    if (total % size > 0) {
      totalPages++;
    }
    return new PaginationResponseDTO(totalPages, total, size);
  }
}
