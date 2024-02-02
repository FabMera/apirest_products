package fabian.mera.inyecciondepen.crud_api_restful.services;

import fabian.mera.inyecciondepen.crud_api_restful.entities.Product;
import fabian.mera.inyecciondepen.crud_api_restful.exceptions.CustomExceptionUpdateProducts;
import fabian.mera.inyecciondepen.crud_api_restful.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> listAll() {
        return productRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    @Transactional
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public Optional<Product> updateProduct(Map<String, Object> updates, Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()) {
            return Optional.empty();
        }
        Product product = productOptional.get();
        Map<String, String> errors = new HashMap<>();
        updates.forEach((k, v) -> {
            switch (k) {
                case "name":
                    if (v != null && v.toString().length() >= 3 && v.toString().length() <= 30) {
                        product.setName(v.toString());
                    } else {
                        errors.put(k, "El campo " + k + " debe tener entre 3 y 30 caracteres");
                    }
                    break;
                case "price":
                    try {
                        Double price = Double.valueOf(v.toString());
                        if (price >= 0) {
                            product.setPrice(price);
                        } else {
                            errors.put(k, "El campo " + k + " debe ser mayor o igual a 0");
                        }
                    } catch (NumberFormatException e) {
                        errors.put(k, "El campo " + k + " debe ser un número");
                    }
                    break;
            }
        });
        if (!errors.isEmpty()) {
            throw new CustomExceptionUpdateProducts(errors);
        }
        return Optional.of(productRepository.save(product));
    }


    @Override
    @Transactional
    public Optional<Product> deleteProduct(Long id) {
        Optional<Product> productDB = productRepository.findById(id);
        productDB.ifPresent(productRepository::delete);
        return productDB;
    }

}
