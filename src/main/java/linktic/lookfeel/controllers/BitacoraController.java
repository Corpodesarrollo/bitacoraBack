package linktic.lookfeel.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import linktic.lookfeel.dtos.BitacoraDto;
import linktic.lookfeel.model.Response;
import linktic.lookfeel.service.IBitacoraService;

/**
*
* @author Ing. Giovanny Romero Correa
* @descripcion Controlador para la bitacora
* @fechacreacion 21/10/2023
* @requrimiento HU002,
* @version 1.0
*/

@RestController
@RequestMapping(value = "/api/apoyo/bitacora", method = { RequestMethod.POST})
@CrossOrigin(origins = "*")
public class BitacoraController {

	@Autowired
	private IBitacoraService iBitacoraService;
	
	@PostMapping(value = "/insertarBitacora", produces = "application/json")
	public Response insertarBitacora(BitacoraDto bitacora) {
		return iBitacoraService.insertarBitacora(bitacora);
	}
}
