package capstone.backend.services;

import capstone.backend.model.db.Product;
import capstone.backend.model.dto.ProductDTO;
import capstone.backend.exception.exception.ProductIdAlreadyTakenException;
import capstone.backend.exception.exception.ProductNotFoundException;
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
        //GIVEN
        when(repo.findAll()).thenReturn(List.of(sampleProductWithId()));
        List<ProductDTO> expected = List.of(sampleProductDTOWithDetailsWithId());
        //WHEN
        List<ProductDTO> actual = service.getAllProductsWithDetails();
        //THEN
        assertIterableEquals(actual, expected);
        verify(repo).findAll();
    }

    @Test
    void getProductDetails() {
        //GIVEN
        when(repo.findById(123L)).thenReturn(Optional.of(sampleProductWithId()));
        ProductDTO expected = sampleProductDTOWithDetailsWithId();
        //WHEN
        ProductDTO actual = service.getProductDetails(123L);
        //THEN
        assertThat(actual, is(expected));
        verify(repo).findById(123L);
    }

    @Test
    void getProductDetailsThrowsWhenNoProductFound() {
        //GIVEN
        when(repo.findById(123L)).thenReturn(Optional.empty());
        Exception ex = assertThrows(ProductNotFoundException.class, () -> service.getProductDetails(123L));
        assertThat(ex.getMessage(), is("Couldn't find a product with the id 123"));
        verify(repo).findById(123L);
    }

    @Test
    void createProduct() {
        //GIVEN
        Product productToSave = sampleProductWithId();
        when(repo.save(productToSave)).thenReturn(productToSave);
        ProductDTO expected = sampleProductDTOWithDetailsWithId();
        //WHEN
        ProductDTO actual = service.createProduct(sampleProductDTOWithDetailsWithId());
        //THEN
        assertThat(actual, is(expected));
        verify(repo).save(productToSave);
    }

    @Test
    void createProductThrowsWhenProductIdAlreadyTaken() {
        //GIVEN
        Product product = sampleProductWithId();
        when(repo.findById(123L)).thenReturn(Optional.of(product));
        Exception ex = assertThrows(ProductIdAlreadyTakenException.class, () -> service.createProduct(sampleProductDTOWithDetailsWithId()));
        //THEN
        assertThat(ex.getMessage(), is(String.format("Product %s already has the id %d", product.getName(), product.getId())));
        verify(repo).findById(123L);
    }

    @Test
    void editProduct() {
        //GIVEN
        Product product = sampleProductWithId();
        Product editedProduct = sampleProductWithId();
        editedProduct.setRetailPrice(99.99F);
        when(repo.findById(123L)).thenReturn(Optional.of(product));
        when(repo.save(editedProduct)).thenReturn(editedProduct);
        //WHEN
        ProductDTO expected = sampleProductDTOWithDetailsWithId();
        expected.setRetailPrice(99.99F);
        //THEN
        assertThat(expected, is(service.editProduct(mapProductWithDetails(editedProduct))));
        verify(repo).findById(123L);
        verify(repo).save(editedProduct);
    }

    @Test
    void editProductThrowsWhenProductNotFound() {
        //GIVEN
        ProductDTO nonExistantProduct = sampleProductDTOWithDetailsWithId();
        when(repo.findById(123L)).thenReturn(Optional.empty());
        //WHEN
        Exception ex = assertThrows(ProductNotFoundException.class, () -> service.editProduct(nonExistantProduct));
        //THEN
        assertThat(ex.getMessage(), is((String.format("Couldn't find a product with the id %d", nonExistantProduct.getId()))));
        verify(repo).findById(123L);
    }
}
