package linktic.lookfeel.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseLoginDTO {
	
	private boolean exito;
	private String error;
	private UsuarioDTO usuario;

}
