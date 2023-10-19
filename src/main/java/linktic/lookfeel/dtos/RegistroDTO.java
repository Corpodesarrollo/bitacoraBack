package linktic.lookfeel.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistroDTO {
	
	private PerfilDTO perfil;
	private InstitucionDTO colegio;
	private SedeDTO sede;
	private JornadaDTO jornada;
	private DependenciaDTO dependencia;
	private LocalidadDTO localidad;
}
