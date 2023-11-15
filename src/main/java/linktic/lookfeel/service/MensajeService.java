package linktic.lookfeel.service;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import linktic.lookfeel.dtos.InstitucionDTO;
import linktic.lookfeel.dtos.JornadaDTO;
import linktic.lookfeel.dtos.LocalidadDTO;
import linktic.lookfeel.dtos.MensajeAsuntoDTO;
import linktic.lookfeel.dtos.MensajeContenidoDTO;
import linktic.lookfeel.dtos.MensajeHomeDTO;
import linktic.lookfeel.dtos.MensajeNuevoDTO;
import linktic.lookfeel.dtos.PerfilDTO;
import linktic.lookfeel.dtos.SedeDTO;
import linktic.lookfeel.model.Constante;
import linktic.lookfeel.model.EnviadoPor;
import linktic.lookfeel.model.Institucion;
import linktic.lookfeel.model.Mensaje;
import linktic.lookfeel.model.MensajePaginado;
import linktic.lookfeel.model.Perfil;
import linktic.lookfeel.model.Response;
import linktic.lookfeel.model.Sede;
import linktic.lookfeel.repositories.ConstanteRepository;
import linktic.lookfeel.repositories.InstitucionRepository;
import linktic.lookfeel.repositories.MensajeRepository;
import linktic.lookfeel.repositories.PerfilRepository;
import linktic.lookfeel.repositories.PersonalRepository;
import linktic.lookfeel.repositories.SedeRepository;

@Service
public class MensajeService implements IMensajeService {

	private static final Logger log = LoggerFactory.getLogger(MensajeService.class);

	DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
	ZoneId zonaHorariaColombia = ZoneId.of("America/Bogota");
	LocalDate fechaActual = LocalDate.now(zonaHorariaColombia);
	Calendar calendar = Calendar.getInstance();

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private MensajeRepository mensajeRepository;

	@Autowired
	private PerfilRepository perfilRepository;

	@Autowired
	private ConstanteRepository constanteRepository;

	@Autowired
	private InstitucionRepository institucionRepository;

	@Autowired
	private SedeRepository sedeRepository;

	@Autowired
	private PersonalRepository personalRepository;

	@Value("${localidades.tipo}")
	private long localidades;

	@Value("${mensajes.ver}")
	private long verMensaje;

	@Value("${mensajes.enviar}")
	private long enviarMensaje;

	@Value("${mensajes.editar}")
	private long editarMensaje;

	@Value("${mensajes.eliminar}")
	private long elimarMensaje;

	@Value("${spring.mail.test}")
	private String correoPrueba;

	@Value("${spring.mail.username}")
	private String from;

	/**
	 * Nivel maximo de la jerarquia
	 **/
	@Value("${nivel.maximo.jerarquia}")
	private int nivelJerarquia;

	@Override
	public Response listaPerfiles() {
		List<PerfilDTO> listaPerfiles = new ArrayList<>();
		List<Perfil> perfil = new ArrayList<>();
		try {
			perfil = perfilRepository.findPerfilesAsc();
			for (Perfil iterator : perfil) {
				PerfilDTO perfiles = new PerfilDTO();
				perfiles.setId(Integer.parseInt(iterator.getPerfcodigo()));
				perfiles.setEtiqueta(iterator.getPerfnombre());
				perfiles.setNombre(iterator.getPerfdescripcion());
				listaPerfiles.add(perfiles);
			}
			log.info("Respuesta Exitosa de perfiles.");
			return new Response(HttpStatus.OK.value(), "Respuesta Exitosa de perfiles.", listaPerfiles);

		} catch (Exception e) {
			log.error("Se ha generado una excepción al cargar la lista de perfiles.");
			return new Response(HttpStatus.BAD_REQUEST.value(),
					"Se ha generado una excepción al cargar la lista de perfiles.", null);
		}

	}

	@Override
	public Response listaLocalidades() {
		List<LocalidadDTO> listaLocalidades = new ArrayList<>();
		List<Constante> listadoLocalidades = new ArrayList<>();

		try {
			listadoLocalidades = constanteRepository.findByConstantePkTipo(localidades);
			for (Constante iterator : listadoLocalidades) {
				LocalidadDTO localidad = new LocalidadDTO();
				localidad.setId(Long.valueOf(iterator.getConstantePk().getCodigo()).intValue());
				localidad.setNombre(iterator.getNombre());
				listaLocalidades.add(localidad);
			}
			log.info("Respuesta Exitosa lista de localidades.");
			return new Response(HttpStatus.OK.value(), "Respuesta Exitosa lista de localidades.", listaLocalidades);

		} catch (Exception e) {
			log.error("Se ha generado una excepción al cargar la lista de localidades.");
			return new Response(HttpStatus.BAD_REQUEST.value(),
					"Se ha generado una excepción al cargar la lista de localidades.", null);
		}

	}

