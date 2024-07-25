package com.java.webdevelopment.shopping_app.services;

import com.java.webdevelopment.shopping_app.payload.CategoryDTO;
import com.java.webdevelopment.shopping_app.payload.responses.PageResponse;
import com.java.webdevelopment.shopping_app.payload.responses.ApiResponse;
import com.java.webdevelopment.shopping_app.payload.responses.DataResponse;

public interface CategoryService {

    PageResponse<CategoryDTO> getPaginateCategory(Integer page, Integer pageSize);

    DataResponse<CategoryDTO> getAllCategory();

    CategoryDTO getCategory(String id);

    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO updateCategory(String id, CategoryDTO categoryDTO);

    ApiResponse deleteCategory(String id);

}
