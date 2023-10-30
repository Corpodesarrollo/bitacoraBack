package linktic.lookfeel.model;

import java.io.Serializable;
import java.sql.Blob;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// TODO: Auto-generated Javadoc
/**
 * The Class Personal.
 */
@Entity
@Table(name="PERSONAL")
@IdClass(PersonalPk.class)

/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Builder

/**
 * Gets the peremail.
 *
 * @return the peremail
 */
@Getter

/**
 * Sets the peremail.
 *
 * @param perfotoemail the new peremail
 */
@Setter

/**
 * Instantiates a new personal.
 *
 * @param percodjerar the percodjerar
 * @param pertipo the pertipo
 * @param perexpdoccoddep the perexpdoccoddep
 * @param perexpdoccodmun the perexpdoccodmun
 * @param pertipdocum the pertipdocum
 * @param pernumdocum the pernumdocum
 * @param pernombre1 the pernombre 1
 * @param pernombre2 the pernombre 2
 * @param perapellido1 the perapellido 1
 * @param perapellido2 the perapellido 2
 * @param perfechanac the perfechanac
 * @param perlugnaccoddep the perlugnaccoddep
 * @param perlugnaccodmun the perlugnaccodmun
 * @param peredad the peredad
 * @param pergenero the pergenero
 * @param peretnia the peretnia
 * @param perdireccion the perdireccion
 * @param pertelefono the pertelefono
 * @param perzona the perzona
 * @param perlocvereda the perlocvereda
 * @param perbarcorreg the perbarcorreg
 * @param perescalafon the perescalafon
 * @param perfoto the perfoto
 * @param peremail the peremail
 */
@AllArgsConstructor

/**
 * Instantiates a new personal.
 */
@NoArgsConstructor
public class Personal implements Serializable {

	/** The percodjerar. */
	@Id
	@Column(name="PERCODJERAR")
	private Long percodjerar;

	/** The pertipo. */
	@Column(name="PERTIPO")
	private Long pertipo;

	/** The perexpdoccoddep. */
	@Column(name="PEREXPDOCCODDEP")
	private Long perexpdoccoddep;

	/** The perexpdoccodmun. */
	@Column(name="PEREXPDOCCODMUN")
	private Long perexpdoccodmun;

	/** The pertipdocum. */
	@Column(name="PERTIPDOCUM")
	private Long pertipdocum;

	/** The pernumdocum. */
	@Id
	@Column(name="PERNUMDOCUM")
	private String pernumdocum;

	/** The pernombre 1. */
	@Column(name="PERNOMBRE1")
	private String pernombre1;

	/** The pernombre 2. */
	@Column(name="PERNOMBRE2")
	private String pernombre2;

	/** The perapellido 1. */
	@Column(name="PERAPELLIDO1")
	private String perapellido1;

	/** The perapellido 2. */
	@Column(name="PERAPELLIDO2")
	private String perapellido2;

	/** The perfechanac. */
	@Column(name="PERFECHANAC")
	private Date perfechanac;

	/** The perlugnaccoddep. */
	@Column(name="PERLUGNACCODDEP")
	private Long perlugnaccoddep;

	/** The perlugnaccodmun. */
	@Column(name="PERLUGNACCODMUN")
	private Long perlugnaccodmun;

	/** The peredad. */
	@Column(name="PEREDAD")
	private Long peredad;

	/** The pergenero. */
	@Column(name="PERGENERO")
	private Long pergenero;

	/** The peretnia. */
	@Column(name="PERETNIA")
	private Long peretnia;

	/** The perdireccion. */
	@Column(name="PERDIRECCION")
	private String perdireccion;

	/** The pertelefono. */
	@Column(name="PERTELEFONO")
	private String pertelefono;

	/** The perzona. */
	@Column(name="PERZONA")
	private Long perzona;

	/** The perlocvereda. */
	@Column(name="PERLOCVEREDA")
	private String perlocvereda;

	/** The perbarcorreg. */
	@Column(name="PERBARCORREG")
	private String perbarcorreg;

	/** The perescalafon. */
	@Column(name="PERESCALAFON")
	private String perescalafon;

	/** The perfoto. */
	@Lob
	@Column(name="PERFOTO")
	private Blob perfoto;

	/** The peremail. */
	@Column(name="PEREMAIL")
	private String peremail;
	
	@Column(name="PERFOTOACT")
	private Long perfotoact;
	
	


	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;




	public String getPernumdocum() {
		return pernumdocum;
	}




	public void setPernumdocum(String pernumdocum) {
		this.pernumdocum = pernumdocum;
	}




	public String getPernombre1() {
		return pernombre1;
	}




	public void setPernombre1(String pernombre1) {
		this.pernombre1 = pernombre1;
	}




	public String getPernombre2() {
		return pernombre2;
	}




	public void setPernombre2(String pernombre2) {
		this.pernombre2 = pernombre2;
	}




	public String getPerapellido1() {
		return perapellido1;
	}




	public void setPerapellido1(String perapellido1) {
		this.perapellido1 = perapellido1;
	}




	public String getPerapellido2() {
		return perapellido2;
	}




	public void setPerapellido2(String perapellido2) {
		this.perapellido2 = perapellido2;
	}
	
	

}
