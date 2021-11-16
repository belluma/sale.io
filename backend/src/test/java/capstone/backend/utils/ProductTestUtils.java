package capstone.backend.utils;

import capstone.backend.model.db.Product;
import capstone.backend.model.dto.ProductDTO;

import java.util.List;

import static capstone.backend.utils.SupplierTestUtils.sampleSupplier;
import static capstone.backend.utils.SupplierTestUtils.sampleSupplierDTO;

public class ProductTestUtils {

    public static ProductDTO sampleProductDTOWithDetailsWithId() {
        return ProductDTO
                 .builder()
                .id(123L)
                .name("product")
                .suppliers(List.of(sampleSupplierDTO()))
                .stockCodeSupplier("12345")
//                .category(sampleCategory())
                .purchasePrice(9.99F)
                .retailPrice(19.99F)
                .minAmount(5)
                .maxAmount(20)
                .unitSize(5)
                .amountInStock(0)
                .build();
    }
  public static Product sampleProductWithId() {
        return Product
                 .builder()
                .id(123L)
                .name("product")
                .suppliers(List.of(sampleSupplier()))
                .stockCodeSupplier("12345")
//                .category(sampleCategory())
                .purchasePrice(9.99F)
                .retailPrice(19.99F)
                .minAmount(5)
                .maxAmount(20)
                .unitSize(5)
                .amountInStock(0)
                .build();
    }
public static ProductDTO sampleProductDTOWithDetails() {
        return ProductDTO
                 .builder()
                .name("product")
                .stockCodeSupplier("12345")
//                .category(sampleCategory())
                .purchasePrice(9.99F)
                .retailPrice(19.99F)
                .minAmount(5)
                .maxAmount(20)
                .unitSize(5)
//                .suppliers(List.of(sampleSupplier()))
                .build();
    }
  public static Product sampleProduct() {
        return Product
                 .builder()
                .name("product")
                .stockCodeSupplier("12345")
//                .category(sampleCategory())
                .purchasePrice(9.99F)
                .retailPrice(19.99F)
                .minAmount(5)
                .maxAmount(20)
                .unitSize(5)
//                .suppliers(List.of(sampleSupplier()))
                .build();
    }
}
