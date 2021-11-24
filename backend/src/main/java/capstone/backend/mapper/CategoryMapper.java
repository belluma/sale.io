package capstone.backend.mapper;

import capstone.backend.model.db.Category;
import capstone.backend.model.dto.CategoryDTO;

public class CategoryMapper {

    private CategoryMapper() {}
    public static CategoryDTO mapCategory(Category category) {
        return CategoryDTO
                .builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
public static Category mapCategory(CategoryDTO category) {
        return Category
                .builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
