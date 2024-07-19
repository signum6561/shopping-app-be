package com.java.webdevelopment.shopping_app.services;

import com.java.webdevelopment.shopping_app.payload.ProductDTO;
import com.java.webdevelopment.shopping_app.payload.responses.ApiResponse;
import com.java.webdevelopment.shopping_app.payload.responses.PageResponse;
import com.java.webdevelopment.shopping_app.payload.responses.ProductInfoResponse;

public interface ProductService {

    PageResponse<ProductInfoResponse> getPaginateProduct(Integer page, Integer pageSize);

    ProductDTO getProduct(String id);

    ProductDTO createProduct(ProductDTO productDTO);

    ProductDTO updateProduct(ProductDTO productDTO);

    ApiResponse deleteProduct(String id);

}
