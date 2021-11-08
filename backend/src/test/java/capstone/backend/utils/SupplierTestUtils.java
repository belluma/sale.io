package capstone.backend.utils;

import capstone.backend.model.db.contact.Supplier;
import capstone.backend.model.dto.contact.SupplierDTO;
import capstone.backend.model.enums.Weekdays;

import java.util.List;

import static capstone.backend.utils.ProductTestUtils.sampleProductDTOWithDetailsWithId;
import static capstone.backend.utils.ProductTestUtils.sampleProductWithId;

public class SupplierTestUtils {

    public static SupplierDTO sampleSupplierDTO(){
        return SupplierDTO
                .builder()
                .id(123L)
                .firstName("John")
                .lastName("Doe")
                .email("a@b.c")
                .phone("01234")
                .picture("img")
                .products(List.of(sampleProductDTOWithDetailsWithId()))
                .orders(List.of())
                .orderDay(Weekdays.FRIDAY)
                .build();
    }
  public static Supplier sampleSupplier(){
        return Supplier
                .builder()
                .id(123L)
                .firstName("John")
                .lastName("Doe")
                .email("a@b.c")
                .phone("01234")
                .picture("img")
                .products(List.of(sampleProductWithId()))
                .orders(List.of())
                .orderDay(Weekdays.FRIDAY)
                .build();
    }
}
