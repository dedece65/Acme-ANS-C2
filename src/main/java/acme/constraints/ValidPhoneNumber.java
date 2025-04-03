
package acme.constraints;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Pattern;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@ReportAsSingleViolation

@Pattern(regexp = "^\\+?\\d{6,15}$")
public @interface ValidPhoneNumber {

	// Standard validation properties -----------------------------------------

	String message() default "The phone number must contain between 6 and 15 digits.";

	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};

}
