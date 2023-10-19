package linktic.lookfeel.dtos;

import linktic.lookfeel.model.ConstantePk;
import lombok.*;

import javax.persistence.Column;
import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConstanteDto {

    private Long tipo;
    private Long codigo;
    private String nombre;


    @Override
    public String toString() {
        return "ConstanteDto{" +
                "tipo=" + tipo +
                ", codigo=" + codigo +
                ", nombre='" + nombre + '\'' +
                '}';
    }

    public void setListManuals(Long tipo, Long codigo, String nombre) {
        this.setTipo(tipo);
        this.setCodigo(codigo);
        this.setNombre(nombre);
    }
}
