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

@Entity
@Table(name="USUARIO")
public class Usuario implements Serializable{
	
	

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="USULOGIN", length=15)
	private String usuLogin;
	
	@Column(name="USUPASSWORD", length=32)
	private String usuPassword;
	
	@Column(name="USUCODJERAR", length=11)
	private Long usuCodJerar;
	
	@Column(name="USUPERFCODIGO", length=10)
	private String usuPerfCodigo;
	
	@Column(name="USUPERNUMDOCUM", length=12)
	private Long usuPerNumDocum;
	
	@Column(name="USUPASSWORDTEMPORAL", length=1)
	private String usuPasswordTemporal;
	
	@Column(name="USUESTADO", length=1)
	private Long usuEstado;
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	

	public String getUsuLogin() {
		return usuLogin;
	}

	public void setUsuLogin(String usuLogin) {
		this.usuLogin = usuLogin;
	}

	public String getUsuPassword() {
		return usuPassword;
	}

	public void setUsuPassword(String usuPassword) {
		this.usuPassword = usuPassword;
	}

	public Long getUsuCodJerar() {
		return usuCodJerar;
	}

	public void setUsuCodJerar(Long usuCodJerar) {
		this.usuCodJerar = usuCodJerar;
	}

	public String getUsuPerfCodigo() {
		return usuPerfCodigo;
	}

	public void setUsuPerfCodigo(String usuPerfCodigo) {
		this.usuPerfCodigo = usuPerfCodigo;
	}

	public Long getUsuPerNumDocum() {
		return usuPerNumDocum;
	}

	public void setUsuPerNumDocum(Long usuPerNumDocum) {
		this.usuPerNumDocum = usuPerNumDocum;
	}

	public String getUsuPasswordTemporal() {
		return usuPasswordTemporal;
	}

	public void setUsuPasswordTemporal(String usuPasswordTemporal) {
		this.usuPasswordTemporal = usuPasswordTemporal;
	}

	public Long getUsuEstado() {
		return usuEstado;
	}

	public void setUsuEstado(Long usuEstado) {
		this.usuEstado = usuEstado;
	}	

	
	

}
