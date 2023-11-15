package linktic.lookfeel.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import linktic.lookfeel.dtos.ColegioDTO;
import linktic.lookfeel.dtos.DatosEstudianteDTO;
import linktic.lookfeel.dtos.EstudiantesPorInstitucionDTO;
import linktic.lookfeel.dtos.GradoDTO;
import linktic.lookfeel.dtos.GrupoDTO;
import linktic.lookfeel.dtos.JornadaDTO;
import linktic.lookfeel.dtos.MetodologiaDTO;
import linktic.lookfeel.dtos.PaginadoDTO;
import linktic.lookfeel.dtos.SedeDTO;
import linktic.lookfeel.dtos.TipoDocumentoDTO;
import linktic.lookfeel.dtos.TotalEstudiantesPorInstitucionDTO;
import linktic.lookfeel.model.Constante;
import linktic.lookfeel.model.ConsultaExterna;
import linktic.lookfeel.model.DatosEstudiante;
import linktic.lookfeel.model.Estudiante;
import linktic.lookfeel.model.Grado;
import linktic.lookfeel.model.Grupo;
import linktic.lookfeel.model.Institucion;
import linktic.lookfeel.model.Periodos;
import linktic.lookfeel.model.Response;
import linktic.lookfeel.model.Sede;
import linktic.lookfeel.repositories.ConstanteRepository;
import linktic.lookfeel.repositories.ConsultaExternaRepository;
import linktic.lookfeel.repositories.DatosEstudianteRepository;
import linktic.lookfeel.repositories.EstudianteRepository;
import linktic.lookfeel.repositories.GradoRepository;
import linktic.lookfeel.repositories.GrupoRepository;
import linktic.lookfeel.repositories.InstitucionRepository;
import linktic.lookfeel.repositories.PeriodosRepository;
import linktic.lookfeel.repositories.SedeRepository;
import linktic.lookfeel.util.UtilString;
import linktic.lookfeel.util.Utilidades;

/**
 *
 * @author Ing. Alexander A. Muñoz Coneo
 * @descripcion Servicios Para la operaciones relacionadas con las consultas
 *              externas de los usuarios
 * @fechacreacion 16/08/2023
 * @requrimiento HU009_SED, HU010_SED
 * @version 1.0
 */

@Service
public class ConsultaExternaService implements IConsultaExternaService {
	
	private static final Logger log = LoggerFactory.getLogger(ConsultaExternaService.class);
	  
	@Autowired
	private EstudianteRepository estudianteRepository;

	@Autowired
	private DatosEstudianteRepository datosEstudianteRepository;

	@Autowired
	private ConsultaExternaRepository consultaExternaRepository;

	@Autowired
	private ConstanteRepository constanteRepository;
	
	@Autowired
	private InstitucionRepository institucionRepository;
	
	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private GradoRepository gradoRepository;
	
	@Autowired
	private SedeRepository sedeRepository;
	
	@Autowired
	private PeriodosRepository periodosRepository;
	
	@Value("${boletines.path}")
    private String boletinesPath;
    
    private Path filePath;
    
    @Override
	public Response validPinDocumento(String pinDocumento) throws IOException {
    	ConsultaExterna url;
		if (!pinDocumento.isEmpty()) {
			url = consultaDocumento(pinDocumento);
			if (url != null) {
				Resource resource;
				if(url.getCeRutaArchivo()!=null) {
						String patch = url.getCeRutaArchivo().replace('.', Utilidades.getSeparadorSO());
					filePath = Paths.get(patch+url.getCeNombreArchivo()).toAbsolutePath().normalize();
					try {
						resource = new UrlResource(filePath.toUri());
						if (resource.exists()) {
							return new Response(HttpStatus.OK.value(), "Respuesta Exitosa.", resource);						
		                }else {
		                	log.error("No se encontraron archivo para mostrar ", url.getCeNombreArchivo());
		                	return new Response(HttpStatus.BAD_REQUEST.value(),"No se encontraron archivo para mostrar ", url.getCeNombreArchivo());
	                }
			
					}catch (MalformedURLException e) {
						log.error("Se creado una excepcion para mostar el archivo ", url.getCeNombreArchivo());
						return new Response(HttpStatus.BAD_REQUEST.value(),"Se creado una excepcion para mostar el archivo ", url.getCeNombreArchivo());
					}
				}else {
					return new Response(HttpStatus.BAD_REQUEST.value(),"No se puede presentar el archivo porque no existe registro de la ubicación", url.getCeNombreArchivo());
				}
				
			} else {
				log.error("No se encontraron resultados. " + " Validacion Fallida");
				return new Response(HttpStatus.BAD_REQUEST.value(), "No se encontraron resultados. " , null);
			}
		} else {
			log.error("Campo pin requido para validar");
			return new Response(HttpStatus.BAD_REQUEST.value(),"Campo pin requido para validar", null);
		}
    	
    }
    
    
  
