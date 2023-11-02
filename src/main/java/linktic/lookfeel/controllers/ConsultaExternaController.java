package linktic.lookfeel.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import linktic.lookfeel.dtos.BitacoraDto;
import linktic.lookfeel.dtos.BitacoraFiltroDto;
import linktic.lookfeel.dtos.ColegioFiltroDto;
import linktic.lookfeel.dtos.ConstanteDto;
import linktic.lookfeel.dtos.JornadaFiltroDto;
import linktic.lookfeel.dtos.PaginadoDTO;
import linktic.lookfeel.dtos.SedeFiltroDto;
import linktic.lookfeel.dtos.UsuarioFiltroDto;
import linktic.lookfeel.model.Response;
import linktic.lookfeel.service.ConstanteService;
import linktic.lookfeel.service.IBitacoraService;
import linktic.lookfeel.service.IConsultaExternaService;

/**
 *
 * @author Ing. Alexander A. Muñoz Coneo
 * @descripcion Controlador Para la operaciones relacionadas con las consultas
 *              externas de los usuarios
 * @fechacreacion 16/08/2023
 * @requrimiento HU009_SED, HU010_SED
 * @version 1.0
 */

@RestController
@RequestMapping(value = "/api/apoyo/consultas", method = { RequestMethod.POST, RequestMethod.GET })
@CrossOrigin(origins = "*")
public class ConsultaExternaController {

	@Autowired
	private IConsultaExternaService iConsultaExternaService;
	
	@Autowired
	private ConstanteService constanteService;
	
	@Autowired
	private IBitacoraService iBitacoraService;
	
	@GetMapping(value = "/estudiantes/listaDocumentosIdentidad", produces = "application/json")
	public Response consultaBoletin() {
		List<ConstanteDto> respuesta = constanteService.listaTiposDocumentos();
		if (respuesta.isEmpty()) {
			return new Response(HttpStatus.NO_CONTENT.value(), HttpStatus.BAD_REQUEST.name(), null);
		} else {
			return new Response(HttpStatus.OK.value(), "Lista Tipo de Documentos", respuesta);
		}
		
	}

	@GetMapping(value = "/estudiantes/{tipoDocumentoId}/{numDocumento}/{codigoEstudiante}", produces = "application/json")
	public Response consultaBoletin(@PathVariable("tipoDocumentoId") String tipoDocumentoId,
			@PathVariable("numDocumento") String numDocumento,
			@PathVariable("codigoEstudiante") String codigoEstudiante) throws Exception {
		Response respuesta = iConsultaExternaService.consultaBoletin(tipoDocumentoId, numDocumento, codigoEstudiante);
		return respuesta;
	}

	@GetMapping(value = "/estudiantes/{pin}", produces = "application/zip")
	public ResponseEntity<Resource> pinDocumento(@PathVariable("pin") String pin) throws Exception {
		Response respuesta = iConsultaExternaService.validPinDocumento(pin);
		try {
			Resource resource = (Resource) respuesta.getData();
			
			if (resource.exists()) {
				HttpHeaders headers = new HttpHeaders();
				headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename());
				return ResponseEntity.ok().headers(headers).body(resource);
			} else {
				ObjectMapper objectMapper = new ObjectMapper();
				String errorResponseJson = objectMapper.writeValueAsString(respuesta);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(new ByteArrayResource(errorResponseJson.getBytes()));
			}
		} catch (Exception e) {
			ObjectMapper objectMapper = new ObjectMapper();
			String errorResponseJson = objectMapper.writeValueAsString(respuesta);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(new ByteArrayResource(errorResponseJson.getBytes()));
		}

	}

	@GetMapping(value = "/estudiantes/institucion/{codigoInstitucion}/{codigoSede}/{codigoJornada}/{numPagina}/{numRegPorPagina}", produces = "application/json")
	public Response consultaEstudiantesPorInstitucion(@PathVariable("codigoInstitucion") int codigoInstitucion,
														@PathVariable("codigoSede") String codigoSede,
														@PathVariable("codigoJornada") String codigoJornada,
														@PathVariable("numPagina") int numPagina,
														@PathVariable("numRegPorPagina") int numRegPorPagina) {
		
		PaginadoDTO respuesta = iConsultaExternaService.consultaPaginadaEstudiantesPorInstitucion(codigoInstitucion,
				codigoSede,
				codigoJornada,
				numPagina,
				numRegPorPagina);
		
		if (respuesta == null) {
			return new Response(HttpStatus.NO_CONTENT.value(), HttpStatus.BAD_REQUEST.name(), null);
		} else {
			return new Response(HttpStatus.OK.value(), "La consulta se realizo Exitosamente", respuesta);
		}
	}
	
	@GetMapping(value = "/colegios/{codigoLocalidad}/{numPagina}/{numRegPorPagina}", produces = "application/json")
	public Response consultaColegiosPaginados(@PathVariable("codigoLocalidad") int codigoLocalidad,
											  @PathVariable("numPagina") int numPagina, 
			                                  @PathVariable("numRegPorPagina") int numRegPorPagina) {
		
		PaginadoDTO respuesta = iConsultaExternaService.consultaColegiosPaginados(codigoLocalidad, numPagina,numRegPorPagina);
		
		if (respuesta == null) {
			return new Response(HttpStatus.NO_CONTENT.value(), HttpStatus.BAD_REQUEST.name(), null);
		} else {
			return new Response(HttpStatus.OK.value(), "La consulta se realizo Exitosamente", respuesta);
		}
	}
	
	@PostMapping(value = "/insertarBitacora", produces = "application/json")
	public Response insertarBitacora(@RequestBody(required = true) BitacoraDto bitacora) {
		return iBitacoraService.insertarBitacora(bitacora);
	}
	
	@GetMapping(value = "/tipoLog", produces = "application/json")
	public Response consultarTipoLogsBitacora() {
		return iBitacoraService.obtenerTipoLog();
	}
	
	@PostMapping(value = "/Usuarios", produces = "application/json")
	public Response consultarUsuarios(@RequestBody(required = true) UsuarioFiltroDto usuarioFiltro) {
		return iBitacoraService.obtenerUsuarios(usuarioFiltro);
	}
	
	@PostMapping(value = "/Colegios", produces = "application/json")
	public Response consultarColegios(@RequestBody(required = true) ColegioFiltroDto colegioFiltro) {
		return iBitacoraService.obtenerColegios(colegioFiltro);
	}
	
	@PostMapping(value = "/Sedes", produces = "application/json")
	public Response consultarSedes(@RequestBody(required = true) SedeFiltroDto sedeFiltro) {
		return iBitacoraService.obtenerSedes(sedeFiltro);
	}
	
	@PostMapping(value = "/Jornadas", produces = "application/json")
	public Response consultarJornadas(@RequestBody(required = true) JornadaFiltroDto sedeFiltro) {
		return iBitacoraService.obtenerJornadas(sedeFiltro);
	}
	
	@PostMapping(value = "/Bitacora", produces = "application/json")
	public Response consultarBitacora(@RequestBody(required = true) BitacoraFiltroDto bitacoraFiltro) {
		return iBitacoraService.obtenerBitacora(bitacoraFiltro);
	}
}
