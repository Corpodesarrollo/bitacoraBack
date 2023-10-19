package linktic.lookfeel.dtos;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MensajeHomeDTO {

	private long msjCodigo;
	private String msjAsunto;
	private LocalDate msjFecha;
	private LocalDate msjFechaIni;
    private LocalDate msjFechaFin;
	private Long msjEstado;


}