    @Override
	public Response consultaBoletin(String tipoDocumento, String numeroDocumento, String codigoEstudiante) {
		Estudiante estudiante = new Estudiante();
		DatosEstudiante datosEstudiante = new DatosEstudiante();
		Institucion institucion = new Institucion();
		Sede sede = new Sede();
		Constante jornada = new Constante();
		Constante metodologia = new Constante();
		Grado grado = new Grado();
		Grupo grupo = new Grupo();
		Periodos periodo = new Periodos();
		
		DatosEstudianteDTO datosEstudianteDTO = new DatosEstudianteDTO();
		Constante tipoDocumentoEst = new Constante();
		TipoDocumentoDTO tipoDocumentoDTO = new TipoDocumentoDTO();
		ColegioDTO colegioDTO = new ColegioDTO();
		SedeDTO sedeDTO = new SedeDTO();
		JornadaDTO jornadaDTO = new JornadaDTO();
		MetodologiaDTO metodologiaDTO = new MetodologiaDTO();
		GradoDTO gradoDTO = new GradoDTO();
		GrupoDTO grupoDTO = new GrupoDTO();
		
		
		if (!tipoDocumento.isEmpty()) {
			if (!numeroDocumento.isEmpty()) {
				if (!codigoEstudiante.isEmpty()) {
					estudiante = consultaEstudiante(tipoDocumento, numeroDocumento, codigoEstudiante);
					if (estudiante != null) {
						datosEstudiante = consultaDatosEstudiante(estudiante);
						if (datosEstudiante != null) {
							tipoDocumentoEst = consultaTipoDocumento(datosEstudiante.tipoDocumento);
							if(tipoDocumentoEst!=null) {
								tipoDocumentoDTO.setId(tipoDocumentoEst.getConstantePk().getCodigo());
								tipoDocumentoDTO.setNombre(tipoDocumentoEst.getAbrev());
								datosEstudianteDTO.setTipoDocumento(tipoDocumentoDTO);
							}
							datosEstudianteDTO.setCodigoEstudiante(datosEstudiante.getCodigoEstudiante());
							datosEstudianteDTO.setNumeroDocumento(datosEstudiante.getNumeroDocumento());
							datosEstudianteDTO.setNombreEstudiante(datosEstudiante.getNombreEstudiante());
							
							institucion = consultaInstitucion(datosEstudiante.getCodigoInstitucion());
							datosEstudianteDTO.setVigencia(institucion.getVigencia());
							if(institucion!=null) {
								colegioDTO.setId(institucion.getCodigo());	
								colegioDTO.setNombre(institucion.getNombre());
								datosEstudianteDTO.setInstitucion(colegioDTO);
								
							}
							
							sede = consultaSede(datosEstudiante.getCodigoSede(), datosEstudiante.getCodigoInstitucion());
							if(sede!=null) {
								sedeDTO.setId(sede.getCodigo());	
								sedeDTO.setNombre(sede.getNombre());
								datosEstudianteDTO.setSede(sedeDTO);
								
							}
							jornada = consultaJornada(datosEstudiante.getJornada(), datosEstudiante.getCodigoInstitucion());
							if(jornada != null) {
								jornadaDTO.setId(jornada.getConstantePk().getCodigo());
								jornadaDTO.setNombre(jornada.getNombre());
								datosEstudianteDTO.setJornada(jornadaDTO);
								
							}
							
							metodologia = consultaMetodologia(datosEstudiante.getMetodologia(), datosEstudiante.getCodigoInstitucion());
							if(metodologia != null) {
								metodologiaDTO.setId(metodologia.getConstantePk().getCodigo());
								metodologiaDTO.setNombre(metodologia.getNombre());
								datosEstudianteDTO.setMetodologia(metodologiaDTO);
							}
							grado = consultaGrado(datosEstudiante.getCodigoInstitucion(), datosEstudiante.getMetodologia(), datosEstudiante.getGrado());
							if(grado != null) {
								gradoDTO.setId(grado.getGracodigo());
								gradoDTO.setNombre(grado.getGranombre());
								datosEstudianteDTO.setGrado(gradoDTO);
							}
							grupo = consultaGrupo(datosEstudiante.getCodigoInstitucion(), datosEstudiante.getCodigoSede(), datosEstudiante.getJornada(), datosEstudiante.getGrado(), datosEstudiante.getGrupo());
							if(grupo != null) {
								grupoDTO.setId(grupo.getGrupoPk().getGruCodigo());
								grupoDTO.setNombre(grupo.getGrupoNombre());
								datosEstudianteDTO.setGrupo(grupoDTO);
								
							}
							
							periodo = consultaPeriodos(institucion.getCodigo(), institucion.getVigencia());
							datosEstudianteDTO.setPeriodos(generarLista(periodo));
							
							return new Response(HttpStatus.OK.value(), "Respuesta Exitosa.", datosEstudianteDTO);
						}else {
							log.error("No se encontraron datos del estudiante.");
							return new Response(HttpStatus.BAD_REQUEST.value(),
									"No se encontraron datos del estudiante.", null);
						}
						

					} else {
						log.error("No se encontro informacion del estudiante.");
						return new Response(HttpStatus.BAD_REQUEST.value(),
								"No se encontro informacion del estudiante.", null);
					}

				} else {
					log.error("Codigo de estudiante no puede ser nulo.");
					return new Response(HttpStatus.BAD_REQUEST.value(), "Codigo de estudiante no puede ser nulo.",
							null);
				}

			} else {
				log.error("Numero de documento de estudiante no puede ser nulo.");
				return new Response(HttpStatus.BAD_REQUEST.value(),
						"Numero de documento de estudiante no puede ser nulo.", null);
			}
		} else {
			log.error("Tipo de documento de estudiante no puede ser nulo.");
			return new Response(HttpStatus.BAD_REQUEST.value(), "Tipo de documento de estudiante no puede ser nulo.",
					null);
		}


	}

