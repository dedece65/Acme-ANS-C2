
package acme.constraints;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@ReportAsSingleViolation

@NotBlank
@Pattern(regexp = "^[A-Z]{2,3}\\d{6}$")
public @interface ValidIdentifier {
	// Standard validation properties -----------------------------------------

	String message() default "The license number must start with 2 or 3 uppercase letters followed by exactly 6 digits.";

	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
