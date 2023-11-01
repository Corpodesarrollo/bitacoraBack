package linktic.lookfeel.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TotalEstudiantesPorInstitucionDTO {
	public int idLocalidad;
	public String localidad;
	public String nombreInstitucion;
	public int totalEstudiantes;
}
