package linktic.lookfeel.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


@Getter
@Setter
@Entity
@Table(name = "POLITICA_PROTECCION_DATOS")
public class PoliticasProteccion {
	
	@Id
    @GeneratedValue(generator = "POLITICA_PROTECCION_DATOS_SEQ")
    @SequenceGenerator(name="POLITICA_PROTECCION_DATOS_SEQ",sequenceName="POLITICA_PROTECCION_DATOS_SEQ", allocationSize=1)
    @Column(name="ID", length=11)
    private Long id;
	
	
	@Column(name="CONTENIDO", length=255)
    private String contenido;
	
	@Column(name="FECHA")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate  fecha;
	
	@Column(name="VERSION", length=10)
    private Long  version;
	
	@Column(name="CODIGO_TIPO", length=20)
    private String tipoPolitica;
	
	@Column(name="ESTADO", length=1)
    private Long  estado;

}
