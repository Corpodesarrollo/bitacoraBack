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
	public Long grupoEspacio;	
	
	@Column(name = "GRUCODCOORD")
	public Long grupoCoordinador;	
	
	@Column(name = "GRUCUPO")
	public Long grupoCupo;	
	
	@Column(name = "GRUORDEN")
	public Long grupoOrdden;	
	
	@Column(name = "GRUESTADO")
	public Long grupoEstado;	
	
	@Column(name = "GRUFECHA")
	public Date grupoFecha;	
	
	@Column(name = "GRUJERGRUPO")
	public Long gruJerGrupo;	

}
