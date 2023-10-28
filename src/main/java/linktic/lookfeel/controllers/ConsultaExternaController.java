package linktic.lookfeel.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import linktic.lookfeel.dtos.BitacoraDto;
import linktic.lookfeel.dtos.ConstanteDto;
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
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
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
	
	
}
