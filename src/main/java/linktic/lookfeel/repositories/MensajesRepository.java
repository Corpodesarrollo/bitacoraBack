package linktic.lookfeel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import linktic.lookfeel.dtos.MensajeDTO;
import linktic.lookfeel.model.Mensajes;



@Repository 
public interface MensajesRepository extends JpaRepository<Mensajes, Long> {

    @Query(nativeQuery = true, value= " SELECT DISTINCT msj.ID, msj.ASUNTO,PERSONAL.PERNOMBRE1 || ' ' || PERSONAL.PERAPELLIDO1 AS ENVIADO_POR ,  msj.CONTENIDO, msj.LEIDO, msj.FECHA ,msj.FECHA_CREACION "
    		+ "FROM MENSAJES MSJ "
    		+ "JOIN V_LOCALIDAD lc ON MSJ.LOCALIDAD =lc.ID_LOCALIDAD "
    		+ "JOIN INSTITUCION ins  ON MSJ.COLEGIO  =INS.INSCODIGO  "
    		+ "JOIN SEDE SD  ON MSJ.SEDE  =SD.SEDCODINS "
    		+ "JOIN PERSONAL  ON MSJ.ENVIADO_POR =PERSONAL.PERNUMDOCUM ")
    Page<MensajeDTO> getAllMensajes(Pageable pageable);

    @Query(nativeQuery = true, value= "SELECT DISTINCT msj.ID, msj.ASUNTO, PERSONAL.PERNOMBRE1 || ' ' || PERSONAL.PERAPELLIDO1 AS ENVIADO_POR, msj.CONTENIDO, msj.LEIDO, msj.FECHA, msj.FECHA_CREACION "
            + "FROM MENSAJES MSJ "
            + "JOIN V_LOCALIDAD lc ON MSJ.LOCALIDAD = lc.ID_LOCALIDAD "
            + "JOIN INSTITUCION ins ON MSJ.COLEGIO = INS.INSCODIGO "
            + "JOIN SEDE SD ON MSJ.SEDE = SD.SEDCODINS "
            + "JOIN PERSONAL ON MSJ.ENVIADO_POR = PERSONAL.PERNUMDOCUM "
            + "WHERE msj.ID = :id")
    MensajeDTO getOneMensajeById(@Param("id") Long id);
	
}
