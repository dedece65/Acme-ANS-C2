
package acme.constraints;

import javax.validation.ConstraintValidatorContext;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;

@Validator
public class PercentageValidator extends AbstractValidator<ValidPercentage, String> {

	private static final String PATTERN = "^\\d+(\\.\\d+)?%$";

	/*
	 * 
	 * ^ Inicio de la cadena
	 * \\d+ Uno o mas digitos enteros
	 * (\\.\\d+)? Opcionalmente, un punto seguido de uno o mas digitos
	 * %$ el simbolo % debe ser el final de la cadena
	 * 
	 */


	@Override
	public boolean isValid(final String value, final ConstraintValidatorContext context) {
		if (value == null || value.isBlank())
			return true; // No validar si es opcional y está vacío

		// Primero validamos que cumpla con el patrón general de porcentaje
		if (!value.matches(PercentageValidator.PATTERN))
			return false;

		// Ahora extraemos el valor numérico antes del símbolo '%' y verificamos que esté entre 0 y 100
		try {
			// Eliminar el símbolo de porcentaje y convertir a un número
			String numberPart = value.substring(0, value.length() - 1);
			double percentage = Double.parseDouble(numberPart);

			// Verificar que el porcentaje esté entre 0 y 100
			return percentage >= 0 && percentage <= 100;
		} catch (NumberFormatException e) {
			return false; // Si hay un error al parsear el número, el valor no es válido
		}
	}
}
