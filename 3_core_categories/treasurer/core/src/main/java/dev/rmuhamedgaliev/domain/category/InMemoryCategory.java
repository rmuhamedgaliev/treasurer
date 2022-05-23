package dev.rmuhamedgaliev.domain.category;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class InMemoryCategory implements CategoryRepository {

    private final Map<String, Category> categories;

    public InMemoryCategory(Map<String, Category> categories) {
        this.categories = categories;
    }

    public InMemoryCategory() {
        this.categories = new HashMap<>();
    }

    @Override
    public Category save(Category category) {
        checkAlreadyExistsCategory(category);
        categories.put(category.getId(), category);
        if (!category.getSubcategories().isEmpty()) {
            category.getSubcategories().forEach(c -> save(category.getId(), c));
        }
        return category;
    }

    @Override
    public Category save(String parentId, Category category) {
        if (!categories.containsKey(parentId)) {
            throw new IllegalArgumentException("Parent category not exists");
        }
        checkAlreadyExistsCategory(category);
        Category parent = categories.get(parentId);
        parent.getSubcategories().add(category);
        category.setParent(parent);
        categories.put(category.getId(), category);
        if (!category.getSubcategories().isEmpty()) {
            category.getSubcategories().forEach(c -> save(category.getId(), c));
        }
        return category;
    }

    private void checkAlreadyExistsCategory(Category category) {
        if (findCategory(
                (root) -> root.getName().equals(category.getName()),
                categories.values(),
                (parent) -> {
                }
        ).isPresent()
                || categories.containsKey(category.getId())) {
            throw new IllegalArgumentException("Category already exists");
        }
    }

    private Optional<Category> findCategory(Function<Category, Boolean> findFunction,
                                            Collection<Category> categories, Consumer<Optional<Category>> parentCategory) {
        Optional<Category> resultCategory = Optional.empty();
        for (Category rootCategory : categories) {
            if (!rootCategory.getSubcategories().isEmpty()) {
                for (Category subCategory : rootCategory.getSubcategories()) {
                    return findCategory(findFunction, subCategory.getSubcategories(), parentCategory);
                }
            }
            if (!findFunction.apply(rootCategory)) {
                resultCategory = findCategory(findFunction, rootCategory.getSubcategories(), parentCategory);
            } else {
                resultCategory = Optional.of(rootCategory);
                parentCategory.accept(rootCategory.getParent());
                break;
            }
            if (rootCategory.getSubcategories().isEmpty()) {
                break;
            }
        }
        return resultCategory;
    }

    @Override
    public Collection<Category> findAll() {
        return categories.values();
    }

    @Override
    public void delete(String categoryId) {
        checkExistsCategoryById(categoryId);
        findCategory(
                (root) -> root.getId().equals(categoryId),
                categories.values(),
                (parent) -> parent.ifPresent(c -> c.getSubcategories().remove(categories.get(categoryId)))
        );
        categories.remove(categoryId);
    }

    private void checkExistsCategoryById(String categoryId) {
        if (!categories.containsKey(categoryId)) {
            throw new IllegalArgumentException("Category not exists");
        }
    }

    @Override
    public Optional<Category> findCategoryByName(String name) {
        return findCategory(
                (category -> category.getName().equals(name)),
                categories.values(),
                (parent) -> {
                }
        );
    }

    @Override
    public Optional<Category> findCategoryById(String id) {
        return findCategory(
                (category -> category.getId().equals(id)),
                categories.values(),
                (parent) -> {
                }
        );
    }

    @Override
    public Optional<Category> findCategoryBy(Function<Category, Boolean> condition) {
        return findCategory(
                condition,
                categories.values(),
                (parent) -> {
                }
        );
    }
}
