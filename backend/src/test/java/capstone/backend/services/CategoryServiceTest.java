package capstone.backend.services;

import capstone.backend.model.db.Category;
import capstone.backend.model.dto.CategoryDTO;
import capstone.backend.repo.CategoryRepo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class CategoryServiceTest {

    private final CategoryRepo repo = mock(CategoryRepo.class);
    private final CategoryService service = new CategoryService(repo);

    @Test
    void getAllCategories() {
        //GIVEN
        when(repo.findAll()).thenReturn(List.of(new Category(1L, "test category")));
        List<CategoryDTO> expected = List.of(new CategoryDTO(1L, "test category"));
        //WHEN
        List<CategoryDTO> actual = service.getAllCategories();
        //THEN
        assertThat(actual, is(expected));
        verify(repo).findAll();
    }

    @Test
    void createCategory() {
        //GIVEN
        Category category = new Category(1L, "test category");
        when(repo.save(category)).thenReturn(category);
        CategoryDTO expected = new CategoryDTO(1L, "test category");
        //WHEN
        CategoryDTO actual = service.createCategory(expected);
        //THEN
        assertThat(actual, is(expected));
        verify(repo).save(category);
    }
}
