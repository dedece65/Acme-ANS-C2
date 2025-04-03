
package acme.features.authenticated.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.principals.Authenticated;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.service.Service;

@GuiService
public class AuthenticatedServiceShowService extends AbstractGuiService<Authenticated, Service> {

	// Internal State ------------------------------------------------------------
	@Autowired
	private AuthenticatedServiceRepository repository;

	// AbstractGuiService --------------------------------------------------------


	@Override
	public void authorise() {
		// Verificar si el usuario está autenticado
		boolean isAuthenticated = super.getRequest().getPrincipal().isAuthenticated();
		super.getResponse().setAuthorised(isAuthenticated);
	}

	@Override
	public void load() {
		Service service;
		List<Service> services;

		// Obtener un servicio aleatorio
		services = this.repository.findRandomService();
		service = services.isEmpty() ? null : services.get(0);

		if (service != null && service.getPicture() != null)
			super.getBuffer().addData("picture", service.getPicture());
	}

	@Override
	public void unbind(final Service service) {
		Dataset dataset;

		if (service != null) {
			// Crear dataset con solo la URL de la imagen
			dataset = super.unbindObject(service, "picture");
			super.getResponse().addData(dataset);
		}
	}
}
