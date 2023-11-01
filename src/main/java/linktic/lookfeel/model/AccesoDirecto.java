package linktic.lookfeel.model;

/**
*
* @author Ing. Alexander A. Muñoz Coneo
* @fechacreacion 19/07/2023
* @requrimiento HU007_SED, HU008_SED
* @version 1.0
*/



import java.io.Serializable;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="ACCESO_DIRECTO")

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class AccesoDirecto implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ACDICODIGO", length=10)
	private Long acdicodigo;
	
	@Column(name="ACDINOMBRE", length=150)
	private String acdinombre;
	
	@Column(name="ACDIPERMISO", length=150)
	private String acdipermiso;
	
	@Column(name="ACDIRUTA", length=255)
	private String acdiruta;
	
	@Column(name="ACDIACTIVO", length=1)
	private Long acdiactivo;
		
}
