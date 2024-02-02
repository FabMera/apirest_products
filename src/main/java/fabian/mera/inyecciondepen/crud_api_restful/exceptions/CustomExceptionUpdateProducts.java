package fabian.mera.inyecciondepen.crud_api_restful.exceptions;

import java.util.Map;

public class CustomExceptionUpdateProducts extends RuntimeException{
    private Map<String, String> errors;

    public CustomExceptionUpdateProducts(Map<String, String> errors) {
        super("Validation error");
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
