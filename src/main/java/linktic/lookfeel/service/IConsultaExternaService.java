package linktic.lookfeel.service;

import java.io.IOException;

import linktic.lookfeel.model.Response;

public interface IConsultaExternaService {

	Response consultaBoletin(String tipoDocumento, String numeroDocumento, String codigoEstudiante);

	Response validPinDocumento(String pinDocumento) throws IOException;

}
