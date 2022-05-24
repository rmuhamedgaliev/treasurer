package dev.rmuhamedgaliev.api.category;

import dev.rmuhamedgaliev.core.domain.category.Category;
import dev.rmuhamedgaliev.core.domain.category.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public class CategoryManager {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryManager(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Collection<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Optional<Category> create(String name, Optional<String> description) {
        return Optional.ofNullable(
                categoryRepository.save(
                        new Category(
                                UUID.randomUUID().toString(),
                                name,
                                description
                        )
                )
        );
    }

    public Optional<Category> findById(String categoryId) {
        return categoryRepository.findCategoryById(categoryId);
    }

    public Optional<Category> update(String categoryId, String name, Optional<String> description) {
        return categoryRepository
                .findCategoryById(categoryId)
                .map(c -> {
                    c.setName(name);
                    description.ifPresent(c::setDescription);
                    return c;
                });
    }
}
