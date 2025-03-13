
package acme.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = EmployeeCodeValidator.class)
@ReportAsSingleViolation
public @interface ValidEmployeeCode {

	String message() default "{acme.validation.employee-code.message}";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};

	// Atributo para definir el número de dígitos que debe tener el código
	int value() default 6;  // Valor por defecto es 6
}
