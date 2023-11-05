package linktic.lookfeel.dtos;

import java.sql.Date;

public class BitacoraFiltroDto {
	
	private Date fechaInicio;
	private Date fechaFin;
	private String usuario = "";
	private Long colegio = 0L;
	private Long sede = 0L;
	private Long jornada = 0L;
	private Long tipoLogBitacora = 0L;
	private String descripcion = "";
	private int paginaActual;
	private int itemsPagina;
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario==null? "" : usuario;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion==null? "" : descripcion;
	}
	public int getPaginaActual() {
		return paginaActual;
	}
	public void setPaginaActual(int paginaActual) {
		this.paginaActual = paginaActual;
	}
	public int getItemsPagina() {
		return itemsPagina;
	}
	public void setItemsPagina(int itemsPagina) {
		this.itemsPagina = itemsPagina;
	}
	public Long getColegio() {
		return colegio;
	}
	public void setColegio(Long colegio) {
		this.colegio = colegio==null? 0 : colegio;
	}
	public Long getSede() {
		return sede;
	}
	public void setSede(Long sede) {
		this.sede = sede==null? 0 : sede;
	}
	public Long getJornada() {
		return jornada;
	}
	public void setJornada(Long jornada) {
		this.jornada = jornada==null? 0 : jornada;
	}
	public Long getTipoLogBitacora() {
		return tipoLogBitacora;
	}
	public void setTipoLogBitacora(Long tipoLogBitacora) {
		this.tipoLogBitacora = tipoLogBitacora==null? 0 : tipoLogBitacora;
	}
}