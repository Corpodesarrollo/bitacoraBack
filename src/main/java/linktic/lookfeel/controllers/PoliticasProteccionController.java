package linktic.lookfeel.controllers;

import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import linktic.lookfeel.dtos.AceptarPoliticaDto;
import linktic.lookfeel.dtos.CrearPoliticaDto;
import linktic.lookfeel.dtos.MensajeNuevoDTO;
import linktic.lookfeel.model.Response;
import linktic.lookfeel.model.TipoPoliticaEnum;
import linktic.lookfeel.service.IPoliticasProteccionService;

@RestController
@RequestMapping(value = "/api/apoyo/politicas",method = { RequestMethod.POST, RequestMethod.GET })
@CrossOrigin(origins = "*")
public class PoliticasProteccionController {
	 private static final Logger log = LoggerFactory.getLogger(PoliticasProteccionController.class);

	@Autowired
	private IPoliticasProteccionService iPoliticasProteccionService; 
	
	@GetMapping(value = "/lista", produces = "application/json")
	public Response listaPoliticasUso() {
		Response politicas = iPoliticasProteccionService.listaPoliticas();
		return politicas;
	}
	
	@GetMapping(value = "/lista/politicasActivas", produces = "application/json")
	public Response listaPoliticasActivas() {
		Response politicas = iPoliticasProteccionService.listaPoliticasActivas();
		return politicas;
	}
	
	@GetMapping(value = "/ver/{id}", produces = "application/json")
	public Response verPolitica(@PathVariable(name = "id", required = true) Long id) {
		Response politicas = iPoliticasProteccionService.verPoliticaUso(id);
		return politicas;
	}
	
	@PostMapping(value = "/crearPoliticaUso", produces = "application/json")
	public Response crearPolitica(@RequestBody(required = true) CrearPoliticaDto politica) throws ParseException {		
		Response responseCreacion = iPoliticasProteccionService.crearPoliticaUso(politica);
		return responseCreacion;
	}
	
	@PostMapping(value = "/eliminar/{id}/{usuarioId}", produces = "application/json")
	public Response eliminarMensajes(@PathVariable(name = "id", required = true) Long id, @PathVariable(name = "usuarioId", required = false) String usuarioId) {
		Response verMensaje = iPoliticasProteccionService.eliminarPoliticaUso(id, usuarioId);
		return verMensaje;
	}
	
	@PostMapping(value = "/aceptarPolitica", produces = "application/json")
	public Response resgistrarAceptacion(@RequestBody(required = true) AceptarPoliticaDto datosAceptacion) throws ParseException {		
		Response responseCreacion = iPoliticasProteccionService.aceptarPolitica(datosAceptacion);
		return responseCreacion;
	}
	
	@GetMapping(value = "/listaAceptacion", produces = "application/json")
	public Response verListaAceptacion() {
		Response listaAceptacion = iPoliticasProteccionService.verListaAceptacion();
		return listaAceptacion;
	}
	
	@GetMapping(value = "/aceptacionPoliticaUso/{usuario}", produces = "application/json")
	public Response verAceptacionPoliticaUso(@PathVariable(name = "usuario", required = true) String usuario) {
		Response aceptacion = iPoliticasProteccionService.consultarAceptacionPoliticaUso(usuario);
		return aceptacion;
	}
	
	@GetMapping(value = "/aceptacionPoliticaDatos/{usuario}", produces = "application/json")
	public Response verAceptacionPOliticaDatos(@PathVariable(name = "usuario", required = true) String usuario) {
		Response aceptacion = iPoliticasProteccionService.consultarAceptacionPoliticaDatos(usuario);
		return aceptacion;
	}
	
	@PostMapping(value = "/marcarReenviarPoliticaUso", produces = "application/json")
	public Response reenviarPoliticaUso() throws ParseException {		
		Response responseReenviar = iPoliticasProteccionService.marcarReenviar(TipoPoliticaEnum.USO.getValor());
		return responseReenviar;
	}
	@PostMapping(value = "/marcarReenviarPoliticaDatos", produces = "application/json")
	public Response reenviarPoliticaDatos() throws ParseException {		
		Response responseReenviar = iPoliticasProteccionService.marcarReenviar(TipoPoliticaEnum.DATOS.getValor());
		return responseReenviar;
	}
}

