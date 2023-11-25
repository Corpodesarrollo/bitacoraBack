package linktic.lookfeel.repositories;

import linktic.lookfeel.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MenuRepository extends JpaRepository<Categoria, Long> {



    @Query(nativeQuery = true, value = "SELECT SERCATCODIGO, CATNOMBRE, CATIMAGEN, COUNT(SERCODIGO) \n" +
            "FROM CATEGORIA, MENU \n" +
            "WHERE CATCODIGO=SERCATCODIGO AND GRUSERCODIGO IN \n" +
            "(SELECT SERPERGRUSERCODIGO FROM SERVICIO_PERFIL, GRUPO_SERVICIO \n" +
            "WHERE GRUSERCODIGO=SERPERGRUSERCODIGO AND (:perfil = 0 OR SERPERFPERFCODIGO=:perfil) AND (GRUSERPROGRAMA=0 OR GRUSERPROGRAMA IN \n" +
            "(SELECT INSPROPROGRAMA FROM INSTITUCION_PROGRAMA WHERE INSPROCOLEGIO=:institucion))) AND SERVISIBLE=1 AND SERCATCODIGO NOT IN \n" +
            "(16,17,18,19) AND CATNOMBRE IS NOT NULL GROUP BY SERCATCODIGO, CATNOMBRE, CATIMAGEN")
    List<Object[]> getListGeneralCategory(Long institucion, String perfil);

    @Query(nativeQuery = true, value = "SELECT SERRECURSO, SERTARGET, SERNOMBRE, SERCODIGO \n" +
            "FROM MENU \n" +
            "WHERE SERPUBLICO=1 AND SERVISIBLE=1 AND GRUSERCODIGO IN \n" +
            "(SELECT GRUSERCODIGO FROM GRUPO_SERVICIO WHERE GRUSERPROGRAMA=0 OR GRUSERPROGRAMA IN \n" +
            "(SELECT INSPROPROGRAMA FROM INSTITUCION_PROGRAMA WHERE INSPROCOLEGIO=:institucion)) AND SERNOMBRE IS NOT NULL ORDER BY SERORDEN")
    List<Object[]> getListGeneral(Long institucion);

    @Query(nativeQuery = true, value = "SELECT SERRECURSO, SERTARGET, SERNOMBRE, SERCODIGO, SERCATCODIGO \n" +
            "FROM MENU \n" +
            "WHERE SERPUBLICO=0 AND SERVISIBLE=1 AND GRUSERCODIGO IN \n" +
            "(SELECT SERVICIO_PERFIL.SERPERGRUSERCODIGO \n" +
            "FROM SERVICIO_PERFIL, PERFIL \n" +
            "WHERE (:perfil = 0 OR SERVICIO_PERFIL.SERPERFPERFCODIGO=:perfil) AND SERVICIO_PERFIL.SERPERFPERFCODIGO = PERFCODIGO AND SERPERGRUSERCODIGO IN\n" +
            "(SELECT GRUSERCODIGO FROM GRUPO_SERVICIO WHERE GRUSERPROGRAMA=0 OR GRUSERPROGRAMA IN\n" +
            "(SELECT INSPROPROGRAMA FROM INSTITUCION_PROGRAMA\n" +
            "WHERE INSPROCOLEGIO=:institucion))) AND SERCATCODIGO NOT IN (16,17,18,19) AND SERNOMBRE IS NOT NULL -- arregalr machetaso\n" +
            "ORDER BY SERCATCODIGO, SERORDEN")
    List<Object[]> getListGeneralSubCategory(Long institucion, String perfil);

    @Query(nativeQuery = true, value = "select ParSerCodigo,ParNombre, ParValor from servicio_param " +
            "where ParSerCodigo in (select SerCodigo from servicio where serpublico=1 and servisible=1)")
    List<Object[]> getListGeneralCategoryParam();

    @Query(nativeQuery = true, value = "select ParSerCodigo,ParNombre, ParValor \n" +
            "from servicio_param \n" +
            "where ParSerCodigo in \n" +
            "(select SerCodigo \n" +
            "from servicio where serpublico=0 and servisible=1 and GruSerCodigo in \n" +
            "(select DISTINCT servicio_perfil.SerPerGruSerCodigo \n" +
            "from servicio_perfil, perfil \n" +
            "where (:perfil = 0 OR servicio_perfil.SerPerfPerfCodigo=:perfil)))")
    List<Object[]> getListCategoryPrivateParam(String perfil);

    @Query(nativeQuery = true, value = " SELECT sp.PARNOMBRE,sp.PARVALOR FROM servicio_param sp "
    		+ "JOIN servicio s ON sp.PARSERCODIGO = s.SERCODIGO "
    		+ "WHERE s.SERCODIGO =:codigo OR s.SERRECURSO =:recurso")
    List<Object[]> getServiciosParamPorCodigoRecurso(Long codigo, String recurso);
}
