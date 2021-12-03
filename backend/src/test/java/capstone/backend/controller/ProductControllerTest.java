package capstone.backend.controller;

import capstone.backend.CombinedTestContainer;
import capstone.backend.exception.CustomError;
import capstone.backend.mapper.ProductMapper;
import capstone.backend.model.db.Product;
import capstone.backend.model.db.contact.Supplier;
import capstone.backend.model.dto.ProductDTO;
import capstone.backend.model.dto.contact.SupplierDTO;
import capstone.backend.repo.ProductRepo;
import capstone.backend.repo.SupplierRepo;
import capstone.backend.utils.ControllerTestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static capstone.backend.mapper.ProductMapper.mapProductWithDetails;
import static capstone.backend.mapper.ProductMapper.mapToProductInfo;
import static capstone.backend.mapper.SupplierMapper.mapSupplier;
import static capstone.backend.utils.ProductTestUtils.*;
import static capstone.backend.utils.SupplierTestUtils.sampleSupplier;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;


@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest {


    @Autowired
    ProductController controller;
    @Autowired
    ProductRepo productRepo;
    @Autowired
    SupplierRepo supplierRepo;
    @Autowired
    TestRestTemplate restTemplate;
    @Autowired
    ProductMapper mapper;
    @Autowired
    ControllerTestUtils utils;

    String BASEURL = "/api/products";

    @Container
    public static PostgreSQLContainer<CombinedTestContainer> container = CombinedTestContainer.getInstance();

    @AfterEach
    void clearDB() {
        supplierRepo.findAll().forEach(supplier -> supplier.setProducts(Set.of()));
        productRepo.findAll().forEach(product -> product.setSuppliers(Set.of()));
        productRepo.deleteAll();
        supplierRepo.deleteAll();
    }

    @Test
    void test() {
        assertTrue(container.isRunning());
    }


    @Test
    void getAllProductsWithDetails() {
        Supplier supplier = supplierRepo.save(sampleSupplier());
        Product product = productRepo.save(sampleProduct().withSuppliers(Set.of(supplier)));
        HttpHeaders headers = utils.createHeadersWithJwtAuth();
        //WHEN
        ResponseEntity<ProductDTO[]> response = restTemplate.exchange(BASEURL, HttpMethod.GET, new HttpEntity<>(headers), ProductDTO[].class);
        ProductDTO expected = mapProductWithDetails(product);
        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertIterableEquals(Arrays.asList(Objects.requireNonNull(response.getBody())), List.of(expected));
    }

    @Test
    void getProductDetails() {
        //GIVEN
        Supplier supplier = supplierRepo.save(sampleSupplier());
        Product product = productRepo.save(sampleProduct().withSuppliers(Set.of(supplier)));
        HttpHeaders headers = utils.createHeadersWithJwtAuth();
        //WHEN
        ResponseEntity<ProductDTO> response = restTemplate.exchange(BASEURL + "/" + product.getId(), HttpMethod.GET, new HttpEntity<>(headers), ProductDTO.class);
        ProductDTO expected = mapProductWithDetails(product);
        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(expected));
    }

    @Test
    void getProductDetailsReturnsNotFoundWhenProductNonExistent() {
        //GIVEN
        HttpHeaders headers = utils.createHeadersWithJwtAuth();
        //WHEN
        ResponseEntity<CustomError> response = restTemplate.exchange(BASEURL + "/12", HttpMethod.GET, new HttpEntity<>(headers), CustomError.class);
        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
        assertThat(Objects.requireNonNull(response.getBody()).getMessage(), is(String.format("Couldn't find a product with the id %d", 12)));
    }

    @Test
    void createProduct() {
        //GIVEN
        Supplier supplier = supplierRepo.save(sampleSupplier());
        ProductDTO product = sampleProductDTOWithDetailsWithId();
        product.setSuppliers(Set.of(mapSupplier(supplier)));
        HttpHeaders headers = utils.createHeadersWithJwtAuth();
        //WHEN
        ResponseEntity<ProductDTO> response = restTemplate.exchange(BASEURL, HttpMethod.POST, new HttpEntity<>(product, headers), ProductDTO.class);
        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(productRepo.findAll().size(), is(1));
    }

    @Test
    void createProductAddsProductToSuppliersProductList() {
        //GIVEN
        Supplier supplier = supplierRepo.save(sampleSupplier());
        ProductDTO product = sampleProductDTOWithDetailsWithId();
        product.setSuppliers(Set.of(mapSupplier(supplier)));
        HttpHeaders headers = utils.createHeadersWithJwtAuth();
        //WHEN
        ResponseEntity<ProductDTO> response = restTemplate.exchange(BASEURL, HttpMethod.POST, new HttpEntity<>(product, headers), ProductDTO.class);
        ResponseEntity<SupplierDTO> updatedSupplier = restTemplate.exchange("/api/suppliers/" + supplier.getId(), HttpMethod.GET, new HttpEntity<>(headers), SupplierDTO.class);
        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(Objects.requireNonNull(updatedSupplier.getBody()).getProducts(),
                containsInAnyOrder(mapToProductInfo(product.withId(Objects.requireNonNull(response.getBody()).getId()))));
    }

    @Test
    void createProductFailsWhenProductIdAlreadyTaken() {
        //GIVEN
        Supplier supplier = supplierRepo.save(sampleSupplier());
        ProductDTO product = mapProductWithDetails(productRepo.save(sampleProduct().withSuppliers(Set.of(supplier))));
        HttpHeaders headers = utils.createHeadersWithJwtAuth();
        //WHEN
        ResponseEntity<CustomError> response = restTemplate.exchange(BASEURL, HttpMethod.POST, new HttpEntity<>(product, headers), CustomError.class);
        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.NOT_ACCEPTABLE));
        assertThat(productRepo.findAll().size(), is(1));
        assertThat(Objects.requireNonNull(response.getBody()).getMessage(), is(String.format("Product %s already has the id %d", product.getName(), product.getId())));

    }

    @Test
    void createProductFailsWhenNoSupplier() {
        //GIVEN
        ProductDTO product = sampleProductDTOWithDetailsWithId().withSuppliers(Set.of());
        HttpHeaders headers = utils.createHeadersWithJwtAuth();
        //WHEN
        ResponseEntity<CustomError> response = restTemplate.exchange(BASEURL, HttpMethod.POST, new HttpEntity<>(product, headers), CustomError.class);
        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.NOT_ACCEPTABLE));
        assertThat(Objects.requireNonNull(response.getBody()).getMessage(), is("You forgot to add a supplier to your product"));
    }

    @Test
    void createProductFailsWhenSupplierNonExistent() {
        //GIVEN
        ProductDTO product = sampleProductDTOWithDetailsWithId();
        Supplier supplier = sampleSupplier();
        HttpHeaders headers = utils.createHeadersWithJwtAuth();
        //WHEN
        ResponseEntity<CustomError> response = restTemplate.exchange(BASEURL, HttpMethod.POST, new HttpEntity<>(product, headers), CustomError.class);
        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
        assertThat(Objects.requireNonNull(response.getBody()).getMessage(), is(String.format("Couldn't find a supplier with the id %d", supplier.getId())));
    }

    @Test
    void editProduct() {
        //GIVEN
        Supplier supplier = supplierRepo.save(sampleSupplier());
        Product product = productRepo.save(sampleProduct().withSuppliers(Set.of(supplier)));
        ProductDTO productToEdit = mapProductWithDetails(product);
        productToEdit.setRetailPrice(99.99F);
        HttpHeaders headers = utils.createHeadersWithJwtAuth();
        //WHEN
        ResponseEntity<ProductDTO> response = restTemplate.exchange(BASEURL + "/" + product.getId(), HttpMethod.PUT, new HttpEntity<>(productToEdit, headers), ProductDTO.class);
        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(productToEdit));
    }

    @Test
    void editProductFailsWhenProductNonExistent() {
        //GIVEN
        HttpHeaders headers = utils.createHeadersWithJwtAuth();
        ProductDTO productToEdit = sampleProductDTOWithDetailsWithId();
        //WHEN
        ResponseEntity<CustomError> response = restTemplate.exchange(BASEURL + "/1", HttpMethod.PUT, new HttpEntity<>(productToEdit, headers), CustomError.class);
        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
        assertThat(Objects.requireNonNull(response.getBody()).getMessage(), is(String.format("Couldn't find a product with the id %d", productToEdit.getId())));
    }

    @Test
    void editProductAddsProductToSuppliersProductList() {
        //GIVEN
        Supplier supplier = supplierRepo.save(sampleSupplier());
        Supplier supplier2 = supplierRepo.save(sampleSupplier());
        Product product = productRepo.save(sampleProduct().withSuppliers(Set.of(supplier)));
        ProductDTO productToEdit = mapProductWithDetails(product.withSuppliers(Set.of(supplier, supplier2)));
        HttpHeaders headers = utils.createHeadersWithJwtAuth();
        //WHEN
        ResponseEntity<ProductDTO> response = restTemplate.exchange(BASEURL + "/" + product.getId(), HttpMethod.PUT, new HttpEntity<>(productToEdit, headers), ProductDTO.class);
        ResponseEntity<SupplierDTO> oldSupplier = restTemplate.exchange("/api/suppliers/" + supplier.getId(), HttpMethod.GET, new HttpEntity<>(headers), SupplierDTO.class);
        ResponseEntity<SupplierDTO> updatedSupplier = restTemplate.exchange("/api/suppliers/" + supplier2.getId(), HttpMethod.GET, new HttpEntity<>(headers), SupplierDTO.class);
        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(Objects.requireNonNull(oldSupplier.getBody()).getProducts(), containsInAnyOrder(mapToProductInfo(productToEdit)));
        assertThat(Objects.requireNonNull(updatedSupplier.getBody()).getProducts(), containsInAnyOrder(mapToProductInfo(productToEdit)));
    }

    @Test
    void editProductReplacesOneSupplier() {
        //GIVEN
        Supplier supplier = supplierRepo.save(sampleSupplier());
        Supplier supplier2 = supplierRepo.save(sampleSupplier());
        Product product = productRepo.save(sampleProduct().withSuppliers(Set.of(supplier)));
        ProductDTO productToEdit = mapProductWithDetails(product.withSuppliers(Set.of(supplier2)));
        HttpHeaders headers = utils.createHeadersWithJwtAuth();
        //WHEN
        ResponseEntity<ProductDTO> response = restTemplate.exchange(BASEURL + "/" + product.getId(), HttpMethod.PUT, new HttpEntity<>(productToEdit, headers), ProductDTO.class);
        ResponseEntity<SupplierDTO> oldSupplier = restTemplate.exchange("/api/suppliers/" + supplier.getId(), HttpMethod.GET, new HttpEntity<>(headers), SupplierDTO.class);
        ResponseEntity<SupplierDTO> updatedSupplier = restTemplate.exchange("/api/suppliers/" + supplier2.getId(), HttpMethod.GET, new HttpEntity<>(headers), SupplierDTO.class);
        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertFalse(Objects.requireNonNull(oldSupplier.getBody()).getProducts().contains(mapToProductInfo(productToEdit)));
        assertThat(Objects.requireNonNull(updatedSupplier.getBody()).getProducts(), containsInAnyOrder(mapToProductInfo(productToEdit)));
    }

    @Test
    void editProductRemovesProductFromOneSuppliersProductList() {
        //GIVEN
        Supplier supplier = supplierRepo.save(sampleSupplier());
        Supplier supplier2 = supplierRepo.save(sampleSupplier());
        Product product = productRepo.save(sampleProduct().withSuppliers(Set.of(supplier, supplier2)));
        ProductDTO productToEdit = mapProductWithDetails(product.withSuppliers(Set.of(supplier)));
        HttpHeaders headers = utils.createHeadersWithJwtAuth();
        //WHEN
        ResponseEntity<ProductDTO> response = restTemplate.exchange(BASEURL + "/" + product.getId(), HttpMethod.PUT, new HttpEntity<>(productToEdit, headers), ProductDTO.class);
        ResponseEntity<SupplierDTO> supplierThatKeepsProduct = restTemplate.exchange("/api/suppliers/" + supplier.getId(), HttpMethod.GET, new HttpEntity<>(headers), SupplierDTO.class);
        ResponseEntity<SupplierDTO> supplierThatTakesProductOutOfSTock = restTemplate.exchange("/api/suppliers/" + supplier2.getId(), HttpMethod.GET, new HttpEntity<>(headers), SupplierDTO.class);
        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(Objects.requireNonNull(supplierThatKeepsProduct.getBody()).getProducts(), containsInAnyOrder(mapToProductInfo(productToEdit)));
        assertFalse(Objects.requireNonNull(supplierThatTakesProductOutOfSTock.getBody()).getProducts().contains(mapToProductInfo(productToEdit)));
    }

    @Test
    void editProductFailsWhenTryingToAddNonExistentSupplier() {
        //GIVEN
        Supplier supplier = supplierRepo.save(sampleSupplier());
        Supplier supplier2 = sampleSupplier();
        Product product = productRepo.save(sampleProduct().withSuppliers(Set.of(supplier)));
        ProductDTO productToEdit = mapProductWithDetails(product.withSuppliers(Set.of(supplier, supplier2)));
        HttpHeaders headers = utils.createHeadersWithJwtAuth();
        //WHEN
        ResponseEntity<CustomError> response = restTemplate.exchange(BASEURL + "/" + product.getId(), HttpMethod.PUT, new HttpEntity<>(productToEdit, headers), CustomError.class);
        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
        assertThat(Objects.requireNonNull(response.getBody()).getMessage(), is(String.format("Couldn't find a supplier with the id %d", supplier2.getId())));
    }
}
