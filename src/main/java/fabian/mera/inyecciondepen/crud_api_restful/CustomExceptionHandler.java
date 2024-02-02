package fabian.mera.inyecciondepen.crud_api_restful;

import fabian.mera.inyecciondepen.crud_api_restful.exceptions.CustomExceptionUpdateProducts;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomExceptionUpdateProducts.class)
    public ResponseEntity<?> handleCustomExceptionUpdateProducts(CustomExceptionUpdateProducts e) {
        return ResponseEntity.badRequest().body(e.getErrors());
    }
}
