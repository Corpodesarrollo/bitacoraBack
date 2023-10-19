package linktic.lookfeel.service;


import linktic.lookfeel.dtos.ConstanteDto;
import linktic.lookfeel.model.Constante;
import linktic.lookfeel.repositories.ConstanteRepository;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConstanteService implements IConstanteService {

    private final ConstanteRepository constanteRepository;
    
    private final ModelMapper modelMapper;

    @Autowired
    public ConstanteService(ConstanteRepository constanteRepository, ModelMapper modelMapper) {
        this.constanteRepository = constanteRepository;
        this.modelMapper = modelMapper;
    }
    
    @Override
    public List<ConstanteDto> listaTiposDocumentos(){
    	List<ConstanteDto> respuesta = new ArrayList<>();
    	try {
    		List<Constante> consulta =  constanteRepository.listaTiposDocumentos();
    		for (Constante iterator : consulta) {
    			ConstanteDto constanteDto = new ConstanteDto();
    			
    			constanteDto.setTipo(iterator.getConstantePk().getTipo());
    			constanteDto.setCodigo(iterator.getConstantePk().getCodigo());
    			constanteDto.setNombre(iterator.getAbrev());
    			respuesta.add(constanteDto);
 			}

    		
		} catch (Exception e) {
			respuesta = null;
		}
    	
    	return respuesta;
    	
    }

    /**
     * @param category
     * @return el listado de tipos de manuales
     */
    @Override
    public List<ConstanteDto> GetManualsType(Long category) {

        List<Constante> result = constanteRepository.findByConstantePkTipo(category);

        if (!result.isEmpty()) {
            return result.stream()
                    .map(this::ConstanteToDto)
                    .collect(Collectors.toList());
        }else{
            return null;
        }

    }

    @NotNull
    private ConstanteDto ConstanteToDto(Constante result) {

        ConstanteDto constanteDto = modelMapper.map(result, ConstanteDto.class);

        constanteDto.setListManuals(result.getConstantePk().getTipo(), result.getConstantePk().getCodigo(), result.getNombre());

        return constanteDto;

    }
}
