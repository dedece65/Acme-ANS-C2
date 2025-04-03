
package acme.features.authenticated.technician;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.principals.Authenticated;
import acme.client.components.principals.UserAccount;
import acme.client.helpers.PrincipalHelper;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.realms.Technician;

@GuiService
public class AuthenticatedTechnicianCreateService extends AbstractGuiService<Authenticated, Technician> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedTechnicianRepository repository;

	// AbstractService<Authenticated, Provider> ---------------------------


	@Override
	public void authorise() {
		boolean status;

		status = !super.getRequest().getPrincipal().hasRealmOfType(Technician.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Technician object;
		int userAccountId;
		UserAccount userAccount;

		userAccountId = super.getRequest().getPrincipal().getAccountId();
		userAccount = this.repository.findUserAccountById(userAccountId);

		object = new Technician();
		object.setUserAccount(userAccount);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Technician object) {
		assert object != null;

		super.bindObject(object, "licenseNumber", "phoneNumber", "specialisation", "passedHealthTest", "yearsOfExperience", "certifications");
	}

	@Override
	public void validate(final Technician object) {
		assert object != null;

		// Validar licenseNumber - Debe coincidir con el patrón "^[A-Z]{2,3}\\d{6}$"
		if (!this.getBuffer().getErrors().hasErrors("licenseNumber") && object.getLicenseNumber() != null) {
			boolean isValidLicenseNumber = object.getLicenseNumber().matches("^[A-Z]{2,3}\\d{6}$");
			super.state(isValidLicenseNumber, "licenseNumber", "technician.form.error.invalidLicenseNumber", object);
		}

		// Validar phoneNumber - Debe coincidir con el patrón "^\\+?\\d{6,15}$"
		if (!this.getBuffer().getErrors().hasErrors("phoneNumber") && object.getPhoneNumber() != null) {
			boolean isValidPhoneNumber = object.getPhoneNumber().matches("^\\+?\\d{6,15}$");
			super.state(isValidPhoneNumber, "phoneNumber", "technician.form.error.invalidPhoneNumber", object);
		}

		// Validar specialisation - No debe ser null y debe tener un máximo de 50 caracteres
		if (!this.getBuffer().getErrors().hasErrors("specialisation") && object.getSpecialisation() != null) {
			boolean isValidSpecialisation = object.getSpecialisation().length() <= 50;
			super.state(isValidSpecialisation, "specialisation", "technician.form.error.invalidSpecialisation", object);
		}

		// Validar passedHealthTest - Debe ser true o false, pero no null
		if (!this.getBuffer().getErrors().hasErrors("passedHealthTest"))
			super.state(object.getPassedHealthTest() != null, "passedHealthTest", "technician.form.error.invalidPassedHealthTest", object);

		// Validar yearsOfExperience - Debe ser un número entero y no null
		if (!this.getBuffer().getErrors().hasErrors("yearsOfExperience") && object.getYearsOfExperience() != null) {
			boolean isValidYearsOfExperience = object.getYearsOfExperience() >= 0;
			super.state(isValidYearsOfExperience, "yearsOfExperience", "technician.form.error.invalidYearsOfExperience", object);
		}

		// Validar certifications - Si existe, no debe superar los 255 caracteres
		if (!this.getBuffer().getErrors().hasErrors("certifications") && object.getCertifications() != null) {
			boolean isValidCertifications = object.getCertifications().length() <= 255;
			super.state(isValidCertifications, "certifications", "technician.form.error.invalidCertifications", object);
		}
	}

	@Override
	public void perform(final Technician object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Technician object) {
		Dataset dataset;

		dataset = super.unbindObject(object, "licenseNumber", "phoneNumber", "specialisation", "passedHealthTest", "yearsOfExperience", "certifications");

		super.getResponse().addData(dataset);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals("POST"))
			PrincipalHelper.handleUpdate();
	}

}
