
package acme.constraints;

import javax.validation.ConstraintValidatorContext;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.StringHelper;
import acme.entities.claim.Claim;

@Validator
public class ClaimValidator extends AbstractValidator<ValidClaim, Claim> {

	@Override
	protected void initialise(final ValidClaim annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Claim claim, final ConstraintValidatorContext context) {
		assert context != null;

		boolean result;

		if (claim == null)
			super.state(context, false, "*", "javax.validation.constraints.NotNull.message");
		else {
			// Validar que el passengerEmail no sea nulo o vacío
			boolean validEmail = !StringHelper.isBlank(claim.getPassengerEmail());
			super.state(context, validEmail, "passengerEmail", "acme.validation.claim.passengerEmail.required");
		}

		result = !super.hasErrors(context);
		return result;
	}
}
