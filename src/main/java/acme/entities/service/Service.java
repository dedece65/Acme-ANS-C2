
package acme.entities.service;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.mappings.Automapped;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidNumber;
import acme.client.components.validation.ValidString;
import acme.client.components.validation.ValidUrl;
import acme.constraints.ValidPromotionCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Service extends AbstractEntity {

	// Serialisation version ------------------------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -----------------------------------------------------------------------

	@Mandatory
	@ValidString(max = 50)
	@Automapped
	private String				name;

	@Mandatory
	@ValidUrl
	@Automapped
	private String				picture;

	@Optional
	@ValidPromotionCode(checkYear = true) // Código de promoción (opcional, formato "XXXX-YY" donde YY es el año actual)
	@Column(unique = true)
	private String				promotionCode;

	@Optional
	@ValidNumber(min = 0.00, max = 1000000.00)
	@Automapped
	private BigDecimal			discountedMoney;

	@Mandatory
	@ValidNumber(min = 0, max = 48)  // Se cambió para representar horas correctamente, 48h = 2 días, suficiente para cualquier tipo de actividad.
	@Automapped
	private Double				averageDwellTime;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
