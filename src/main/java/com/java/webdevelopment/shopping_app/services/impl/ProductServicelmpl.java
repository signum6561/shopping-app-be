package com.java.webdevelopment.shopping_app.services.impl;

import java.util.List;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.java.webdevelopment.shopping_app.constants.Contants;
import com.java.webdevelopment.shopping_app.conventers.DefaultImageConvert;
import com.java.webdevelopment.shopping_app.conventers.ProductImageConveter;
import com.java.webdevelopment.shopping_app.entities.Category;
import com.java.webdevelopment.shopping_app.entities.Product;
import com.java.webdevelopment.shopping_app.exceptions.CategoryNotFoundException;
import com.java.webdevelopment.shopping_app.exceptions.ProductNotFoundException;
import com.java.webdevelopment.shopping_app.payload.requests.ProductRequest;
import com.java.webdevelopment.shopping_app.payload.responses.ApiResponse;
import com.java.webdevelopment.shopping_app.payload.responses.PageResponse;
import com.java.webdevelopment.shopping_app.payload.responses.ProductInfoResponse;
import com.java.webdevelopment.shopping_app.payload.responses.ProductResponse;
import com.java.webdevelopment.shopping_app.repositories.CategoryRepository;
import com.java.webdevelopment.shopping_app.repositories.ProductRepository;
import com.java.webdevelopment.shopping_app.services.ProductService;
import com.java.webdevelopment.shopping_app.utils.IdUtil;
import com.java.webdevelopment.shopping_app.utils.PageUtil;

import jakarta.annotation.PostConstruct;

@Service
public class ProductServicelmpl implements ProductService {

    private final ProductRepository productRepository;

    private final ModelMapper modelMapper;

    private final CategoryRepository categoryRepository;

    public ProductServicelmpl(ProductRepository productRepository, ModelMapper modelMapper,
            CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
    }

    @PostConstruct
    public void postConstruct() {
        TypeMap<Product, ProductInfoResponse> entityToInfoResponse = modelMapper.createTypeMap(Product.class,
                ProductInfoResponse.class);

        entityToInfoResponse.addMappings(
                mapper -> mapper.using(new DefaultImageConvert()).map(Product::getImages,
                        ProductInfoResponse::setDefaultImage));

        Converter<Category, String> categoryNameOnlyConverter = s -> s.getSource() == null ? null
                : s.getSource().getName();

        entityToInfoResponse.addMappings(
                mapper -> mapper.using(categoryNameOnlyConverter).map(Product::getCategory,
                        ProductInfoResponse::setCategoryName));

        entityToInfoResponse.addMappings(
                mapper -> mapper.map(Product::isInStock, ProductInfoResponse::setInStock));

        TypeMap<Product, ProductResponse> entityToResponse = modelMapper.createTypeMap(Product.class,
                ProductResponse.class);
        entityToResponse.addMappings(
                mapper -> mapper.using(new ProductImageConveter()).map(Product::getImages,
                        ProductResponse::setImageLinks));

        entityToResponse.addMappings(
                mapper -> mapper.map(Product::isInStock, ProductResponse::setInStock));

        entityToResponse.addMappings(
                mapper -> mapper.using(categoryNameOnlyConverter).map(Product::getCategory,
                        ProductResponse::setCategoryName));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<ProductInfoResponse> getPaginateProduct(Integer page, Integer pageSize) {

        Page<Product> products = productRepository.findAll(PageUtil.request(page, pageSize));
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
    @Transactional(readOnly = true)
    public PageResponse<ProductInfoResponse> getProductsByCategory(String categoryId, Integer page, Integer pageSize) {
        
        boolean isCategoryExist = categoryRepository.existsById(categoryId);
        if(!isCategoryExist) {
            throw new CategoryNotFoundException(categoryId);
        }
        
        Pageable pageable = PageUtil.request(page, pageSize);
        Page<Product> products = productRepository.findByCategoryId(categoryId, pageable);
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
    public ProductResponse getProduct(String id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        return modelMapper.map(product, ProductResponse.class);
    }

    @Override
    @Transactional
    public ProductResponse createProduct(ProductRequest request) {
        String id = request.getCategoryId();
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));

        Product product = Product.builder()
                .id(IdUtil.generate())
                .name(request.getName())
                .amount(request.getAmount())
                .category(category)
                .description(request.getDescription())
                .price(request.getPrice())
                .build();

        Product insertedProduct = productRepository.save(product);
        return modelMapper.map(insertedProduct, ProductResponse.class);
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(String id, ProductRequest request) {

        String categoryId = request.getCategoryId();
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));

        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setAmount(request.getAmount());
        product.setDescription(request.getDescription());
        product.setCategory(category);

        productRepository.save(product);
        return modelMapper.map(product, ProductResponse.class);
    }

    @Override
    @Transactional
    public ApiResponse deleteProduct(String id) {

        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        productRepository.delete(product);

        return new ApiResponse(
                true,
                Contants.PRODUCT_DELETED_SUCCESS,
                HttpStatus.OK);
    }

}
