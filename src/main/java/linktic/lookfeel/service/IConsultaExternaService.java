package linktic.lookfeel.service;

import java.io.IOException;

import linktic.lookfeel.dtos.PaginadoDTO;
import linktic.lookfeel.model.Response;

public interface IConsultaExternaService {

	Response consultaBoletin(String tipoDocumento, String numeroDocumento, String codigoEstudiante);

	Response validPinDocumento(String pinDocumento) throws IOException;
	
	PaginadoDTO consultaPaginadaEstudiantesPorInstitucion( int codigoInstitucion,
			String codigoSede,
			String codigoJornada,
			int numPagina,
			int numRegPorPagina);
	
	PaginadoDTO consultaColegiosPaginados(int codigoLocalidad, int numPagina, int numRegPorPagina);
}
