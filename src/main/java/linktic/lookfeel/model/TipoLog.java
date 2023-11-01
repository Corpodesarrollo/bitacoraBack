package linktic.lookfeel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "TIPO_LOG_BITACORA")
public class TipoLog {

	@Id
    @GeneratedValue(generator = "TIPO_LOG_SEQ1")
    @SequenceGenerator(name="TIPO_LOG_SEQ1",sequenceName="TIPO_LOG_SEQ1", allocationSize=1)
    @Column(name="ID")
    private Long id;
	
	@Column(name="NOMBRE",length = 250)
    private String nombre;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
}