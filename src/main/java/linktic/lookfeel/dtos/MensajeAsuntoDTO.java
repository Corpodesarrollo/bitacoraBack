package linktic.lookfeel.dtos;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MensajeAsuntoDTO {
	
	private long msjCodigo;
	private String msjAsunto;
	private LocalDate msjFecha;
	private LocalDate msjFechaIni;
    private LocalDate msjFechaFin;
	private Long msjEnviadoPor;
	private String msjEnviadoPorNombre;
	private Long msjEstado;
	//private Long totalRegistros;
	//private int numeroPaginas;
	//private int paginaActual;
	

	

}