	@Override
	public Response listaColegiosLocalidad(Long idLocalidad) {
		List<InstitucionDTO> listaColegioDto = new ArrayList<>();
		List<Institucion> listadoColegios = new ArrayList<>();
		try {
			listadoColegios = institucionRepository.findByLocalidadInstitucions(idLocalidad);
			for (Institucion iterator : listadoColegios) {
				InstitucionDTO colegioDto = new InstitucionDTO();
				colegioDto.setId(iterator.getCodigo());
				colegioDto.setNombre(iterator.getNombre());
				listaColegioDto.add(colegioDto);
			}
			log.info("Respuesta Exitosa lista de colegios por localidad.");
			return new Response(HttpStatus.OK.value(), "Respuesta Exitosa lista de colegios por localidad.",
					listaColegioDto);

		} catch (Exception e) {
			log.error("Se ha generado una excepción al cargar la lista de colegios por localidad.");
			return new Response(HttpStatus.BAD_REQUEST.value(),
					"Se ha generado una excepción al cargar la lista de colegios por localidad.", null);
		}

	}

	@Override
	public Response listaSedesColegios(Long idColegios) {
		List<SedeDTO> listaSedesDto = new ArrayList<>();
		List<Sede> listadoSedes = new ArrayList<>();

		try {
			listadoSedes = sedeRepository.findSedeByInstitucion(idColegios);
			for (Sede iterator : listadoSedes) {
				SedeDTO sedeDto = new SedeDTO();
				sedeDto.setId(iterator.getCodigo());
				sedeDto.setNombre(iterator.getNombre());
				listaSedesDto.add(sedeDto);
			}
			log.info("Respuesta Exitosa de Sedes por Colegios.");
			return new Response(HttpStatus.OK.value(), "Respuesta Exitosa de Sedes por Colegios.", listaSedesDto);

		} catch (Exception e) {
			log.error("Se ha generado una excepción al cargar la lista de sedes por colegios.");
			return new Response(HttpStatus.BAD_REQUEST.value(),
					"Se ha generado una excepción al cargar la lista de sedes por colegios.", null);
		}

	}

	@Override
	public Response listaJornadaSedes(Long idColegios, Long idSede) {
		List<JornadaDTO> listaJornadaDto = new ArrayList<>();
		List<Constante> listadoJornada = new ArrayList<>();

		try {
			listadoJornada = constanteRepository.listaJornadas(idColegios, idSede);
			for (Constante iterator : listadoJornada) {
				JornadaDTO jornadaDto = new JornadaDTO();
				jornadaDto.setId(iterator.getConstantePk().getCodigo());
				jornadaDto.setNombre(iterator.getNombre());
				listaJornadaDto.add(jornadaDto);
			}
			log.info("Respuesta Exitosa de Jornadas por Sedes.");
			return new Response(HttpStatus.OK.value(), "Respuesta Exitosa de Jornadas por Sedes.", listaJornadaDto);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Se ha generado una excepción al cargar la lista de jornadas por sedes y colegios.");
			return new Response(HttpStatus.BAD_REQUEST.value(),
					"Se ha generado una excepción al cargar la lista de jornadas por sedes y colegios.", null);
		}

	}

	@Override
	public Response verMensaje(Long id, String perfilId) {

		if (id != null) {
			Mensaje mensaje = mensaje(id);
			if (mensaje != null) {
				MensajeContenidoDTO verMensajeContenidoDTO = new MensajeContenidoDTO();
				verMensajeContenidoDTO.setMsjCodigo(mensaje.getMsjCodigo());
				verMensajeContenidoDTO.setMsjAsunto(mensaje.getMsjAsunto());
				verMensajeContenidoDTO.setMsjFecha(mensaje.getMsjFecha());
				verMensajeContenidoDTO.setMsjEnviadoPor(mensaje.getMsjEnviadoPor());
				verMensajeContenidoDTO.setMsjEnviadoPorNombre(nombreEnviadoPor(mensaje.getMsjEnviadoPor()));
				verMensajeContenidoDTO.setMsjContenido(mensaje.getMsjContenido());
				log.info("Respuesta Exitosa.");
				return new Response(HttpStatus.OK.value(), "Respuesta Exitosa.", verMensajeContenidoDTO);

			} else {
				log.error("No se encontro mensajes.");
				return new Response(HttpStatus.BAD_REQUEST.value(), "No se encontro mensajes.", null);
			}

		} else {
			log.error("Id del mensaje no puede ser nulo.");
			return new Response(HttpStatus.BAD_REQUEST.value(), "Id del mensaje no puede ser nulo.", null);
		}

	}

