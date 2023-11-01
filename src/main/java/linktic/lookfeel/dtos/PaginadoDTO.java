package linktic.lookfeel.dtos;

import java.util.List;

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
public class PaginadoDTO {
	public int paginaActual;
	public int numeroPaginas;
	public int totalRegPorPagina;
	public int totalRegistros;
	List<?> data;
}
