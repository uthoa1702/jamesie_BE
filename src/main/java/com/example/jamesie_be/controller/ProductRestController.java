package com.example.jamesie_be.controller;

import com.example.jamesie_be.model.DTO.IImageDTO;
import com.example.jamesie_be.model.DTO.ImageDTO;
import com.example.jamesie_be.model.DTO.ProductDTO;
import com.example.jamesie_be.model.Images;
import com.example.jamesie_be.model.ProductColor;
import com.example.jamesie_be.model.ProductSize;
import com.example.jamesie_be.model.ProductType;
import com.example.jamesie_be.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/products")
public  class ProductRestController {
    @Autowired
    IProductService iProductService;


    //    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    @GetMapping("")
    public ResponseEntity<Page<ProductDTO>> getList(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                    @RequestParam("sortBy") String sortBy,
                                                    @RequestParam("price") String priceFromTo,
                                                    @RequestParam("color") String color,
                                                    @RequestParam("type") String type,
                                                    @RequestParam("productName") String productName) {
        Pageable pageable;
        switch (sortBy) {

            case "lowToHigh": {
                pageable = PageRequest.of(page, 8, Sort.by(Sort.Order.asc("price")));
                break;
            }
            case "highToLow": {
                pageable = PageRequest.of(page, 8, Sort.by(Sort.Order.desc("price")));
                break;
            }

            default: {
                pageable = PageRequest.of(page, 8);
            }

        }

        double from = 0;
        double to = 0;

        switch (priceFromTo) {
            case "0-50": {
                from = 0;
                to = 50;
                break;
            }
            case "50-100": {
                from = 50;
                to = 100;
                break;
            }

            case "100": {
                from = 100;
                to = iProductService.getMaxPrice();
                break;
            }
            default:{
                from = 0;
                to = iProductService.getMaxPrice();
                break;
            }

        }

        Page<ProductDTO> productDTOPage = iProductService.getProductsPage(pageable, sortBy, from, to, color, type, productName);
        System.out.println("max " + iProductService.getMaxPrice());

        return new ResponseEntity<>(productDTOPage, HttpStatus.OK);


    }

    @PostMapping("/images")
    public ResponseEntity<List<ImageDTO>> getImages(@RequestBody String productName ){
        List<IImageDTO> imagesList = iProductService.getImages(productName);
        List<ImageDTO> imageDTOList = new ArrayList<>();
        for (int i = 0; i < imagesList.size(); i++) {
            ImageDTO imageDTO = new ImageDTO();
            imageDTO.setUrl(imagesList.get(i).getUrl());
            imageDTOList.add(imageDTO);
        }
        return new ResponseEntity<>(imageDTOList, HttpStatus.OK);
    }



    @GetMapping("/getSize")
    public ResponseEntity<List<ProductSize>> getSize(@RequestParam("productName")String productName) {
        List<ProductSize> productSizes = iProductService.getSize(productName);

        return new ResponseEntity<>(productSizes, HttpStatus.OK);
    }

    @GetMapping("/getType")
    public ResponseEntity<List<ProductType>> getType() {
        List<ProductType> productType = iProductService.getType();
        return new ResponseEntity<>(productType, HttpStatus.OK);
    }

}
