package linktic.lookfeel.controllers;

import linktic.lookfeel.dtos.MensajeNuevoDTO;
import linktic.lookfeel.model.Response;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import linktic.lookfeel.service.IMensajeService;

@RestController
@RequestMapping(value = "/api/apoyo/mensajes/")
@CrossOrigin(origins = "*")
public class MensajesController {
	
	/**
	 * Bandera == 0: Ver Mensajes Home
	 * Bandera == 1: Ver Mensjaes con Asunto.
	 */
	
	@Autowired
	private IMensajeService iMensajeService; 
	
	@GetMapping(value = "/mensaje", produces = "application/json")
	@Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('Ver Mensaje')")
	public Response listaMensajes(
			@RequestParam(name = "perfilLoginId", required = false) String perfilLoginId,
			@RequestParam(name = "usuarioId", required = false) String usuarioId,
			@RequestParam(name = "perfilId", required = false) String perfilId,
			@RequestParam(name = "colegioId", required = false) String colegioId,
			@RequestParam(name = "sedeId", required = false) String sedeId,
			@RequestParam(name = "jornadaId", required = false) String jornadaId,
			@RequestParam(name = "localidadId", required = false) String localidadId,
			@RequestParam(name = "fechaDesde", required = false) String fechaDesde,
			@RequestParam(name = "fechaHasta", required = false) String fechaHasta,
			@RequestParam(name = "bandera", required = false) Long bandera,
			@RequestParam(name = "pagina", defaultValue = "0") int pagina,
			@RequestParam(name = "tamanoPagina", defaultValue = "10") int tamanoPagina) {
		
		Response listaMensajes = iMensajeService.listadoMensajes(perfilLoginId, usuarioId, perfilId, colegioId, sedeId, jornadaId,
				localidadId, fechaDesde, fechaHasta, bandera, pagina, tamanoPagina);

		return listaMensajes;
	}
	
	@GetMapping(value = "/mensaje/{id}", produces = "application/json")
	@Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('Ver Mensaje')")
	public Response verMensajes(@PathVariable(name = "id", required = true) Long id, @RequestParam(name = "perfilLoginId", required = false) String perfilLoginId) {
		Response verMensaje = iMensajeService.verMensaje(id, perfilLoginId);
		return verMensaje;
	}
		
	@PostMapping(value = "/mensaje/crearMensaje", produces = "application/json")
	@Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('Enviar Mensajes')")
	public Response crearMensajes(@RequestBody(required = true) MensajeNuevoDTO mensaje, @RequestParam(name = "usuarioId", required = false) String usuarioId, @RequestParam(name = "perfilLoginId", required = false) String perfilLoginId) throws ParseException {
		Long comodin = (long) 0;
		Response nuevoMensaje=iMensajeService.nuevoMensaje(mensaje, comodin, usuarioId, perfilLoginId);
		return nuevoMensaje;
	}
	
	@GetMapping(value = "/mensajeActualizar/{id}", produces = "application/json")
	@Operation(security = {@SecurityRequirement(name = "bearer-key")})
	@PreAuthorize("hasRole('Ver Mensaje')")
	public Response verMensajesActualizar(@PathVariable(name = "id", required = true) Long id, @RequestParam(name = "perfilLoginId", required = false) String perfilLoginId) {
		Response verMensaje = iMensajeService.verMensajeActualizar(id, perfilLoginId);
		return verMensaje;
	}
	
	@PostMapping(value = "/mensaje/actualizar/{id}", produces = "application/json")
	@Operation(security = {@SecurityRequirement(name = "bearer-key")})
	@PreAuthorize("hasRole('Editar Mensaje')")
	public Response actualizarMensajes(@RequestBody(required = true) MensajeNuevoDTO mensaje, @PathVariable(name = "id", required = true) Long id, @RequestParam(name = "usuarioId", required = false) String usuarioId, @RequestParam(name = "perfilLoginId", required = false) String perfilLoginId) throws ParseException {
		Response nuevoMensaje=iMensajeService.actualizarMensaje(mensaje, id, usuarioId, perfilLoginId);
		return nuevoMensaje;
	}

	@PostMapping(value = "/mensaje/eliminar/{id}", produces = "application/json")
	@Operation(security = {@SecurityRequirement(name = "bearer-key")})
	@PreAuthorize("hasRole('Eliminar Mensaje')")
	public Response eliminarMensajes(@PathVariable(name = "id", required = true) Long id, @RequestParam(name = "usuarioId", required = false) String usuarioId, @RequestParam(name = "perfilLoginId", required = false) String perfilLoginId) {
		Response verMensaje = iMensajeService.eliminarMensaje(usuarioId, id, perfilLoginId);
		return verMensaje;
	}
	
	@GetMapping(value = "/lista/perfiles", produces = "application/json")
	public Response listaPerfiles() {
		Response listaPerfiles = iMensajeService.listaPerfiles();
		return listaPerfiles;
	}
	
	@GetMapping(value = "/lista/localidades", produces = "application/json")
	public Response listaLocalidades() {
		Response listaPerfiles = iMensajeService.listaLocalidades();
		return listaPerfiles;
	}
	
	@GetMapping(value = "/lista/colegios/{idLocalidad}", produces = "application/json")
	public Response listaColegios(@PathVariable(name = "idLocalidad", required = true) Long idLocalidad) {
		Response listaPerfiles = iMensajeService.listaColegiosLocalidad(idLocalidad);
		return listaPerfiles;
	}
	
	@GetMapping(value = "/lista/sedes/{idColegios}", produces = "application/json")
	public Response listaSedes(@PathVariable(name = "idColegios", required = true) Long idColegios) {
		Response listaPerfiles = iMensajeService.listaSedesColegios(idColegios);
		return listaPerfiles;
	}
	
	@GetMapping(value = "/lista/jornadas/{idColegios}/{idSede}", produces = "application/json")
	public Response listaSedes(@PathVariable(name = "idColegios", required = true) Long idColegios, @PathVariable(name = "idSede", required = true) Long idSede) {
		Response listaPerfiles = iMensajeService.listaJornadaSedes(idColegios, idSede);
		return listaPerfiles;
	}
	
	
	
}
