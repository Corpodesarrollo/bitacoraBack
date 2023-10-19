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
public class GrupoPk implements Serializable {

    @Column(name = "GRUCODJERAR")
    private Long gruCodJerarquia;
    @Column(name = "GRUCODIGO")
    private Long gruCodigo;

    public GrupoPk(Long gruCodJerarquia) {
        this.gruCodJerarquia = gruCodJerarquia;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GrupoPk that = (GrupoPk) o;
        return Objects.equals(gruCodJerarquia, that.gruCodJerarquia) && Objects.equals(gruCodigo, that.gruCodigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gruCodJerarquia, gruCodigo);
    }
}