	@Override
	public Response verMensajeActualizar(Long id, String perfilId) {

		if (id != null) {
			Mensaje mensaje = mensaje(id);
			if (mensaje != null) {
				MensajeNuevoDTO verMensajeContenidoDTO = new MensajeNuevoDTO();
				verMensajeContenidoDTO.setAsunto(mensaje.getMsjAsunto());
				verMensajeContenidoDTO.setFecha(mensaje.getMsjFecha());
				verMensajeContenidoDTO.setFecha_inicio(mensaje.getMsjFechaIni());
				verMensajeContenidoDTO.setFecha_finalizacion(mensaje.getMsjFechaFin());
				verMensajeContenidoDTO.setEnviado_por(mensaje.getMsjEnviadoPor());
				verMensajeContenidoDTO.setContenido(mensaje.getMsjContenido());
				if (mensaje.getMsjEnviadoPerfil() != null)
					verMensajeContenidoDTO.setPerfiles(convertirCadenaALista(mensaje.getMsjEnviadoPerfil()));
				if (mensaje.getMsjEnviadoAlocal() != null)
					verMensajeContenidoDTO.setLocalidades(convertirCadenaALista(mensaje.getMsjEnviadoAlocal()));
				if (mensaje.getMsjEnviadoAcoleg() != null)
					verMensajeContenidoDTO.setColegios(convertirCadenaALista(mensaje.getMsjEnviadoAcoleg()));
				if (mensaje.getMsjEnviadoAsede() != null)
					verMensajeContenidoDTO.setSedes(convertirCadenaALista(mensaje.getMsjEnviadoAsede()));
				if (mensaje.getMsjEnviadoAjorn() != null)
					verMensajeContenidoDTO.setJornadas(convertirCadenaALista(mensaje.getMsjEnviadoAjorn()));
				log.info("Respuesta Exitosa.");
				return new Response(HttpStatus.OK.value(), "Respuesta Exitosa.", verMensajeContenidoDTO);

			} else {
				log.error("No se encontro mensajes.");
				return new Response(HttpStatus.BAD_REQUEST.value(), "No se encontro mensajes.", null);
			}

		} else {
			log.error("Id del mensaje no puede ser nulo.");
			return new Response(HttpStatus.BAD_REQUEST.value(), "Id del mensaje no puede ser nulo.", null);
		}

	}

	@Override
	public Response eliminarMensaje(String usuarioId, Long id, String perfilId) {

		if (validarPermisos(perfilId, editarMensaje)) {
			if (id != null) {
				Mensaje mensaje = mensaje(id);
				if (mensaje != null) {
					mensajeRepository.delete(mensaje);
					log.info("Mensaje Borrado Exitosamente.");
					return new Response(HttpStatus.OK.value(), "Mensaje Borrado Exitosamente.", null);
				} else {
					log.error("Id del mensaje no encontrado.");
					return new Response(HttpStatus.BAD_REQUEST.value(), "Id del mensaje no encontrado.", null);
				}
			} else {
				log.error("Id del mensaje no puede ser nulo.");
				return new Response(HttpStatus.BAD_REQUEST.value(), "Id del mensaje no puede ser nulo.", null);
			}
		} else {
			log.error("Usuario no tiene permisos para eliminar mensajes.");
			return new Response(HttpStatus.BAD_REQUEST.value(), "Usuario no tiene permisos para eliminar mensajes.",
					null);
		}

	}

