package capstone.backend.services;

import capstone.backend.mapper.CategoryMapper;
import capstone.backend.model.dto.CategoryDTO;
import capstone.backend.repo.CategoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static capstone.backend.mapper.CategoryMapper.mapCategory;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepo repo;

    public List<CategoryDTO> getAllCategories() {
        return repo
                .findAll()
                .stream()
                .map(CategoryMapper::mapCategory)
                .toList();
    }

    public CategoryDTO createCategory(CategoryDTO category) {
        return mapCategory(repo.save(mapCategory(category)));
    }
}
