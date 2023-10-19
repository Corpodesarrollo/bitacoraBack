package linktic.lookfeel.security.resources;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class GrupoServicio.
 */
@Entity
@Table(name="GRUPO_SERVICIO")

/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Builder

/**
 * Gets the gruposerprograma.
 *
 * @return the gruposerprograma
 */
@Getter

/**
 * Sets the gruposerprograma.
 *
 * @param gruposerprograma the new gruposerprograma
 */
@Setter

/**
 * Instantiates a new grupo servicio.
 *
 * @param gruposercodigo the gruposercodigo
 * @param gruserenombre the gruserenombre
 * @param grusernivel the grusernivel
 * @param gruposerprograma the gruposerprograma
 */
@AllArgsConstructor

/**
 * Instantiates a new grupo servicio.
 */
@NoArgsConstructor
public class GrupoServicio implements Serializable {

    /** The gruposercodigo. */
    @Id
    @Column(name="GRUSERCODIGO", length=10)
    private String gruposercodigo;

    /** The gruserenombre. */
    @Column(name="GRUSERNOMBRE")
    private String gruserenombre;

    /** The grusernivel. */
    @Column(name="GRUSERNIVEL")
    private Long grusernivel;

    /** The gruposerprograma. */
    @Column(name="GRUSERPROGRAMA")
    private Long gruposerprograma;


}
