package linktic.lookfeel.security.services;



import linktic.lookfeel.model.Usuario;
import linktic.lookfeel.security.repositories.GrupoServicioRepository;
import linktic.lookfeel.security.repositories.UsuarioRepository;
import linktic.lookfeel.security.resources.GrupoServicio;
import linktic.lookfeel.security.resources.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// TODO: Auto-generated Javadoc
/**
 * The Class SecurityService.
 */
@Service
public class SecurityService implements ISecurityService {

    /** The repository. */
    @Autowired
    private UsuarioRepository repository;
    
    /** The repository grupo. */
    @Autowired
    private GrupoServicioRepository repositoryGrupo;



    /* (non-Javadoc)
     * @see com.itosoftware.observador.security.services.ISecurityService#PermisosByUsername(java.lang.String, java.lang.Long, java.lang.Long, java.lang.Long)
     */
    @Override
    @Transactional
    public UserDetails PermisosByUsername(String username, Long codInstitucion, Long sede, Long jornada, String password) throws UsernameNotFoundException {
        Optional<Usuario> user = repository.findByusuario(username, codInstitucion, sede, jornada);

        if (!user.isPresent()) {
            throw new UsernameNotFoundException("User Not Found with -> username : " + username);
        }

        List<Object> permisosTemp = repository.getPermisosByPerfil(user.get().getUsuPerfCodigo());

        if (permisosTemp.size() == 0) {
            throw new UsernameNotFoundException("Permissions Not Found with -> perfil : " + user.get().getUsuPerfCodigo());
        }
        List<GrupoServicio> permisos = new ArrayList<>();

        for (Object permiso : permisosTemp) {
            GrupoServicio grupoServicio;
            grupoServicio = repositoryGrupo.getByIdAndActive(String.valueOf(permiso));
            permisos.add(grupoServicio);
        }

        return null;
    }

    @Override
    @Transactional
    public UserDetails generateToken(String username, String password) throws UsernameNotFoundException {


        return UserPrincipal.buildNew(username, password);
    }

    @Override
    public Usuario obtenerInfoByUsername(String username) {
        return repository.findByUsuPerNumDocum(username).orElse(null);
    }

    @Override
    public Usuario saveUser(Usuario usuario) {
        return repository.save(usuario);
    }


}
