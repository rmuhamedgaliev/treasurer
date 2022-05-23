package dev.rmuhamedgaliev.domain.category;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;

public class Category {
    private final String id;

    private final String name;

    private final Collection<Category> subcategories;

    private Optional<Category> parent = Optional.empty();

    private Optional<String> description;

    public Category(String id, String name) {
        this.id = id;
        this.name = name;
        this.subcategories = new LinkedList<>();
        this.description = Optional.empty();
    }

    public Category(String id, String name, Optional<String> description) {
        this.id = id;
        this.name = name;
        this.subcategories = new LinkedList<>();
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Collection<Category> getSubcategories() {
        return subcategories;
    }

    public Optional<String> getDescription() {
        return description;
    }

    public Optional<Category> getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = Optional.of(parent);
    }

    public void setDescription(String description) {
        this.description = Optional.of(description);
    }
}
