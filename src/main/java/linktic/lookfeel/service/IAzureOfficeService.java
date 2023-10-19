package linktic.lookfeel.service;

import linktic.lookfeel.dtos.ReponseUsuarioLoginDTO;
import linktic.lookfeel.dtos.ResponseLoginDTO;

/**
 * 
 * Clase encargada de validar el login
 * 
 * @author Ing. Paulo Perdomo
 *
 */
public interface IAzureOfficeService {
	
	/**
	 * 
	 * Metodo encargado de validar el correo contra office 365
	 * 
	 * @param email
	 * @return ResponseLoginDTO
	 */
	public ResponseLoginDTO getDataUserByEmailOffice(String email, String pass);
	
	/**
	 * 
	 * Metodo encargado de validar el correo y retornar objeto con todos los atributos
	 * 
	 * @param email
	 * @return ReponseUsuarioLoginDTO
	 */
	public ReponseUsuarioLoginDTO getDataUserByEmailOfficeEncriptado(String email) throws Exception;

}
