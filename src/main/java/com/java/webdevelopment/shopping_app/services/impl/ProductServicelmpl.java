package com.java.webdevelopment.shopping_app.services.impl;

import java.util.List;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.java.webdevelopment.shopping_app.constants.Contants;
import com.java.webdevelopment.shopping_app.conventers.DefaultImageConvert;
import com.java.webdevelopment.shopping_app.conventers.ProductImageConveter;
import com.java.webdevelopment.shopping_app.entities.Category;
import com.java.webdevelopment.shopping_app.entities.Product;
import com.java.webdevelopment.shopping_app.entities.ProductImage;
import com.java.webdevelopment.shopping_app.exceptions.CategoryNotFoundException;
import com.java.webdevelopment.shopping_app.exceptions.ProductNotFoundException;
import com.java.webdevelopment.shopping_app.payload.ProductDTO;
import com.java.webdevelopment.shopping_app.payload.responses.ApiResponse;
import com.java.webdevelopment.shopping_app.payload.responses.PageResponse;
import com.java.webdevelopment.shopping_app.payload.responses.ProductInfoResponse;
import com.java.webdevelopment.shopping_app.repositories.CategoryRepository;
import com.java.webdevelopment.shopping_app.repositories.ProductRepository;
import com.java.webdevelopment.shopping_app.services.ProductService;
import com.java.webdevelopment.shopping_app.utils.IdUtil;
import com.java.webdevelopment.shopping_app.utils.PageUtil;

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

        TypeMap<Product, ProductDTO> entityToDto = modelMapper.createTypeMap(Product.class, ProductDTO.class);
        entityToDto.addMappings(
                mapper -> mapper.using(new ProductImageConveter()).map(Product::getImages, ProductDTO::setImageLinks));
    }

    @Override
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
    public ProductDTO getProduct(String id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        String id = productDTO.getCategoryId();
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));

        Product product = Product.builder()
                .id(IdUtil.generate())
                .name(productDTO.getName())
                .amount(productDTO.getAmount())
                .category(category)
                .description(productDTO.getDescription())
                .price(productDTO.getPrice())
                .build();

        for (String link : productDTO.getImageLinks()) {
            ProductImage productImage = ProductImage.builder()
                    .id(IdUtil.generate())
                    .link(link)
                    .build();
            product.addImage(productImage);
        }

        Product insertedProduct = productRepository.save(product);
        return modelMapper.map(insertedProduct, ProductDTO.class);
    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO) {

        String id = productDTO.getId();
        String categoryId = productDTO.getCategoryId();
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));

        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setAmount(productDTO.getAmount());
        product.setDescription(productDTO.getDescription());
        product.setCategory(category);

        productRepository.save(product);
        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public ApiResponse deleteProduct(String id) {

        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        productRepository.delete(product);

        return new ApiResponse(
                true,
                Contants.PRODUCT_DELETED_SUCCESS,
                HttpStatus.OK);
    }

}
