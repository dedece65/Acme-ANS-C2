
package acme.constraints;

import javax.validation.ConstraintValidatorContext;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;

@Validator
public class EmployeeCodeValidator extends AbstractValidator<ValidEmployeeCode, String> {

	@Override
	public boolean isValid(final String value, final ConstraintValidatorContext context) {
		if (value == null || value.isBlank())
			return true; // Si el valor es vacío, se considera válido

		// Crear el patrón dinámicamente con el número de dígitos configurado
		String pattern = "^[A-Z]{2,3}\\d{6}$";

		// Validar que el valor cumple con el patrón especificado
		if (!value.matches(pattern))
			return false; // Si no cumple con el patrón, se considera no válido

		// Si el valor cumple con el patrón, es válido
		return true;
	}
}
