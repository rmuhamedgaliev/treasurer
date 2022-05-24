package dev.rmuhamedgaliev.api.web.dto;

import dev.rmuhamedgaliev.core.domain.category.Category;

import java.util.Collection;
import java.util.Optional;

public record CategoryDto(String id, String name, Collection<Category> subcategories, Optional<String> description) {

    public CategoryDto(Category category) {
        this(category.getId(), category.getName(), category.getSubcategories(), category.getDescription());
    }
}
