package linktic.lookfeel.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="MENSAJE")
@Getter
@Setter
public class Mensaje{
	
	@Id
	@Column(name="MSJCODIGO", length=6)
	private Long msjCodigo;
	
	@Column(name="MSJASUNTO", length=300)
	private String msjAsunto;
	
	@Column(name="MSJFECHA")
	private LocalDate msjFecha;
	
	@Column(name="MSJFECHAINI")
	private LocalDate msjFechaIni;
	
	@Column(name="MSJFECHAFIN")
	private LocalDate msjFechaFin;
	
	@Column(name="MSJCONTENIDO", length=3000)
	private String msjContenido;
	
	@Column(name="MSJENVIADOPOR", length=1)
	private Long msjEnviadoPor;
	
	@Column(name="MSJENVIADOAPERFIL", length=3000)
	private String msjEnviadoPerfil;
	
	@Column(name="MSJENVIADOALOCAL", length=3000)
	private String msjEnviadoAlocal;
	
	@Column(name="MSJENVIADOACOLEG", length=3000)
	private String msjEnviadoAcoleg;
	
	@Column(name="MSJENVIADOASEDE", length=3000)
	private String msjEnviadoAsede;
	
	@Column(name="MSJENVIADOAJORN", length=3000)
	private String msjEnviadoAjorn;
	
	@Column(name="MSJUSUARIO", length=10)
	private String msjUsuario;

	@Column(name="MSJESTADO", length=1)
	private Long msjEstado;

}
