package linktic.lookfeel.repositories;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import linktic.lookfeel.model.Bitacora;

public interface BitacoraRepository extends PagingAndSortingRepository<Bitacora, Long>{
	
	@Query(nativeQuery = true, value = "SELECT b.*"
			+ " from bitacora b"
			+ " where (b.fecha_registro between :fechaInicio and :fechaFin)"
			+ " and (:usuario IS NULL or b.usuario = :usuario)"
			+ " and (:colegio = 0 or b.colegio = :colegio)"
			+ " and (:sede = 0 or b.sede = :sede)"
			+ " and (:jornada = 0 or b.jornada = :jornada)"
			+ " and (:tipoLogBitacora = 0 or b.tipo_log_bitacora = :tipoLogBitacora)"
			+ " and b.descripcion like :descripcion ")
	List<Bitacora> consultaBitacora(Date fechaInicio,Date fechaFin,String usuario,Long colegio,Long sede,Long jornada,Long tipoLogBitacora,String descripcion, Pageable pageable);
	
	@Query(nativeQuery = true, value = "SELECT count(*)"
			+ " from bitacora b"
			+ " where (b.fecha_registro between :fechaInicio and :fechaFin)"
			+ " and (:usuario IS NULL or b.usuario = :usuario)"
			+ " and (:colegio = 0 or b.colegio = :colegio)"
			+ " and (:sede = 0 or b.sede = :sede)"
			+ " and (:jornada = 0 or b.jornada = :jornada)"
			+ " and (:tipoLogBitacora = 0 or b.tipo_log_bitacora = :tipoLogBitacora)"
			+ " and b.descripcion like :descripcion ")
	Integer totalResgistrosFiltro(Date fechaInicio,Date fechaFin,String usuario,Long colegio,Long sede,Long jornada,Long tipoLogBitacora,String descripcion);
	
	@Query(nativeQuery = true, value = "SELECT p.PERFNOMBRE FROM PERFIL p WHERE p.PERFCODIGO = :idPerfil FETCH NEXT 1 ROWS ONLY")
	String consultarNomPerfil(Long idPerfil); 
}
