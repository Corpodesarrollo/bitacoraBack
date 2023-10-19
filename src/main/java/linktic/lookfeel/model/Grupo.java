package linktic.lookfeel.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "GRUPO")
@Getter
@Setter
public class Grupo {
	
	
	@EmbeddedId
    private GrupoPk grupoPk;

	@Column(name = "GRUNOMBRE")
	public String grupoNombre;
	
	@Column(name = "GRUCODESPACIO")
	public long grupoEspacio;	
	
	@Column(name = "GRUCODCOORD")
	public long grupoCoordinador;	
	
	@Column(name = "GRUCUPO")
	public long grupoCupo;	
	
	@Column(name = "GRUORDEN")
	public long grupoOrdden;	
	
	@Column(name = "GRUESTADO")
	public long grupoEstado;	
	
	@Column(name = "GRUFECHA")
	public Date grupoFecha;	
	
	@Column(name = "GRUJERGRUPO")
	public long gruJerGrupo;	

}
