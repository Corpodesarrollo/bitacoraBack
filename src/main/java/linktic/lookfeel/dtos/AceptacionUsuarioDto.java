package linktic.lookfeel.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AceptacionUsuarioDto {
	
	public String usuario;
	public String nombres;
	public String apellidos;
	public boolean aceptaPoliticaDatos;
	public boolean aceptaPoliticaUso;
	public boolean reenviar;

}
