
package acme.constraints;

import javax.validation.ConstraintValidatorContext;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.StringHelper;
import acme.entities.trackingLog.IndicatorType;
import acme.entities.trackingLog.TrackingLog;

@Validator
public class TrackingLogValidator extends AbstractValidator<ValidTrackingLog, TrackingLog> {

	@Override
	protected void initialise(final ValidTrackingLog annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final TrackingLog trackingLog, final ConstraintValidatorContext context) {
		assert context != null;

		boolean result;

		if (trackingLog == null)
			super.state(context, false, "*", "javax.validation.constraints.NotNull.message");
		else {
			// Validar que si indicator es ACCEPTED o REJECTED, resolutionReason no sea nulo o vacío
			boolean requiresResolutionReason = trackingLog.getIndicator() == IndicatorType.ACCEPTED || trackingLog.getIndicator() == IndicatorType.REJECTED;
			boolean hasResolutionReason = !StringHelper.isBlank(trackingLog.getResolutionReason());

			if (requiresResolutionReason)
				super.state(context, hasResolutionReason, "resolutionReason", "acme.validation.trackingLog.resolutionReason.required");
		}

		result = !super.hasErrors(context);
		return result;
	}
}
