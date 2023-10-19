package linktic.lookfeel.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import linktic.lookfeel.ImageToBase64Converter;
import linktic.lookfeel.dtos.DependenciaDTO;
import linktic.lookfeel.dtos.FotoDTO;
import linktic.lookfeel.dtos.InstitucionDTO;
import linktic.lookfeel.dtos.JornadaDTO;
import linktic.lookfeel.dtos.LocalidadDTO;
import linktic.lookfeel.dtos.PerfilDTO;
import linktic.lookfeel.dtos.RegistroDTO;
import linktic.lookfeel.dtos.ResponseInsitucionDTO;
import linktic.lookfeel.dtos.SedeDTO;
import linktic.lookfeel.repositories.PerfilRepository;

@Service
public class IntitucionService implements IInstitucionService {

	//@Autowired private InstitucionRepository institucionRepository;
	//@Autowired private SedeRepository sedeRepository;
	//@Autowired private IFotoService fotoService;
	
	@Autowired
	private PerfilRepository perfilRepository;

	@Autowired
	private ImageToBase64Converter imageToBase64Converter;

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
		FotoDTO fotoDTO;
		List<Object[]> listData = null;
		String codPerfil = "";
		
		List<Object[]> perfiles = perfilRepository.findPerfil(idPersona);

		for (Object[] regPerfil : perfiles) {
			codPerfil = regPerfil[0].toString();
		}

		if (codPerfil != null && !codPerfil.isEmpty()) {
			
			// VALIDA: Si los perfiles son (110)-ADMINSEC,(410)-RECTOR(A),(423)-ADMINISTRATIVO
			if (Arrays.asList("110","410","423").contains(codPerfil)) { 
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

					if (regData[4] != null) {
						// Se genera imagen
						try {
							fotoDTO.setNombreArchivo(regData[4].toString() + ".jpg");
							fotoDTO.setCodificacion(imageToBase64Converter
									.convertImageToBase64("imagenes/" + (regData[4].toString() + ".jpg")));
						} catch (IOException e) {
							e.printStackTrace();
						}

						localidadInstitucion.setId(Integer.parseInt(regData[6].toString()));
						localidadInstitucion.setNombre(regData[7].toString());

						// Se cargan datos de la institucion
						institucion.setId(Long.parseLong(regData[4].toString()));
						institucion.setNombre(regData[5].toString());
						institucion.setLocalidad(localidadInstitucion);
						institucion.setFoto_escudo(fotoDTO);

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

						registro.setColegio(institucion);
						registro.setSede(sede);
						registro.setJornada(jornada);
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

}
