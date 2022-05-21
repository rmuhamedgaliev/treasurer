package dev.rmuhamedgaliev.domain.category;

import java.util.Collection;
import java.util.Optional;

public record Category(String id, String name, Collection<Category> subcategories, Optional<String> description) {
}
