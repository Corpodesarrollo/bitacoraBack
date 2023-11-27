package linktic.lookfeel.service;

import java.util.List;

import linktic.lookfeel.dtos.AccesoDirectoPorPerfilDTO;
import linktic.lookfeel.dtos.ResponseEscudoDTO;
import linktic.lookfeel.dtos.ResponseInsitucionDTO;
import linktic.lookfeel.dtos.grupoServicioDTO;

public interface IInstitucionService {
	
	
	/**
	 * 
	 * Metodo encargado de obtener las intituciones de la persona
	 * 
	 * @param idPersona
	 * @return ResponseInsitucionDTO
	 */
	public ResponseInsitucionDTO getInsitucionesByPersona(String idPersona);

	ResponseEscudoDTO getEscudoInstitucion(Long codigoColegio);
	
	List<grupoServicioDTO> consultarPermisosPorPerfil(int idPerfilCodigo);
	
	List<AccesoDirectoPorPerfilDTO> consultarAccesosDirectosPorPerfil(int idPerfilCodigo);
}
