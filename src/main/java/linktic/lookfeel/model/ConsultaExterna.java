package linktic.lookfeel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity

@Getter
@Setter
public class ConsultaExterna {
	
	@Id	
	@Column(name = "CE_CONSECUTIVO")
	public long ceConsecutivo;
	
	@Column(name = "CE_TIPO")
	public String ceTipo;
	
	@Column(name = "CE_RUTA_ARCHIVO")
	public String ceRutaArchivo;
	
	@Column(name = "CE_NOMBRE_ARCHIVO")
	public String ceNombreArchivo;
	
	@Column(name = "CE_EXTENSION_ARCHIVO")
	public String ceExtensionArchivo;
	
}
