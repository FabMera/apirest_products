package fabian.mera.inyecciondepen.crud_api_restful.validation;

import fabian.mera.inyecciondepen.crud_api_restful.entities.Product;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ProductValidation implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Product.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Product product = (Product) target;
        //  ValidationUtils.rejectIfEmptyOrWhitespace(errors,"name",null,"El campo nombre es obligatorio");
        if (product.getDescription() == null || product.getDescription().isBlank()) {
            errors.rejectValue("description", null, "es requerido");
        }
        if (product.getName() == null || product.getName().isBlank()) {
            errors.rejectValue("name", null, "es requerido");
        }
        if (product.getPrice() == null) {
            errors.rejectValue("price", null, "no puede ser nulo");
        } else if (product.getPrice() <= 0) {
            errors.rejectValue("price", null, "debe ser un valor numerico mayor a 0");
        }
    }
}
