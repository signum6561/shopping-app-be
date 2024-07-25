package com.java.webdevelopment.shopping_app.services;

import com.java.webdevelopment.shopping_app.payload.requests.ProductRequest;
import com.java.webdevelopment.shopping_app.payload.responses.ApiResponse;
import com.java.webdevelopment.shopping_app.payload.responses.PageResponse;
import com.java.webdevelopment.shopping_app.payload.responses.ProductInfoResponse;
import com.java.webdevelopment.shopping_app.payload.responses.ProductResponse;

public interface ProductService {

    PageResponse<ProductInfoResponse> getPaginateProduct(Integer page, Integer pageSize);

    PageResponse<ProductInfoResponse> getProductsByCategory(String categoryId, Integer page, Integer pageSize);

    ProductResponse getProduct(String id);

    ProductResponse createProduct(ProductRequest request);

    ProductResponse updateProduct(String id, ProductRequest request);

    ApiResponse deleteProduct(String id);

}
