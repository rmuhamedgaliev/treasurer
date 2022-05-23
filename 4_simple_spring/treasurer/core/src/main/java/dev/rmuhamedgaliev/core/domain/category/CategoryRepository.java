package dev.rmuhamedgaliev.core.domain.category;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;

public interface CategoryRepository {

    Category save(Category category);

    Category save(String parentId, Category category);

    Collection<Category> findAll();

    void delete(String categoryId);

    Optional<Category> findCategoryByName(String name);

    Optional<Category> findCategoryById(String id);

    Optional<Category> findCategoryBy(Function<Category, Boolean> condition);
}
