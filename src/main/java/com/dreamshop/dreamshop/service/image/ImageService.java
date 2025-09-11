package com.dreamshop.dreamshop.service.image;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dreamshop.dreamshop.dto.ImageDto;
import com.dreamshop.dreamshop.exceptions.ResourceNotFoundException;
import com.dreamshop.dreamshop.model.Image;
import com.dreamshop.dreamshop.model.Product;
import com.dreamshop.dreamshop.repository.ImageRepository;
import com.dreamshop.dreamshop.service.product.IProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {

  private final ImageRepository imageRepository;
  private final IProductService productService;

  @Override
  public Image getImageById(Long id) {
    return imageRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("no image found with id: " + id));
  }

  @Override
  public void deleteImageById(Long id) {
    imageRepository.findById(id)
        .ifPresentOrElse(imageRepository::delete, () -> {
          throw new ResourceNotFoundException("no image found with id: " + id);
        });
  }

  @Override
  public List<ImageDto> saveImages(List<MultipartFile> files, Long productId) {
    Product product = productService.getProductById(productId);
    List<ImageDto> savedImageDto = new ArrayList<>();
    for (MultipartFile file : files) {
      try {
        Image image = new Image();
        image.setFileName(file.getOriginalFilename());
        image.setFileType(file.getContentType());
        image.setImage(new SerialBlob(file.getBytes()));
        image.setProduct(product);

        Image savedImage = imageRepository.save(image);

        String buildDownloadUrl = "/ap1/v1/images/image/download/";
        savedImage.setDownloadUrl(buildDownloadUrl + savedImage.getId());
        imageRepository.save(savedImage);

        ImageDto imageDto = new ImageDto();
        imageDto.setImageId(savedImage.getId());
        imageDto.setImageName(savedImage.getFileName());
        imageDto.setDownloadUrl(savedImage.getDownloadUrl());
        savedImageDto.add(imageDto);

      } catch (IOException | SQLException e) {
        throw new RuntimeException(e.getMessage());
      }
    }
    return savedImageDto;
  }

  @Override
  public void updateImage(MultipartFile file, Long imageId) {
    Image image = getImageById(imageId);
    try {
      image.setFileName(file.getOriginalFilename());
      image.setFileType(file.getContentType());
      image.setImage(new SerialBlob(file.getBytes()));
      imageRepository.save(image);
    } catch (IOException | SQLException e) {
      throw new RuntimeException(e.getMessage());
    }

  }

}