	@Override
	public Response listadoMensajes(String perfilLoginId, String usuarioId, String perfilId, String colegioId,
			String sedeId, String jornadaId, String localidadId, String fechaDesde, String fechaHasta, Long bandera,
			int pagina, int tamanoPagina) {

		List<MensajeHomeDTO> listaMensajeHomeDTO = new ArrayList<>();
		List<MensajeAsuntoDTO> listaMensajeAsuntoDTO = new ArrayList<>();
		List<Mensaje> listaMensajes = new ArrayList<>();
		LocalDate fechaInicioStr = null;
		LocalDate fechaFinStr = null;

		Map<String, Object> response = new HashMap<>();
		MensajePaginado listaMensajesFiltro = null;

		if (!fechaDesde.equals("") && !fechaHasta.equals("")) {
			fechaInicioStr = LocalDate.parse(fechaDesde);
			fechaFinStr = LocalDate.parse(fechaHasta);
		}

		if (validarPermisos(perfilLoginId, verMensaje)) {
			List<Integer> jerarquia = validarNivel(perfilLoginId);
			int niveles = jerarquia.size();
			int minimo = jerarquia.get(0);
			if (bandera == 0 || bandera == 1) {
				String sede = colegioId + "|" + sedeId;
				listaMensajes = listadoMensajesHome(null, perfilId, colegioId, sede, jornadaId, localidadId);
			} else {
				if (!fechaDesde.equals("") && !fechaHasta.equals("")) {
					if (validarFechas(fechaInicioStr, fechaFinStr)) {
						if (niveles >= nivelJerarquia) {
							int flag = 0;
							if ((perfilId.equals("") || perfilId == null) && (colegioId.equals("") || colegioId == null)
									&& (localidadId.equals("") || localidadId == null)) {
								listaMensajesFiltro = listadoMensajesBuscar(null, null, null, null,
										fechaInicioStr.format(formatoFecha), fechaFinStr.format(formatoFecha), pagina,
										tamanoPagina, flag);
							} else if (perfilId != null && (colegioId.equals("") || colegioId == null)
									&& (localidadId.equals("") || localidadId == null)) {
								listaMensajesFiltro = listadoMensajesBuscar(null, perfilId, null, null,
										fechaInicioStr.format(formatoFecha), fechaFinStr.format(formatoFecha), pagina,
										tamanoPagina, flag);
							} else if ((perfilId.equals("") || perfilId == null) && colegioId != null
									&& (localidadId.equals("") || localidadId == null)) {
								listaMensajesFiltro = listadoMensajesBuscar(null, null, colegioId, null,
										fechaInicioStr.format(formatoFecha), fechaFinStr.format(formatoFecha), pagina,
										tamanoPagina, flag);
							} else if ((perfilId.equals("") || perfilId == null)
									&& (colegioId.equals("") || colegioId == null) && localidadId != null) {
								listaMensajesFiltro = listadoMensajesBuscar(null, null, null, localidadId,
										fechaInicioStr.format(formatoFecha), fechaFinStr.format(formatoFecha), pagina,
										tamanoPagina, flag);
							} else if (perfilId != null && colegioId != null
									&& (localidadId.equals("") || localidadId == null)) {
								listaMensajesFiltro = listadoMensajesBuscar(null, perfilId, colegioId, null,
										fechaInicioStr.format(formatoFecha), fechaFinStr.format(formatoFecha), pagina,
										tamanoPagina, flag);
							} else if (perfilId != null && (colegioId.equals("") || colegioId == null)
									&& localidadId != null) {
								listaMensajesFiltro = listadoMensajesBuscar(null, perfilId, null, localidadId,
										fechaInicioStr.format(formatoFecha), fechaFinStr.format(formatoFecha), pagina,
										tamanoPagina, flag);
							} else {
								listaMensajesFiltro = listadoMensajesBuscar(null, perfilId, colegioId, localidadId,
										fechaInicioStr.format(formatoFecha), fechaFinStr.format(formatoFecha), pagina,
										tamanoPagina, flag);
							}

						} else if (niveles < nivelJerarquia) {
							for (Integer itr : jerarquia) {
								if (itr < minimo) {
									minimo = itr;
								}
							}
							if (minimo == 4 || minimo == 5) {
								int flag = 1;
								if ((perfilId.equals("") || perfilId == null)
										&& (colegioId.equals("") || colegioId == null)
										&& (localidadId.equals("") || localidadId == null)) {
									listaMensajesFiltro = listadoMensajesBuscar(usuarioId, null, null, null,
											fechaInicioStr.format(formatoFecha), fechaFinStr.format(formatoFecha),
											pagina, tamanoPagina, flag);

								} else if (perfilId != null && (colegioId.equals("") || colegioId == null)
										&& (localidadId.equals("") || localidadId == null)) {
									listaMensajesFiltro = listadoMensajesBuscar(usuarioId, perfilId, null, null,
											fechaInicioStr.format(formatoFecha), fechaFinStr.format(formatoFecha),
											pagina, tamanoPagina, flag);

								} else if ((perfilId.equals("") || perfilId == null) && colegioId != null
										&& (localidadId.equals("") || localidadId == null)) {
									listaMensajesFiltro = listadoMensajesBuscar(usuarioId, null, colegioId, null,
											fechaInicioStr.format(formatoFecha), fechaFinStr.format(formatoFecha),
											pagina, tamanoPagina, flag);

								} else if ((perfilId.equals("") || perfilId == null)
										&& (colegioId.equals("") || colegioId == null) && localidadId != null) {
									listaMensajesFiltro = listadoMensajesBuscar(usuarioId, null, null, localidadId,
											fechaInicioStr.format(formatoFecha), fechaFinStr.format(formatoFecha),
											pagina, tamanoPagina, flag);

								} else if (perfilId != null && colegioId != null
										&& (localidadId.equals("") || localidadId == null)) {
									listaMensajesFiltro = listadoMensajesBuscar(usuarioId, perfilId, colegioId, null,
											fechaInicioStr.format(formatoFecha), fechaFinStr.format(formatoFecha),
											pagina, tamanoPagina, flag);

								} else if (perfilId != null && (colegioId.equals("") || colegioId == null)
										&& localidadId != null) {
									listaMensajesFiltro = listadoMensajesBuscar(usuarioId, perfilId, null, localidadId,
											fechaInicioStr.format(formatoFecha), fechaFinStr.format(formatoFecha),
											pagina, tamanoPagina, flag);

								} else {
									listaMensajesFiltro = listadoMensajesBuscar(usuarioId, perfilId, colegioId,
											localidadId, fechaInicioStr.format(formatoFecha),
											fechaFinStr.format(formatoFecha), pagina, tamanoPagina, flag);
								}

							}

						}
					} else {
						log.error("Se ha generado un error, la fecha de hasta no puede ser menor a la fecha desde.");
						return new Response(HttpStatus.BAD_REQUEST.value(),
								"Se ha generado un error, la fecha de hasta no puede ser menor a la fecha desde.", null);
					}
				} else {
					log.error("Uno de los campos de fecha se encuentra en blanco, No se puede realizar la consulta.");
					return new Response(HttpStatus.BAD_REQUEST.value(),
							"Uno de los campos de fecha se encuentra en blanco, No se puede realizar la consulta.",
							null);
				}

			}

			if (bandera == 0) {
				if (!listaMensajes.isEmpty()) {
					for (Mensaje iterator : listaMensajes) {
						MensajeHomeDTO mensajeHomeDTO = new MensajeHomeDTO();
						mensajeHomeDTO.setMsjCodigo(iterator.getMsjCodigo());
						mensajeHomeDTO.setMsjAsunto(iterator.getMsjAsunto());
						mensajeHomeDTO.setMsjFecha(iterator.getMsjFecha());
						mensajeHomeDTO.setMsjFechaIni(iterator.getMsjFechaIni());
						mensajeHomeDTO.setMsjFechaFin(iterator.getMsjFechaFin());
						mensajeHomeDTO.setMsjEstado(iterator.getMsjEstado());
						listaMensajeHomeDTO.add(mensajeHomeDTO);
					}

					log.info("Respuesta Exitosa.");
					return new Response(HttpStatus.OK.value(), "Respuesta Exitosa.", listaMensajeHomeDTO);

				} else {
					log.error("No se encontraron mensajes.");
					return new Response(HttpStatus.BAD_REQUEST.value(), "No se encontraron mensajes.", null);
				}
			}

			if (bandera == 1) {
				if (!listaMensajes.isEmpty()) {
					for (Mensaje iterator : listaMensajes) {

						MensajeAsuntoDTO mensajeDTO = new MensajeAsuntoDTO();
						mensajeDTO.setMsjCodigo(iterator.getMsjCodigo());
						mensajeDTO.setMsjEnviadoPor(iterator.getMsjEnviadoPor());
						mensajeDTO.setMsjEnviadoPorNombre(nombreEnviadoPor(iterator.getMsjEnviadoPor()));
						mensajeDTO.setMsjAsunto(iterator.getMsjAsunto());
						mensajeDTO.setMsjFecha(iterator.getMsjFecha());
						mensajeDTO.setMsjFechaIni(iterator.getMsjFechaIni());
						mensajeDTO.setMsjFechaFin(iterator.getMsjFechaFin());
						mensajeDTO.setMsjEstado(iterator.getMsjEstado());
						listaMensajeAsuntoDTO.add(mensajeDTO);

					}
					log.info("Respuesta Exitosa.");
					return new Response(HttpStatus.OK.value(), "Respuesta Exitosa.", listaMensajeAsuntoDTO);

				} else {
					log.error("No se encontraron mensajes.");
					return new Response(HttpStatus.BAD_REQUEST.value(), "No se encontraron mensajes.", null);
				}
			}

			if (bandera == 2) {
				List<Mensaje> mensajes = listaMensajesFiltro.getMensajes();
				if (!mensajes.isEmpty()) {
					for (Mensaje iterator : mensajes) {
						MensajeAsuntoDTO mensajeDTO = new MensajeAsuntoDTO();
						mensajeDTO.setMsjCodigo(iterator.getMsjCodigo());
						mensajeDTO.setMsjEnviadoPor(iterator.getMsjEnviadoPor());
						mensajeDTO.setMsjEnviadoPorNombre(nombreEnviadoPor(iterator.getMsjEnviadoPor()));
						mensajeDTO.setMsjAsunto(iterator.getMsjAsunto());
						mensajeDTO.setMsjFecha(iterator.getMsjFecha());
						mensajeDTO.setMsjFechaIni(iterator.getMsjFechaIni());
						mensajeDTO.setMsjFechaFin(iterator.getMsjFechaFin());
						mensajeDTO.setMsjEstado(iterator.getMsjEstado());
						listaMensajeAsuntoDTO.add(mensajeDTO);
					}

					response.put("datos:", listaMensajeAsuntoDTO);
					response.put("totalRegistros:", listaMensajesFiltro.getTotalRegistros());
					response.put("numeroPaginas:", listaMensajesFiltro.getNumeroPaginas());
					response.put("paginaActual:", listaMensajesFiltro.getPaginaActual());

					log.info("Respuesta Exitosa.");
					return new Response(HttpStatus.OK.value(), "Respuesta Exitosa.", response);
				} else {
					log.error("No se encontraron mensajes.");
					return new Response(HttpStatus.BAD_REQUEST.value(), "No se encontraron mensajes.", null);
				}
			}
		} else {
			log.error("Usuario no tiene permisos para ver mensajes.");
			return new Response(HttpStatus.BAD_REQUEST.value(), "Usuario no tiene permisos para ver mensajes.", null);
		}
		log.error("No se encontraron mensajes.");
		return new Response(HttpStatus.BAD_REQUEST.value(), "No se encontraron mensajes.", null);
	}

