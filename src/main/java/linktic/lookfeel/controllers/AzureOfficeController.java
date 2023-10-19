package linktic.lookfeel.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import linktic.lookfeel.dtos.ReponseUsuarioLoginDTO;
import linktic.lookfeel.dtos.RequestLoginDTO;
import linktic.lookfeel.dtos.ResponseLoginDTO;
import linktic.lookfeel.dtos.UsuarioAzureDTO;
import linktic.lookfeel.service.IAzureOfficeService;

/**
 *
 * @author Ing. Paulo Perdomo
 * @descripcion Controlador del requerimiento 01, Validacion de email office
 *              365.
 * @fechacreacion 07/07/2023
 * @fechamodificacion 07/07/2023
 * @requrimiento 03 24/01/2023
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/api/apoyo/seguridad")
@CrossOrigin(origins = "*")
public class AzureOfficeController {

	@Autowired
	private IAzureOfficeService iAzureOfficeService;

	/**
	 * 
	 * Metodo encargado de obtener los modulos disponibles
	 * 
	 * @return ResponseEntity<?>
	 */
	@PostMapping(value = "/login", produces = "application/json")
	public ResponseEntity<?> validarEmail(@RequestBody(required = true) RequestLoginDTO login) {
		ResponseLoginDTO usuarioAzureDTO = iAzureOfficeService.getDataUserByEmailOffice(login.getUsuario(),login.getContrasenia());

		return ResponseEntity.ok().body(usuarioAzureDTO);
	}
	
	/**
	 * 
	 * Metodo encargado de valdiar el usuario por el email
	 * 
	 * @param hashEncriptado
	 * @return ResponseEntity<?>
	 * @throws Exception 
	 */
	@GetMapping(value = "/usuarios/{hashEncriptado}", produces = "application/json")
	public ResponseEntity<?> validarEmailEncriptado(@PathVariable("hashEncriptado") String hashEncriptado) throws Exception {
		ReponseUsuarioLoginDTO usuarioAzureDTO = iAzureOfficeService.getDataUserByEmailOfficeEncriptado(hashEncriptado);

		return ResponseEntity.ok().body(usuarioAzureDTO);
	}
	
	

}
