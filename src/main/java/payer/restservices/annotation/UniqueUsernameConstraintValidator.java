package payer.restservices.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUsernameConstraintValidator implements ConstraintValidator<UniqueUsername, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		 boolean result=value.contains("9912247388");  
	        return result;  
	}

}
