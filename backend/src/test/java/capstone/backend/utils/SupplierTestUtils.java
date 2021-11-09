package capstone.backend.utils;

import capstone.backend.model.db.contact.Supplier;
import capstone.backend.model.dto.contact.SupplierDTO;
import capstone.backend.model.enums.Weekdays;

import java.util.List;

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
                .products(List.of())
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
                .products(List.of())
                .orders(List.of())
                .orderDay(Weekdays.FRIDAY)
                .build();
    }

}
