package linktic.lookfeel.dtos;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuDto {

    private String serCatCodigo;
    private String catNombre;
    private String catImagen;
    private String serCodigo;

    private String serRecurso;
    private String serTarget;
    private String serNombre;

   private String parSerCodigo;
   private String parNombre;
   private String parValor;



    @Override
    public String toString() {
        return "MenuDto{" +
                "serCatCodigo='" + serCatCodigo + '\'' +
                ", catNombre='" + catNombre + '\'' +
                ", catImagen='" + catImagen + '\'' +
                ", serCodigo='" + serCodigo + '\'' +
                ", serRecurso='" + serRecurso + '\'' +
                ", serTarget='" + serTarget + '\'' +
                ", serNombre='" + serNombre + '\'' +
                ", parSerCodigo='" + parSerCodigo + '\'' +
                ", parNombre='" + parNombre + '\'' +
                ", parValor='" + parValor + '\'' +
                '}';
    }
}
