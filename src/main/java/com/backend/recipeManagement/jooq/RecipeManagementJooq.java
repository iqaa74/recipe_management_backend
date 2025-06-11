package com.backend.recipeManagement.jooq;

import static org.jooq.impl.DSL.*;

import com.backend.recipeManagement.dto.RecipeDetailsDTO;
import com.backend.recipeManagement.dto.RecipeIngredientListDTO;
import com.backend.recipeManagement.dto.RecipeListDTO;
import com.backend.recipeManagement.dto.RecipeListRequestDTO;
import com.backend.recipeManagement.paging.PaginationRequestDTO;
import com.backend.recipeManagement.util.JooqUtil;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Record5;
import org.jooq.Record7;
import org.jooq.SelectConditionStep;
import org.jooq.SelectLimitPercentAfterOffsetStep;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
@AllArgsConstructor
public class RecipeManagementJooq {
  DSLContext dsl;

  public List<RecipeListDTO> getRecipeList(
      RecipeListRequestDTO requestDTO, PaginationRequestDTO pgDTO) {

    Condition condition = noCondition();
    condition =
        JooqUtil.andCondition(
            condition, field("rm.title"), Field::containsIgnoreCase, requestDTO.title());
    condition =
        JooqUtil.andCondition(
            condition,
            field("rm.description"),
            Field::containsIgnoreCase,
            requestDTO.description());
    condition =
        JooqUtil.andCondition(
            condition,
            field("rm.cooking_time"),
            Field::containsIgnoreCase,
            requestDTO.cookingTime());

    Field<Long> recipeID = field("rm.recipe_id", Long.class).as("recipeID");
    Field<String> title = field("rm.title", String.class).as("title");
    Field<String> description = field("rm.description", String.class).as("description");
    Field<String> cookingTime = field("rm.cooking_time", String.class).as("cookingTime");
    Field<String> status = field("rm.active_flag", String.class).as("status");
    Field<String> category = field("cat.category_name", String.class).as("category");
    Field<LocalDateTime> createdDate =
        field("rm.created_date", LocalDateTime.class).as("createdDate");

    SelectLimitPercentAfterOffsetStep<
            Record7<Long, String, String, String, String, String, LocalDateTime>>
        query =
            dsl.select(recipeID, title, description, cookingTime, status, category, createdDate)
                .from(table("recipe rm"))
                .leftJoin(table("recipe_category rc"))
                .on(field("rm.recipe_id").eq(field("rc.recipe_category_id")))
                .leftJoin(table("category cat"))
                .on(field("rc.recipe_id").eq(field("cat.category_id")))
                .where(condition)
                .orderBy(
                    CoreUtilsRepositoryJooq.getOrderByField(pgDTO.sort(), pgDTO.sortDirection()))
                .offset((pgDTO.page() - 1) * pgDTO.size())
                .limit(pgDTO.size());

    log.info("Query: {}", query.getSQL());

    return query.fetchInto(RecipeListDTO.class);
  }

  public Long getRecipeListPages(RecipeListRequestDTO requestDTO, PaginationRequestDTO pgDTO) {

    Condition condition = noCondition();
    condition =
        JooqUtil.andCondition(
            condition, field("rm.title"), Field::containsIgnoreCase, requestDTO.title());
    condition =
        JooqUtil.andCondition(
            condition,
            field("rm.description"),
            Field::containsIgnoreCase,
            requestDTO.description());
    condition =
        JooqUtil.andCondition(
            condition,
            field("rm.cooking_time"),
            Field::containsIgnoreCase,
            requestDTO.cookingTime());

    SelectConditionStep<Record1<Integer>> query =
        dsl.selectCount()
            .from(table("recipe rm"))
            .leftJoin(table("recipe_category rc"))
            .on(field("rm.recipe_id").eq(field("rc.recipe_category_id")))
            .leftJoin(table("category cat"))
            .on(field("rc.recipe_id").eq(field("cat.category_id")))
            .where(condition);

    log.info("Query: {}", query.getSQL());
    return query.fetchOneInto(Long.class);
  }

  public RecipeDetailsDTO getRecipeDetails(Long recipeId) {

    Condition condition = noCondition();
    condition = JooqUtil.andCondition(condition, field("rm.recipe_id"), Field::eq, recipeId);

    Field<Long> recipeID = field("rm.recipe_id", Long.class).as("recipeID");
    Field<String> title = field("rm.title", String.class).as("title");
    Field<String> description = field("rm.description", String.class).as("description");
    Field<String> cookingTime = field("rm.cooking_time", String.class).as("cookingTime");
    Field<String> category = field("cat.category_name", String.class).as("category");

    SelectConditionStep<Record5<Long, String, String, String, String>> query =
        dsl.select(recipeID, title, description, cookingTime, category)
            .from(table("recipe rm"))
            .leftJoin(table("recipe_category rc"))
            .on(field("rm.recipe_id").eq(field("rc.recipe_category_id")))
            .leftJoin(table("category cat"))
            .on(field("rc.recipe_id").eq(field("cat.category_id")))
            .where(field("rm.recipe_id").eq(recipeId));

    log.info("Query: {}", query.getSQL());
    return query.fetchOneInto(RecipeDetailsDTO.class);
  }

  public List<RecipeIngredientListDTO> getRecipeIngredientList(Long recipeId) {

    Condition condition = noCondition();
    condition = JooqUtil.andCondition(condition, field("rm.recipe_id"), Field::eq, recipeId);

    Field<Long> ingredientId = field("ing.ingredient_id", Long.class).as("ingredientId");
    Field<String> ingredientName = field("ing.ingredient_name", String.class).as("ingredientName");
    Field<String> quantity = field("ing.quantity", String.class).as("quantity");
    Field<String> unit = field("ing.unit", String.class).as("unit");

    SelectConditionStep<Record4<Long, String, String, String>> query =
        dsl.select(ingredientId, ingredientName, quantity, unit)
            .from(table("recipe rm"))
            .leftJoin(table("recipe_ingredient ri"))
            .on(field("rm.recipe_id").eq(field("ri.recipe_id")))
            .leftJoin(table("ingredient ing"))
            .on(field("ri.ingredient_id").eq(field("ing.ingredient_id")))
            .where(condition);

    log.info("Query: {}", query.getSQL());
    return query.fetchInto(RecipeIngredientListDTO.class);
  }
}
