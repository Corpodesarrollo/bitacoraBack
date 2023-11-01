package linktic.lookfeel.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import linktic.lookfeel.dtos.AceptacionUsuarioDto;
import linktic.lookfeel.model.AceptacionPoliticas;
import linktic.lookfeel.model.PoliticasProteccion;

public interface AceptacionPoliticasRepository extends JpaRepository<AceptacionPoliticas, Long> {
	
	@Query(nativeQuery = true, value = "SELECT\r\n"
			+ "    ap.USULOGIN, CONCAT(per.PERNOMBRE1,per.PERNOMBRE2) nombres,\r\n"
			+ "    CONCAT(per.PERAPELLIDO1,per.PERAPELLIDO2) apellidos,\r\n"
			+ "    MAX(CASE WHEN p.CODIGO_TIPO = 'POLITICA_USO' THEN ap.ACEPTADA ELSE NULL END) AS aceptaUso,\r\n"
			+ "    MAX(CASE WHEN p.CODIGO_TIPO = 'DATOS_PERSONALES' THEN ap.ACEPTADA ELSE NULL END) AS aceptaDatos\r\n"
			+ "FROM ACEPTACION_POLITICAS ap\r\n"
			+ "    LEFT JOIN POLITICA_PROTECCION_DATOS p ON ap.IDPOLITICA = p.ID\r\n"
			+ "    LEFT JOIN PERSONAL per ON ap.USULOGIN = per.PERNUMDOCUM\r\n"
			+ "WHERE p.ESTADO = 1\r\n"
			+ "GROUP BY ap.USULOGIN, \r\n"
			+ "    per.PERNOMBRE1,\r\n"
			+ "    per.PERNOMBRE2, \r\n"
			+ "    per.PERAPELLIDO1,\r\n"
			+ "    per.PERAPELLIDO2")
	List<Object[]>  verListaAceptacion();
	
	
	@Query(nativeQuery = true, value = "SELECT ap.USULOGIN, ap.IDPOLITICA, pp.CODIGO_TIPO, ap.ACEPTADA, ap.REENVIAR\r\n"
			+ "            FROM ACEPTACION_POLITICAS ap\r\n"
			+ "            JOIN POLITICA_PROTECCION_DATOS pp ON ap.IDPOLITICA = pp.ID \r\n"
			+ "            WHERE ap.IDACEPTACION IN (\r\n"
			+ "                SELECT IDACEPTACION\r\n"
			+ "                FROM (\r\n"
			+ "                  SELECT\r\n"
			+ "                           IDACEPTACION, \r\n"
			+ "                            ROW_NUMBER() OVER (PARTITION BY USULOGIN ORDER BY FECHA_ACEPTACION DESC) AS numero\r\n"
			+ "                        FROM\r\n"
			+ "                            ACEPTACION_POLITICAS ap        \r\n"
			+ "                        JOIN POLITICA_PROTECCION_DATOS pp ON ap.IDPOLITICA = pp.ID       \r\n"
			+ "                        WHERE\r\n"
			+ "                            ap.USULOGIN =:usuario                \r\n"
			+ "                            AND CODIGO_TIPO = 'POLITICA_USO'\r\n"
			+ "                                    ) subquery\r\n"
			+ "                                   WHERE numero = 1\r\n"
			+ "                    )")
	List<Object[]> verAceptacionPoliticaUso(String usuario);
	
