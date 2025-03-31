
package acme.constraints;

import javax.validation.ConstraintValidatorContext;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.SpringHelper;
import acme.client.helpers.StringHelper;
import acme.realms.Manager;
import acme.realms.ManagerRepository;

@Validator
public class ManagerValidator extends AbstractValidator<ValidManager, Manager> {

	@Override
	protected void initialise(final ValidManager annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Manager manager, final ConstraintValidatorContext context) {

		assert context != null;

		boolean result;

		if (manager == null)
			super.state(context, false, "*", "javax.validation.constraints.NotNull.message");
		else {
			{
				if (!StringHelper.isBlank(manager.getIdNumber())) {
					String initials = "";
					String name = manager.getUserAccount().getIdentity().getName();
					String surname = manager.getUserAccount().getIdentity().getSurname();

					initials += name.charAt(0);
					initials += surname.charAt(0);

					boolean validIdentifier = StringHelper.startsWith(manager.getIdNumber(), initials, true);

					super.state(context, validIdentifier, "identifier", "acme.validation.manager.validIdentifier.invalid-identifier");
				}
			}
			{
				ManagerRepository repository;
				repository = SpringHelper.getBean(ManagerRepository.class);

				Boolean repeatedIdentifier = repository.findByIdentifier(manager.getIdNumber(), manager.getId()).isEmpty();

				super.state(context, repeatedIdentifier, "identifier", "acme.validation.manager.repeatedIdentifier.identifier.message");

			}
		}

		result = !super.hasErrors(context);

		return result;
	}

}
