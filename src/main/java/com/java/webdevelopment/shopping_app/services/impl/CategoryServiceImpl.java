package com.java.webdevelopment.shopping_app.services.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.java.webdevelopment.shopping_app.constants.Contants;
import com.java.webdevelopment.shopping_app.entities.Category;
import com.java.webdevelopment.shopping_app.exceptions.CategoryNotFoundException;
import com.java.webdevelopment.shopping_app.exceptions.ProductsLinkedException;
import com.java.webdevelopment.shopping_app.payload.CategoryDTO;
import com.java.webdevelopment.shopping_app.payload.responses.ApiResponse;
import com.java.webdevelopment.shopping_app.payload.responses.DataResponse;
import com.java.webdevelopment.shopping_app.payload.responses.PageResponse;
import com.java.webdevelopment.shopping_app.repositories.CategoryRepository;
import com.java.webdevelopment.shopping_app.services.CategoryService;
import com.java.webdevelopment.shopping_app.utils.IdUtil;
import com.java.webdevelopment.shopping_app.utils.PageUtil;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<CategoryDTO> getPaginateCategory(Integer page, Integer pageSize) {
    
        Page<Category> categories = categoryRepository.findAll(PageUtil.request(page, pageSize));
        List<CategoryDTO> categoryDTOs = categories.get()
            .map(category -> modelMapper.map(category, CategoryDTO.class))
            .toList();
        PageResponse<CategoryDTO> pageResponse = new PageResponse<>();
        pageResponse.setCurrentPage(page);
        pageResponse.setLastPage(categories.getTotalPages());
        pageResponse.setPageSize(pageSize);
        pageResponse.setTotal(categories.getNumberOfElements());
        pageResponse.setData(categoryDTOs);
        return pageResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public DataResponse<CategoryDTO> getAllCategory() {
        List<CategoryDTO> categoryDTOs = categoryRepository.findAll()
            .stream()
            .map(category -> modelMapper.map(category, CategoryDTO.class))
            .toList();
        DataResponse<CategoryDTO> response = new DataResponse<>(categoryDTOs);
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDTO getCategory(String id) {
        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new CategoryNotFoundException(id));
        return modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    @Transactional
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = Category.builder()
            .id(IdUtil.generateCompact())
            .name(categoryDTO.getName())
            .build();
        Category insertedCategory = categoryRepository.save(category);
        return modelMapper.map(insertedCategory, CategoryDTO.class);
    }

    @Override
    @Transactional
    public CategoryDTO updateCategory(String id, CategoryDTO categoryDTO) {

        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new CategoryNotFoundException(id));
        
        category.setName(categoryDTO.getName());
        Category updatedCategory = categoryRepository.save(category);
        return modelMapper.map(updatedCategory, CategoryDTO.class);
    }

    @Override
    @Transactional
    public ApiResponse deleteCategory(String id) {
        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new CategoryNotFoundException(id));
        
        if(category.hasProducts()) {
            throw new ProductsLinkedException();
        }

        categoryRepository.delete(category);
        
        return new ApiResponse(
            true,
            Contants.CATEGORY_DELETED_SUCCESS,
            HttpStatus.OK
        );
    }

}
