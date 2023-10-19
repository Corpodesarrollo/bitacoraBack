package linktic.lookfeel.security.services;



import linktic.lookfeel.model.Usuario;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

// TODO: Auto-generated Javadoc
/**
 * The Interface ISecurityService.
 */
public interface ISecurityService {

    /**
     * Permisos by username.
     *
     * @param username the username
     * @param codInstitucion the cod institucion
     * @param sede the sede
     * @param jornada the jornada
     * @return the user details
     * @throws UsernameNotFoundException the username not found exception
     */
    @Transactional
    UserDetails PermisosByUsername(String username, Long codInstitucion, Long sede, Long jornada,  String password) throws UsernameNotFoundException;

    @Transactional
    UserDetails generateToken(String username , String password) throws UsernameNotFoundException;

    /**
     * usuario por user name
     * @param username
     * @return
     */
    Usuario obtenerInfoByUsername(String username);

    Usuario saveUser(Usuario usuario);




}
