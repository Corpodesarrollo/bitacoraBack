package linktic.lookfeel.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// TODO: Auto-generated Javadoc
/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Builder

/**
 * Gets the pernumdocum.
 *
 * @return the pernumdocum
 */
@Getter

/**
 * Sets the pernumdocum.
 *
 * @param pernumdocum the new pernumdocum
 */
@Setter

/**
 * Instantiates a new personal pk.
 *
 * @param percodjerar the percodjerar
 * @param pernumdocum the pernumdocum
 */
@AllArgsConstructor

/**
 * Instantiates a new personal pk.
 */
@NoArgsConstructor
public class PersonalPk implements Serializable{


	/** The percodjerar. */
	private Long percodjerar;

	/** The pernumdocum. */
	private String pernumdocum;


	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;


    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
	public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof PersonalPk)) return false;
        PersonalPk pk = (PersonalPk) obj;
        return pk.percodjerar == this.percodjerar && pk.pernumdocum.equals(this.pernumdocum);
    }

    @Override
    public int hashCode() {
    	final int prime = 31;
        int result = 1;
        result = prime * result
                + ((percodjerar == null) ? 0 : percodjerar.hashCode());
        return result;
    }



}
