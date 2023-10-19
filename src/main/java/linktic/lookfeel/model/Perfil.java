package linktic.lookfeel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
*
* @author Ing. Paulo Perdomo
* @descripcion Modelo del requerimiento 1, Concursos
* @fechacreacion 27/02/2023
* @fechamodificacion 27/02/2023
* @requrimiento 05  24/01/2023
* @version 1.0
*/

@Entity
@Table(name = "PERFIL")

@Getter
@Setter
public class Perfil {
	
	@Id
	@Column(name = "PERFCODIGO")
	public String perfcodigo;
	
	@Column(name = "PERFNOMBRE")
	public String perfnombre;
	
	@Column(name = "PERFDESCRIPCION")
	public String perfdescripcion;
	
	@Column(name = "PERFABREVIATURA")
	public String perfabreviatura;
	
	@Column(name = "PERFNIVEL")
	public Long perfnivel;
	
	@Column(name = "PERFPERMISO")
	public Long perfpermiso;
	
	

}