	@Query(nativeQuery = true, value = "SELECT ap.USULOGIN, ap.IDPOLITICA, pp.CODIGO_TIPO, ap.ACEPTADA, ap.REENVIAR\r\n"
			+ "            FROM ACEPTACION_POLITICAS ap\r\n"
			+ "            JOIN POLITICA_PROTECCION_DATOS pp ON ap.IDPOLITICA = pp.ID \r\n"
			+ "            WHERE ap.IDACEPTACION IN (\r\n"
			+ "                SELECT IDACEPTACION\r\n"
			+ "                FROM (\r\n"
			+ "                  SELECT\r\n"
			+ "                           IDACEPTACION, \r\n"
			+ "                            ROW_NUMBER() OVER (PARTITION BY USULOGIN ORDER BY FECHA_ACEPTACION DESC) AS numero\r\n"
			+ "                        FROM\r\n"
			+ "                            ACEPTACION_POLITICAS ap        \r\n"
			+ "                        JOIN POLITICA_PROTECCION_DATOS pp ON ap.IDPOLITICA = pp.ID       \r\n"
			+ "                        WHERE\r\n"
			+ "                            ap.USULOGIN =:usuario                \r\n"
			+ "                            AND CODIGO_TIPO = 'DATOS_PERSONALES'\r\n"
			+ "                                    ) subquery\r\n"
			+ "                                   WHERE numero = 1\r\n"
			+ "                    )")
	List<Object[]> verAceptacionPoliticaDatos(String usuario);
	
	@Query(nativeQuery = true, value = "SELECT ap.USULOGIN, ap.IDPOLITICA, ap.ACEPTADA, ap.FECHA_ACEPTACION, ap.REENVIAR, ap.IDACEPTACION\r\n"
			+ "            FROM ACEPTACION_POLITICAS ap\r\n"
			+ "            WHERE ap.IDACEPTACION IN (\r\n"
			+ "                SELECT IDACEPTACION\r\n"
			+ "                FROM (\r\n"
			+ "                   SELECT USULOGIN,IDPOLITICA, ACEPTADA, FECHA_ACEPTACION, REENVIAR, IDACEPTACION, ROW_NUMBER() OVER (PARTITION BY USULOGIN ORDER BY FECHA_ACEPTACION DESC) AS numero\r\n"
			+ "                   FROM ACEPTACION_POLITICAS a\r\n"
			+ "                  JOIN POLITICA_PROTECCION_DATOS pp ON a.IDPOLITICA = pp.ID AND pp.CODIGO_TIPO = 'POLITICA_USO'\r\n"
			+ "                    WHERE a.IDPOLITICA !=:idPoliticaActiva\r\n"
			+ "                   AND a.ACEPTADA = 1\r\n"
			+ "                ) subquery\r\n"
			+ "               WHERE numero = 1)")
	List<AceptacionPoliticas> consultarReenviarPoliticaUso(Long idPoliticaActiva);
	
	@Query(nativeQuery = true, value = "SELECT ap.USULOGIN, ap.IDPOLITICA, ap.ACEPTADA, ap.FECHA_ACEPTACION, ap.REENVIAR, ap.IDACEPTACION\r\n"
			+ "            FROM ACEPTACION_POLITICAS ap\r\n"
			+ "            WHERE ap.IDACEPTACION IN (\r\n"
			+ "                SELECT IDACEPTACION\r\n"
			+ "                FROM (\r\n"
			+ "                   SELECT USULOGIN,IDPOLITICA, ACEPTADA, FECHA_ACEPTACION, REENVIAR, IDACEPTACION, ROW_NUMBER() OVER (PARTITION BY USULOGIN ORDER BY FECHA_ACEPTACION DESC) AS numero\r\n"
			+ "                   FROM ACEPTACION_POLITICAS a\r\n"
			+ "                  JOIN POLITICA_PROTECCION_DATOS pp ON a.IDPOLITICA = pp.ID AND pp.CODIGO_TIPO = 'DATOS_PERSONALES'\r\n"
			+ "                    WHERE a.IDPOLITICA =:idPoliticaActiva\r\n"
			+ "                   AND a.ACEPTADA = 1\r\n"
			+ "                ) subquery\r\n"
			+ "               WHERE numero = 1)")
	List<AceptacionPoliticas> consultarReenviarPoliticaDatos(Long idPoliticaActiva);
	
	@Query(nativeQuery = true, value = "SELECT 1\r\n"
			+ "                   FROM ACEPTACION_POLITICAS a\r\n"
			+ "                  WHERE a.IDPOLITICA =:idPolitica\r\n")
	Object[] consultarRegistrosPolitica(Long idPolitica);

}
