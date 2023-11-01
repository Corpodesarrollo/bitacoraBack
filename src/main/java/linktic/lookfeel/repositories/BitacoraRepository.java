package linktic.lookfeel.repositories;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import linktic.lookfeel.model.Bitacora;

public interface BitacoraRepository extends PagingAndSortingRepository<Bitacora, Long>{
	
	@Query(nativeQuery = true, value = "SELECT b.* from bitacora b"
			+ " where b.fecha_registro>=:fechaInicio and b.fecha_registro<=:fechaFin "
			+ " and b.usuario = case when :usuario is null then b.usuario else :usuario end "
			+ " and b.colegio = case when :colegio is null then b.colegio else :colegio end "
			+ " and b.sede = case when :sede is null then b.sede else :sede end "
			+ " and b.jornada = case when :jornada is null then b.jornada else :jornada end "
			+ " and b.tipo_log_bitacora = case when :tipoLog is null then b.tipo_log_bitacora else :tipoLog end "
			+ " and b.descripcion like :descripcion ")
	List<Bitacora> consultaBitacora(Date fechaInicio,Date fechaFin,String usuario,Long colegio,Long sede,Long jornada,Long tipoLog,String descripcion, Pageable pageable);
}