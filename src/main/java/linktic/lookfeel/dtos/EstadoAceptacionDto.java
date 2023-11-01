package linktic.lookfeel.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadoAceptacionDto {

	public String usuario;
	public String idPolitica;
	public String tipoPolitica;
	public boolean aceptada;
	public boolean reenviar;
}
