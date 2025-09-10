package com.dreamshop.dreamshop.service.product;

import java.util.List;

import com.dreamshop.dreamshop.model.Product;
import com.dreamshop.dreamshop.request.AddProductRequest;
import com.dreamshop.dreamshop.request.ProductUpdateRequest;

public interface IProductService {
  Product addProduct(AddProductRequest product);

  Product getProductById(Long id);

  void deleteProductById(Long id);

  Product updateProduct(ProductUpdateRequest product, Long productId);

  List<Product> getAllProducts();

  List<Product> getProductsByCategory(String category);

  List<Product> getProductsByBrand(String brand);

  List<Product> getProductsByCategoryAndBrand(String category, String brand);

  List<Product> getProductsByName(String name);

  List<Product> getProductsByBrandAndName(String brand, String name);

  Long countProductsByBrandAndName(String brand, String name);
}
