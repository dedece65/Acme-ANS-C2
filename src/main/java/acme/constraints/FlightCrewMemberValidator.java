
package acme.constraints;

import javax.validation.ConstraintValidatorContext;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.SpringHelper;
import acme.client.helpers.StringHelper;
import acme.realms.FlightCrewMember;
import acme.realms.FlightCrewMemberRepository;

@Validator
public class FlightCrewMemberValidator extends AbstractValidator<ValidFlightCrewMember, FlightCrewMember> {

	@Override
	protected void initialise(final ValidFlightCrewMember annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final FlightCrewMember crewMember, final ConstraintValidatorContext context) {

		assert context != null;

		boolean result;

		if (crewMember == null)
			super.state(context, false, "*", "javax.validation.constraints.NotNull.message");
		else {
			{
				if (StringHelper.isBlank(crewMember.getCode()))
					super.state(context, false, "identifier", "java.validation.crewMember.identifier.identifier-couldnt-be-blank");
			}
			{
				FlightCrewMemberRepository repository;
				repository = SpringHelper.getBean(FlightCrewMemberRepository.class);
				boolean repeatedEmployeeCode = repository.findByEmployeeCode(crewMember.getCode(), crewMember.getId()).isEmpty();
				super.state(context, repeatedEmployeeCode, "identifier", "java.validation.crewMember.repeatedEmployeeCode.message");
			}
		}

		result = !super.hasErrors(context);

		return result;
	}
}
