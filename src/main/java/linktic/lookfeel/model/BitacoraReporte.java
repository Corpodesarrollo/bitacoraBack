package linktic.lookfeel.model;

import java.sql.Date;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
public class BitacoraReporte {
	
		@Id
		@Column(name="ID")
		private Long id;
		
		@Column(name="FECHA_REGISTRO")
	    private LocalDateTime fechaRegistro;
		
		@Column(name="USUARIO",length = 15)
	    private String usuario;
		
		@Column(name="PERFNOMBRE",length = 60)
	    private String perfil;
		
		@Column(name="SERNOMBRE",length = 150)
	    private String modulo;
		
		@Column(name="CATNOMBRE",length = 25)
	    private String submodulo;
		
		@Column(name="TIPO_LOG_BITACORA",length = 30)
	    private String tipoLog;
				
		@Column(name="DESCRIPCION",length = 250)
	    private String descripcion;
		
		public BitacoraReporte() {
			
		}
		
		public BitacoraReporte(Long id, LocalDateTime fechaRegistro, String perfil, String modulo, String submodulo, String tipoLog, String descripcion) {
			this.id = id;
			this.fechaRegistro = fechaRegistro;
			this.perfil = perfil;
			this.modulo = modulo;
			this.submodulo = submodulo;
			this.tipoLog = tipoLog;
			this.descripcion = descripcion;
		}

		public LocalDateTime getFechaRegistro() {
			return fechaRegistro;
		}

		public void setFechaRegistro(LocalDateTime fechaRegistro) {
			this.fechaRegistro = fechaRegistro;
		}

		public String getUsuario() {
			return usuario;
		}

		public void setUsuario(String usuario) {
			this.usuario = usuario;
		}

		public String getModulo() {
			return modulo;
		}

		public void setModulo(String modulo) {
			this.modulo = modulo;
		}

		public String getSubmodulo() {
			return submodulo;
		}

		public void setSubmodulo(String submodulo) {
			this.submodulo = submodulo;
		}

		public String getTipoLog() {
			return tipoLog;
		}

		public void setTipoLog(String tipoLog) {
			this.tipoLog = tipoLog;
		}

		public String getPerfil() {
			return perfil;
		}

		public void setPerfil(String perfil) {
			this.perfil = perfil;
		}

		public String getDescripcion() {
			return descripcion;
		}

		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}
}
