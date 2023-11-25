package linktic.lookfeel.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServicioParamDTO {

    private String nombre;
    private String valor;
    
    public static ServicioParamDTO objectoToDTO(Object obj[]) {

    	ServicioParamDTO servicioParam = new ServicioParamDTO();
    	servicioParam.setNombre(obj[0] == null ? "" : String.valueOf(obj[0]));
    	servicioParam.setValor(obj[1] == null ? "" : String.valueOf(obj[1]));
    	
        return servicioParam;
        
    }
}
