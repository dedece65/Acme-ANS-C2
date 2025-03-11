
package acme.constraints;

import javax.validation.ConstraintValidatorContext;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.StringHelper;

@Validator
public class EmployeeCodeValidator extends AbstractValidator<ValidEmployeeCode, String> {

	@Override
	protected void initialise(final ValidEmployeeCode annotation) {

	}

	@Override
	public boolean isValid(final String value, final ConstraintValidatorContext context) {
		if (StringHelper.isBlank(value))
			return true; // Si el valor es vacío, lo validamos como opcional

		// Verificamos que el patrón del código de empleado sea válido
		String pattern = "^[A-Z]{2,3}\\d{6}$"; // Iniciales seguidas de 6 números
		if (!value.matches(pattern)) {
			this.state(context, false, "employeeCode", "acme.validation.employee-code.pattern");
			return false;
		}

		// Extraemos las iniciales del trabajador
		String employeeName = this.getEmployeeName();
		String initials = this.getInitials(employeeName);

		// Verificamos que las iniciales coincidan con las del código de empleado
		String employeeCodeInitials = value.substring(0, Math.min(3, value.length() - 6)); // Obtenemos las iniciales del código de empleado

		boolean isValid = initials.equals(employeeCodeInitials);

		if (!isValid)
			this.state(context, false, "employeeCode", "acme.validation.employee-code.initials");

		return isValid;
	}

	// Método para extraer las iniciales del nombre del empleado
	private String getInitials(final String name) {
		if (name == null || name.trim().isEmpty())
			return "";

		String[] parts = name.split(" ");
		StringBuilder initials = new StringBuilder();

		for (String part : parts)
			if (!part.isEmpty())
				initials.append(part.charAt(0)); // Tomamos la primera letra de cada parte del nombre

		return initials.toString().toUpperCase(); // Devolvemos las iniciales en mayúsculas
	}

	// Método simulado para obtener el nombre del empleado
	private String getEmployeeName() {
		return "John Doe";
	}
}
