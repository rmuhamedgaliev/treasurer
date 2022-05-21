package dev.rmuhamedgaliev.domain.category;

import java.util.Collection;

public interface CategoryRepository {

    Category add(Category category);

    Collection<Category> findAll();
}
