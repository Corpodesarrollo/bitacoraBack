package linktic.lookfeel.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import linktic.lookfeel.model.Estudiante;

public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {

	@Query(nativeQuery = true, value = "SELECT *  "
			+ "FROM "
			+ "    estudiante  est "
			+ "WHERE "
			+ "        est.estcodigo = TO_NUMBER(:codigoEstudiante) "
			+ "    AND est.esttipodoc = TO_NUMBER(:tipoDocumento) "
			+ "    AND est.estnumdoc = :numeroDocumento")
	Estudiante buscarEstudiante(String tipoDocumento, String numeroDocumento, String codigoEstudiante);
	
	
	@Query(nativeQuery = true, value = "SELECT grupo, grado, id_jornada, nombre_jornada, estnombre1, estnombre2, estapellido1, estapellido2, id_sede, nombre_sede "
			+ "FROM ( SELECT ROWNUM AS num_reg, tbl1. *  "
			+ "		FROM (SELECT gru.GRUNOMBRE AS grupo, "
			+ "		       gra.GRANOMBRE AS grado, "
			+ "		       jer.G_JERJORN AS id_jornada,  "
			+ "		       consJor.g_connombre AS nombre_jornada, "
			+ "		       est.estnombre1, est.estnombre2, est.estapellido1, est.estapellido2, "
			+ "		       s.SEDCODIGO AS id_sede, "
			+ "		       s.SEDNOMBRE AS nombre_sede "
			+ "		  FROM g_jerarquia jer "
			+ "		 INNER JOIN estudiante est ON jer.G_JERCODIGO = est.ESTGRUPO "
			+ "		 INNER JOIN grupo gru ON jer.G_JERCODIGO = gru.GRUJERGRUPO   "
			+ "		 INNER JOIN grado gra ON jer.G_JERGRADO = gra.GRACODIGO AND jer.G_JERINST = gra.GRACODINST "
			+ "		 INNER JOIN g_constante consJor ON consJor.G_CONTIPO = 5 AND jer.G_JERJORN = consJor.G_CONCODIGO "
			+ "		 INNER JOIN sede s ON jer.G_JERSEDE = s.SEDCODIGO AND jer.G_JERINST = s.SEDCODINS AND s.SEDESTADO = 'A' "
			+ "		 WHERE jer.G_JERTIPO = 1  "
			+ "		   AND jer.G_JERNIVEL = 8 "
			+ "		   AND jer.G_JERINST = :codigoInstitucion "
			+ "		   AND ((:codigoSede > 0 AND jer.G_JERSEDE = :codigoSede) OR (:codigoSede <= 0 AND jer.G_JERSEDE > 0)) "
			+ "		   AND ((:codigoJornada >= 0 AND jer.G_JERJORN = :codigoJornada) OR (:codigoJornada < 0 AND jer.G_JERJORN >= 0)) "
			+ "		  ORDER BY gru.grunombre ASC, NLSSORT(gra.GRANOMBRE,'NLS_SORT=XSpanish') ASC, NLSSORT(est.estapellido1,'NLS_SORT=XSpanish') ASC,  "
			+ "		  		   NLSSORT(est.estnombre1,'NLS_SORT=XSpanish') ASC, NLSSORT(consJor.g_connombre,'NLS_SORT=XSpanish') ASC) tbl1) "
			+ "WHERE num_reg BETWEEN (((:numPagina - 1) * :numRegPorPagina) + 1) AND (:numPagina * :numRegPorPagina)")
	List<Object[]> consultaPaginadaEstudiantesPorInstitucion(int codigoInstitucion, int codigoSede, int codigoJornada, int numPagina, int numRegPorPagina);
	
	@Query(nativeQuery = true, value = "SELECT COUNT(*) "
			+ "		  FROM g_jerarquia jer "
			+ "		 INNER JOIN estudiante est ON jer.G_JERCODIGO = est.ESTGRUPO "
			+ "		 INNER JOIN grupo gru ON jer.G_JERCODIGO = gru.GRUJERGRUPO   "
			+ "		 INNER JOIN grado gra ON jer.G_JERGRADO = gra.GRACODIGO AND jer.G_JERINST = gra.GRACODINST "
			+ "		 INNER JOIN g_constante consJor ON consJor.G_CONTIPO = 5 AND jer.G_JERJORN = consJor.G_CONCODIGO "
			+ "		 INNER JOIN sede s ON jer.G_JERSEDE = s.SEDCODIGO AND jer.G_JERINST = s.SEDCODINS AND s.SEDESTADO = 'A' "
			+ "		 WHERE jer.G_JERTIPO = 1  "
			+ "		   AND jer.G_JERNIVEL = 8 "
			+ "		   AND jer.G_JERINST = :codigoInstitucion "
			+ "		   AND ((:codigoSede > 0 AND jer.G_JERSEDE = :codigoSede) OR (:codigoSede <= 0 AND jer.G_JERSEDE > 0)) "
			+ "		   AND ((:codigoJornada >= 0 AND jer.G_JERJORN = :codigoJornada) OR (:codigoJornada < 0 AND jer.G_JERJORN >= 0)) ")
	List<Object[]> consultaTotalRegEstudiantesPorInstitucion(int codigoInstitucion, int codigoSede, int codigoJornada);
	
	@Query(nativeQuery = true, value = " SELECT count(*)  "
			+ "			  FROM (SELECT i.INSNOMBRE, jer.G_JERINST, count(e.ESTGRUPO) AS total_estu  "
			+ "			   FROM g_jerarquia jer, INSTITUCION i, ESTUDIANTE e "
			+ "			 WHERE jer.G_JERCODIGO = e.ESTGRUPO "
			+ "			   AND jer.G_JERINST = i.INSCODIGO   "
			+ "			   AND G_JerTipo=1 and G_JerNivel=8  "
			+ "			   AND ((:codigoLocalidad > 0 AND i.INSCODLOCAL = :codigoLocalidad) OR (:codigoLocalidad <= 0 AND i.INSCODLOCAL > 0))   "
			+ "			  GROUP BY i.INSNOMBRE, jer.G_JERINST  "
			+ "			  ORDER BY i.INSNOMBRE) ")
	List<Object[]> consultaTotalRegNumEstudiantesPorInstituto(int codigoLocalidad);
	
	@Query(nativeQuery = true, value = "SELECT id_localidad, localidad, institucion, estudiantes   "
			+ "			   FROM (SELECT ROWNUM AS num_reg, tbl1.* "
			+ "					   FROM (SELECT l.lc_codi_id AS id_localidad, l.lc_nomb AS localidad, i.INSNOMBRE AS institucion, count(e.ESTGRUPO) AS estudiantes "
			+ "							   FROM g_jerarquia jer, INSTITUCION i, ESTUDIANTE e, egd_localidad l  "
			+ "							  WHERE G_JerTipo=1 and G_JerNivel=8 "
			+ "							    AND jer.G_JERCODIGO = e.ESTGRUPO  "
			+ "							    AND jer.G_JERINST = i.INSCODIGO "
			+ "							    AND i.INSCODLOCAL = l.LC_CODI_ID "
			+ "						        AND ((:codigoLocalidad > 0 AND i.INSCODLOCAL = :codigoLocalidad) OR (:codigoLocalidad <= 0 AND i.INSCODLOCAL > 0))  "
			+ "						      GROUP BY i.INSNOMBRE, jer.G_JERINST, l.lc_codi_id, l.lc_nomb "
			+ "						      ORDER BY i.INSNOMBRE) tbl1) "
			+ "			  WHERE num_reg BETWEEN (((:numPagina - 1) * :numRegPorPagina) + 1) AND (:numPagina * :numRegPorPagina)")
	List<Object[]> consultaPaginadaNumEstudiantesPorInstituto(int codigoLocalidad, int numPagina, int numRegPorPagina);
}
