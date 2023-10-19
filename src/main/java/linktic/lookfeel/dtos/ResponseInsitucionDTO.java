package linktic.lookfeel.dtos;

import java.util.List;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ResponseInsitucionDTO {
	
	private boolean exito;
	private List<RegistroDTO> registros;
	private String error;

}
