package linktic.lookfeel.service;

import linktic.lookfeel.dtos.BitacoraDto;
import linktic.lookfeel.model.Response;

public interface IBitacoraService {

	Response insertarBitacora(BitacoraDto bitacora);
}
