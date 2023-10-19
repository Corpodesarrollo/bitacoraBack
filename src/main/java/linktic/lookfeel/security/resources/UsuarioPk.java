package linktic.lookfeel.security.resources;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class UsuarioPk.
 */
@Embeddable

/**
 * Gets the usucodjerar.
 *
 * @return the usucodjerar
 */
@Getter

/**
 * Sets the usucodjerar.
 *
 * @param USUCODJERAR the new usucodjerar
 */
@Setter

/**
 * Instantiates a new usuario pk.
 *
 * @param USULOGIN the usulogin
 * @param USUCODJERAR the usucodjerar
 */
@AllArgsConstructor

/**
 * Instantiates a new usuario pk.
 */
@NoArgsConstructor
public class UsuarioPk implements Serializable {

	
		/** The usulogin. */
		private String USULOGIN;

	    /** The usucodjerar. */
    	private Long USUCODJERAR;

	    /** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1L;

		/* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		public int hashCode() {
	        return (int) USULOGIN.hashCode() + USUCODJERAR.intValue();
	    }

	    /* (non-Javadoc)
    	 * @see java.lang.Object#equals(java.lang.Object)
    	 */
    	public boolean equals(Object obj) {
	        if (obj == this) return true;
	        if (!(obj instanceof UsuarioPk)) return false;
	        UsuarioPk pk = (UsuarioPk) obj;
	        return pk.USUCODJERAR == USUCODJERAR && pk.USULOGIN.equals(USULOGIN);
	    }

}