	private Estudiante consultaEstudiante(String tipoDocumento, String numeroDocumento, String codigoEstudiante) {
		Estudiante estudiante = new Estudiante();
		try {
			estudiante = estudianteRepository.buscarEstudiante(tipoDocumento, numeroDocumento, codigoEstudiante);
		} catch (Exception e) {
			estudiante = null;
		}
		return estudiante;
	}

	private DatosEstudiante consultaDatosEstudiante(Estudiante estudiante) {
		DatosEstudiante datosEstudiante = new DatosEstudiante();
		try {
			datosEstudiante = datosEstudianteRepository.buscarEstudiante(estudiante.getTipo_documento(),
					estudiante.getNumero_documento(), estudiante.getCodigo_estudiante());
		} catch (Exception e) {
			datosEstudiante = null;
		}
		return datosEstudiante;
	}

	private Constante consultaTipoDocumento(long id) {
		Constante respuesta = new Constante();
		try {
			respuesta = constanteRepository.tipoDocumentoPorId(id);
		} catch (Exception e) {
			respuesta = null;
		}
		return respuesta;
	}

	private Institucion consultaInstitucion(long id) {
		Institucion respuesta = new Institucion();
		try {
			respuesta = institucionRepository.findByCodigo(id);
		} catch (Exception e) {
			respuesta = null;
		}
		return respuesta;
	}
	
	private Sede consultaSede(long idSede, long idInstitucion) {
		Sede respuesta = new Sede();
		try {
			respuesta = sedeRepository.sedePorCodigoInstYCodigoSede(idSede, idInstitucion);
		} catch (Exception e) {
			respuesta = null;
		}
		return respuesta;
	}
	
