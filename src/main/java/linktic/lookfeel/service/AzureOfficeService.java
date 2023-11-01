package linktic.lookfeel.service;

import java.io.IOException;

import linktic.lookfeel.security.services.ISecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import linktic.lookfeel.ImageToBase64Converter;
import linktic.lookfeel.dtos.FotoDTO;
import linktic.lookfeel.dtos.PerfilDTO;
import linktic.lookfeel.dtos.ReponseUsuarioLoginDTO;
import linktic.lookfeel.dtos.ResponseAzure;
import linktic.lookfeel.dtos.ResponseAzureData;
import linktic.lookfeel.dtos.ResponseLoginDTO;
import linktic.lookfeel.dtos.UsuarioAzureDTO;
import linktic.lookfeel.dtos.UsuarioDTO;
import linktic.lookfeel.model.Perfil;
import linktic.lookfeel.model.Personal;
import linktic.lookfeel.repositories.PerfilRepository;
import linktic.lookfeel.util.Utilidades;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Service
public class AzureOfficeService implements IAzureOfficeService {

	private static final int CODERESPONSEOFFICE = 200;

	@Value("${office.client_secret}")
	private String client_secret;

	@Value("${office.tenant}")
	private String tenant;

	@Value("${office.grant_type}")
	private String grant_type;

	@Value("${office.client_id}")
	private String client_id;

	@Value("${office.scope}")
	private String scope;

	@Value("${office.client_secret_edu}")
	private String client_secret_edu;

	@Value("${office.tenant_edu}")
	private String tenant_edu;

	@Value("${office.client_id_edu}")
	private String client_id_edu;

	@Value("${office.email.validate.edu}")
	private String edu_co;

	@Value("${office.email.validate.gov}")
	private String gov_co;

	@Value("${clave.encriptado}")
	private String claveDesencriptado;

	@Autowired
	private PerfilRepository perfilRepository;

	@Autowired
	private IFotoService fotoService;

	@Autowired
	private ISecurityService serviceSecurity;


	@Autowired
	private ImageToBase64Converter imageToBase64Converter;

	@Override
	public ResponseLoginDTO getDataUserByEmailOffice(String numeroIdentificacion, String pass) {
		ResponseLoginDTO response = new ResponseLoginDTO();
		UsuarioAzureDTO usuarioAzu;
		UsuarioDTO usuario;
		FotoDTO foto;
		PerfilDTO perfil;
		ResponseAzure token;

		//String hashEncriptado = Utilidades.getHash(pass, "MD5");
		Long perfilDB = perfilRepository.findByNumeroIdentificacionAndPass(numeroIdentificacion, pass);

		if (perfilDB != null) {

			Personal personal = perfilRepository.findPersonalByNumeroIdentificacion(numeroIdentificacion);
			String validaContrasenia = perfilRepository.findPassTemp(numeroIdentificacion);

			token = getTokenEdu();
			usuario = new UsuarioDTO();
			usuario.setNombre(personal.getPernombre1() + " " + personal.getPernombre2() + " "
					+ personal.getPerapellido1() + " " + personal.getPerapellido2());
			usuario.setNombre_usuario(personal.getPernumdocum());
			usuario.setId(personal.getPernumdocum());

			foto = new FotoDTO();

			try {
				foto.setNombreArchivo(perfilRepository.findNumDocumentoByEmail(personal.getPernumdocum()) == null ? personal.getPernumdocum()+".jpg" : perfilRepository.findNumDocumentoByEmail(personal.getPernumdocum()));
				foto.setCodificacion(imageToBase64Converter.convertImageToBase64("imagenes/"+personal.getPernumdocum()+".jpg"));
				usuario.setFoto(foto);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} //perfilRepository.findNumDocumentoByEmail(personal.getPernumdocum()))

			response.setExito(true);
			response.setUsuario(usuario);



			//usuario.setToken(token.getAccess_token());
			//usuario.setExpira_en(token.getExpires_in());
			usuario.setCambiar_contrasenia(false);

			// inicia  generación del token


			UserDetails userDetails =  serviceSecurity.generateToken(numeroIdentificacion,pass);
			usuario.setUserDetails(userDetails);
			if(validaContrasenia != null) {
				if(validaContrasenia.equals("0")) {
					usuario.setCambiar_contrasenia(false);
				}else {
					usuario.setCambiar_contrasenia(true);
				}
			}

			if (!usuario.isCambiar_contrasenia()){

			}

		} else {
			response.setExito(false);
			response.setError("Usuario o Contraseña no Valida");
		}

		return response;

	}

