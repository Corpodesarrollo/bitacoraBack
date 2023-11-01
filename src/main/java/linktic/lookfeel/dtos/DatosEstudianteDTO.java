package linktic.lookfeel.dtos;

import java.util.List;

import linktic.lookfeel.model.Constante;
import linktic.lookfeel.model.Grado;
import linktic.lookfeel.model.Grupo;
import linktic.lookfeel.model.Institucion;
import linktic.lookfeel.model.Periodos;
import linktic.lookfeel.model.Sede;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DatosEstudianteDTO {

	private TipoDocumentoDTO tipoDocumento;

	private String numeroDocumento;

	private Long codigoEstudiante;

	private String nombreEstudiante;
	
	private Long vigencia;

	private ColegioDTO institucion;

	private SedeDTO sede;

	private JornadaDTO jornada;

	private MetodologiaDTO metodologia;

	private GradoDTO grado;

	private GrupoDTO grupo;

	private List<Periodos> periodos;

}
