package linktic.lookfeel.service;

import linktic.lookfeel.dtos.BitacoraDto;
import linktic.lookfeel.dtos.ColegioFiltroDto;
import linktic.lookfeel.dtos.JornadaFiltroDto;
import linktic.lookfeel.dtos.SedeFiltroDto;
import linktic.lookfeel.dtos.UsuarioFiltroDto;
import linktic.lookfeel.model.Response;

public interface IBitacoraService {

	Response insertarBitacora(BitacoraDto bitacora);
	
	Response obtenerTipoLog();
	
	Response obtenerUsuarios(UsuarioFiltroDto usuario);
	
	Response obtenerColegios(ColegioFiltroDto colegio);

	Response obtenerSedes(SedeFiltroDto colegio);
	
	Response obtenerJornadas(JornadaFiltroDto colegio);
}
