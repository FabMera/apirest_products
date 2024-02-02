package fabian.mera.inyecciondepen.crud_api_restful.services;

import fabian.mera.inyecciondepen.crud_api_restful.entities.Product;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProductService {

    List<Product> listAll();

    Optional<Product> getProductById(Long id);

    Product saveProduct(Product product);

    Optional<Product> updateProduct(Map<String, Object> updates, Long id);

    Optional<Product> deleteProduct(Long id);

}
