package linktic.lookfeel.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="CATEGORIA")
@Getter
@Setter
public class Categoria  {


    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="CATCODIGO")
    private Long catCodigo;

    @Column(name="CATNOMBRE")
    private String catNombre;

    @Column(name="CATDESCRIPCION")
    private String catDescripcion;

    @Column(name="CATORDEN")
    private Long catOrden;


    @Column(name="CATIMAGEN")
    private String catImagen;

}
