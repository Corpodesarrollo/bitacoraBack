package linktic.lookfeel.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import linktic.lookfeel.dtos.ResponseInsitucionDTO;
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

}
