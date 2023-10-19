package linktic.lookfeel.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The Class Sede.
 */
@Entity
@Table(name="SEDE")
public class Sede implements Serializable {

	/** The codigo. */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="SEDCODIGO", length=11)
	private Long codigo;


	/** The institucion. */
	@Column(name="SEDCODINS")
	private Long institucion;

	/** The nombre. */
	@Column(name="SEDNOMBRE")
	private String nombre;

	/** The estado. */
	@Column(name="SEDESTADO")
	private String estado;

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



}
