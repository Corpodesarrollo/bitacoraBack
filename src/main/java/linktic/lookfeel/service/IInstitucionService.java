package linktic.lookfeel.service;

import linktic.lookfeel.dtos.ResponseInsitucionDTO;

public interface IInstitucionService {
	
	
	/**
	 * 
	 * Metodo encargado de obtener las intituciones de la persona
	 * 
	 * @param idPersona
	 * @return ResponseInsitucionDTO
	 */
	public ResponseInsitucionDTO getInsitucionesByPersona(String idPersona);

}
