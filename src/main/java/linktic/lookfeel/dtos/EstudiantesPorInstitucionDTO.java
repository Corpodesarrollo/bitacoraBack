package linktic.lookfeel.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EstudiantesPorInstitucionDTO {
	public String grupo;
	public String grado;
	public String idJornada;
	public String jornadaNombre;
	public String estnombre1;
	public String estnombre2;
	public String estapellido1;
	public String estapellido2;
	public String idSede;
	public String nombreSede;
}
