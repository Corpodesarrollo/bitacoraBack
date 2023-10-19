package linktic.lookfeel.dtos;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AceptarPoliticaDto {

	public String usuario;
	
	public Long idPolitica;
	
	public boolean aceptada;
}
