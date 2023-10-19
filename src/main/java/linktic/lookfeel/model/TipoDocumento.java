package linktic.lookfeel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class TipoDocumento {
	
	@Id
	@Column(name = "ID")
	public long id;
	
	@Column(name = "NOMBRE")
	public String nombre;
	

}
