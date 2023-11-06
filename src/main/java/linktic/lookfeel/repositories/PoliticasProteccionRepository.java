package linktic.lookfeel.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import linktic.lookfeel.model.Mensaje;
import linktic.lookfeel.model.PoliticasProteccion;


public interface PoliticasProteccionRepository extends JpaRepository<PoliticasProteccion, Long> {
	
	@Query(nativeQuery = true, value = "SELECT * FROM POLITICA_PROTECCION_DATOS p\r\n" + "WHERE p.id =:id")
	PoliticasProteccion verPolitica(Long id);
	
	@Query(nativeQuery = true, value = "SELECT * FROM POLITICA_PROTECCION_DATOS p\r\n" 
			+ "WHERE p.CODIGO_TIPO = 'POLITICA_USO'"
			+ "AND p.ESTADO = 1")
	PoliticasProteccion verPoliticaUsoActivaTipo();
	
	@Query(nativeQuery = true, value = "SELECT * FROM POLITICA_PROTECCION_DATOS p\r\n" 
			+ "WHERE p.CODIGO_TIPO = 'DATOS_PERSONALES'"
			+ "AND p.ESTADO = 1")
	PoliticasProteccion verPoliticaDatosActivaTipo();
	
	@Query(nativeQuery = true, value = "SELECT * FROM POLITICA_PROTECCION_DATOS p\r\n" 
			+ "WHERE p.ESTADO = 1")
	List<PoliticasProteccion> verPoliticasActivas();
	
	@Query(nativeQuery = true, value = "SELECT * FROM POLITICA_PROTECCION_DATOS p\r\n" 
			+ "WHERE p.CODIGO_TIPO = 'POLITICA_USO' AND HABILITADO = 1 ORDER BY VERSION DESC")
	List<PoliticasProteccion> verPoliticasUso();
	
	@Query(nativeQuery = true, value = "SELECT MAX(p.VERSION) version from POLITICA_PROTECCION_DATOS p")
	Long ultimaVersion();
	
}
