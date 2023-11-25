package linktic.lookfeel.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import linktic.lookfeel.model.Institucion;
import linktic.lookfeel.repositories.InstitucionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import linktic.lookfeel.ImageToBase64Converter;
import linktic.lookfeel.dtos.AccesoDirectoPorPerfilDTO;
import linktic.lookfeel.dtos.DependenciaDTO;
import linktic.lookfeel.dtos.FotoDTO;
import linktic.lookfeel.dtos.InstitucionDTO;
import linktic.lookfeel.dtos.JornadaDTO;
import linktic.lookfeel.dtos.LocalidadDTO;
import linktic.lookfeel.dtos.PerfilDTO;
import linktic.lookfeel.dtos.RegistroDTO;
import linktic.lookfeel.dtos.ResponseInsitucionDTO;
import linktic.lookfeel.dtos.SedeDTO;
import linktic.lookfeel.dtos.grupoServicioDTO;
import linktic.lookfeel.repositories.AccesoDirectoRepository;
import linktic.lookfeel.repositories.PerfilRepository;
import linktic.lookfeel.security.repositories.GrupoServicioRepository;

@Service
public class IntitucionService implements IInstitucionService {

	//@Autowired private InstitucionRepository institucionRepository;
	//@Autowired private SedeRepository sedeRepository;
	//@Autowired private IFotoService fotoService;
	
	@Autowired
	private PerfilRepository perfilRepository;

	@Autowired
	private ImageToBase64Converter imageToBase64Converter;

	@Autowired
	private GrupoServicioRepository grupoServicioRepository;

	@Autowired
	private AccesoDirectoRepository accesoDirectoRepository;

	@Autowired
	private InstitucionRepository institucionRepository;

	@Autowired
	private IFotoService fotoService;