	@Override
	public Response nuevoMensaje(MensajeNuevoDTO mensaje, Long id, String usuarioId, String perfilId)
			throws ParseException {

		Mensaje nuevoMensaje = new Mensaje();
		int bandera = 0;
		if (validarPermisos(perfilId, enviarMensaje)) {
			nuevoMensaje = crearMensaje(mensaje, usuarioId, perfilId, id, bandera);
			if (nuevoMensaje != null) {
				return new Response(HttpStatus.OK.value(), "Se realizo el envio del mensaje de forma exitosa.",
						nuevoMensaje);
			} else {
				return new Response(HttpStatus.BAD_REQUEST.value(),
						"Se ha generado un error al enviar los datos para la creacion del mensaje.", null);
			}
		} else {
			log.error("Usuario no tiene permisos para enviar mensajes.");
			return new Response(HttpStatus.BAD_REQUEST.value(), "Usuario no tiene permisos para enviar mensajes.",
					null);
		}

	}

	@Override
	public Response actualizarMensaje(MensajeNuevoDTO mensajeDto, Long id, String usuarioId, String perfilId)
			throws ParseException {

		if (validarPermisos(perfilId, editarMensaje)) {
			if (id != null) {
				Mensaje mensaje = mensaje(id);
				if (mensaje != null) {
					if (mensajeDto.getAsunto().equals(mensaje.getMsjAsunto())
							&& FormateoDeFechas(mensajeDto.getFecha_inicio())
									.equals(FormateoDeFechas(mensaje.getMsjFechaIni()))
							&& FormateoDeFechas(mensajeDto.getFecha_finalizacion())
									.equals(FormateoDeFechas(mensaje.getMsjFechaFin()))
							&& mensajeDto.getContenido().equals(mensaje.getMsjContenido())
							&& convertirListaACadena(mensajeDto.getPerfiles()).equals(mensaje.getMsjEnviadoPerfil())
							&& convertirListaACadena(mensajeDto.getLocalidades()).equals(mensaje.getMsjEnviadoAlocal())
							&& convertirListaACadena(mensajeDto.getColegios()).equals(mensaje.getMsjEnviadoAcoleg())
							&& convertirListaACadena(mensajeDto.getSedes()).equals(mensaje.getMsjEnviadoAsede())
							&& convertirListaACadena(mensajeDto.getJornadas()).equals(mensaje.getMsjEnviadoAjorn())) {
						log.info("No se encontraron cambios en el mensaje.");
						return new Response(HttpStatus.OK.value(), "No se encontraron cambios en el mensaje.", mensaje);
					} else {
						Mensaje nuevoMensaje = new Mensaje();
						int bandera = 1;
						nuevoMensaje = crearMensaje(mensajeDto, usuarioId, perfilId, id, bandera);
						if (nuevoMensaje != null) {
							log.info("Se realizo el envio del mensaje de forma exitosa.");
							return new Response(HttpStatus.OK.value(), "Se realizo el envio de mensaje de forma exitosa.",
									nuevoMensaje);
						} else {
							return new Response(HttpStatus.BAD_REQUEST.value(),
									"Error al enviar los datos para la actualizacion del mensaje.", null);
						}
					}
				} else {
					log.error("No se encontro mensajes.");
					return new Response(HttpStatus.BAD_REQUEST.value(), "No se encontro mensajes.", null);
				}

			} else {
				log.error("Id del mensaje no puede ser nulo.");
				return new Response(HttpStatus.BAD_REQUEST.value(), "Id del mensaje no puede ser nulo.", null);
			}
		} else {
			log.error("Usuario no tiene permisos para actualizar mensajes.");
			return new Response(HttpStatus.BAD_REQUEST.value(), "Usuario no tiene permisos para actualizar mensajes.",
					null);
		}

	}

