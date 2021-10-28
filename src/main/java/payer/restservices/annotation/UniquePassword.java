package payer.restservices.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({FIELD })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {UniquePasswordConstraintValidator.class })
public @interface UniquePassword {
	
	String message() ;

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };

}
