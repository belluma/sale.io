package capstone.backend.controller;

import capstone.backend.model.dto.CategoryDTO;
import capstone.backend.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/categories")
public class CategoryController {

    private final CategoryService service;

    @GetMapping
    public List<CategoryDTO> getAllCategories() {
        return service.getAllCategories();
    }

    @PostMapping
    public CategoryDTO createCategory(CategoryDTO category) {
        return service.createCategory(category);
    }

}
