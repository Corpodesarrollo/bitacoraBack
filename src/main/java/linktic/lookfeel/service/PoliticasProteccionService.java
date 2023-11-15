package linktic.lookfeel.service;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import linktic.lookfeel.dtos.AceptacionUsuarioDto;
import linktic.lookfeel.dtos.AceptarPoliticaDto;
import linktic.lookfeel.dtos.CrearPoliticaDto;
import linktic.lookfeel.dtos.EstadoAceptacionDto;
import linktic.lookfeel.dtos.PoliticaProteccionDto;
import linktic.lookfeel.model.AceptacionPoliticas;
import linktic.lookfeel.model.PoliticasProteccion;
import linktic.lookfeel.model.Response;
import linktic.lookfeel.repositories.AceptacionPoliticasRepository;
import linktic.lookfeel.repositories.PoliticasProteccionRepository;
import linktic.lookfeel.model.TipoPoliticaEnum;

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
			politica.setHabilitado(1L);
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
		PoliticasProteccion politicaEncontrada;
		try {
				politicaEncontrada = politicasProteccionRepository.findById(idPolitica).get();
				
				if(politicaEncontrada != null) {
					if(politicaEncontrada.getEstado()== 1) {
						return new Response(HttpStatus.BAD_REQUEST.value(), "No se puede eliminar la politica, Se encuentra activa!", null);
					}
					politicaEncontrada.setHabilitado(0L);
					politicasProteccionRepository.save(politicaEncontrada);
				}
				
				
			log.info("Respuesta Exitosa al eliminar politica de uso.");
			return new Response(HttpStatus.OK.value(), "La politica de uso fue eliminada Exitosamente!", null);
			
		}
		catch(DataIntegrityViolationException ce) {
			return new Response(HttpStatus.BAD_REQUEST.value(), "No se puede eliminar la politica, existen usuarios asociados!", null);
		}
		catch (Exception e) {
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
			aceptacion.setFechaAceptacion(LocalDateTime.now());
			
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
					aceptacion.setAceptaPoliticaUso(iterator[3]== null ? false : iterator[3].toString().equals("1"));
					aceptacion.setAceptaPoliticaDatos(iterator[4]== null ? false :iterator[4].toString().equals("1"));
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
	public Response consultarAceptacionPoliticaUso(String usuario) {
		List<Object[]> listaUsuarios = new ArrayList<>(); 
		List<EstadoAceptacionDto> listausuariosDto = new ArrayList<>();
		try {			
			listaUsuarios = aceptacionPoliticasRepository.verAceptacionPoliticaUso(usuario);
			if(listaUsuarios.size()>0) {
				for (Object[] iterator : listaUsuarios) {
					
					EstadoAceptacionDto aceptacion = new EstadoAceptacionDto();
					aceptacion.setUsuario(iterator[0].toString());
					aceptacion.setIdPolitica(iterator[1].toString());
					aceptacion.setTipoPolitica(iterator[2].toString());
					aceptacion.setAceptada(iterator[3]== null ? false : iterator[3].toString().equals("1"));
					aceptacion.setReenviar(iterator[4]== null ? false :iterator[4].toString().equals("1"));
					listausuariosDto.add(aceptacion);
				}
			}else {
				EstadoAceptacionDto aceptacion = new EstadoAceptacionDto();
				aceptacion.setUsuario(usuario);
				aceptacion.setIdPolitica("");
				aceptacion.setTipoPolitica("POLITICA_USO");
				aceptacion.setAceptada(false);
				aceptacion.setReenviar(false);
				listausuariosDto.add(aceptacion);
			}
			
			
			
			log.info("Respuesta Exitosa de ver aceptacion politicas.");
			return new Response(HttpStatus.OK.value(), "Respuesta Exitosa de ver aceptacion politicas.", listausuariosDto);

		} catch (Exception e) {
			log.error("Se ha generado una excepcion al cargar la aceptacion de politicas.");
			return new Response(HttpStatus.BAD_REQUEST.value(),
					"Se ha generado una excepcion al cargar la aceptacion de politicas.", null);
		}
	}
	
	@Override
	public Response consultarAceptacionPoliticaDatos(String usuario) {
		List<Object[]> listaUsuarios = new ArrayList<>(); 
		List<EstadoAceptacionDto> listausuariosDto = new ArrayList<>();
		try {			
			listaUsuarios = aceptacionPoliticasRepository.verAceptacionPoliticaDatos(usuario);
			if(listaUsuarios.size()>0) {
				for (Object[] iterator : listaUsuarios) {
					
					EstadoAceptacionDto aceptacion = new EstadoAceptacionDto();
					aceptacion.setUsuario(iterator[0].toString());
					aceptacion.setIdPolitica(iterator[1].toString());
					aceptacion.setTipoPolitica(iterator[2].toString());
					aceptacion.setAceptada(iterator[3]== null ? false : iterator[3].toString().equals("1"));
					aceptacion.setReenviar(iterator[4]== null ? false :iterator[4].toString().equals("1"));
					listausuariosDto.add(aceptacion);
				}
			}
		   else {
					EstadoAceptacionDto aceptacion = new EstadoAceptacionDto();
					aceptacion.setUsuario(usuario);
					aceptacion.setIdPolitica("");
					aceptacion.setTipoPolitica("DATOS_PERSONALES");
					aceptacion.setAceptada(false);
					aceptacion.setReenviar(false);
					listausuariosDto.add(aceptacion);
		   		}
			
			
			log.info("Respuesta Exitosa de aceptacion politicas.");
			return new Response(HttpStatus.OK.value(), "Respuesta Exitosa de aceptacion politicas.", listausuariosDto);

		} catch (Exception e) {
			log.error("Se ha generado una excepcion al cargar la aceptacion de politicas.");
			return new Response(HttpStatus.BAD_REQUEST.value(),
					"Se ha generado una excepcion al cargar la aceptacion de politicas.", null);
		}
	}
	
	@Override
	public Response marcarReenviar(String tipoPolitica){
		try 
		{
			
			if(tipoPolitica != null) {
				if(tipoPolitica.equals(TipoPoliticaEnum.USO.getValor())) 
				{
					PoliticasProteccion politicaActiva = politicasProteccionRepository.verPoliticaUsoActivaTipo();
					List<AceptacionPoliticas> aceptaciones =  aceptacionPoliticasRepository.consultarReenviarPoliticaUso(politicaActiva.getId().longValue());
					if(aceptaciones.size()>0) {
						for (AceptacionPoliticas aceptacion : aceptaciones) {
							aceptacion.setReenviar(1L);
							aceptacionPoliticasRepository.save(aceptacion);
						}
					}
				}
				else {
					PoliticasProteccion politicaActiva = politicasProteccionRepository.verPoliticaDatosActivaTipo();
					List<AceptacionPoliticas> aceptaciones =  aceptacionPoliticasRepository.consultarReenviarPoliticaDatos(politicaActiva.getId().longValue());
					if(aceptaciones.size()>0) {
						for (AceptacionPoliticas aceptacion : aceptaciones) {
							aceptacion.setReenviar(1L);
							aceptacionPoliticasRepository.save(aceptacion);
						}
					}
				}
			}
			
			log.info("Respuesta Exitosa al marcar para reenviar.");
			return new Response(HttpStatus.OK.value(), "Respuesta Exitosa al marcar para reenviar!", null);
			
		} catch (Exception e) {
			log.error("Se generado una excepcion mientras se realizaba marcar para reenviar.", e.getMessage());
			return null;
		}
		
	}

}