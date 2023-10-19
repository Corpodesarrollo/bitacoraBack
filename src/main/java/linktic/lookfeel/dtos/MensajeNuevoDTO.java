package linktic.lookfeel.dtos;
import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MensajeNuevoDTO {
	private String asunto;
    private String contenido;
    private LocalDate fecha;
    private LocalDate fecha_inicio;
    private LocalDate fecha_finalizacion;
    private Long enviado_por;
    private List<String> perfiles;
    private List<String> localidades;
    private List<String> colegios;
    private List<String> sedes;
    private List<String> jornadas;
    

}
