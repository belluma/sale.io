package capstone.backend.services;

import capstone.backend.model.db.Product;
import capstone.backend.model.dto.ProductDTO;
import capstone.backend.model.exception.ProductIdAlreadyTakenException;
import capstone.backend.model.exception.ProductNotFoundException;
import capstone.backend.repo.ProductRepo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static capstone.backend.mapper.ProductMapper.mapProductWithDetails;
import static capstone.backend.utils.ProductTestUtils.sampleProductWithId;
import static capstone.backend.utils.ProductTestUtils.sampleProductDTOWithDetailsWithId;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;

@RunWith(MockitoJUnitRunner.class)
class ProductServiceTest {

    private final ProductRepo repo = mock(ProductRepo.class);
    private final ProductService service = new ProductService(repo);

    @Test
    void getAllProductsWithDetails() {
        when(repo.findAll()).thenReturn(List.of(sampleProductWithId()));
        List<ProductDTO> expected = List.of(sampleProductDTOWithDetailsWithId());
        List<ProductDTO> actual = service.getAllProductsWithDetails();
        assertIterableEquals(actual, expected);
        verify(repo).findAll();
    }

    @Test
    void getProductDetails() {
        when(repo.findById(123L)).thenReturn(Optional.of(sampleProductWithId()));
        ProductDTO expected = sampleProductDTOWithDetailsWithId();
        ProductDTO actual = service.getProductDetails(123L);
        assertThat(actual, is(expected));
        verify(repo).findById(123L);
    }

    @Test
    void getProductDetailsThrowsWhenNoProductFound() {
        when(repo.findById(123L)).thenReturn(Optional.empty());
        Exception ex = assertThrows(ProductNotFoundException.class, () -> service.getProductDetails(123L));
        assertThat(ex.getMessage(), is("Couldn't find a product with the id 123"));
        verify(repo).findById(123L);
    }

    @Test
    void createProduct() {
        Product productToSave = sampleProductWithId();
        when(repo.save(productToSave)).thenReturn(productToSave);
        ProductDTO expected = sampleProductDTOWithDetailsWithId();
        ProductDTO actual = service.createProduct(sampleProductDTOWithDetailsWithId());
        assertThat(actual, is(expected));
        verify(repo).save(productToSave);
    }

    @Test
    void createProductThrowsWhenProductIdAlreadyTaken() {
        Product product = sampleProductWithId();
        when(repo.findById(123L)).thenReturn(Optional.of(product));
        Exception ex = assertThrows(ProductIdAlreadyTakenException.class, () -> service.createProduct(sampleProductDTOWithDetailsWithId()));
        assertThat(ex.getMessage(), is(String.format("Product %s already has the id %d", product.getName(), product.getId())));
        verify(repo).findById(123L);
    }

    @Test
    void editProduct() {
        Product product = sampleProductWithId();
        Product editedProduct = sampleProductWithId();
        editedProduct.setRetailPrice(99.99F);
        when(repo.findById(123L)).thenReturn(Optional.of(product));
        when(repo.save(editedProduct)).thenReturn(editedProduct);
        ProductDTO expected = sampleProductDTOWithDetailsWithId();
        expected.setRetailPrice(99.99F);
        assertThat(expected, is(service.editProduct(mapProductWithDetails(editedProduct))));
        verify(repo).findById(123L);
        verify(repo).save(editedProduct);
    }

    @Test
    void editProductThrowsWhenProductNotFound(){
        ProductDTO nonExistantProduct = sampleProductDTOWithDetailsWithId();
        when(repo.findById(123L)).thenReturn(Optional.empty());
        Exception ex = assertThrows(ProductNotFoundException.class, () -> service.editProduct(nonExistantProduct));
        assertThat(ex.getMessage(), is((String.format("Couldn't find a product with the id %d", nonExistantProduct.getId()))));
        verify(repo).findById(123L);
    }
}
