package linktic.lookfeel.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PerfilDTO {
	
	private int id;
	private String nombre;
	private String etiqueta;
	private int idPerfilNivel;
	private int codJerarquia;

}
