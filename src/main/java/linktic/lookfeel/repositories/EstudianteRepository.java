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
			+ "WHERE num_reg BETWEEN ((:numPagina - 1) * (:numRegPorPagina)) AND ((:numPagina * :numRegPorPagina)-1)")
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
	
	@Query(nativeQuery = true, value = "SELECT COUNT(*) "
			+ "										FROM (SELECT  inst.INSCODIGO, count(*) AS estudiantes  "
			+ "												FROM estudiante est   "
			+ "												INNER JOIN g_jerarquia jer on jer.G_JerCodigo = EstGrupo  "
			+ "												INNER JOIN g_constante cons on cons.G_conCodigo = ESTTIPODOC and  G_CONTIPO='10'  "
			+ "												INNER JOIN institucion inst on G_JerInst = inst.INSCODIGO  "
			+ "												INNER JOIN grupo gru on gru.GRUJERGRUPO = jer.G_JerCodigo   "
			+ "												INNER JOIN grado gra on gra.GRACODIGO = jer.G_JerGrado AND gra.GRACODINST=inst.INSCODIGO  "
			+ "												LEFT JOIN G_CONSTANTE gcons ON gcons.G_CONTIPO = 5 AND gcons.G_CONCODIGO = jer.G_JERJORN  "
			+ "												WHERE G_JerTipo=1 and G_JerNivel=8  "
			+ "												GROUP BY inst.INSCODIGO) q  "
			+ "										INNER JOIN institucion inst on inst.INSCODIGO = q.INSCODIGO  "
			+ "										INNER JOIN egd_localidad ln ON ln.lc_codi_id=inst.INSCODLOCAL "
			+ "										WHERE ((:codigoLocalidad > 0 AND lc_codi_id = :codigoLocalidad) OR (:codigoLocalidad <= 0 AND lc_codi_id > 0)) ")
	List<Object[]> consultaTotalRegTotalEstudiantesPorInstituto(int codigoLocalidad);
	
	@Query(nativeQuery = true, value = "SELECT id_localidad, localidad, institucion, estudiantes  "
			+ " FROM ( SELECT ROWNUM AS num_reg, id_localidad, localidad, institucion, estudiantes  "
			+ "			         FROM (SELECT lc_codi_id AS id_localidad, lc_nomb AS localidad, inst.insnombre AS institucion, q.estudiantes  "
			+ "							FROM (SELECT  inst.INSCODIGO, count(*) AS estudiantes  "
			+ "									FROM estudiante est   "
			+ "									INNER JOIN g_jerarquia jer on jer.G_JerCodigo = EstGrupo  "
			+ "									INNER JOIN g_constante cons on cons.G_conCodigo = ESTTIPODOC and  G_CONTIPO='10'  "
			+ "									INNER JOIN institucion inst on G_JerInst = inst.INSCODIGO  "
			+ "									INNER JOIN grupo gru on gru.GRUJERGRUPO = jer.G_JerCodigo   "
			+ "									INNER JOIN grado gra on gra.GRACODIGO = jer.G_JerGrado AND gra.GRACODINST=inst.INSCODIGO  "
			+ "									LEFT JOIN G_CONSTANTE gcons ON gcons.G_CONTIPO = 5 AND gcons.G_CONCODIGO = jer.G_JERJORN  "
			+ "									WHERE G_JerTipo=1 and G_JerNivel=8  "
			+ "									GROUP BY inst.INSCODIGO) q  "
			+ "							INNER JOIN institucion inst on inst.INSCODIGO = q.INSCODIGO  "
			+ "							INNER JOIN egd_localidad ln ON ln.lc_codi_id=inst.INSCODLOCAL "
			+ "							WHERE ((:codigoLocalidad > 0 AND lc_codi_id = :codigoLocalidad) OR (:codigoLocalidad <= 0 AND lc_codi_id > 0)) "
			+ "							ORDER BY lc_nomb ASC, inst.insnombre)) "
			+ "WHERE num_reg BETWEEN ((:numPagina - 1) * (:numRegPorPagina + 1)) AND (:numPagina * :numRegPorPagina)")
	List<Object[]> consultaPaginadaTotalEstudiantesPorInstituto(int codigoLocalidad, int numPagina, int numRegPorPagina);
}