	@Override
	public ResponseInsitucionDTO getInsitucionesByPersona(String idPersona) {
		ResponseInsitucionDTO responseInsitucionDTO = new ResponseInsitucionDTO();

		PerfilDTO perfil = new PerfilDTO();
		InstitucionDTO institucion = new InstitucionDTO();
		SedeDTO sede = new SedeDTO();
		JornadaDTO jornada = new JornadaDTO();
		List<RegistroDTO> listRegistros = new ArrayList<>();
		RegistroDTO registro = new RegistroDTO();
		LocalidadDTO localidadInstitucion;
		LocalidadDTO municipio = null;
		FotoDTO fotoDTO;
		List<Object[]> listData = null;
		String codNivel = "";
		
		List<Object[]> perfiles = perfilRepository.findPerfil(idPersona);
		
		for (Object[] regPerfil : perfiles) {
			codNivel = regPerfil[4].toString();
		}

		if (codNivel != null && !codNivel.isEmpty()) {
			
			if (Arrays.asList("0","1","2","4").contains(codNivel)) { 
				listData = perfilRepository.findRectorPerfilByNumDocumento(idPersona);
			} else {
				listData = perfilRepository.findHabitualPerfilByNumDocumento(idPersona);
			}
		
			if (listData != null && !listData.isEmpty()) {

				for (Object[] regData : listData) {
					perfil = new PerfilDTO();
					institucion = new InstitucionDTO();
					sede = new SedeDTO();
					jornada = new JornadaDTO();
					localidadInstitucion = new LocalidadDTO();
					registro = new RegistroDTO();
					fotoDTO = new FotoDTO();

					perfil.setId(Integer.parseInt(regData[1].toString()));
					perfil.setEtiqueta(regData[3].toString());
					perfil.setNombre(regData[2].toString());
					perfil.setIdPerfilNivel(Integer.parseInt(regData[13].toString()));
					perfil.setCodJerarquia(Integer.parseInt(regData[18].toString()));


					if (regData[4] != null) {
						try {
							Long codigoDane = this.perfilRepository.getByCodInstitucion(Long.parseLong(String.valueOf(regData[4])));
							System.out.println("1. Se tiene regData para trabajar colegio de imagen codigo dane12 : " + codigoDane );
							if (codigoDane != null) fotoDTO = fotoService.getEscudo(codigoDane.toString());
						} catch (Exception e) {
							e.printStackTrace();
						}

						// Se cargan datos de la LOCALIDAD DE LA INSTITUCION
						if (regData[6] != null) {
							localidadInstitucion.setId(Integer.parseInt(regData[6].toString()));
							localidadInstitucion.setNombre(regData[7].toString());	
						}						
						
						// Se cargan datos de la INSTITUCION
						if (regData[4] != null) {
							institucion.setId(Long.parseLong(regData[4].toString()));
							institucion.setNombre(regData[5].toString());
							institucion.setLocalidad(localidadInstitucion);
							institucion.setFoto_escudo(fotoDTO);
							if(institucion.getId()!=null){
								Institucion inst = this.institucionRepository.findByCodigo(institucion.getId());
								if (inst != null) {
									if (inst.getVigencia() != null) institucion.setVigencia(inst.getVigencia());
									if (inst.getMunicipio() != null) {
										municipio = new LocalidadDTO();
										municipio.setId(inst.getMunicipio().intValue());
									}
								}
							}
						}
						
						// Se entrega datos de SEDE
						if (regData[8] != null) {
							sede.setId(Long.parseLong(regData[8].toString()));
							sede.setNombre(regData[9].toString());
						}

						// Se entrega datos de JORNADA
						if (regData[10] != null && regData[11] != null) {
							jornada.setId(Long.parseLong(regData[10].toString()));
							jornada.setNombre(regData[11].toString());
						}
						registro.setMunicipio(municipio);
						registro.setColegio(institucion);
						registro.setSede(sede);
						registro.setJornada(jornada);
					}
					
					// Se entrega datos de DEPENDENCIA
					if (regData[14] != null) {
						registro.setDependencia(new DependenciaDTO(Integer.parseInt(regData[14].toString()), 
																   regData[15].toString()));
					}

					// Se entrega datos de LOCALIDAD
					if (regData[16] != null) {
						registro.setLocalidad(new LocalidadDTO(Integer.parseInt(regData[16].toString()), 
															   regData[17].toString()));
					}

					registro.setPerfil(perfil);
					listRegistros.add(registro);

				}

				responseInsitucionDTO.setExito(true);
				responseInsitucionDTO.setRegistros(listRegistros);

			} else {
				responseInsitucionDTO.setError("Datos de Usuario No Encontrados");
				responseInsitucionDTO.setExito(false);
			}
			
		} else {
			responseInsitucionDTO.setError("Error: No se encontro perfil asociado, datos de Usuario No Encontrados");
			responseInsitucionDTO.setExito(false);
		}

		return responseInsitucionDTO;
	}
	
	@Override
	public List<grupoServicioDTO> consultarPermisosPorPerfil(int idPerfilCodigo) {

		List<grupoServicioDTO> listaGrupoServicio = new ArrayList<>();
		List<Object[]> listData = null;
		
		try {
			if (idPerfilCodigo <= 0) {
				throw new Exception("El id del perfil codigo es inferior o igual a cero, digite un numero entero y superior a cero");
			}
			
			listData = grupoServicioRepository.findGrupoServicioByPerfilCodigo(idPerfilCodigo);
			
			if (listData != null && !listData.isEmpty()) {
				for (Object[] regData : listData) {
					listaGrupoServicio.add(new grupoServicioDTO(Integer.parseInt(regData[0].toString()), regData[1].toString()));
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listaGrupoServicio;
	}
	
	
	@Override
	public List<AccesoDirectoPorPerfilDTO> consultarAccesosDirectosPorPerfil(int idPerfilCodigo) {

		List<AccesoDirectoPorPerfilDTO> listaAccesoDirecto = null;
		List<Object[]> listData = null;
		
		try {
			if (idPerfilCodigo <= 0) {
				throw new Exception("El id del perfil codigo es inferior o igual a cero, digite un numero entero y superior a cero");
			}
			
			listData = accesoDirectoRepository.findAllByPerfil(idPerfilCodigo);
			
			if (listData != null && !listData.isEmpty()) {
				listaAccesoDirecto = new ArrayList<>();
				
				for (Object[] regData : listData) {
					listaAccesoDirecto.add(new AccesoDirectoPorPerfilDTO(Integer.parseInt(regData[0].toString()),
																		 regData[1].toString(),
																		 regData[2].toString()));
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listaAccesoDirecto;
	}

}
