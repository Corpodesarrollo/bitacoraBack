/**
 * 
 */
package linktic.lookfeel.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import linktic.lookfeel.dtos.UsuariosDTO;
import linktic.lookfeel.dtos.grupoServicioDTO;
import linktic.lookfeel.model.Response;
import linktic.lookfeel.model.Usuario;
import linktic.lookfeel.service.ISeguridadService;

/**
 *
 * @author Ing. Alexander A. Muñoz Coneo
 * @descripcion Controlador Para la operaciones relacionadas con la contraseñas
 *              de los usuarios
 * @fechacreacion 19/07/2023
 * @fechamodificacion 19/07/2023
 * @requrimiento HU007_SED, HU008_SED
 * @version 1.0
 */

@RestController
@RequestMapping(value = "/api/apoyo/seguridad", method = { RequestMethod.POST, RequestMethod.GET })
@CrossOrigin(origins = "*")
public class SeguridadController {

	@Autowired
	private ISeguridadService iSeguridadService;

	/**
	 * 
	 * Metodo para Cambiar la Contraseña del Usuario
	 * 
	 * @return Response
	 * @throws Exception
	 */
	@PostMapping(value = "/usuario/{id}/cambiarContrasenia", produces = "application/json")
	public Response cambiarContrasena(@RequestBody(required = true) UsuariosDTO usuario, @PathVariable("id") String id)
			throws Exception {
		Response respuesta = iSeguridadService.cambiarContrasena(id, usuario.getContrasenia_actual(),
				usuario.getContrasenia_nueva(), usuario.getConfirmacion_contrasenia(), "0");
		return respuesta;
	}

	/**
	 * 
	 * Metodo para Actualizar la Contraseña del Usuario
	 * 
	 * @return Response
	 * @throws Exception
	 */
	@GetMapping(value = "/usuario/{numeroDocumento}/recuperarContrasenia", produces = "application/json")
	public Response actualizarContrasena(@PathVariable("numeroDocumento") String numeroDocumento) throws Exception {
		Response respuesta = iSeguridadService.actualizarContrasena(numeroDocumento);
		return respuesta;
	}

	/**
	 * 
	 * Metodo para Consultar Usuario por codigo usuLogin
	 * 
	 * @return Response
	 */
	@GetMapping(value = "/consultarUsuarioPorUsuLogin", produces = "application/json")
	public Response consultarUsuarioPorUsuLogin(@RequestParam String usuLogin) {
		List<Usuario> resultado = iSeguridadService.consultarUsuarioPorUsuLogin(usuLogin);
		if (resultado.isEmpty()) {
			return new Response(HttpStatus.NO_CONTENT.value(), HttpStatus.BAD_REQUEST.name(), null);
		} else {
			return new Response(HttpStatus.OK.value(), "La consulta se realizo Exitosamente", resultado);
		}

	}
	
	/**
	 * 
	 * Metodo para obtener los grupos de servicios para permisos
	 * 
	 * @return Response
	 */
	@GetMapping(value = "/usuarios/permisos/{idPerfilCodigo}", produces = "application/json")
	public Response consultarPermisosPorPerfil(@PathVariable("idPerfilCodigo") int idPerfilCodigo) {
		List<grupoServicioDTO> resultado = iSeguridadService.consultarPermisosPorPerfil(idPerfilCodigo);
		
		if (resultado.isEmpty()) {
			return new Response(HttpStatus.NO_CONTENT.value(), HttpStatus.BAD_REQUEST.name(), null);
		} else {
			return new Response(HttpStatus.OK.value(), "La consulta se realizo Exitosamente", resultado);
		}
	}

}