	private List<Mensaje> listadoMensajesHome(String usuarioId, String perfilId, String colegioId, String sedeId,
			String jornadaId, String localidadId) {
		List<Mensaje> listadoMensajes = null;
		try {
			listadoMensajes = mensajeRepository.listaMensajesHome(perfilId, colegioId, sedeId, jornadaId, localidadId);
		} catch (Exception e) {
			log.error("Se ha generado una excepción al listar los mensajes en el metodo listadoMensajesHome.",
					e);
			listadoMensajes = null;
		}
		return listadoMensajes;
	}

	private MensajePaginado listadoMensajesBuscar(String usuarioId, String perfilId, String colegioId,
			String localidadId, String fechaInicio, String fechaFin, int pagina, int tamanoPagina, int flag) {

		List<Mensaje> listadoMensajes = null;

		long totalRegistros = 0;
		int numeroPaginas = 0;
		int paginaActual = pagina;

		try {
			if (flag == 1)
				listadoMensajes = mensajeRepository.listadoMensajesBuscar(usuarioId, perfilId, colegioId, localidadId,
						fechaInicio, fechaFin);
			if (flag == 0)
				listadoMensajes = mensajeRepository.listadoMensajesBuscarAdmin(usuarioId, perfilId, colegioId,
						localidadId, fechaInicio, fechaFin);

			totalRegistros = listadoMensajes.size();
			numeroPaginas = (int) Math.ceil((double) totalRegistros / tamanoPagina);

			int desde = pagina * tamanoPagina;
			int hasta = Math.min(desde + tamanoPagina, listadoMensajes.size());
			listadoMensajes = listadoMensajes.subList(desde, hasta);

		} catch (Exception e) {
			log.error(
					"Se ha generado una excepción al listar los mensajes en el metodo listadoMensajesBuscar.",
					e);
			listadoMensajes = null;
		}
		return new MensajePaginado(listadoMensajes, totalRegistros, numeroPaginas, paginaActual);
	}

	private Mensaje mensaje(Long id) {
		Mensaje mensaje = null;
		try {
			mensaje = mensajeRepository.verMensaje(id);
		} catch (Exception e) {
			log.error("Se ha generado una excepción al listar los mensajes en el metodo Mensajes.",
					e.getMessage());
			mensaje = null;
		}
		return mensaje;

	}

	private String convertirListaACadena(List<String> arrayList) {
		String cadena = new String();

		for (int i = 0; i < arrayList.size(); i++) {
			cadena += arrayList.get(i);
			if (i < arrayList.size() - 1) {
				cadena += (",");
			}
		}
		return cadena;
	}

