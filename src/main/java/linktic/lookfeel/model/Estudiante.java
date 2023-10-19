package linktic.lookfeel.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name="ESTUDIANTE")
public class Estudiante implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ESTCODIGO", length=11)
	private long codigo_estudiante;
	
	@Column(name="ESTTIPODOC", length=1)
	private long tipo_documento;
	
	@Column(name="ESTNUMDOC", length=25)
	private String numero_documento;
	
	@Column(name="ESTNOMBRE1", length=25)
	private String nombre1;	
	
	@Column(name="ESTNOMBRE2", length=25)
	private String nombre2;	
	
	@Column(name="ESTAPELLIDO1", length=25)
	private String apellido1;	
	
	@Column(name="ESTAPELLIDO2", length=25)
	private String apellido2;
	

}
