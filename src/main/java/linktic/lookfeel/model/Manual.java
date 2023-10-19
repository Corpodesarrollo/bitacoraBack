package linktic.lookfeel.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "MANUALES")
public class Manual {

    @Id
    @GeneratedValue(generator = "SEQ_MANUALES")
    @SequenceGenerator(name="SEQ_MANUALES",sequenceName="SEQ_MANUALES", allocationSize=1)
    @Column(name="ID", length=11)
    private Long id;
    @Column(name="NOMBRE_ARCHIVO", length=300)
    private String nombreArchivo;
    @Column(name="ACTIVO", length=1)
    private Long activo;
    @Column(name="TIPO_MANUAL", length=10)
    private Long tipoManual;
    @Column(name="FECHA")
    private Date fecha;
}
