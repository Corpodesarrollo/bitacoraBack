package linktic.lookfeel.repositories;

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

}
