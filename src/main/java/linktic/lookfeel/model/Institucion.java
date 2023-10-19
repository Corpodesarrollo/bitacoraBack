package linktic.lookfeel.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The Class Institucion.
 */
@Entity
@Table(name="INSTITUCION")
public class Institucion implements Serializable {


	/** The codigo. */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="INSCODIGO", length=11)
	private Long codigo;


	/** The nombre. */
	@Column(name="INSNOMBRE")
	private String nombre;


	/** The estado. */
	@Column(name="INSESTADO")
	private String estado;


	/** The codigo dane. */
	@Column(name="INSCODDANE", length=12)
	private Long codigoDane;


	/** The departamento. */
	@Column(name="INSCODDEPTO", length=3)
	private Long departamento;


	/** The municipio. */
	@Column(name="INSCODMUN", length=3)
	private Long municipio;


	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;


	/**
	 * Gets the codigo.
	 *
	 * @return the codigo
	 */
	public Long getCodigo() {
		return codigo;
	}


	/**
	 * Sets the codigo.
	 *
	 * @param codigo the new codigo
	 */
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}


	/**
	 * Gets the nombre.
	 *
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}


	/**
	 * Sets the nombre.
	 *
	 * @param nombre the new nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	/**
	 * Gets the estado.
	 *
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}


	/**
	 * Sets the estado.
	 *
	 * @param estado the new estado
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}


	/**
	 * Gets the codigo dane.
	 *
	 * @return the codigo dane
	 */
	public Long getCodigoDane() {
		return codigoDane;
	}


	/**
	 * Sets the codigo dane.
	 *
	 * @param codigoDane the new codigo dane
	 */
	public void setCodigoDane(Long codigoDane) {
		this.codigoDane = codigoDane;
	}


	/**
	 * Gets the departamento.
	 *
	 * @return the departamento
	 */
	public Long getDepartamento() {
		return departamento;
	}


	/**
	 * Sets the departamento.
	 *
	 * @param departamento the new departamento
	 */
	public void setDepartamento(Long departamento) {
		this.departamento = departamento;
	}


	/**
	 * Gets the municipio.
	 *
	 * @return the municipio
	 */
	public Long getMunicipio() {
		return municipio;
	}


	/**
	 * Sets the municipio.
	 *
	 * @param municipio the new municipio
	 */
	public void setMunicipio(Long municipio) {
		this.municipio = municipio;
	}



}
