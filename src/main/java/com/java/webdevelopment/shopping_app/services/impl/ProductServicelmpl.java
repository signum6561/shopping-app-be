package com.java.webdevelopment.shopping_app.services.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.java.webdevelopment.shopping_app.constants.Contants;
import com.java.webdevelopment.shopping_app.entities.Product;
import com.java.webdevelopment.shopping_app.entities.ProductImage;
import com.java.webdevelopment.shopping_app.exceptions.ProductNotFoundException;
import com.java.webdevelopment.shopping_app.payload.ProductDTO;
import com.java.webdevelopment.shopping_app.payload.responses.ApiResponse;
import com.java.webdevelopment.shopping_app.payload.responses.PageResponse;
import com.java.webdevelopment.shopping_app.payload.responses.ProductInfoResponse;
import com.java.webdevelopment.shopping_app.repositories.ProductRepository;
import com.java.webdevelopment.shopping_app.services.ProductService;
import com.java.webdevelopment.shopping_app.utils.IdUtil;
import com.java.webdevelopment.shopping_app.utils.ValidateUtil;

@Service
public class ProductServicelmpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public PageResponse<ProductInfoResponse> getPaginateProduct(Integer page, Integer pageSize) {
        
        ValidateUtil.validatePaginateParams(page, pageSize);
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<Product> products = productRepository.findAll(pageable);
        List<ProductInfoResponse> productResponse = products.get()
                .map(product -> modelMapper.map(product, ProductInfoResponse.class))
                .toList();

        PageResponse<ProductInfoResponse> pageResponse = new PageResponse<>();
        pageResponse.setCurrentPage(page);
        pageResponse.setLastPage(products.getTotalPages());
        pageResponse.setPageSize(pageSize);
        pageResponse.setTotal(products.getNumberOfElements());
        pageResponse.setData(productResponse);
        return pageResponse;

    }

    @Override
    public ProductDTO getProduct(String id) {
     
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
      
        Product product = modelMapper.map(productDTO, Product.class);
        product.setId(IdUtil.generate());
        productRepository.save(product);
        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO) {
       
        Product product = productRepository.findById(productDTO.getId())
                .orElseThrow(() -> new ProductNotFoundException());
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setAmount(productDTO.getAmount());
        product.setCategory(productDTO.getCategory());
        product.setDescription(productDTO.getDescription());
        List<ProductImage> imageList = productDTO.getImages();
        Set<ProductImage> images = new HashSet<>(imageList);
        product.setImages(images);
        productRepository.save(product);
        return modelMapper.map(product, ProductDTO.class);

    }

    @Override
    public ApiResponse deleteProduct(String id) {
        
        Product product = productRepository.findById(id).orElseThrow(()-> new ProductNotFoundException(id));
        productRepository.delete(product);
        
        return new ApiResponse(
            true, Contants.USER_DELETED_SUCCESS(product.getName()),
            HttpStatus.OK
        );
    }

}
