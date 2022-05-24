package dev.rmuhamedgaliev.api.web;

import dev.rmuhamedgaliev.api.category.CategoryManager;
import dev.rmuhamedgaliev.api.category.exception.CategoryException;
import dev.rmuhamedgaliev.api.web.dto.CategoryDto;
import dev.rmuhamedgaliev.api.web.dto.CreateCategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/category")
public class CategoryController {

    private final CategoryManager manager;

    @Autowired
    public CategoryController(CategoryManager categoryManager) {
        this.manager = categoryManager;
    }

    @GetMapping
    public List<CategoryDto> findAll() {
        return manager.findAll().stream().map(CategoryDto::new).collect(Collectors.toList());
    }

    @PostMapping
    public CategoryDto create(@RequestBody CreateCategoryDTO dto) {
        return manager.create(dto.name(), dto.description())
                      .map(CategoryDto::new)
                      .orElseThrow(() -> new CategoryException("Error on create category"));
    }

    @GetMapping(path = "/{categoryId}")
    public CategoryDto findBydId(@PathVariable("categoryId") String categoryId) {
        return manager.findById(categoryId)
                      .map(CategoryDto::new)
                      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
    }

    @PutMapping(path = "/{categoryId}")
    public CategoryDto updateCategory(@PathVariable("categoryId") String categoryId, @RequestBody CreateCategoryDTO dto) {
        return manager.update(categoryId, dto.name(), dto.description())
                      .map(CategoryDto::new)
                      .orElseThrow(() -> new CategoryException("Error on update category"));
    }
}
