package capstone.backend.services;

import capstone.backend.model.db.Product;
import capstone.backend.model.db.order.OrderItem;
import capstone.backend.model.dto.ProductDTO;
import capstone.backend.exception.model.EntityWithThisIdAlreadyExistException;
import capstone.backend.model.dto.order.OrderItemDTO;
import capstone.backend.repo.ProductRepo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static capstone.backend.mapper.ProductMapper.mapProductWithDetails;
import static capstone.backend.utils.OrderItemTestUtils.sampleOrderItem;
import static capstone.backend.utils.OrderItemTestUtils.sampleOrderItemDTO;
import static capstone.backend.utils.ProductTestUtils.*;
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
        //WHEN - THEN
        Exception ex = assertThrows(EntityNotFoundException.class, () -> service.getProductDetails(123L));
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
        ProductDTO nonExistentProduct = mapProductWithDetails(product);
        when(repo.existsById(123L)).thenReturn(true);
        Exception ex = assertThrows(EntityWithThisIdAlreadyExistException.class, () -> service.createProduct(nonExistentProduct));
        //THEN
        assertThat(ex.getMessage(), is(String.format("Product %s already has the id %d", product.getName(), product.getId())));
        verify(repo).existsById(123L);
    }

    @Test
    void editProduct() {
        //GIVEN
        Product editedProduct = sampleProductWithId();
        editedProduct.setRetailPrice(99.99F);
        when(repo.existsById(123L)).thenReturn(true);
        when(repo.save(editedProduct)).thenReturn(editedProduct);
        //WHEN
        ProductDTO expected = sampleProductDTOWithDetailsWithId();
        expected.setRetailPrice(99.99F);
        //THEN
        assertThat(expected, is(service.editProduct(mapProductWithDetails(editedProduct))));
        verify(repo).existsById(123L);
        verify(repo).save(editedProduct);
    }

    @Test
    void editProductThrowsWhenIdNotGiven() {
        //GIVEN
        ProductDTO productWithoutId = sampleProductDTOWithDetails();
        //WHEN
        Exception ex = assertThrows(EntityNotFoundException.class, () -> service.editProduct(productWithoutId));
        assertThat(ex.getMessage(), is((String.format("Couldn't find a product with the id %d", productWithoutId.getId()))));
        //THEN
    }

    @Test
    void editProductThrowsWhenProductNotFound() {
        //GIVEN
        ProductDTO nonExistentProduct = sampleProductDTOWithDetailsWithId();
        when(repo.existsById(123L)).thenReturn(false);
        //WHEN
        Exception ex = assertThrows(EntityNotFoundException.class, () -> service.editProduct(nonExistentProduct));
        //THEN
        assertThat(ex.getMessage(), is((String.format("Couldn't find a product with the id %d", nonExistentProduct.getId()))));
        verify(repo).existsById(123L);
    }

    @Test
    void adjustAmountInStock(){
        //GIVEN
        List<OrderItemDTO> receivedOrder = List.of(sampleOrderItemDTO());
        Long productId = receivedOrder.get(0).getProduct().getId();
        when(repo.getById(productId)).thenReturn(sampleProductWithId());
        //WHEN
        service.adjustAmountInStock(receivedOrder);
        //THEN
        verify(repo).getById(productId);
        verify(repo).save(sampleProductWithId().withAmountInStock(1));
    }

    @Test
    void productExists(){
        //GIVEN
        ProductDTO product = sampleProductDTOWithDetailsWithId();
        //WHEN
        service.productExists(product);
        //THEN
        verify(repo).existsById(product.getId());
    }

}
