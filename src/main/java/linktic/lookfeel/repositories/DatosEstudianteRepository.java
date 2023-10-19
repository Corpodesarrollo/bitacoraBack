package linktic.lookfeel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import linktic.lookfeel.model.DatosEstudiante;

public interface DatosEstudianteRepository extends JpaRepository<DatosEstudiante, Long> {

	@Query(nativeQuery = true, value = "SELECT\r\n"
			+ "    to_char(est.esttipodoc) AS tipoDocumento,\r\n"
			+ "    to_char(est.estnumdoc) AS numeroDocumento,\r\n"
			+ "    to_char(est.estcodigo) AS codigoEstudiante,\r\n"
			+ "    est.estnombre1\r\n"
			+ "    || ' '\r\n"
			+ "    || est.estnombre2\r\n"
			+ "    || ' '\r\n"
			+ "    || est.estapellido1\r\n"
			+ "    || ' '\r\n"
			+ "    || est.estapellido2     AS nombreEstudiante,\r\n"
			+ "    to_char(sed.sedcodins)  AS codigoInstitucion,\r\n"
			+ "    to_char(sed.sedcodigo)  AS codigoSede,\r\n"
			+ "    to_char(jer.g_jerjorn)  AS jornada,\r\n"
			+ "    to_char(jer.g_jermetod) AS metodologia,\r\n"
			+ "    to_char(jer.g_jergrado) AS grado,\r\n"
			+ "    to_char(est.estgrupo) AS grupo\r\n"
			+ "FROM\r\n"
			+ "    estudiante  est,\r\n"
			+ "    g_jerarquia jer,\r\n"
			+ "    sede        sed,\r\n"
			+ "    metodologia met\r\n"
			+ "WHERE\r\n"
			+ "        est.estcodigo = :codigoEstudiante\r\n"
			+ "    AND est.esttipodoc = :tipoDocumento\r\n"
			+ "    AND est.estnumdoc = :numeroDocumento\r\n"
			+ "    AND jer.g_jercodigo = est.estgrupo\r\n"
			+ "    AND sed.sedcodins = jer.g_jerinst\r\n"
			+ "    AND sed.sedcodigo = jer.g_jersede\r\n"
			+ "    AND met.metcodinst = jer.g_jerinst\r\n"
			+ "    AND met.metcodigo = jer.g_jermetod")
	DatosEstudiante buscarEstudiante(long tipoDocumento, String numeroDocumento, long codigoEstudiante);

}
