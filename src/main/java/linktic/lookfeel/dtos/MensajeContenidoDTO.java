package linktic.lookfeel.dtos;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MensajeContenidoDTO {
	
	private long msjCodigo;
	private String msjAsunto;
	private LocalDate msjFecha;
	private Long msjEnviadoPor;
	private String msjEnviadoPorNombre;
	private String msjContenido;
}

