package fabian.mera.inyecciondepen.crud_api_restful.controllers;

import fabian.mera.inyecciondepen.crud_api_restful.entities.Product;
import fabian.mera.inyecciondepen.crud_api_restful.services.ProductService;
import fabian.mera.inyecciondepen.crud_api_restful.validation.ProductValidation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final ProductValidation productValidation;

    public ProductController(ProductService productService, ProductValidation productValidation) {
        this.productService = productService;
        this.productValidation = productValidation;
    }

    @GetMapping
    public List<Product> listAllProducts() {
        return productService.listAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> viewProduct(@PathVariable Long id) {
        Optional<Product> productOp = productService.getProductById(id);
        if (productOp.isPresent()) {
            return ResponseEntity.ok(productOp.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    //@Valid va en el RequestBody
    //BindingResult va despues del RequestBody
    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody Product product, BindingResult bindingResult) {
        productValidation.validate(product, bindingResult);
        if (bindingResult.hasErrors()) {
            return validation(bindingResult);
        }
        Product productDB = productService.saveProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body("Producto creado con éxito: " + productDB);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<?> updateProduct(@RequestBody Map<String, Object> updates, @PathVariable Long id) {
        Optional<Product> updateProduct = productService.updateProduct(updates, id);
        if (!updateProduct.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Producto actualizado con exito: " + updateProduct.orElseThrow());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        Optional<Product> productDB = productService.deleteProduct(id);
        if (productDB.isPresent()) {
            return ResponseEntity.ok("Producto eliminado con exito: " + productDB.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    //Validacion de errores con BindingResult
    public ResponseEntity<?> validation(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        bindingResult.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
