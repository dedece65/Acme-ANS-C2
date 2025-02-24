
package acme.entities.customer;

import javax.persistence.Entity;

import acme.client.components.basis.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Customer extends AbstractEntity {

	// Serialisation version --------------------------------------------------

	private static final long serialVersionUID = 1L;

	// Attributes -------------------------------------------------------------

}
