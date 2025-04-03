/*
 * AuthenticatedProviderCreateService.java
 *
 * Copyright (C) 2012-2025 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.authenticated.assistanceAgents;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.principals.Authenticated;
import acme.client.components.principals.UserAccount;
import acme.client.helpers.PrincipalHelper;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.realms.AssistanceAgent;

@GuiService
public class AuthenticatedAssistanceAgentCreateService extends AbstractGuiService<Authenticated, AssistanceAgent> {

	@Autowired
	private AuthenticatedAssistanceAgentRepository repository;


	@Override
	public void authorise() {
		boolean status;
		status = !super.getRequest().getPrincipal().hasRealmOfType(AssistanceAgent.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		AssistanceAgent object;
		int userAccountId;
		UserAccount userAccount;

		userAccountId = super.getRequest().getPrincipal().getAccountId();
		userAccount = this.repository.findUserAccountById(userAccountId);

		object = new AssistanceAgent();
		object.setUserAccount(userAccount);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final AssistanceAgent object) {
		assert object != null;
		super.bindObject(object, "employeeCode", "spokenLanguages", "airline", "employmentStartDate", "bio", "salary", "photoUrl");
	}

	@Override
	public void validate(final AssistanceAgent object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("employeeCode") && object.getEmployeeCode() != null) {
			boolean isValidEmployeeCode = object.getEmployeeCode().matches("^[A-Z]{2,3}\\d{6}$");
			super.state(isValidEmployeeCode, "employeeCode", "assistanceAgent.form.error.invalidEmployeeCode", object);
		}

		if (!super.getBuffer().getErrors().hasErrors("employmentStartDate") && object.getEmploymentStartDate() != null) {
			boolean isPastDate = object.getEmploymentStartDate().before(new Date());
			super.state(isPastDate, "employmentStartDate", "assistanceAgent.form.error.invalidEmploymentStartDate", object);
		}

		if (!super.getBuffer().getErrors().hasErrors("salary") && object.getSalary() != null) {
			boolean isValidSalary = Double.compare(object.getSalary().getAmount(), 0.0) > 0;
			super.state(isValidSalary, "salary", "assistanceAgent.form.error.invalidSalary", object);
		}

		if (!super.getBuffer().getErrors().hasErrors("photoUrl") && object.getPhotoUrl() != null) {
			boolean isValidUrl = object.getPhotoUrl().matches("^(http|https)://.*$");
			super.state(isValidUrl, "photoUrl", "assistanceAgent.form.error.invalidPhotoUrl", object);
		}
	}

	@Override
	public void perform(final AssistanceAgent object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final AssistanceAgent object) {
		Dataset dataset;
		dataset = super.unbindObject(object, "employeeCode", "spokenLanguages", "airline", "employmentStartDate", "bio", "salary", "photoUrl");
		super.getResponse().addData(dataset);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals("POST"))
			PrincipalHelper.handleUpdate();
	}
}
