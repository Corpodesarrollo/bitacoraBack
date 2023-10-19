package linktic.lookfeel.service;

import java.text.ParseException;

import linktic.lookfeel.dtos.MensajeNuevoDTO;
import linktic.lookfeel.model.Response;

public interface IMensajeService {

	Response verMensaje(Long Id, String perfilId);

	Response nuevoMensaje(MensajeNuevoDTO mensaje, Long comodin, String usuarioId, String perfilId) throws ParseException;

	Response actualizarMensaje(MensajeNuevoDTO mensaje, Long id, String usuarioId, String perfilId) throws ParseException;

	Response listadoMensajes(String perfilLoginId, String usuarioId, String perfilId, String colegioId, String sedeId, String jornadaId, String localidadId,
			String fechaDesde, String fechaHasta, Long bandera, int pagina, int tamanoPagina);

	Response eliminarMensaje(String usuarioId, Long id, String perfilId);

	Response verMensajeActualizar(Long id, String perfilId);
	
	Response listaPerfiles();

	Response listaLocalidades();

	Response listaColegiosLocalidad(Long idLocalidad);

	Response listaSedesColegios(Long idColegios);

	Response listaJornadaSedes(Long idColegios, Long idSede);

}
