package linktic.lookfeel.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
public class ConstantePk implements Serializable {

    @Column(name = "G_CONTIPO")
    private Long tipo;
    @Column(name = "G_CONCODIGO")
    private Long codigo;

    public ConstantePk(Long tipo) {
        this.tipo = tipo;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConstantePk that = (ConstantePk) o;
        return Objects.equals(tipo, that.tipo) && Objects.equals(codigo, that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tipo, codigo);
    }
}
