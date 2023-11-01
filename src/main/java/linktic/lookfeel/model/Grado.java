package linktic.lookfeel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity 
@Getter
@Setter
public class Grado {
	
	@Id
	@Column(name = "GRACODIGO")
	public long gracodigo;
	
	@Column(name = "GRANOMBRE")
	public String granombre;
	

}
