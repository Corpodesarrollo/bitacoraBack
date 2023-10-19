package linktic.lookfeel.service;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import linktic.lookfeel.dtos.AceptacionUsuarioDto;
import linktic.lookfeel.dtos.AceptarPoliticaDto;
import linktic.lookfeel.dtos.CrearPoliticaDto;
import linktic.lookfeel.dtos.PoliticaProteccionDto;
import linktic.lookfeel.model.AceptacionPoliticas;
import linktic.lookfeel.model.PoliticasProteccion;
import linktic.lookfeel.model.Response;
import linktic.lookfeel.repositories.AceptacionPoliticasRepository;
import linktic.lookfeel.repositories.PerfilRepository;
import linktic.lookfeel.repositories.PoliticasProteccionRepository;

@Service
public class PoliticasProteccionService implements IPoliticasProteccionService {
	
	private static final Logger log = LoggerFactory.getLogger(PoliticasProteccionService.class);
	@Autowired
	private PoliticasProteccionRepository politicasProteccionRepository;
	
	@Autowired
	private AceptacionPoliticasRepository aceptacionPoliticasRepository;
	
	@Autowired
	private ISeguridadService seguridadService;
	
	@Value("${politicas.eliminar}")
	private long eliminarPolitica;

	@Override
	public Response listaPoliticas() {
		List<PoliticaProteccionDto> listaPoliticas = new ArrayList<>();
		List<PoliticasProteccion> politicas = new ArrayList<>();
		try {
			politicas = politicasProteccionRepository.verPoliticasUso();
			for (PoliticasProteccion iterator : politicas) {
				
				PoliticaProteccionDto politica = new PoliticaProteccionDto();
				politica.setId(iterator.getId());
				politica.setContenido(iterator.getContenido());
				politica.setVersion(iterator.getVersion());
				politica.setEstado(iterator.getEstado()== 1? true:false);
				politica.setFecha(iterator.getFecha());
				politica.setTipoPolitica(iterator.getTipoPolitica());
				listaPoliticas.add(politica);
			}
			log.info("Respuesta Exitosa de politicas.");
			return new Response(HttpStatus.OK.value(), "Respuesta Exitosa de politicas.", listaPoliticas);

		} catch (Exception e) {
			log.error("Se ha generado una excepcion al cargar la lista de politicas.");
			return new Response(HttpStatus.BAD_REQUEST.value(),
					"Se ha generado una excepcion al cargar la lista de politicas.", null);
		}
	}
	@Override
	public Response listaPoliticasActivas() {
		List<PoliticaProteccionDto> listaPoliticas = new ArrayList<>();
		List<PoliticasProteccion> politicas = new ArrayList<>();
		try {
			politicas = politicasProteccionRepository.verPoliticasActivas();
			for (PoliticasProteccion iterator : politicas) {
				
				PoliticaProteccionDto politica = new PoliticaProteccionDto();
				politica.setId(iterator.getId());
				politica.setContenido(iterator.getContenido());
				politica.setVersion(iterator.getVersion());
				politica.setEstado(iterator.getEstado()== 1? true:false);
				politica.setFecha(iterator.getFecha());
				politica.setTipoPolitica(iterator.getTipoPolitica());
				listaPoliticas.add(politica);
			}
			log.info("Respuesta Exitosa de politicas.");
			return new Response(HttpStatus.OK.value(), "Respuesta Exitosa de lista politicas activas.", listaPoliticas);

		} catch (Exception e) {
			log.error("Se ha generado una excepcion al cargar la lista de politicas activas.");
			return new Response(HttpStatus.BAD_REQUEST.value(),
					"Se ha generado una excepcion al cargar la lista de politicas activas.", null);
		}
	}
	@Override
	public Response verPoliticaUso(Long idPolitica){
		PoliticasProteccion politicaEncontrada;
		try {
			politicaEncontrada = politicasProteccionRepository.findById(idPolitica).get();
			
				PoliticaProteccionDto politica = new PoliticaProteccionDto();
				politica.setId(politicaEncontrada.getId());
				politica.setContenido(politicaEncontrada.getContenido());
				politica.setVersion(politicaEncontrada.getVersion());
				politica.setEstado(politicaEncontrada.getEstado()== 1? true:false);
				politica.setFecha(politicaEncontrada.getFecha());
				politica.setTipoPolitica(politicaEncontrada.getTipoPolitica());
			log.info("Respuesta Exitosa de politicas.");
			return new Response(HttpStatus.OK.value(), "Respuesta Exitosa de politicas.", politica);

		} catch (Exception e) {
			log.error("Se ha generado una excepcion al cargar la lista de politicas.");
			return new Response(HttpStatus.BAD_REQUEST.value(),
					"Se ha generado una excepcion al cargar la lista de politicas.", null);
		}
	}
	
