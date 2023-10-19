package linktic.lookfeel.security.repositories;



import linktic.lookfeel.security.resources.GrupoServicio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;



// TODO: Auto-generated Javadoc
/**
 * The Interface GrupoServicioRepository.
 */
@Repository
public interface GrupoServicioRepository extends JpaRepository<GrupoServicio, Long> {

    /**
     * Gets the by id and active.
     *
     * @param id the id
     * @return the by id and active
     */
    @Query("select p from GrupoServicio p where p.gruposercodigo = ?1")
    GrupoServicio getByIdAndActive(String id);
    
    /**
	 * Se listan los registros de grupo servicio por perfil codigo
	 * 
	 * @param perfilCodigo - Identificador de perfil codigo
	 * @return List<Object[]> - retorna lista de registros
	 */
	@Query(nativeQuery = true, value = "SELECT GRUSERCODIGO, GRUSERNOMBRE "
			+ "FROM grupo_servicio gs "
			+ "INNER JOIN servicio_perfil sp ON sp.SERPERGRUSERCODIGO = gs.GRUSERCODIGO "
			+ "WHERe sp.SERPERFPERFCODIGO = :perfilCodigo")
	List<Object[]> findGrupoServicioByPerfilCodigo(int perfilCodigo);
}