	private Constante consultaJornada(long idJornada, long idInstitucion) {
		Constante respuesta = new Constante();
		try {
			respuesta = constanteRepository.jornadaPorCodigoInsti(idJornada, idInstitucion);
		} catch (Exception e) {
			respuesta = null;
		}
		return respuesta;
	}
	
	private Constante consultaMetodologia(long idMetodologia, long idInstitucion) {
		Constante respuesta = new Constante();
		try {
			respuesta = constanteRepository.metodologiaPorCodigoInsti(idMetodologia, idInstitucion);
		} catch (Exception e) {
			respuesta = null;
		}
		return respuesta;
	}
	
	private Grado consultaGrado(long idInstitucion, long idMetodologia, long idGrado) {
		Grado respuesta = new Grado();
		try {
			respuesta = gradoRepository.gradoPorInstMetodIdEst(idInstitucion, idMetodologia, idGrado);
		} catch (Exception e) {
			respuesta = null;
		}
		return respuesta;
	}
	
	private Grupo consultaGrupo(long idInstitucion, long idSede, long idJornada, long idGrado, long idGrupo) {
		Grupo respuesta = new Grupo();
		try {
			respuesta = grupoRepository.grupoPorDatoEstudiante(idInstitucion, idSede, idJornada, idGrado, idGrupo);
		} catch (Exception e) {
			e.printStackTrace();
			respuesta = null;
		}
		return respuesta;
	}
	
		
	private Periodos consultaPeriodos(long idInstitucion, long idVigencia) {
		Periodos respuesta = new Periodos();
		try {
			respuesta = periodosRepository.listaPeriodos(idInstitucion, idVigencia);
		} catch (Exception e) {
			respuesta = null;
		}
		return respuesta;
	}
	
	
	private ConsultaExterna consultaDocumento(String pinDocumento) {
		ConsultaExterna info = new ConsultaExterna();
		try {
			info = consultaExternaRepository.buscarDocumento(pinDocumento);
		} catch (Exception e) {
			info = null;
		}
		return info;
	}
	
	private List<Periodos> generarLista(Periodos periodoFinal){
		List<Periodos> listaPeriodos = new ArrayList<>();
		
		for (int i = 1; i <= Integer.parseInt(periodoFinal.getId())-1; i++) {
			Periodos periodo = new Periodos();
			String NumeroLetras = obtenerNumeroLetras(i);
			periodo.setId(String.valueOf(i));
			periodo.setNombre(NumeroLetras);
			listaPeriodos.add(periodo);
		}
		listaPeriodos.add(periodoFinal);
		return listaPeriodos;
	}
	
	private String obtenerNumeroLetras(int numero) {
		switch (numero) {
        case 1:
            return "Primero";
        case 2:
            return "Segundo";
        case 3:
            return "Tercero";
        case 4:
            return "Cuarto";
        case 5:
            return "Quinto";
        case 6:
            return "Sexto";
        case 7:
            return "Septimo";
        default:
            return "Número Desconocido";            
		}
	}
	
