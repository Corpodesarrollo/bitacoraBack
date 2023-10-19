package linktic.lookfeel.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

@Builder


/**
 * Instantiates a new ver foto.
 *
 * @param urlFoto the url foto
 * @param rutaLocalFoto the ruta local foto
 * @param fotoCodificada the foto codificada
 */
@AllArgsConstructor

/**
 * Instantiates a new ver foto.
 */
@NoArgsConstructor
public class FotoDTO {
	
	private String nombreArchivo;
	private String codificacion;

}
