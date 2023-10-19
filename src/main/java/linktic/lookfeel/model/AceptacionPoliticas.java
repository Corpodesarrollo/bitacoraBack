package linktic.lookfeel.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;


@Getter
@Setter
@Entity
@Table(name = "ACEPTACION_POLITICAS")
public class AceptacionPoliticas{

	@Id
    @GeneratedValue(generator = "ACEPTACION_POLITICAS_SEQ1")
    @SequenceGenerator(name="ACEPTACION_POLITICAS_SEQ1",sequenceName="ACEPTACION_POLITICAS_SEQ1", allocationSize=1)
    @Column(name="IDACEPTACION", length=11)
    private Long id;
	
	@Column(name="USULOGIN", length=15)
    private String usuario;
	
	@Column(name="IDPOLITICA", length=11)
    private Long idPolitica;
	
	@Column(name="ACEPTADA", length=1)
    private Long aceptada;
	
	@Column(name="FECHA_ACEPTACION")
    private LocalDateTime fechaAceptacion;
	
	@Column(name="REENVIAR")
    private Long reenviar;
}
