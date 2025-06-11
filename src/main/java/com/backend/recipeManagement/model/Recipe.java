package com.backend.recipeManagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "recipe")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {
  @Id
  @Column(name = "recipe_id", unique = true, nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long recipeId;

  @Column(name = "title")
  private String title;

  @Column(name = "description")
  private String description;

  @Column(name = "cooking_time")
  private String cookingTime;

  @Column(name = "active_flag")
  private String activeFlag;

  @Column(name = "reason")
  private String reason;

  @Column(name = "created_by")
  private Long createdBy;

  @Column(name = "created_date")
  private LocalDateTime createdDate;

  @Column(name = "updated_by")
  private Long updatedBy;

  @Column(name = "updated_date")
  private LocalDateTime updatedDate;
}
