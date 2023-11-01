package linktic.lookfeel.service;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import linktic.lookfeel.model.Personal;
import linktic.lookfeel.model.Response;
import linktic.lookfeel.model.Usuario;
import linktic.lookfeel.repositories.PerfilRepository;
import linktic.lookfeel.repositories.PersonalRepository;
import linktic.lookfeel.repositories.SeguridadRepository;
import linktic.lookfeel.util.Utilidades;

/**
 *
 * @author Ing. Alexander A. Muñoz Coneo
 * @descripcion Controlador Para la operaciones relacionadas con la contraseñas
 *              de los usuarios
 * @fechacreacion 19/07/2023
 * @fechamodificacion 19/07/2023
 * @requrimiento HU007_SED, HU008_SED
 * @version 1.0
 */

@Service
public class SeguridadService implements ISeguridadService {

	@Autowired
	private JavaMailSender mailSender;

	@Value("${spring.mail.username}")
	private String from;

	@Value("${email.emailTest}")
	private String correoPrueba;

	@Autowired
	private SeguridadRepository seguridadRepository;

	@Autowired
	private PersonalRepository personalRepository;
	
	@Autowired
	private PerfilRepository perfilRepository;
	
	

	@Override
	public Response cambiarContrasena(String usuLogin, String contrasenaActual, String contrasenaNueva,
			String confirmacionContraseña, String usuPasswordTemporal) throws Exception {
		Usuario usuarioUpdate = null;
		String ajusteContraseña = null;
		String passNew = Utilidades.code(contrasenaNueva);
		List<Usuario> consultaUsuario = consultarUsuarioPorUsuLogin(usuLogin);
		if (consultaUsuario != null) {
			if (usuPasswordTemporal.equals("0")) {
				ajusteContraseña = Utilidades.code(contrasenaActual);
			} 
			for (Usuario iterator : consultaUsuario) {
				if (iterator.getUsuPassword() != null && iterator.getUsuPassword().equals(ajusteContraseña)
						&& !iterator.getUsuPerNumDocum().toString().equals(contrasenaNueva)) {
					if (contrasenaNueva.equals(confirmacionContraseña)) {
						//seguridadRepository.updateByPerNumDocumForUniquePass(iterator.getUsuLogin(),iterator.getUsuPassword(), passNew, usuPasswordTemporal);
						usuarioUpdate = iterator;
					} else {
						return new Response(HttpStatus.NO_CONTENT.value(),
								"Contraseña nueva no coincide con su confirmacion de contraseña.", null);
					}

				} else {
					return new Response(HttpStatus.NO_CONTENT.value(), "Contraseña actual es invalidad.", null);
				}

			}
			try {
				if(usuarioUpdate.getUsuPassword()!=null)
					seguridadRepository.updateAllPasswords(usuarioUpdate.getUsuLogin(), usuarioUpdate.getUsuPassword(), passNew, "0");
			} catch (Exception e) {
				return new Response(HttpStatus.BAD_REQUEST.value(),
						"Fallo el cambio de la contraseña Se ha generado una excepcion, Comucate con el administrador.", e);
			}
			

		} else {
			return new Response(HttpStatus.NO_CONTENT.value(),
					"Fallo el cambio de la contraseña porque el usuario No Exite.", null);
		}
		return new Response(HttpStatus.NO_CONTENT.value(),
				"Su contraseña ha sido actualizada exitosamente. A partir de ahora, utilice su nueva "
						+ "contraseña para iniciar sesión en su cuenta",
				null);
	}

