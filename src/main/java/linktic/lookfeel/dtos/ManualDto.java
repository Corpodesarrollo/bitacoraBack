package linktic.lookfeel.dtos;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ManualDto {


    private Long id;

    private String nombreArchivo;

    private Long activo;

    private Long tipoManual;

    private String encoded;

    private Date fecha;


    @Override
    public String toString() {
        return "ManualDto{" +
                "id=" + id +
                ", nombreArchivo='" + nombreArchivo + '\'' +
                ", activo=" + activo +
                ", tipoManual=" + tipoManual +
                '}';
    }


    public void createManualDto(Long id, Long tipoManual, Long activo, String nombreArchivo, Date fecha) {
        this.setId(id);
        this.setTipoManual(tipoManual);
        this.setActivo(activo);
        this.setNombreArchivo(nombreArchivo);
        this.setFecha(fecha);
    }
}
