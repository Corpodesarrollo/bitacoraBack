package linktic.lookfeel.service;


import linktic.lookfeel.dtos.ConstanteDto;
import linktic.lookfeel.model.Constante;

import java.util.List;

public interface IConstanteService {

    List<ConstanteDto> GetManualsType(Long category);

	List<ConstanteDto> listaTiposDocumentos();

}
