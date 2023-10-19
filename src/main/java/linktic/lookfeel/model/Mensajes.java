package linktic.lookfeel.model;

import java.io.Serializable;
import java.util.Date;
import java.util.stream.Stream;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="MENSAJES")

@Builder
@Getter
@Setter

@AllArgsConstructor

public class Mensajes implements Serializable{
	/**
	 * ID mensaje
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID", length=11)
	private Long Id;
	
	/**
	 * Asunto mensaje
	 */
	@Column(name="ASUNTO")
	private String Asunto;
	/**
	 * Asunto mensaje
	 */
	@Column(name="FECHA")
	private Date Fecha;
	
	@Column(name="ENVIADO_POR")
	private Long Enviado_por;
	
	@Column(name="CONTENIDO")
	private String Contenido;
	
	@Column(name="LEIDO")
	private Long leido;
	
	@Column(name="FECHA_CREACION")
	private Date fecha_creacion;
	
	@Column(name="FECHA_ACTUALIZACION")
	private Date fecha_actualizacion;
	
	@Column(name="ENVIADO_A")
	private Long enviado_a;
	
	@Column(name="LOCALIDAD")
	private Long localidad;
	
	@Column(name="COLEGIO")
	private Long colegio;
	
	@Column(name="SEDE")
	private Long sede;
	
	@Column(name="JORNADA")
	private Long jornada;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Mensajes() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Stream<Mensajes> stream() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
