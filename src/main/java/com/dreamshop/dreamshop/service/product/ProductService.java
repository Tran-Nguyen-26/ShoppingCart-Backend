package com.dreamshop.dreamshop.service.product;

import java.util.List;
import java.util.Optional;

import com.dreamshop.dreamshop.model.Category;

import org.springframework.stereotype.Service;

import com.dreamshop.dreamshop.exceptions.ProductNotFoundException;
import com.dreamshop.dreamshop.model.Product;
import com.dreamshop.dreamshop.repository.CategoryRepository;
import com.dreamshop.dreamshop.repository.ProductRepository;
import com.dreamshop.dreamshop.request.AddProductRequest;
import com.dreamshop.dreamshop.request.ProductUpdateRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

  private ProductRepository productRepository;
  private CategoryRepository categoryRepository;

  @Override
  public Product addProduct(AddProductRequest request) {
    Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
        .orElseGet(() -> {
          Category newCategory = new Category(request.getCategory().getName());
          return categoryRepository.save(newCategory);
        });
    request.setCategory(category);
    return productRepository.save(createProduct(request, category));

  }

  private Product createProduct(AddProductRequest request, Category category) {
    return new Product(
        request.getName(),
        request.getBrand(),
        request.getPrice(),
        request.getInventory(),
        request.getDescription(),
        category);
  }

  @Override
  public Product getProductById(Long id) {
    return productRepository.findById(id)
        .orElseThrow(() -> new ProductNotFoundException("Product not found"));
  }

  @Override
  public void deleteProductById(Long id) {
    productRepository.findById(id)
        .ifPresentOrElse(productRepository::delete, () -> {
          throw new ProductNotFoundException("Product not found");
        });
  }

  @Override
  public Product updateProduct(ProductUpdateRequest request, Long productId) {
    return productRepository.findById(productId)
        .map(existingProduct -> updateExistingProduct(existingProduct, request))
        .map(productRepository::save)
        .orElseThrow(() -> new ProductNotFoundException("Product not found"));
  }

  private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request) {
    existingProduct.setName(request.getName());
    existingProduct.setDescription(request.getDescription());
    existingProduct.setBrand(request.getBrand());
    existingProduct.setPrice(request.getPrice());
    existingProduct.setInventory(request.getInventory());

    Category category = categoryRepository.findByName(request.getCategory().getName());
    existingProduct.setCategory(category);

    return existingProduct;
  }

  @Override
  public List<Product> getAllProducts() {
    return productRepository.findAll();
  }

  @Override
  public List<Product> getProductsByCategory(String category) {
    return productRepository.findByCategoryName(category);
  }

  @Override
  public List<Product> getProductsByBrand(String brand) {
    return productRepository.findByBrand(brand);
  }

  @Override
  public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
    return productRepository.findByCategoryNameAndBrand(category, brand);
  }

  @Override
  public List<Product> getProductsByName(String name) {
    return productRepository.findByName(name);
  }

  @Override
  public List<Product> getProductsByBrandAndName(String brand, String name) {
    return productRepository.findByBrandAndName(brand, name);
  }

  @Override
  public Long countProductsByBrandAndName(String brand, String name) {
    return productRepository.countByBrandAndName(brand, name);
  }

}
