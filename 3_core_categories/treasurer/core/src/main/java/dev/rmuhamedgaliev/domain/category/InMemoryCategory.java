package dev.rmuhamedgaliev.domain.category;

import java.util.Collection;
import java.util.LinkedList;

public class InMemoryCategory implements CategoryRepository {

    private final Collection<Category> categories;

    public InMemoryCategory(Collection<Category> categories) {
        this.categories = categories;
    }
    public InMemoryCategory() {
        this.categories = new LinkedList<>();
    }

    @Override
    public Category add(Category category) {
        if (!isUniqueName(category, categories)) {
            throw new IllegalArgumentException("Category with name " + category.name() + " already exists");
        }
        categories.add(category);
        return category;
    }

    private boolean isUniqueName(Category category, Collection<Category> categories) {
        var result = true;
        var iterator = categories.iterator();
        while (iterator.hasNext()) {
            var rootCategory= iterator.next();
            boolean isContainsCategoryInRoot = rootCategory.name().equals(category.name());
            if (!isContainsCategoryInRoot) {
                isUniqueName(category, rootCategory.subcategories());
            } else {
                result = false;
                break;
            }
            if (rootCategory.subcategories().isEmpty()) {
                break;
            }
        }
        return result;
    }

    @Override
    public Collection<Category> findAll() {
        return categories;
    }
}