	/**
	 *
	 * Metodo encargado obtener el token del servicio graph
	 *
	 * @return String - Token
	 */
	@SuppressWarnings("deprecation")
	private ResponseAzure getToken() {
		ResponseAzure responseAzure = null;
		OkHttpClient client = new OkHttpClient().newBuilder().build();
		MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
		RequestBody body = RequestBody.create(mediaType, "client_id=" + client_id + "&scope=" + scope
				+ "&client_secret=" + client_secret + "&grant_type=" + grant_type);
		Request request = new Request.Builder()
				.url("https://login.microsoftonline.com/" + tenant + "/oauth2/v2.0/token").method("POST", body)
				.addHeader("Content-Type", "application/json")
				.addHeader("Cookie",
						"buid=0.AQ8AMe_N-B6jSkuT5F9XHpElWltEZUfGMrBJg-Ydk3ZSdsoBAAA.AQABAAEAAAD--DLA3VO7QrddgJg7WevrK8sVB0RsYnjINoYjBCHoUSQqo94MdBsyAIgGYjAp22ZtO2EyBQrvQ1QY9GaPHZu98HGWEczDEbzpn_ePilQpdCuhhBUKuDBB-i-2ROl-nucgAA; fpc=Apd8edTlsXNLn3fkfbO6k7kAvTvkAgAAACQ33dsOAAAA")
				.build();
		try {
			Response response = client.newCall(request).execute();
			ObjectMapper mapper = new ObjectMapper();
			responseAzure = mapper.readValue(response.body().byteStream(), ResponseAzure.class);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (responseAzure != null) {
			return responseAzure;
		} else {
			return null;
		}

	}

	/**
	 *
	 * Metodo encargado obtener el token del servicio graph
	 *
	 * @return String - Token
	 */
	@SuppressWarnings("deprecation")
	private ResponseAzure getTokenEdu() {
		ResponseAzure responseAzure = null;
		OkHttpClient client = new OkHttpClient().newBuilder().build();
		MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
		RequestBody body = RequestBody.create(mediaType, "client_id=" + client_id_edu + "&scope=" + scope
				+ "&client_secret=" + client_secret_edu + "&grant_type=" + grant_type);
		Request request = new Request.Builder()
				.url("https://login.microsoftonline.com/" + tenant_edu + "/oauth2/v2.0/token").method("POST", body)
				.addHeader("Content-Type", "application/json")
				.addHeader("Cookie",
						"buid=0.AQ8AMe_N-B6jSkuT5F9XHpElWltEZUfGMrBJg-Ydk3ZSdsoBAAA.AQABAAEAAAD--DLA3VO7QrddgJg7WevrK8sVB0RsYnjINoYjBCHoUSQqo94MdBsyAIgGYjAp22ZtO2EyBQrvQ1QY9GaPHZu98HGWEczDEbzpn_ePilQpdCuhhBUKuDBB-i-2ROl-nucgAA; fpc=Apd8edTlsXNLn3fkfbO6k7kAvTvkAgAAACQ33dsOAAAA")
				.build();
		try {
			Response response = client.newCall(request).execute();
			ObjectMapper mapper = new ObjectMapper();
			responseAzure = mapper.readValue(response.body().byteStream(), ResponseAzure.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (responseAzure != null) {
			return responseAzure;
		} else {
			return null;
		}

	}

	/**
	 *
	 * Metodo encargado de obtener el usuario de azure y sus datos
	 *
	 * @param token
	 * @param email
	 * @return UsuarioAzureDTO
	 */
	private UsuarioAzureDTO getDataUserAzure(String token, String email) {
		ResponseAzureData responseAzureData = null;
		UsuarioAzureDTO usuarioOffice = new UsuarioAzureDTO();
		OkHttpClient client = new OkHttpClient().newBuilder().build();
		MediaType mediaType = MediaType.parse("text/plain");
		Request request = new Request.Builder().url("https://graph.microsoft.com/v1.0/users/" + email).get()
				.addHeader("Content-Type", "application/json").addHeader("Authorization", "Bearer " + token).build();
		try {
			Response response = client.newCall(request).execute();
			ObjectMapper mapper = new ObjectMapper();
			if (response.code() == CODERESPONSEOFFICE) {
				responseAzureData = mapper.readValue(response.body().byteStream(), ResponseAzureData.class);
				usuarioOffice.setEmail(responseAzureData.getMail());
				usuarioOffice.setGivenName(responseAzureData.getGivenName());
				usuarioOffice.setSurname(responseAzureData.getSurname());
				usuarioOffice.setId(responseAzureData.getId());
			} else {
				usuarioOffice = null;
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return usuarioOffice;
	}

	@Override
	public ReponseUsuarioLoginDTO getDataUserByEmailOfficeEncriptado(String email) throws Exception {
		ReponseUsuarioLoginDTO response = new ReponseUsuarioLoginDTO();
		UsuarioAzureDTO usuarioAzu;
		UsuarioDTO usuario;
		FotoDTO foto;
		PerfilDTO perfil;
		ResponseAzure token;
		Personal personal;

		email = email.toLowerCase();

		//String emailDesencriptado = Utilidades.decrypt("U2FsdGVkX198gzUd8rYN6yOcMoeDHisJ7Zx1k5pdXK3FXZdcyPefz0faIuWghfQh", claveDesencriptado);
//
		//System.out.println("Email: "+email);
		//	Perfil perfilDB = perfilRepository.findPerfilByEmail(email);

		if (email.contains(edu_co)) {
			token = getTokenEdu();
			usuarioAzu = getDataUserAzure(token.getAccess_token(), email);

			if (usuarioAzu == null) {
				response.setExito(false);
				response.setError("Correo no Encontrado en Office 365");
			} else {

				personal = perfilRepository.findPersonalByEmail(email);

				if (personal != null) {

					usuario = new UsuarioDTO();
					usuario.setNombre(usuarioAzu.getGivenName() + " " + usuarioAzu.getSurname());
					usuario.setNombre_usuario(usuarioAzu.getEmail());
					usuario.setId(personal.getPernumdocum());

					foto = new FotoDTO();

					try {
						foto.setNombreArchivo(perfilRepository.findNumDocumentoByEmail(personal.getPernumdocum()) == null ? personal.getPernumdocum()+".jpg" : perfilRepository.findNumDocumentoByEmail(personal.getPernumdocum()));
						foto.setCodificacion(imageToBase64Converter.convertImageToBase64("imagenes/"+personal.getPernumdocum()+".jpg"));
						usuario.setFoto(foto);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} //perfilRepository.findNumDocumentoByEmail(personal.getPernumdocum()))


					response.setExito(true);
					response.setUsuario(usuario);

					//usuario.setToken(token.getAccess_token());
					//usuario.setExpira_en(token.getExpires_in());

					UserDetails userDetails =  serviceSecurity.generateToken(email,token.getAccess_token());
					usuario.setUserDetails(userDetails);
				} else {
					response.setExito(false);
					response.setError("Persona no encontrada");
				}
			}
			return response;
		} else if (email.contains(gov_co)) {
			token = getToken();
			usuarioAzu = getDataUserAzure(token.getAccess_token(), email);

			if (usuarioAzu == null) {
				response.setExito(false);
				response.setError("Correo no Encontrado en Office 365");
			} else {

				personal = perfilRepository.findPersonalByEmail(email);

				if (personal != null) {

					usuario = new UsuarioDTO();
					usuario.setNombre(usuarioAzu.getGivenName() + " " + usuarioAzu.getSurname());
					usuario.setNombre_usuario(usuarioAzu.getEmail());
					usuario.setId(personal.getPernumdocum());

					foto = new FotoDTO();

					try {
						foto.setNombreArchivo(perfilRepository.findNumDocumentoByEmail(personal.getPernumdocum()) == null ? personal.getPernumdocum()+".jpg" : perfilRepository.findNumDocumentoByEmail(personal.getPernumdocum()));
						foto.setCodificacion(imageToBase64Converter.convertImageToBase64("imagenes/"+personal.getPernumdocum()+".jpg"));
						usuario.setFoto(foto);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} //perfilRepository.findNumDocumentoByEmail(personal.getPernumdocum()))


					response.setExito(true);
					response.setUsuario(usuario);

					//usuario.setToken(token.getAccess_token());
					//usuario.setExpira_en(token.getExpires_in());

					UserDetails userDetails =  serviceSecurity.generateToken(email,token.getAccess_token());
					usuario.setUserDetails(userDetails);
				} else {
					response.setExito(false);
					response.setError("Persona no encontrada");
				}
			}
			return response;
		} else {
			response.setExito(false);
			response.setError("Correo no Encontrado en Office 365");
			return response;
		}
	}

}
