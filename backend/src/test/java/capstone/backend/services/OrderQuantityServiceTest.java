package capstone.backend.services;

import capstone.backend.exception.model.EntityNotFoundException;
import capstone.backend.model.db.order.OrderQuantity;
import capstone.backend.repo.OrderQuantityRepo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static capstone.backend.utils.OrderQuantityUtils.sampleOrderQuantity;
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
        OrderQuantity expected = sampleOrderQuantity();
        //WHEN
        List<OrderQuantity> actual = service.getAllOrderQuantities();
        //THEN
        assertThat(actual.get(0).withId(1L), is(expected.withId(1L)));
        verify(repo).findAll();
    }

    @Test
    void getSingleOrderQuantity() {
        //GIVEN
        when(repo.findById(123L)).thenReturn(Optional.of(sampleOrderQuantity().withId(123L)));
        OrderQuantity expected = sampleOrderQuantity().withId(123L);
        //WHEN
        OrderQuantity actual = service.getSingleOrderQuantity(123L);
        //THEN
        assertThat(actual, is(expected));
        verify(repo).findById(123L);
    }

    @Test
    void getSingleOrderQuantityThrowsWhenNoProductFound() {
        //GIVEN
        when(repo.findById(123L)).thenReturn(Optional.empty());
        //WHEN - THEN
        Exception ex = assertThrows(EntityNotFoundException.class, () -> service.getSingleOrderQuantity(123L));
        assertThat(ex.getMessage(), is("Something went wrong while retrieving your data!"));
        verify(repo).findById(123L);
    }

    @Test
    void createProduct() {
        //GIVEN
        OrderQuantity expected = sampleOrderQuantity();
        when(repo.save(expected)).thenReturn(expected);
        //WHEN
        OrderQuantity actual = service.addOrderQuantity(expected);
        //THEN
        verify(repo).save(expected);
        assertThat(actual, is(expected));
    }
}
