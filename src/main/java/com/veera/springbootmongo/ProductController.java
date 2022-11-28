package com.veera.springbootmongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/products")
    public void addProducts(@RequestBody final List<Product> products){
        productRepository.saveAll(products);
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> findProducts(){
        return ResponseEntity.status(201).body(productRepository.findAll());
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity findProduct(@PathVariable final String productId){
        Optional<Product> product = productRepository.findById(productId); //orElseGet(Product::new);
        return product.isPresent() ? ResponseEntity.ok(product) : ResponseEntity.ok("Product id :"+productId +" not found");
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity deleteProduct(@PathVariable final String productId){
        Optional<Product> product = productRepository.findById(productId); //orElseGet(Product::new);
        if(product.isPresent()){
            productRepository.deleteById(productId);
           return ResponseEntity.ok("Success");
        }else {
            return  ResponseEntity.ok("Product id :"+productId +" not found");
        }
    }
    @PostMapping("/products/product")
    public ResponseEntity updateProduct(@RequestBody final Product product){
        String productId = product.getId();
        Optional<Product> productDetails = productRepository.findById(productId); //orElseGet(Product::new);
        if(productDetails.isPresent()){
            productRepository.deleteById(productId);
            productRepository.save(product);
            return ResponseEntity.ok("Updated product Success");
        }else {
            return  ResponseEntity.ok("Product id :"+productId +" not found");
        }
    }

}
