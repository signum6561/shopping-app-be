package com.java.webdevelopment.shopping_app.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.java.webdevelopment.shopping_app.constants.Contants;
import com.java.webdevelopment.shopping_app.payload.requests.ProductRequest;
import com.java.webdevelopment.shopping_app.payload.responses.ApiResponse;
import com.java.webdevelopment.shopping_app.payload.responses.PageResponse;
import com.java.webdevelopment.shopping_app.payload.responses.ProductInfoResponse;
import com.java.webdevelopment.shopping_app.payload.responses.ProductResponse;
import com.java.webdevelopment.shopping_app.services.ProductService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/product")
@SecurityRequirement(name = "shopping-api")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/o")
    public ResponseEntity<PageResponse<ProductInfoResponse>> getAllProduct(
        @RequestParam(required = false, defaultValue =  Contants.DEFAULT_PAGE_INDEX) Integer page,
		@RequestParam(required = false, defaultValue = Contants.DEFAULT_PAGE_SIZE) Integer perPage
    ) {
        PageResponse<ProductInfoResponse> response = productService.getPaginateProduct(page, perPage);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/o/{productId}")
    public ResponseEntity<ProductResponse> getProduct(
        @PathVariable String productId
    ) {
        ProductResponse response = productService.getProduct(productId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("")
    @PreAuthorize("@authz.permission(#root, 'create:product')")
    public ResponseEntity<ProductResponse> createProduct(
        @Valid @RequestBody ProductRequest newProduct
    ) {
        ProductResponse response = productService.createProduct(newProduct);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(response);
    }

    @PutMapping("/{productId}")
    @PreAuthorize("@authz.permission(#root, 'update:product')")
    public ResponseEntity<ProductResponse> updateProduct(
        @Valid @RequestBody ProductRequest updateProduct,
        @PathVariable String productId 
    ) {
        ProductResponse response = productService.updateProduct(productId, updateProduct);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{productId}")
    @PreAuthorize("@authz.permission(#root, 'delete:product')")
    public ResponseEntity<ApiResponse> deleteProduct(
        @PathVariable String productId 
    ) {
        ApiResponse response = productService.deleteProduct(productId);
        return ResponseEntity.ok(response);
    }
}
