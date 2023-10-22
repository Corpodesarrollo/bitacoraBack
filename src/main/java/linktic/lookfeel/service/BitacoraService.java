package linktic.lookfeel.service;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import linktic.lookfeel.dtos.BitacoraDto;
import linktic.lookfeel.model.Bitacora;
import linktic.lookfeel.model.Response;
import linktic.lookfeel.security.repositories.BitacoraRepository;

/**
*
* @author Ing. Giovanny Romero Correa
* @descripcion Servicios Para la operaciones relacionadas con la bitacora
* @fechacreacion 22/10/2023
* @requrimiento HU002
* @version 1.0
*/

@Service
public class BitacoraService implements IBitacoraService{

	private final BitacoraRepository bitacoraRepository;

    BitacoraService(BitacoraRepository bitacoraRepository) {
        this.bitacoraRepository = bitacoraRepository;
    }	

	@Override
	public Response insertarBitacora(BitacoraDto bitacora) {
		Bitacora b = new Bitacora();
		b.setUsuario(bitacora.getUsuario());
		b.setModulo(bitacora.getModulo());
		b.setFechaRegistro(LocalDateTime.now());
		b.setTipoLog(bitacora.getTipoLog());
		b.setDescripcion(bitacora.getDescripcion());
		b.setSubmodulo(bitacora.getSubmodulo());
		b.setColegio(bitacora.getColegio());
		b.setJornada(bitacora.getJornada());
		b.setSede(bitacora.getSede());
		b.setPerfil(bitacora.getPerfil());
		
		try {
			bitacoraRepository.save(b);
			return new Response(HttpStatus.OK.value(), "Bitacora insertada correctamente", true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return  new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(),e.getMessage(),null);
		}
	}
}
