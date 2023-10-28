package linktic.lookfeel.security.repositories;


import linktic.lookfeel.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// TODO: Auto-generated Javadoc
/**
 * The Interface UsuarioRepository.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    /**
     * Find byusuario.
     *
     * @param usulogin the usulogin
     * @param codInstitucion the cod institucion
     * @param sede the sede
     * @param jornada the jornada
     * @return the optional
     */
    @Query(nativeQuery = true, value = "select u.* from usuario u\n" +
            "join g_jerarquia gjer on gjer.g_jercodigo = usucodjerar\n" +
            "where usulogin= :usulogin  and g_jerinst= :codInstitucion\n" +
            "and (G_JERSEDE is null OR G_JERSEDE =:sede)\n" +
            "and (G_JERJORN is null OR G_JERJORN =:jornada) ")
    Optional<Usuario> findByusuario(String usulogin, Long codInstitucion, Long sede, Long jornada);

    /**
     * Gets the permisos by perfil.
     *
     * @param codigo the codigo
     * @return the permisos by perfil
     */
    @Query(nativeQuery = true, value = "select SERPERGRUSERCODIGO from servicio_perfil where SERPERFPERFCODIGO =:codigo")
    List<Object> getPermisosByPerfil(String codigo);

    /**
     * the user
     *
     * @param username
     * @return
     */
    Optional<Usuario> findByUsuPerNumDocum(String username);
    
    @Query(nativeQuery = true, value = "select gjer.g_jernivel from usuario u\n" +
            "join g_jerarquia gjer on gjer.g_jercodigo = u.usucodjerar\n" +
            "where usulogin= :usulogin ")
    Integer nivelUsuario(String usulogin);
    
    @Query(nativeQuery = true, value = "select gjer.g_jerlocal from usuario u\n" +
            "join g_jerarquia gjer on gjer.g_jercodigo = u.usucodjerar\n" +
            "where usulogin= :usulogin ")
    Long localidadUsuario(String usulogin);
    
    @Query(nativeQuery = true, value = "select gjer.g_jerinst from usuario u\n" +
            "join g_jerarquia gjer on gjer.g_jercodigo = u.usucodjerar\n" +
            "where usulogin= :usulogin ")
    Long institucionUsuario(String usulogin);
}