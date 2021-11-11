package capstone.backend.services;

import capstone.backend.model.db.order.OrderQuantity;
import capstone.backend.model.dto.ProductDTO;
import capstone.backend.repo.OrderQuantityRepo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static capstone.backend.utils.OrderQuantityUtils.sampleOrderQuantity;
import static capstone.backend.utils.ProductTestUtils.sampleProductDTOWithDetailsWithId;
import static capstone.backend.utils.ProductTestUtils.sampleProductWithId;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
class OrderQuantityServiceTest {

    private final OrderQuantityRepo repo = mock(OrderQuantityRepo.class);
    private final OrderQuantityService service = new OrderQuantityService(repo);

    @Test
    void getAllOrderQuantities() {
        //GIVEN
        when(repo.findAll()).thenReturn(List.of(sampleOrderQuantity()));
        List<OrderQuantity> expected = List.of(sampleOrderQuantity());
        //WHEN
        List<OrderQuantity> actual = service.getAllOrderQuantities();
        //THEN
        assertIterableEquals(actual, expected);
        verify(repo).findAll();
    }

    @Test
    void getSingleOrderQuantity() {
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
    void addOrderQuantity() {
    }
}
