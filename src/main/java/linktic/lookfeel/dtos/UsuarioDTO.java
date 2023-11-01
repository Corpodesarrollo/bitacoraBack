package linktic.lookfeel.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
public class UsuarioDTO {
	
	private String id;
	private String nombre_usuario;
	private String nombre;
	//private PerfilDTO perfil;
	private FotoDTO foto;
	//private String token;
	//private int expira_en;
	private boolean cambiar_contrasenia;
	private UserDetails userDetails;

}
