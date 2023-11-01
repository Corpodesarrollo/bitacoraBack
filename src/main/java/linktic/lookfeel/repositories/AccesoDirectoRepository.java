package linktic.lookfeel.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import linktic.lookfeel.model.AccesoDirecto;

/**
 *
 * @author JAMES TELLEZ
 * @fechacreacion 17/10/2023
 * @requrimiento HU018_SED
 * @version 1.0
 */

public interface AccesoDirectoRepository extends JpaRepository<AccesoDirecto, Long> {
	@Query(nativeQuery = true, value = "SELECT ad.acdicodigo, ad.acdinombre, ad.acdiruta "
			+ "FROM grupo_servicio gs "
			+ "INNER JOIN servicio_perfil sp ON sp.SERPERGRUSERCODIGO=gs.GRUSERCODIGO "
			+ "INNER JOIN acceso_directo ad ON ad.acdigruposervicio=gs.GRUSERCODIGO "
			+ "WHERe sp.SERPERFPERFCODIGO = :idPerfilCodigo ")
	List<Object[]> findAllByPerfil(int idPerfilCodigo);
}