	@Override
	public Response crearPoliticaUso(CrearPoliticaDto datosPolitica){
		try {
			PoliticasProteccion politica = new PoliticasProteccion();
			Long uVersion = politicasProteccionRepository.ultimaVersion();
			politica.setContenido(datosPolitica.getContenido());
			politica.setVersion(uVersion == null ? 1: uVersion + 1);
			politica.setFecha(LocalDate.now());
			politica.setTipoPolitica("POLITICA_USO");
			politicasProteccionRepository.save(politica);
			
			log.info("Respuesta Exitosa al crear politica de uso.");
			return new Response(HttpStatus.OK.value(), "La politica de uso fue creada Exitosamente!", politica);
			
		} catch (Exception e) {
			log.error("Se generado una excepcion durante la creacion de la politica de uso.", e.getMessage());
			return null;
		}
		
	}
	
	@Override
	public Response eliminarPoliticaUso(Long idPolitica, String idUsuario){
		try {
			if(!seguridadService.validarPermisos(idUsuario, eliminarPolitica)) {
				return new Response(HttpStatus.BAD_REQUEST.value(), "El usuario no tiene permisos para esta acción!", null);
			}
			politicasProteccionRepository.deleteById(idPolitica);
			log.info("Respuesta Exitosa al eliminar politica de uso.");
			return new Response(HttpStatus.OK.value(), "La politica de uso fue eliminada Exitosamente!", null);
			
		} catch (Exception e) {
			log.error("Se generado una excepcion mientras se eliminaba la politica de uso.", e.getMessage());
			return null;
		}
		
	}
	
	
	@Override
	public Response aceptarPolitica(AceptarPoliticaDto datosAceptacion) {
		try {
			AceptacionPoliticas aceptacion = new AceptacionPoliticas();
			aceptacion.setUsuario(datosAceptacion.getUsuario());
			aceptacion.setIdPolitica(datosAceptacion.getIdPolitica());
			aceptacion.setAceptada(datosAceptacion.aceptada? 1L : 0L);
			aceptacion.setReenviar(0L);
			if(datosAceptacion.aceptada) {
				aceptacion.setFechaAceptacion(LocalDateTime.now());
			}
			aceptacionPoliticasRepository.save(aceptacion);
			log.info("Respuesta Exitosa al registrar aceptación de  politica de uso.");
			return new Response(HttpStatus.OK.value(), "Aceptación de  politica de uso registrada Exitosamente!", null);
			
		} catch (Exception e) {
			log.error("Se generado una excepcion mientras se registraba aceptación de  politica de uso.", e.getMessage());
			return null;
		}
	}
	
	@Override
	public Response verListaAceptacion() {
		
		List<Object[]> listaUsuarios = new ArrayList<>(); 
		List<AceptacionUsuarioDto> listausuariosDto = new ArrayList<>();
		try {
			listaUsuarios = aceptacionPoliticasRepository.verListaAceptacion();
			
				for (Object[] iterator : listaUsuarios) {
				
					AceptacionUsuarioDto aceptacion = new AceptacionUsuarioDto();
					aceptacion.setUsuario(iterator[0].toString());
					aceptacion.setNombres(iterator[1].toString());
					aceptacion.setApellidos(iterator[2].toString());
					aceptacion.setAceptaPoliticaUso(Boolean.parseBoolean(iterator[3]==null?"0":iterator[3].toString()));
					aceptacion.setAceptaPoliticaDatos(Boolean.parseBoolean(iterator[4]==null?"0":iterator[4].toString()));
					listausuariosDto.add(aceptacion);
				}
			
			log.info("Respuesta Exitosa de aceptacion politicas.");
			return new Response(HttpStatus.OK.value(), "Respuesta Exitosa de listado de aceptacion.", listausuariosDto);

		} catch (Exception e) {
			log.error("Se ha generado una excepcion al cargar la lista de aceptación de politicas.");
			return new Response(HttpStatus.BAD_REQUEST.value(),
					"Se ha generado una excepcion al cargar la lista de aeptación de politicas.", null);
		}
	}
	
	@Override
	public Response consultarAceptacionUsuario(String usuario) {
		try {
			AceptacionUsuarioDto usuarioAceptacion = aceptacionPoliticasRepository.verAceptacionUsuario(usuario);
			
			log.info("Respuesta Exitosa de aceptacion politicas.");
			return new Response(HttpStatus.OK.value(), "Respuesta Exitosa de aceptacion politicas.", usuarioAceptacion);

		} catch (Exception e) {
			log.error("Se ha generado una excepcion al cargar la aceptacion de politicas.");
			return new Response(HttpStatus.BAD_REQUEST.value(),
					"Se ha generado una excepcion al cargar la aceptacion de politicas.", null);
		}
	}

}
