
package acme.constraints;

import javax.validation.ConstraintValidatorContext;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.SpringHelper;
import acme.client.helpers.StringHelper;
import acme.features.authenticated.assistanceAgents.AuthenticatedAssistanceAgentRepository;
import acme.realms.AssistanceAgent;

@Validator
public class AssistanceAgentValidator extends AbstractValidator<ValidEmployeeCode, AssistanceAgent> {

	@Override
	protected void initialise(final ValidEmployeeCode annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final AssistanceAgent agent, final ConstraintValidatorContext context) {
		assert context != null;

		boolean result;

		if (agent == null)
			super.state(context, false, "*", "javax.validation.constraints.NotNull.message");
		else {
			// Validar que el employeeCode no esté vacío y siga un formato específico
			if (!StringHelper.isBlank(agent.getEmployeeCode())) {
				String initials = "";
				String name = agent.getUserAccount().getIdentity().getName();
				String surname = agent.getUserAccount().getIdentity().getSurname();

				initials += name.charAt(0);
				initials += surname.charAt(0);

				boolean validIdentifier = StringHelper.startsWith(agent.getEmployeeCode(), initials, true);

				super.state(context, validIdentifier, "employeeCode", "acme.validation.assistanceAgent.validIdentifier.invalid-identifier");
			}

			// Validar que el employeeCode sea único en la base de datos
			AuthenticatedAssistanceAgentRepository repository = SpringHelper.getBean(AuthenticatedAssistanceAgentRepository.class);
			Boolean repeatedIdentifier = repository.findByEmployeeCode(agent.getEmployeeCode()).isEmpty();

			super.state(context, repeatedIdentifier, "employeeCode", "acme.validation.assistanceAgent.repeatedIdentifier.identifier.message");
		}

		result = !super.hasErrors(context);
		return result;
	}
}
