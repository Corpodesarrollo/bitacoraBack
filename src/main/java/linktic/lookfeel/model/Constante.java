package linktic.lookfeel.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "G_CONSTANTE")
@Getter
@Setter
public class Constante {

    @EmbeddedId
    private ConstantePk constantePk;

    @Column(name = "G_CONCODPADRE")
    private Long codPadre;
    
    @Column(name = "G_CONNOMBRE")
    private String nombre;
    
    @Column(name = "G_CONABREV")
    private String abrev;

    @Column(name = "G_CONORDEN")
    private Long orden;

    @Column(name = "G_ACTCOMP")
    private Date actComp;


}
