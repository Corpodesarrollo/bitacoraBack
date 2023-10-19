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
			+ "WHERE p.CODIGO_TIPO = ':tipoPolitica'"
			+ "AND p.ACTIVA = 1")
	PoliticasProteccion verPoliticaActivaTipo(String tipoPolitica);
	
	@Query(nativeQuery = true, value = "SELECT * FROM POLITICA_PROTECCION_DATOS p\r\n" 
			+ "WHERE p.ESTADO = 1")
	List<PoliticasProteccion> verPoliticasActivas();
	
	@Query(nativeQuery = true, value = "SELECT * FROM POLITICA_PROTECCION_DATOS p\r\n" 
			+ "WHERE p.CODIGO_TIPO = 'POLITICA_USO'")
	List<PoliticasProteccion> verPoliticasUso();
	
	@Query(nativeQuery = true, value = "SELECT MAX(p.VERSION) version from POLITICA_PROTECCION_DATOS p")
	Long ultimaVersion();

}
