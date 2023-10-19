package linktic.lookfeel.service;

import java.util.List;

import javax.mail.MessagingException;

import linktic.lookfeel.dtos.grupoServicioDTO;
import linktic.lookfeel.model.Response;
import linktic.lookfeel.model.Usuario;

/**
*
* @author Ing. Alexander A. Muñoz Coneo
* @fechacreacion 19/07/2023
* @requrimiento HU007_SED, HU008_SED
* @version 1.0
*/

public interface ISeguridadService {
	
	List<Usuario> consultarUsuarioPorUsuLogin(String usuLogin);

	Response cambiarContrasena(String usuLogin, String contrasenaActual, String contrasenaNueva,
			String confirmacionContraseña, String usuPasswordTemporal) throws Exception;

	Response actualizarContrasena(String usuLogin) throws Exception;

	void enviarCorreoElectronico(String from, String to, String subject, String mensaje) throws MessagingException;
	
	boolean validarPermisos(String usuarioId, long permisoId);
	
	List<grupoServicioDTO> consultarPermisosPorPerfil(int idPerfilCodigo);
}
