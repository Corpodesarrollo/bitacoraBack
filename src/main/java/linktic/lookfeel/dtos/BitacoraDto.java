package linktic.lookfeel.dtos;

public class BitacoraDto {

	private Long usuario;
    private Long modulo;
    private Long submodulo;
    private Long tipoLog;
    private Long colegio;
    private Long jornada;
    private Long sede;
    private Long perfil;
    private String descripcion;
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
