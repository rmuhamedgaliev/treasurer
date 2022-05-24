package dev.rmuhamedgaliev.api.web.dto;

import org.springframework.lang.NonNull;

import java.util.Optional;

public record CreateCategoryDTO(
        @NonNull String name,
        Optional<String> description
) {
}
