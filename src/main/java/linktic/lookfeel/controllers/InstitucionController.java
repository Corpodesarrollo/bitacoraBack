package linktic.lookfeel.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import linktic.lookfeel.dtos.AccesoDirectoPorPerfilDTO;
import linktic.lookfeel.dtos.ResponseInsitucionDTO;
import linktic.lookfeel.dtos.grupoServicioDTO;
import linktic.lookfeel.model.Response;
import linktic.lookfeel.service.IInstitucionService;

@RestController
@RequestMapping(value = "/api/apoyo/")
@CrossOrigin(origins = "*")
public class InstitucionController {
	
	@Autowired
	private IInstitucionService iInstitucionService; 
	
	/**
	 * 
	 * Metodo encargado de valdiar el usuario por el email
	 * 
	 * @param hashEncriptado
	 * @return ResponseEntity<?>
	 */
	@GetMapping(value = "/usuarios/{id}/info-usuario", produces = "application/json")
	public ResponseEntity<?> obtenerInstituciones(@PathVariable("id") String ipPersona) {
		ResponseInsitucionDTO responseInsitucionDTO = iInstitucionService.getInsitucionesByPersona(ipPersona);

		return ResponseEntity.ok().body(responseInsitucionDTO);
	}
	
	/**
	 * 
	 * Metodo para obtener los grupos de servicios para permisos
	 * 
	 * @return Response
	 */
	@GetMapping(value = "/usuarios/permisos/{idPerfilCodigo}", produces = "application/json")
	public Response consultarPermisosPorPerfil(@PathVariable("idPerfilCodigo") int idPerfilCodigo) {
		List<grupoServicioDTO> resultado = iInstitucionService.consultarPermisosPorPerfil(idPerfilCodigo);
		
		if (resultado.isEmpty()) {
			return new Response(HttpStatus.NO_CONTENT.value(), HttpStatus.BAD_REQUEST.name(), null);
		} else {
			return new Response(HttpStatus.OK.value(), "La consulta se realizo Exitosamente", resultado);
		}
	}
	
	/**
	 * 
	 * Metodo para obtener todos los registros activos de accesos directos
	 * 
	 * @return Response
	 */
	@GetMapping(value = "/usuarios/accesos-directos/{idPerfilCodigo}", produces = "application/json")
	public Response consultarAccesosDirectosPorPerfil(@PathVariable("idPerfilCodigo") int idPerfilCodigo) {
		List<AccesoDirectoPorPerfilDTO> resultado = iInstitucionService.consultarAccesosDirectosPorPerfil(idPerfilCodigo);
		
		if (resultado.isEmpty()) {
			return new Response(HttpStatus.NO_CONTENT.value(), HttpStatus.BAD_REQUEST.name(), null);
		} else {
			return new Response(HttpStatus.OK.value(), "La consulta se realizo Exitosamente", resultado);
		}
	}
}
