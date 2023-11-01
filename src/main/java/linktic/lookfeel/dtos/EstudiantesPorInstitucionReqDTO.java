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
public class EstudiantesPorInstitucionReqDTO {
	public int codigoInstitucion;
	public String codigoSede;
	public String codigoJornada;
	public int numPagina;
	public int numRegPorPagina;
}
