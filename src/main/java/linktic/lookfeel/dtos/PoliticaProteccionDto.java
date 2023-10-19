package linktic.lookfeel.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PoliticaProteccionDto {
	
	private Long id;
	
	private String contenido;
	
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private LocalDate  fecha;
	
	private boolean estado;
	
	private String tipoPolitica;
	
	private Long version;
}

