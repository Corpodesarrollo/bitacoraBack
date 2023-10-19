package linktic.lookfeel.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

@Entity

@Getter
@Setter
public class DatosEstudiante {
	
	@Column(name = "TIPODOCUMENTO")
	public Long tipoDocumento;
	
	@Column(name = "NUMERODOCUMENTO")
	public String numeroDocumento;
	
	@Id
	@Column(name = "CODIGOESTUDIANTE")
	public Long codigoEstudiante;
	
	@Column(name = "NOMBREESTUDIANTE")
	public String nombreEstudiante;
		
	@Column(name = "CODIGOINSTITUCION")
	public Long codigoInstitucion;
	
	@Column(name = "CODIGOSEDE")
	public Long codigoSede;
		
	@Column(name = "JORNADA")
	public Long jornada;
	
	@Column(name = "METODOLOGIA")
	public Long metodologia;
	
	@Column(name = "GRADO")
	public Long grado;
	
	@Column(name = "GRUPO")
	public Long grupo;

}
