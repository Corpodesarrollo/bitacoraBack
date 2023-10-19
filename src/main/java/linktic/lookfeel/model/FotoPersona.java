package linktic.lookfeel.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "LOG_FOTOGRAFIA_PERSONAL")

@Getter
@Setter
public class FotoPersona {

	@Id
	@GeneratedValue(generator = "SeqFotoPersona")
	@SequenceGenerator(name="SeqFotoPersona",sequenceName="SEQ_LOG_FOT_PER", allocationSize=1)
	@Column(name="ID", length=11)
	private Long Id;

	@Column(name="FECHA")
	private Date Fecha;

	@Column(name="ACTUALIZADO",length = 19)
	private Long Actualizado;

	@Column(name="NOMBRE_ARCHIVO", length = 200)
	private String NombreArchivo;

	@Column(name="USUARIO_LOGIN_ACTIVIDAD", length = 15)
	private String UsuarioLoginActividad;

	@Column(name="PERSONAL_LOGUSUIN",length = 12)
	private String PersonalLogUsuin;
	
	@Column(name="PERNUMDOCUM",length = 12)
	private String PerNumDocum;


	public FotoPersona() {

	}

	public FotoPersona(Long id, Date fecha, Long actualizado, String nombreArchivo,
			String usuarioLoginActividad, String personalLogUsuin) {
		super();
		Id = id;
		Fecha = fecha;
		Actualizado = actualizado;
		NombreArchivo = nombreArchivo;
		UsuarioLoginActividad = usuarioLoginActividad;
		PersonalLogUsuin = personalLogUsuin;
	}

}
