package linktic.lookfeel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity

@Getter
@Setter
public class Jornada {
	
	@Id
	@Column(name = "SEDJORCODJOR")
	public Long sedjorcodjor;
	
	@Column(name = "G_CONNOMBRE")
	public String g_connombre;

}