	@Override
	public Response actualizarContrasena(String usuLogin) throws Exception {
		Personal personal = null;
		Usuario usuarioUpdate = null;
		List<Usuario> consultaUsuario = consultarUsuarioPorUsuLogin(usuLogin);
		
		String claveTemporal = null;
		String cuerpoCorreo = null;
		if (!consultaUsuario.isEmpty()) {
			personal = consultarPersonalPorNumDoc(usuLogin);
			if (personal.getPeremail() != null) {
				claveTemporal = Utilidades.generateRandomPassword(12);
				if (claveTemporal.length()<8)
					claveTemporal = Utilidades.generateRandomPassword(15);
				
				for (Usuario iterator : consultaUsuario) {
					if (iterator.getUsuPassword() != null) {
						//cambiarContrasena(usuLogin, iterator.getUsuPassword(), claveTemporal, claveTemporal, "1");
						//seguridadRepository.updateByPerNumDocumForUniquePass(iterator.getUsuLogin(), iterator.getUsuPassword(), claveTemporal, "1");
						usuarioUpdate=iterator;
					} else {
						seguridadRepository.updateByUsuLoginPassNull(iterator.getUsuLogin(), Utilidades.code(claveTemporal), "1");
					}

				}
				
				try {
					if(usuarioUpdate.getUsuPassword()!=null)
						seguridadRepository.updateAllPasswords(usuLogin, usuarioUpdate.getUsuPassword(), Utilidades.code(claveTemporal), "1");
				} catch (Exception e) {
					return new Response(HttpStatus.BAD_REQUEST.value(),
							"Fallo el cambio de la contraseña Se ha generado una excepcion, Comucate con el administrador.", e);
				}
				
				cuerpoCorreo = "<html><body>Estimad@ " + personal.getPernombre1() + " " + personal.getPerapellido1()
						+ "<br><br>"
						+ "A Continuación, encontrará la contraseña temporal que le ha sido asignada por el sistema: "
						+ claveTemporal + "<br><br>"
						+ "Tan pronto ingrese al aplicativo se le solicitará que la cambie por su seguridad.<br><br>"
						+ "Por favor, no responda este correo. Es un mensaje generado automáticamente.<br><br></body></html>";

				enviarCorreoElectronico(from, personal.getPeremail(), "Envio Contraseña Temporal", cuerpoCorreo);

			} else {
				return new Response(HttpStatus.NO_CONTENT.value(),
						"El usuario no tiene correo electrónico asignado para iniciar el proceso de cambio de contraseña.",
						null);
			}
		} else {
			return new Response(HttpStatus.NO_CONTENT.value(),
					"El número de documento no se encuentra registrado en el sistema.", null);
		}
		return new Response(HttpStatus.NO_CONTENT.value(),
				"Informamos que se ha enviado una contraseña temporal de un solo uso a la siguiente"
						+ " dirección de correo electrónico: " + personal.getPeremail() + " asegúrese de"
						+ " revisar la bandeja de entrada."
						+ " Recuerde que esta contraseña es temporal y solo será valida por un solo uso. Se"
						+ " recomienda iniciar sesión con esta contraseña lo antes posible para"
						+ " garantizar la seguridad de su cuenta.",
				null);
	}

	@Override
	public List<Usuario> consultarUsuarioPorUsuLogin(String usuLogin) {

		ArrayList<Usuario> usuario = new ArrayList<>();
		try {
			if (!usuLogin.isEmpty()) {
				if (usuLogin.matches("[0-9]+")) {
					return usuario = seguridadRepository.findByUsuLogin(usuLogin);
				}
			}

		} catch (Exception e) {
			usuario = null;
		}

		return usuario;
	}

	private Personal consultarPersonalPorNumDoc(String usuLogin) {
		Personal personal = null;
		try {
			if (!usuLogin.isEmpty()) {
				if (usuLogin.matches("[0-9]+")) {
					return personal = personalRepository.findByPerNumDocum(usuLogin);
				}
			}

		} catch (Exception e) {
			personal = null;
		}

		return personal;
	}

	@Override
	public void enviarCorreoElectronico(String from, String to, String subject, String mensaje)
			throws MessagingException {

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		try {
			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(mensaje, true);
			mailSender.send(message);

		} catch (Exception e) {
			throw new IllegalStateException("Error enviando el email: " + e.getMessage());
		}

	}
	
	@Override
	public boolean validarPermisos(String usuarioId, long permisoId) {
		boolean respuesta = false;
		String perfilId = "";
		try {
			List<Object[]> perfiles = perfilRepository.findPerfilId(usuarioId);
			if (!perfiles.isEmpty()) {
				for (Object[] tmp : perfiles) {
					perfilId = tmp[0].toString();
				}
				if (!perfilId.isEmpty()) {
					List<Object[]> permiso = perfilRepository.findPermisoPerfil(perfilId, permisoId);
					if (!permiso.isEmpty()) {
						respuesta = true;
					} else {
						respuesta = false;
					}
				}

			} else {
				respuesta = false;
			}
		} catch (Exception e) {
				respuesta = false;
		}
		return respuesta;
	}
	
	

}
