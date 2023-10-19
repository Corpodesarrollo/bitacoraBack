package linktic.lookfeel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import linktic.lookfeel.model.Estudiante;

public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {

	@Query(nativeQuery = true, value = "SELECT * \r\n"
			+ "FROM\r\n"
			+ "    estudiante  est\r\n"
			+ "WHERE\r\n"
			+ "        est.estcodigo = TO_NUMBER(:codigoEstudiante)\r\n"
			+ "    AND est.esttipodoc = TO_NUMBER(:tipoDocumento)\r\n"
			+ "    AND est.estnumdoc = :numeroDocumento")
	Estudiante buscarEstudiante(String tipoDocumento, String numeroDocumento, String codigoEstudiante);

}
