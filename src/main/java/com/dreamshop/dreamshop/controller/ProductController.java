package com.dreamshop.dreamshop.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dreamshop.dreamshop.dto.ProductDto;
import com.dreamshop.dreamshop.model.Product;
import com.dreamshop.dreamshop.request.AddProductRequest;
import com.dreamshop.dreamshop.request.ProductUpdateRequest;
import com.dreamshop.dreamshop.response.ApiResponse;
import com.dreamshop.dreamshop.service.product.IProductService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {

  private final IProductService productService;

  @GetMapping("/all")
  public ResponseEntity<ApiResponse> getAllProducts() {
    List<Product> products = productService.getAllProducts();
    List<ProductDto> productDtos = productService.getConvertedProducts(products);
    return ResponseEntity.ok(new ApiResponse("success", productDtos));
  }

  @GetMapping("/id/{id}/product")
  public ResponseEntity<ApiResponse> getProductById(@PathVariable Long id) {
    Product product = productService.getProductById(id);
    ProductDto productDto = productService.convertToDto(product);
    return ResponseEntity.ok(new ApiResponse("success", productDto));
  }

  @PostMapping("/add")
  public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product) {
    Product addProduct = productService.addProduct(product);
    return ResponseEntity.ok(new ApiResponse("add product success", addProduct));
  }

  @PutMapping("/{id}/update")
  public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductUpdateRequest product, @PathVariable Long id) {
    Product updateProduct = productService.updateProduct(product, id);
    return ResponseEntity.ok(new ApiResponse("update product success", updateProduct));
  }

  @DeleteMapping("/{id}/delete")
  public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id) {
    productService.deleteProductById(id);
    return ResponseEntity.ok(new ApiResponse("delete product success", null));
  }

  @GetMapping("{category}/all/products")
  public ResponseEntity<ApiResponse> getProductsByCategory(@PathVariable String category) {
    List<Product> products = productService.getProductsByCategory(category);
    List<ProductDto> productDtos = productService.getConvertedProducts(products);
    return ResponseEntity.ok(new ApiResponse("found", productDtos));
  }

  @GetMapping("by/brand-and-name")
  public ResponseEntity<ApiResponse> getProductByBrandAndName(@RequestParam String brand, @RequestParam String name) {
    List<Product> products = productService.getProductsByBrandAndName(brand, name);
    if (products.isEmpty()) {
      return ResponseEntity.status(404).body(new ApiResponse("no products found", null));
    }
    List<ProductDto> productDtos = productService.getConvertedProducts(products);
    return ResponseEntity.ok(new ApiResponse("success", productDtos));
  }

  @GetMapping("by/category-and-brand")
  public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(@RequestParam String category,
      @RequestParam String brand) {
    List<Product> products = productService.getProductsByCategoryAndBrand(category, brand);
    if (products.isEmpty()) {
      return ResponseEntity.status(404).body(new ApiResponse("no products found", null));
    }
    return ResponseEntity.ok(new ApiResponse("success", products));
  }

  @GetMapping("/by-brand")
  public ResponseEntity<ApiResponse> getProductsByBrand(@RequestParam String brand) {
    List<Product> products = productService.getProductsByBrand(brand);
    if (products.isEmpty()) {
      return ResponseEntity.status(404).body(new ApiResponse("no products found", null));
    }
    List<ProductDto> productDtos = productService.getConvertedProducts(products);
    return ResponseEntity.ok(new ApiResponse("success", productDtos));
  }

  @GetMapping("{name}/product")
  public ResponseEntity<ApiResponse> getProductByName(@PathVariable String name) {
    List<Product> products = productService.getProductsByName(name);
    if (products.isEmpty()) {
      return ResponseEntity.status(404).body(new ApiResponse("no products found", null));
    }
    List<ProductDto> productDtos = productService.getConvertedProducts(products);
    return ResponseEntity.ok(new ApiResponse("success", productDtos));
  }

  @GetMapping("/count/by-brand/and-name")
  public ResponseEntity<ApiResponse> countProductsByBrandAndName(@RequestParam String brand,
      @RequestParam String name) {
    var productCount = productService.countProductsByBrandAndName(brand, name);
    return ResponseEntity.ok(new ApiResponse("Product count", productCount));
  }
}
