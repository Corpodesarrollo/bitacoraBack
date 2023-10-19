package linktic.lookfeel.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import linktic.lookfeel.model.ConsultaExterna;


public interface ConsultaExternaRepository extends JpaRepository<ConsultaExterna, String> {
	
	@Query(nativeQuery = true, value = "Select * \r\n"
			+ "from consultas_externas ce\r\n"
			+ "WHERE concat(ce.ce_tipo,ce.ce_consecutivo) = :pinDocumento")
	ConsultaExterna buscarDocumento(String pinDocumento);

}