	private List<String> convertirCadenaALista(String cadena) {
		String[] datos = cadena.split(",");
		List<String> lista = new ArrayList<>();
		try {
			if (cadena != null) {
				for (String iterator : datos) {
					if (iterator != null && !iterator.trim().isEmpty())
						lista.add(iterator);
				}
			}

		} catch (Exception e) {
			lista = null;
		}
		return lista;

	}

	private String nombreEnviadoPor(Long idEnviadoPor) {
		List<EnviadoPor> enviadoPorList = new ArrayList<>();
		enviadoPorList.add(new EnviadoPor((long) 1, "SISTEMA"));
		enviadoPorList.add(new EnviadoPor((long) 2, "SED"));
		enviadoPorList.add(new EnviadoPor((long) 3, "LOCALIDAD"));
		enviadoPorList.add(new EnviadoPor((long) 4, "COLEGIO"));
		for (EnviadoPor item : enviadoPorList) {
			if (item.getCodigo().equals(idEnviadoPor))
				return item.getNombre();
		}
		return null;
	}

	private Mensaje crearMensaje(MensajeNuevoDTO mensaje, String usuarioId, String perfilId, Long id, int bandera)
			throws ParseException {
		Mensaje nuevoMensaje = new Mensaje();
		Mensaje oldmensaje = mensaje(id);
		Long estado = null;

		try {
			Long secuencia = secuenciaMensaje();
			if (bandera == 0) {
				nuevoMensaje.setMsjCodigo(secuencia);
				estado = (long) 3;
				nuevoMensaje.setMsjFecha(FormateoDeFechas(fechaActual));
				List<Integer> jerarquia = validarNivel(perfilId);

				int minimo = jerarquia.get(0);
				for (Integer itr : jerarquia) {
					if (itr < minimo) {
						minimo = itr;
					}
				}
				if (minimo == 0 || minimo == 1)
					nuevoMensaje.setMsjEnviadoPor((long) 2);
				if (minimo == 3)
					nuevoMensaje.setMsjEnviadoPor((long) 3);
				if (minimo == 4 || minimo == 5)
					nuevoMensaje.setMsjEnviadoPor((long) 4);
			} else {

				nuevoMensaje.setMsjCodigo(id);
				estado = (long) 0;
				nuevoMensaje.setMsjFecha(mensaje.getFecha());
				nuevoMensaje.setMsjFecha(oldmensaje.getMsjFecha());

				nuevoMensaje.setMsjEnviadoPor(oldmensaje.getMsjEnviadoPor());
			}
			nuevoMensaje.setMsjAsunto(mensaje.getAsunto());
			nuevoMensaje.setMsjFechaIni(FormateoDeFechas(mensaje.getFecha_inicio()));
			nuevoMensaje.setMsjFechaFin(FormateoDeFechas(mensaje.getFecha_finalizacion()));
			nuevoMensaje.setMsjContenido(mensaje.getContenido());
			nuevoMensaje.setMsjEnviadoPerfil(convertirListaACadena(mensaje.getPerfiles()));
			nuevoMensaje.setMsjEnviadoAcoleg(convertirListaACadena(mensaje.getColegios()));
			nuevoMensaje.setMsjEnviadoAlocal(convertirListaACadena(mensaje.getLocalidades()));
			nuevoMensaje.setMsjEnviadoAsede(convertirListaACadena(mensaje.getSedes()));
			nuevoMensaje.setMsjEnviadoAjorn(convertirListaACadena(mensaje.getJornadas()));

			nuevoMensaje.setMsjUsuario(usuarioId);
			nuevoMensaje.setMsjEstado(estado);
			mensajeRepository.save(nuevoMensaje);

			List<String> listaPersonal = new ArrayList<>();
			listaPersonal = validacionListasMensaje(mensaje.getPerfiles(), mensaje.getColegios(),
					mensaje.getLocalidades(), mensaje.getSedes(), mensaje.getJornadas());
			if (!mensaje.getPerfiles().isEmpty()) {
				String nombrePerfil = "";
				for (String iterablePerfil : mensaje.getPerfiles()) {
					try {
						nombrePerfil = perfilRepository.findPerfilNombre(iterablePerfil);
					} catch (Exception e) {
						nombrePerfil = "N/A";
						log.error("Se generado una excepción la busqueda del nombre del perfil para el mensaje.",
								e.getMessage());
					}
					if (!listaPersonal.isEmpty()) {
						String cuerpoCorreo = "<html><body>Estimad@ " + nombrePerfil + "<br><br>"
								+ mensaje.getContenido()+"<br><br>"
								+ "Puede verificar este y demás comunicados, ingresando desde el Sistema de Apoyo Escolar, en la sección de mensajes.<br><br>"
								+ "NOTA: Por favor, no responda este correo. Es un mensaje generado automáticamente.<br><br></body></html>";

						int batchSize = 100;
						for (int i = 0; i < listaPersonal.size(); i += batchSize) {
							List<String> batch = listaPersonal.subList(i,
									Math.min(i + batchSize, listaPersonal.size()));
							enviarCorreoElectronico(from, batch, mensaje.getAsunto(),
									cuerpoCorreo);
							
						}
					}
				}

			}

			log.info("Se realizo el envio del mensaje de forma exitosa.");
			return nuevoMensaje;

		} catch (Exception e) {
			log.error("Se ha generado una excepción durante la creación del mensaje.", e.getMessage());
			return null;
		}

	}

