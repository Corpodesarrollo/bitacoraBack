package linktic.lookfeel.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "BITACORA")
public class Bitacora {
		@Id
	    @GeneratedValue(generator = "BITACORA_SEQ1")
	    @SequenceGenerator(name="BITACORA_SEQ1",sequenceName="BITACORA_SEQ1", allocationSize=1)
	    @Column(name="ID")
	    private Long id;
		
		@Column(name="FECHA_REGISTRO")
	    private LocalDateTime fechaRegistro;
		
		@Column(name="USUARIO")
	    private Long usuario;
		
		@Column(name="MODULO")
	    private Long modulo;
		
		@Column(name="SUBMODULO")
	    private Long submodulo;
		
		@Column(name="TIPO_LOG_BITACORA")
	    private Long tipoLog;
		
		@Column(name="COLEGIO")
	    private Long colegio;
		
		@Column(name="JORNADA")
	    private Long jornada;
		
		@Column(name="SEDE")
	    private Long sede;
		
		@Column(name="PERFIL")
	    private Long perfil;
		
		@Column(name="DESCRIPCION",length = 250)
	    private String descripcion;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public LocalDateTime getFechaRegistro() {
			return fechaRegistro;
		}

		public void setFechaRegistro(LocalDateTime fechaRegistro) {
			this.fechaRegistro = fechaRegistro;
		}

		public Long getUsuario() {
			return usuario;
		}

		public void setUsuario(Long usuario) {
			this.usuario = usuario;
		}

		public Long getModulo() {
			return modulo;
		}

		public void setModulo(Long modulo) {
			this.modulo = modulo;
		}

		public Long getSubmodulo() {
			return submodulo;
		}

		public void setSubmodulo(Long submodulo) {
			this.submodulo = submodulo;
		}

		public Long getTipoLog() {
			return tipoLog;
		}

		public void setTipoLog(Long tipoLog) {
			this.tipoLog = tipoLog;
		}

		public Long getColegio() {
			return colegio;
		}

		public void setColegio(Long colegio) {
			this.colegio = colegio;
		}

		public Long getJornada() {
			return jornada;
		}

		public void setJornada(Long jornada) {
			this.jornada = jornada;
		}

		public Long getSede() {
			return sede;
		}

		public void setSede(Long sede) {
			this.sede = sede;
		}

		public Long getPerfil() {
			return perfil;
		}

		public void setPerfil(Long perfil) {
			this.perfil = perfil;
		}

		public String getDescripcion() {
			return descripcion;
		}

		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}
}