package linktic.lookfeel.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import linktic.lookfeel.model.Periodos;

public interface PeriodosRepository extends JpaRepository<Periodos, Long> {
	
	@Query(nativeQuery = true, value ="SELECT\r\n"
			+ "    ip.inspartipper as id,\r\n"
			+ "    ip.insparnomperdef as nombre\r\n"
			+ "FROM\r\n"
			+ "    institucion_parametro ip\r\n"
			+ "WHERE\r\n"
			+ "        ip.insparcodinst =:idInstitucion\r\n"
			+ "    AND ip.insparvigencia =:idVigencia\r\n")
	Periodos listaPeriodos(long idInstitucion, long idVigencia);
	
	@Query(nativeQuery = true, value ="SELECT\r\n"
			+ "    tbl1.g_concodigo as id,\r\n"
			+ "    tbl1.g_connombre as nombre\r\n"
			+ "FROM\r\n"
			+ "    (\r\n"
			+ "        SELECT\r\n"
			+ "            gc.g_concodigo,\r\n"
			+ "            gc.g_connombre,\r\n"
			+ "            ip.insparcodinst,\r\n"
			+ "            ip.insparvigencia\r\n"
			+ "        FROM\r\n"
			+ "            g_constante           gc,\r\n"
			+ "            institucion_parametro ip\r\n"
			+ "        WHERE\r\n"
			+ "                gc.g_contipo = 30\r\n"
			+ "            AND ( gc.g_concodigo BETWEEN 1 AND ip.insparnumper )\r\n"
			+ "        UNION ALL\r\n"
			+ "        SELECT\r\n"
			+ "            99                 AS g_concodigo,\r\n"
			+ "            ip.insparnomperdef AS g_connombre,\r\n"
			+ "            ip.insparcodinst,\r\n"
			+ "            ip.insparvigencia\r\n"
			+ "        FROM\r\n"
			+ "            institucion_parametro ip\r\n"
			+ "    )           tbl1,\r\n"
			+ "    institucion i\r\n"
			+ "WHERE\r\n"
			+ "        i.inscodigo = tbl1.insparcodinst\r\n"
			+ "    AND i.insvigencia = tbl1.insparvigencia\r\n"
			+ "    AND i.inscodigo =:idInstitucion")
	List<Periodos> listaPeriodosInstitucion(long idInstitucion);
	

}