	@Override
	public PaginadoDTO consultaPaginadaEstudiantesPorInstitucion( int codigoInstitucion,
			String codigoSede,
			String codigoJornada,
			int numPagina,
			int numRegPorPagina) {
		List<Object[]> listData = null;
		List<EstudiantesPorInstitucionDTO> listaEstudiantesPorInstitucion = null;
		PaginadoDTO paginadoDTO = null;
		int totalRegistros = 0;
		int numeroPaginas = 0;
		
		try {
			
			if (codigoInstitucion <= 0) {
				throw new Exception("El id o codigo de la institución es inferior o igual a cero, digite un numero entero y superior a cero");
			}
			
			if (numPagina <= 0) {
				throw new Exception("El numero de pagina es inferior o igual a cero, digite un numero entero y superior a cero");
			}
			
			if (numRegPorPagina <= 0) {
				throw new Exception("El numero de registros por pagina es inferior o igual a cero, digite un numero entero y superior a cero");
			}
			
			if (codigoSede == null || codigoSede.isEmpty()) {
				codigoSede = "0";
			}		
			
			if (codigoJornada == null || codigoJornada. isEmpty()) {
				codigoJornada = "-1"; // Se realiza, debido a que existen relaciones con el valor cero (0)
			}
			
			listData = estudianteRepository.consultaTotalRegEstudiantesPorInstitucion(codigoInstitucion, Integer.parseInt(codigoSede), Integer.parseInt(codigoJornada));
			
			if (listData == null || listData.isEmpty()) {
				throw new Exception("No se encontraron registros");
			}
			
			// Se obtiene el total de registros
			for (Object[] regData : listData) {
				totalRegistros = Integer.parseInt(UtilString.obtenerValorString(regData[0]));
				numeroPaginas = (int) Math.ceil((double) totalRegistros / numRegPorPagina);
			}
			
			listData = null;
			listData = estudianteRepository.consultaPaginadaEstudiantesPorInstitucion(codigoInstitucion, Integer.parseInt(codigoSede), Integer.parseInt(codigoJornada), numPagina, numRegPorPagina);
			
			listaEstudiantesPorInstitucion = new ArrayList<>();
			
			if (listData != null && !listData.isEmpty()) {
				for (Object[] regData : listData) {
					listaEstudiantesPorInstitucion.add(new EstudiantesPorInstitucionDTO(UtilString.obtenerValorString(regData[0]), 
							UtilString.obtenerValorString(regData[1]),
							UtilString.obtenerValorString(regData[2]),
							UtilString.obtenerValorString(regData[3]), 
							UtilString.obtenerValorString(regData[4]),
							UtilString.obtenerValorString(regData[5]), 
							UtilString.obtenerValorString(regData[6]),
							UtilString.obtenerValorString(regData[7]),
							UtilString.obtenerValorString(regData[8]),
							UtilString.obtenerValorString(regData[9])));
				}
			}
			
			paginadoDTO = new PaginadoDTO(numPagina, numeroPaginas, numRegPorPagina, totalRegistros, listaEstudiantesPorInstitucion);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return paginadoDTO;
	}
	
	
	@Override
	public PaginadoDTO consultaColegiosPaginados(int codigoLocalidad, int numPagina, int numRegPorPagina) {
		List<Object[]> listData = null;
		List<TotalEstudiantesPorInstitucionDTO> listaTotalEstudiantesPorInstitucion = null;
		PaginadoDTO paginadoDTO = null;
		int totalRegistros = 0;
		int numeroPaginas = 0;
		
		try {
			if (numPagina <= 0) {
				throw new Exception("El numero de pagina es inferior o igual a cero, digite un numero entero y superior a cero");
			}
			
			if (numRegPorPagina <= 0) {
				throw new Exception("El numero de registros por pagina es inferior o igual a cero, digite un numero entero y superior a cero");
			}
			
			listData = estudianteRepository.consultaTotalRegNumEstudiantesPorInstituto(codigoLocalidad);
			
			if (listData == null || listData.isEmpty()) {
				throw new Exception("No se encontraron registros");
			}
			
			// Se obtiene el total de registros
			for (Object[] regData : listData) {
				totalRegistros = Integer.parseInt(UtilString.obtenerValorString(regData[0]));
				numeroPaginas = (int) Math.ceil((double) totalRegistros / numRegPorPagina);
			}
			
			listData = null;
			listData = estudianteRepository.consultaPaginadaNumEstudiantesPorInstituto(codigoLocalidad,numPagina, numRegPorPagina);
			
			listaTotalEstudiantesPorInstitucion = new ArrayList<>();
			
			if (listData != null && !listData.isEmpty()) {
				for (Object[] regData : listData) {
					listaTotalEstudiantesPorInstitucion.add(new TotalEstudiantesPorInstitucionDTO(Integer.parseInt(UtilString.obtenerValorString(regData[0])),
							  UtilString.obtenerValorString(regData[1]),
							  UtilString.obtenerValorString(regData[2]), 
							  Integer.parseInt(UtilString.obtenerValorString(regData[3]))));
				}
			}
			
			paginadoDTO = new PaginadoDTO(numPagina, numeroPaginas, numRegPorPagina, totalRegistros, listaTotalEstudiantesPorInstitucion);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return paginadoDTO;
	}
}
