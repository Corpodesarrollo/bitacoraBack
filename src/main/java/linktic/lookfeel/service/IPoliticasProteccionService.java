package linktic.lookfeel.service;

import linktic.lookfeel.dtos.AceptarPoliticaDto;
import linktic.lookfeel.dtos.CrearPoliticaDto;
import linktic.lookfeel.model.Response;

public interface IPoliticasProteccionService {
	
	Response listaPoliticas();
	
	Response crearPoliticaUso(CrearPoliticaDto datosPolitica);
	
	Response eliminarPoliticaUso(Long idPolitica, String idUsuario);
	
	Response verPoliticaUso(Long idPolitica);
	
	Response listaPoliticasActivas();
	
	Response aceptarPolitica(AceptarPoliticaDto datosAceptacion);
	
	Response verListaAceptacion();
	
	Response consultarAceptacionUsuario(String usuario);
}
