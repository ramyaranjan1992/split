package payer.restservices.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniquePasswordConstraintValidator implements ConstraintValidator<UniquePassword, String>  {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		boolean result=value.contains("123456");  
        return result;  
	}

}
