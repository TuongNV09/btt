package com.poly.test.Springboot.tutorial.controller;

import com.poly.test.Springboot.tutorial.models.Product;
import com.poly.test.Springboot.tutorial.models.ResponseObject;
import com.poly.test.Springboot.tutorial.reponsitories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Optional;

@RestController   //Báo cho jv Spring biết đây là controller
@RequestMapping(path = "/api/v1/Products") //Định tuyến gửi các request đến controller theo link
public class ProductController {

    @Autowired
    private ProductRepository repository;

    @GetMapping("")
    List<Product> getAllProduct(){
        return repository.findAll();
    }

    //Get detail Product
    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable Long id){
        Optional<Product> foundProduct = repository.findById(id);

        if (foundProduct.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("Ok", "Query Product Succesfuly", foundProduct)
            );
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("Fail", "Cannot find product with id = "+ id,"")
            );
        }
    }
    //     Insert Product
    @PostMapping("/insert")
    ResponseEntity<ResponseObject> isertProduct(@RequestBody Product newProduct){
        List<Product> foundProduct = repository.findByProductName(newProduct.getProductName().trim());
        if (foundProduct.size() > 0){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("Faile", "FoodName already taken", "")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("Ok", "Create Food succesfuly", repository.save(newProduct))
        );
    }
    // Update Product
    LocalDateTime current = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
    String Formated = current.format(formatter);
    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> updateProduct(@RequestBody Product newProduct, @PathVariable Long id){
        Product updateProduct = repository.findById(id)
                .map(product -> {
                    product.setProductName(newProduct.getProductName());
                    product.setPrice(newProduct.getPrice());
                    product.setYear(newProduct.getYear());
                    product.setUrl(newProduct.getUrl());
                    return repository.save(product);
                }).orElseGet(()->{
                   newProduct.setId(id);
                    return repository.save(newProduct);
                });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("Ok", "Update Food succesfuly", updateProduct)
        );
    }

    // Delete Product
    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deleteProduct(@PathVariable Long id){
        boolean exits = repository.existsById(id);
        if(exits){
            repository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("Ok", "Delete Product successfuly", "")
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                new ResponseObject("Fail", "Cannot find product to delete", "")
        );
    }
}
