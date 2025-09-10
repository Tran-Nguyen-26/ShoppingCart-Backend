package com.dreamshop.dreamshop.request;

import java.math.BigDecimal;

import com.dreamshop.dreamshop.model.Category;
import lombok.Data;

@Data
public class AddProductRequest {
  private Long id;
  private String name;
  private String description;
  private String brand;
  private BigDecimal price;
  private int inventory;
  private Category category;
}
