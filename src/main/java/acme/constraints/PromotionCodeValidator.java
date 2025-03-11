/*
 * ValidPromotionCodeValidator.java
 *
 * Copyright (C) 2012-2025 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.constraints;

import java.time.Year;

import javax.validation.ConstraintValidatorContext;

import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.StringHelper;
import acme.internals.helpers.HibernateHelper;

@Validator
public class PromotionCodeValidator extends AbstractValidator<ValidPromotionCode, String> {

	// Internal state ---------------------------------------------------------

	private boolean checkYear;

	// Initialiser ------------------------------------------------------------


	@Override
	public void initialise(final ValidPromotionCode annotation) {
		assert annotation != null;
		this.checkYear = annotation.checkYear();
	}

	// AbstractValidator interface --------------------------------------------

	@Override
	public boolean isValid(final String value, final ConstraintValidatorContext context) {
		assert context != null;

		boolean result;
		HibernateConstraintValidatorContext hibernateContext;

		hibernateContext = context.unwrap(HibernateConstraintValidatorContext.class);

		if (StringHelper.isBlank(value))
			result = true; // No validar si es opcional y está vacío.
		else {
			// Validar el formato correspondiente
			final String pattern = "^[A-Z]{4}-[0-9]{2}$";
			boolean matchesPattern = value.matches(pattern);

			if (!matchesPattern) {
				HibernateHelper.replaceParameter(hibernateContext, "placeholder", "acme.validation.promotion-code.pattern");
				result = false;
			} else // Validar el año si checkYear está habilitado
			if (this.checkYear) {
				String yearPart = value.substring(value.length() - 2);
				int currentYear = Year.now().getValue() % 100;
				boolean validYear = yearPart.equals(String.format("%02d", currentYear));

				if (!validYear)
					HibernateHelper.replaceParameter(hibernateContext, "placeholder", "acme.validation.promotion-code.year");

				result = validYear;
			} else
				result = true;
		}

		return result;
	}
}
