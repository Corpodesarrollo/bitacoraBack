package linktic.lookfeel.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnviadoPor {
	
	private Long codigo;
	private String nombre;
	
	public EnviadoPor(Long codigo, String nombre) {

		this.codigo = codigo;
		this.nombre = nombre;
	}

	
	
	

}
