package fabian.mera.inyecciondepen.crud_api_restful.repository;

import fabian.mera.inyecciondepen.crud_api_restful.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
