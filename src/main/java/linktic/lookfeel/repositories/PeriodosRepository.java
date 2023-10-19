package linktic.lookfeel.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import linktic.lookfeel.model.Periodos;

public interface PeriodosRepository extends JpaRepository<Periodos, Long> {
	
	@Query(nativeQuery = true, value ="select 1 as id, 'Primero' as nombre from dual \r\n"
	+ "union \r\n"
	+ "select 2 as id, 'Segundo' as nombre from dual \r\n"
	+ "union \r\n"
	+ "select 3 as id, 'Tercero' as nombre from dual \r\n"
	+ "union \r\n"
	+ "select 4 as id, 'Cuarto' as nombre from dual \r\n"
	+ "union\r\n"
	+ "select 5 as id, 'Quinto' as nombre from dual")
	List<Periodos> listaPeriodos();

}
