package dev.rmuhamedgaliev.domain.category;

import dev.rmuhamedgaliev.core.domain.category.Category;
import dev.rmuhamedgaliev.core.domain.category.CategoryRepository;
import dev.rmuhamedgaliev.core.domain.category.InMemoryCategory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class CategoryRepositoryTest {
    private Map<String, Category> categories = new HashMap<>();

    private final CategoryRepository categoryRepository = new InMemoryCategory(categories);

    @BeforeEach
    public void prepareTest() {
        categories = new HashMap<>();
    }

    @Test
    @DisplayName("Add category with duplicate name")
    public void testAddCategoryWithDuplicateName() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            var rootCategory = new Category(
                    "4514a8f5-c7e0-4086-a837-379a212cb32d",
                    "parents"
            );
            categoryRepository.save(rootCategory);
            categoryRepository.save(rootCategory);

            var categories = categoryRepository.findAll();
            Assertions.assertNotNull(categories);
            Assertions.assertTrue(categories.size() > 0);
        });
    }

    @Test
    @DisplayName("Check protection add duplicate name in nested category")
    public void testAddCategoryWithNested() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            var rootCategory = new Category(
                    "4514a8f5-c7e0-4086-a837-379a212cb32d",
                    "parents1"
            );
            categoryRepository.save(rootCategory);
            categoryRepository.save(rootCategory.getId(),
                    new Category(
                            "33777f6f-762f-4bee-b64d-f674c08fbd85",
                            "parents"
                    )
            );

            var category = new Category(
                    "0091b13d-9b37-488c-bec9-67d7de42235d",
                    "parents2"
            );
            categoryRepository.save(category);
            categoryRepository.save(category.getId(),
                    new Category(
                            "33777f6f-762f-4bee-b64d-f674c08fbd85",
                            "parents"
                    )
            );
            var categories = categoryRepository.findAll();
            Assertions.assertNotNull(categories);
            Assertions.assertTrue(categories.size() > 0);
        });
    }

    @Test
    @DisplayName("Check delete category from category")
    public void deleteNestedCategoryTest() {
        var rootCategory = new Category(
                "4514a8f5-c7e0-4086-a837-379a212cb32d",
                "parents1"
        );
        categoryRepository.save(rootCategory);

        var child = new Category(
                "33777f6f-762f-4bee-b64d-f674c08fbd85",
                "parents"
        );
        categoryRepository.save(rootCategory.getId(), child);

        Assertions.assertEquals(2, categoryRepository.findAll().size());
        categoryRepository.delete(child.getId());
        Assertions.assertEquals(1, categoryRepository.findAll().size());
    }

    @Test
    @DisplayName("Check find by name in repository")
    public void findByNameTest() {
        var rootCategory = new Category(
                "4514a8f5-c7e0-4086-a837-379a212cb32d",
                "parents1"
        );
        categoryRepository.save(rootCategory);

        var child = new Category(
                "33777f6f-762f-4bee-b64d-f674c08fbd85",
                "parents"
        );
        categoryRepository.save(rootCategory.getId(), child);

        Assertions.assertTrue(categoryRepository.findCategoryByName("parents").isPresent());
    }

    @Test
    @DisplayName("Check find by id in repository")
    public void findByIdTest() {
        var rootCategory = new Category(
                "4514a8f5-c7e0-4086-a837-379a212cb32d",
                "parents1"
        );
        categoryRepository.save(rootCategory);

        var child = new Category(
                "33777f6f-762f-4bee-b64d-f674c08fbd85",
                "parents"
        );
        categoryRepository.save(rootCategory.getId(), child);

        Assertions.assertTrue(categoryRepository.findCategoryById(child.getId()).isPresent());
    }

    @Test
    @DisplayName("Check find by custom condition in repository")
    public void findByTest() {
        var rootCategory = new Category(
                "4514a8f5-c7e0-4086-a837-379a212cb32d",
                "parents1"
        );
        categoryRepository.save(rootCategory);

        var child = new Category(
                "33777f6f-762f-4bee-b64d-f674c08fbd85",
                "parents"
        );
        categoryRepository.save(rootCategory.getId(), child);

        Assertions.assertTrue(categoryRepository.findCategoryBy(
                (category -> category.getId().equals(child.getId()))
        ).isPresent());
    }

}
