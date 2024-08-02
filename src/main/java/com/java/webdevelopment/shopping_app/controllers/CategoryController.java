package com.java.webdevelopment.shopping_app.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.webdevelopment.shopping_app.constants.Contants;
import com.java.webdevelopment.shopping_app.payload.CategoryDTO;
import com.java.webdevelopment.shopping_app.payload.responses.ApiResponse;
import com.java.webdevelopment.shopping_app.payload.responses.DataResponse;
import com.java.webdevelopment.shopping_app.payload.responses.PageResponse;
import com.java.webdevelopment.shopping_app.payload.responses.ProductInfoResponse;
import com.java.webdevelopment.shopping_app.services.CategoryService;
import com.java.webdevelopment.shopping_app.services.ProductService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("api/v1/category")
@SecurityRequirement(name = "shopping-api")
public class CategoryController {

    private final ProductService productService;

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService, ProductService productService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/o")
    public ResponseEntity<?> getAllCategory(
        @RequestParam(required = false, defaultValue =  Contants.DEFAULT_PAGE_INDEX) Integer page,
		@RequestParam(required = false, defaultValue = Contants.DEFAULT_PAGE_SIZE) Integer perPage,
        @RequestParam(required = false, defaultValue = "false") Boolean all
    ) {
        if(all) {
            DataResponse<CategoryDTO> response = categoryService.getAllCategory();
            return ResponseEntity.ok(response);
        } else {
            PageResponse<CategoryDTO> response = categoryService.getPaginateCategory(perPage, page);
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("/o/{categoryId}")
    public ResponseEntity<CategoryDTO> getCategory(
        @PathVariable String categoryId
    ) {
        CategoryDTO response = categoryService.getCategory(categoryId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/o/products/{categoryId}")
    public ResponseEntity<PageResponse<ProductInfoResponse>> getProductsByCategoryId(
        @RequestParam(required = false, defaultValue =  Contants.DEFAULT_PAGE_INDEX) Integer page,
		@RequestParam(required = false, defaultValue = Contants.DEFAULT_PAGE_SIZE) Integer perPage,
        @PathVariable String categoryId
    ) {
        PageResponse<ProductInfoResponse> response = productService.getProductsByCategory(categoryId, page, perPage);
        return ResponseEntity.ok(response);
    }

    @PostMapping("")
    @PreAuthorize("@authz.permission(#root, 'create:category')")
    public ResponseEntity<CategoryDTO> createCategory(
        @Valid @RequestBody CategoryDTO newCategory
    ) {
        CategoryDTO response = categoryService.createCategory(newCategory);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(response);
    }

    @PutMapping("/{categoryId}")
    @PreAuthorize("@authz.permission(#root, 'update:category')")
    public ResponseEntity<CategoryDTO> updateCategory(
        @Valid @RequestBody CategoryDTO updateCategory,
        @PathVariable String categoryId
    ) {
        CategoryDTO response = categoryService.updateCategory(categoryId, updateCategory);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{categoryId}")
    @PreAuthorize("@authz.permission(#root, 'delete:category')")
    public ResponseEntity<ApiResponse> deleteCategory(
        @PathVariable String categoryId
    ) {
        ApiResponse response = categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok(response);
    }
}
