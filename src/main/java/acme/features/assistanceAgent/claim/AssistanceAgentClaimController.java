
package acme.features.assistanceAgent.claim;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.controllers.AbstractGuiController;
import acme.client.controllers.GuiController;
import acme.entities.claim.Claim;
import acme.realms.AssistanceAgent;

@GuiController
public class AssistanceAgentClaimController extends AbstractGuiController<AssistanceAgent, Claim> {

	// Internal State --------------------------------------------------------------------

	@Autowired
	private AssistanceAgentClaimListService				listService;

	@Autowired
	private AssistanceAgentClaimListUndergoingService	listUndergoingService;

	@Autowired
	private AssistanceAgentClaimShowService				showService;
	@Autowired
	private AssistanceAgentClaimCreateService			createService;

	@Autowired
	private AssistanceAgentClaimDeleteService			deleteService;

	@Autowired
	private AssistanceAgentClaimUpdateService			updateService;

	@Autowired
	private AssistanceAgentClaimPublishService			publishService;

	// Constructors  ----------------------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("delete", this.deleteService);
		super.addBasicCommand("update", this.updateService);

		super.addCustomCommand("list-mine", "list", this.listService);
		super.addCustomCommand("list-undergoing", "list", this.listUndergoingService);
		super.addCustomCommand("publish", "update", this.publishService);
	}

}
