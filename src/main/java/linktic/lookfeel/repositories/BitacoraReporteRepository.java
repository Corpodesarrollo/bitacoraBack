package linktic.lookfeel.repositories;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import linktic.lookfeel.model.Bitacora;
import linktic.lookfeel.model.BitacoraReporte;

public interface BitacoraReporteRepository extends PagingAndSortingRepository<BitacoraReporte, Long>{
	
	@Query(nativeQuery = true, value = "SELECT b.id, b.fecha_registro, b.usuario, p.perfnombre, s.sernombre, c.catnombre, t.nombre TIPO_LOG_BITACORA, b.descripcion"
			+ " from bitacora b, perfil p, servicio s, categoria c, tipo_log_bitacora t"
			+ " where b.perfil = p.perfcodigo"
			+ " and s.sercodigo = b.modulo"
			+ " and c.catcodigo = b.submodulo"
			+ " and b.tipo_log_bitacora = t.id"
			+ " and (:id = 0 or b.id = :id)")
	List<BitacoraReporte> consultaBitacoraReporte(long id);
	
}