	private List<String> validacionListasMensaje(List<String> perfiles, List<String> colegios, List<String> localidades,
			List<String> sedes, List<String> jornadas) {
		List<String> correosPersonal = new ArrayList<>();

		if (!perfiles.isEmpty() && !colegios.isEmpty() && !localidades.isEmpty()) {
			for (String perfil : perfiles) {
				for (String colegio : colegios) {
					for (String localidad : localidades) {
						List<String> listaTemporal = new ArrayList<>();
						if (!sedes.isEmpty() && !jornadas.isEmpty()) {
							for (String sede : sedes) {
								for (String jornada : jornadas) {
									listaTemporal
											.addAll(listaCorreosPersonal(perfil, colegio, localidad, sede, jornada));
								}
							}
						} else if (!sedes.isEmpty() && jornadas.isEmpty()) {
							for (String sede : sedes) {
								listaTemporal.addAll(listaCorreosPersonal(perfil, colegio, localidad, sede, null));
							}
						} else if (sedes.isEmpty() && !jornadas.isEmpty()) {
							for (String jornada : jornadas) {
								listaTemporal.addAll(listaCorreosPersonal(perfil, colegio, localidad, null, jornada));
							}
						} else {
							listaTemporal.addAll(listaCorreosPersonal(perfil, colegio, localidad, null, null));
						}
						correosPersonal.addAll(listaTemporal);

					}

				}
			}
		}
		return correosPersonal;
	}

	private List<String> listaCorreosPersonal(String perfil, String colegio, String localidad, String sede,
			String jornada) {
		List<String> listaCorreosPersonal = new ArrayList<>();

		try {
			listaCorreosPersonal = personalRepository.findByPersonalPorPerfil(perfil, colegio, localidad, sede,
					jornada);
		} catch (Exception e) {
			log.error("Se ha generado una excepción durante la búsqueda de correos electrónicos para enviar el mensaje.", e.getMessage());
			return null;
		}

		return listaCorreosPersonal;
	}

	private boolean validarFechas(LocalDate fechaInicioStr, LocalDate fechaFinStr) {
		try {
			LocalDate fechaInicio = FormateoDeFechas(fechaInicioStr);
			LocalDate fechaFin = FormateoDeFechas(fechaFinStr);
			if (fechaFin.isBefore(fechaInicio))
				return false;

			return true;
		} catch (Exception e) {
			log.error("Se ha generado una excepción durante la validación de fechas para los mensajes.", e.getMessage());
			return false;
		}

	}

	private LocalDate FormateoDeFechas(LocalDate fecha) {
		String fechaFormateada = fecha.format(formatoFecha);
		return LocalDate.parse(fechaFormateada, formatoFecha);

	}

	private Long secuenciaMensaje() {
		try {
			return mensajeRepository.maxCodigo() + 1;
		} catch (Exception e) {
			return (long) 1;
		}
	}

	private boolean validarPermisos(String perfilId, long permisoId) {
		boolean respuesta = false;
		try {
			if (!perfilId.isEmpty()) {
				List<Object[]> permiso = perfilRepository.findPermisoPerfil(perfilId, permisoId);
				if (!permiso.isEmpty()) {
					respuesta = true;
				} else {
					respuesta = false;
				}
			}

		} catch (Exception e) {
			log.error("Se ha generado una excepción durante la validación de permisos para los mensajes.", e.getMessage());
			respuesta = false;
		}
		return respuesta;
	}

	private List<Integer> validarNivel(String perfilId) {
		List<Integer> nivelPerfil = new ArrayList<>();

		try {
			if (!perfilId.isEmpty()) {
				List<Object[]> jerarquia = perfilRepository.findPerfilNivel(perfilId);
				if (!jerarquia.isEmpty()) {
					for (Object[] objects : jerarquia) {
						nivelPerfil.add(Integer.parseInt(objects[2].toString()));
					}
				} else {
					nivelPerfil = null;
				}
			} else {
				nivelPerfil = null;
			}
		} catch (Exception e) {
			log.error("Se ha generado una excepción durante la validación de niveles de los permisos de mensajes.", e.getMessage());
			nivelPerfil = null;
		}

		return nivelPerfil;
	}

	public void enviarCorreoElectronico(String from, List<String> toList, String subject, String mensaje)
			throws MessagingException {
		int batchSize = 100;
		for (int i = 0; i < toList.size(); i += batchSize) {
			List<String> batch = toList.subList(i, Math.min(i + batchSize, toList.size()));
			enviarCorreoElectronicoBatch(from, batch, subject, mensaje);
		}
	}

	private void enviarCorreoElectronicoBatch(String from, List<String> toList, String subject, String mensaje)
			throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		try {
			helper.setFrom(from);
			helper.setTo(toList.toArray(new String[0]));
			helper.setSubject(subject);
			helper.setText(mensaje, true);
			mailSender.send(message);
		} catch (Exception e) {
			throw new IllegalStateException("Se ha generado una excepción enviando el correo electrónico." + e.getMessage());
		}
	}

}
