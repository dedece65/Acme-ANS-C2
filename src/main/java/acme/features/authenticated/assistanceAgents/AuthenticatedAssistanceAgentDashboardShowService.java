
package acme.features.authenticated.assistanceAgents;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.principals.Principal;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.forms.AssistanceAgentDashboard;
import acme.realms.AssistanceAgent;

@GuiService
public class AuthenticatedAssistanceAgentDashboardShowService extends AbstractGuiService<AssistanceAgent, AssistanceAgentDashboard> {

	@Autowired
	private AuthenticatedAssistanceAgentDashboardRepository repository;


	@Override
	public void authorise() {
		boolean status = super.getRequest().getPrincipal().hasRealmOfType(AssistanceAgent.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		AssistanceAgentDashboard dashboard;
		int assistanceAgentId;
		Principal principal;

		// Obtención del ID del agente de asistencia
		principal = super.getRequest().getPrincipal();
		assistanceAgentId = principal.getActiveRealm().getId();

		// Obtención de las métricas de las reclamaciones
		long totalClaims = this.repository.countClaimsByAssistanceAgentId(assistanceAgentId);
		long resolvedClaims = this.repository.countResolvedClaimsByAssistanceAgentId(assistanceAgentId);
		long rejectedClaims = this.repository.countRejectedClaimsByAssistanceAgentId(assistanceAgentId);

		// Cálculo de las proporciones de reclamaciones resueltas y rechazadas
		double ratioResolvedClaims = totalClaims > 0 ? (double) resolvedClaims / totalClaims : 0;
		double ratioRejectedClaims = totalClaims > 0 ? (double) rejectedClaims / totalClaims : 0;

		// Obtención de los tres meses con más reclamaciones
		List<String> topThreeMonths = this.repository.findTopThreeMonthsWithMostClaims(assistanceAgentId);

		// Obtención de las métricas de los TrackingLogs
		double avgClaimLogs = this.repository.averageClaimLogsByAssistanceAgentId(assistanceAgentId);
		double devClaimLogs = this.repository.deviationClaimLogsByAssistanceAgentId(assistanceAgentId);
		int minClaimLogs = this.repository.minClaimLogsByAssistanceAgentId(assistanceAgentId);
		int maxClaimLogs = this.repository.maxClaimLogsByAssistanceAgentId(assistanceAgentId);

		// Obtención de las métricas de las reclamaciones atendidas
		double avgClaimsAssisted = this.repository.averageClaimsAssistedByAssistanceAgentId(assistanceAgentId);
		double devClaimsAssisted = this.repository.deviationClaimsAssistedByAssistanceAgentId(assistanceAgentId);
		int minClaimsAssisted = this.repository.minClaimsAssistedByAssistanceAgentId(assistanceAgentId);
		int maxClaimsAssisted = this.repository.maxClaimsAssistedByAssistanceAgentId(assistanceAgentId);

		// Crear el dashboard con todos los datos obtenidos
		dashboard = new AssistanceAgentDashboard();
		dashboard.setRatioResolvedClaims(ratioResolvedClaims);
		dashboard.setRatioRejectedClaims(ratioRejectedClaims);
		dashboard.setTopThreeMonthsWithMostClaims(topThreeMonths);
		dashboard.setAverageClaimLogs(avgClaimLogs);
		dashboard.setDeviationClaimLogs(devClaimLogs);
		dashboard.setMinClaimLogs(minClaimLogs);
		dashboard.setMaxClaimLogs(maxClaimLogs);
		dashboard.setAverageClaimsAssisted(avgClaimsAssisted);
		dashboard.setDeviationClaimsAssisted(devClaimsAssisted);
		dashboard.setMinClaimsAssisted(minClaimsAssisted);
		dashboard.setMaxClaimsAssisted(maxClaimsAssisted);

		// Añadir el dashboard al buffer de respuesta
		super.getBuffer().addData(dashboard);
	}

	@Override
	public void unbind(final AssistanceAgentDashboard dashboard) {
		// Unbind the dashboard data into a dataset for the response
		Dataset dataset = super.unbindObject(dashboard, "ratioResolvedClaims", "ratioRejectedClaims", "topThreeMonthsWithMostClaims", "averageClaimLogs", "deviationClaimLogs", "minClaimLogs", "maxClaimLogs", "averageClaimsAssisted",
			"deviationClaimsAssisted", "minClaimsAssisted", "maxClaimsAssisted");

		// Añadir los datos al response
		super.getResponse().addData(dataset);
	}
}
