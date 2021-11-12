package capstone.backend.services;

import capstone.backend.exception.model.EntityNotFoundException;
import capstone.backend.model.dto.order.OrderItemDTO;
import capstone.backend.repo.OrderItemRepo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static capstone.backend.mapper.OrderItemMapper.mapOrderItem;
import static capstone.backend.utils.OrderItemTestUtils.sampleOrderItem;
import static capstone.backend.utils.OrderItemTestUtils.sampleOrderItemDTO;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
class OrderItemServiceTest {

    private final OrderItemRepo repo = mock(OrderItemRepo.class);
    private final OrderItemService service = new OrderItemService(repo);

    @Test
    void getAllOrderQuantities() {
        //GIVEN
        when(repo.findAll()).thenReturn(List.of(sampleOrderItem()));
        OrderItemDTO expected = sampleOrderItemDTO();
        //WHEN
        List<OrderItemDTO> actual = service.getAllOrderItems();
        //THEN
        assertThat(actual.get(0), is(expected));
        verify(repo).findAll();
    }

    @Test
    void getSingleOrderQuantity() {
        //GIVEN
        when(repo.findById(1L)).thenReturn(Optional.of(sampleOrderItem().withId(1L)));
        OrderItemDTO expected = sampleOrderItemDTO();
        //WHEN
        OrderItemDTO actual = service.getSingleOrderItem(1L);
        //THEN
        assertThat(actual, is(expected));
        verify(repo).findById(1L);
    }

    @Test
    void getSingleOrderQuantityThrowsWhenNoProductFound() {
        //GIVEN
        when(repo.findById(1L)).thenReturn(Optional.empty());
        //WHEN - THEN
        Exception ex = assertThrows(EntityNotFoundException.class, () -> service.getSingleOrderItem(1L));
        assertThat(ex.getMessage(), is("Something went wrong while retrieving your data!"));
        verify(repo).findById(1L);
    }

    @Test
    void createProduct() {
        //GIVEN
        OrderItemDTO expected = sampleOrderItemDTO();
        when(repo.save(mapOrderItem(expected))).thenReturn(mapOrderItem(expected));
        //WHEN
        OrderItemDTO actual = service.addOrderItem(expected);
        //THEN
        verify(repo).save(mapOrderItem(expected));
        assertThat(actual, is(expected));
    }
}
