package dev.rmuhamedgaliev.domain.category;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

class CategoryRepositoryTest {
    private LinkedList<Category> categories = new LinkedList<>();

    private CategoryRepository categoryRepository = new InMemoryCategory(categories);

    @BeforeEach
    public void prepareTest() {
        categories.clear();
    }

    @Test
    @DisplayName("Add category with duplicate name")
    public void testAddCategoryWithDuplicateName() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            var rootCategory = new Category(
                    "4514a8f5-c7e0-4086-a837-379a212cb32d",
                    "parents",
                    Collections.emptyList(),
                    Optional.empty()
            );
            categoryRepository.add(rootCategory);
            categoryRepository.add(rootCategory);

            var categories = categoryRepository.findAll();
            Assertions.assertNotNull(categories);
            Assertions.assertTrue(categories.size() > 0);
        });
    }

    @Test
    public void testAddCategoryWithNested() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            var rootCategory = new Category(
                    "4514a8f5-c7e0-4086-a837-379a212cb32d",
                    "parents1",
                    List.of(
                            new Category(
                                    "33777f6f-762f-4bee-b64d-f674c08fbd85",
                                    "parents",
                                    Collections.emptyList(),
                                    Optional.empty()
                            )
                    ),
                    Optional.empty()
            );
            categoryRepository.add(rootCategory);

            categoryRepository.add(
                    new Category(
                            "0091b13d-9b37-488c-bec9-67d7de42235d",
                            "parents",
                            Collections.emptyList(),
                            Optional.empty()
                    )
            );

            var categories = categoryRepository.findAll();
            Assertions.assertNotNull(categories);
            Assertions.assertTrue(categories.size() > 0);
        });
    }

}
