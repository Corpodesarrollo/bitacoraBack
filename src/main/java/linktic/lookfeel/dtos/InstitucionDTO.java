package linktic.lookfeel.dtos;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InstitucionDTO {
	
	private Long id;
	private String nombre;
	private FotoDTO foto_escudo;
	private LocalidadDTO localidad;

}
