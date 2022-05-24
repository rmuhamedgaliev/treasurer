package dev.rmuhamedgaliev.api.category;

import dev.rmuhamedgaliev.core.domain.category.CategoryRepository;
import dev.rmuhamedgaliev.core.domain.category.InMemoryCategoryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoryContextConfiguration {

    @Bean
    public CategoryRepository categoryRepository() {
        return new InMemoryCategoryRepository();
    }

    @Bean
    public CategoryManager categoryManager(CategoryRepository categoryRepository) {
        return new CategoryManager(categoryRepository);
    }
}
