package be.normegil.librarium.validation.constraint;

import be.normegil.librarium.validation.constraint.HttpUrlValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({
        ElementType.FIELD,
        ElementType.PARAMETER
})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = HttpUrlValidator.class)
@NotNull
public @interface HttpUrl {

	String message() default
			"Given argument is not a valid http URL";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
