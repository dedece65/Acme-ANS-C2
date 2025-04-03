
package acme.features.authenticated.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.principals.Authenticated;
import acme.client.controllers.AbstractGuiController;
import acme.client.controllers.GuiController;
import acme.entities.service.Service;

@GuiController
public class AuthenticatedServiceController extends AbstractGuiController<Authenticated, Service> {

	// Internal State ------------------------------------------------------------

	@Autowired
	private AuthenticatedServiceShowService showService;

	// Constructors --------------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.showService);
	}
}
