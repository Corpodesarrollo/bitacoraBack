package linktic.lookfeel.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReponseUsuarioLoginDTO {
	
	private UsuarioDTO usuario;
	private boolean exito;
	private String error;

}
