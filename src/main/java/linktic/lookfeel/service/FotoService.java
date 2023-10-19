package linktic.lookfeel.service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import linktic.lookfeel.dtos.FotoDTO;
import linktic.lookfeel.util.Utilidades;
@Service
public class FotoService implements IFotoService {
	
	
	@Value("${foto.pathPersonal}")
	private String rutaPersonal;
	
	@Value("${foto.pathSilueta}")
	private String rutaSilueta;
	
	private ModelMapper modelMapper = new ModelMapper();

	@Override
	public FotoDTO getFotoPersona(String nombreFoto) {
		String urlCompletaFoto = "";
		String archivoSalida = "";
		String fotoCodificada = "";
		
		try {
			archivoSalida = rutaPersonal.replace('.', Utilidades.getSeparadorSO()) + Utilidades.getSeparadorSO();
			java.io.File f = new java.io.File(archivoSalida);
			f = new java.io.File(archivoSalida + nombreFoto);

			f = new java.io.File(archivoSalida + nombreFoto);
			if (f.exists()) {
				urlCompletaFoto = archivoSalida + nombreFoto;
				byte[] contenido = Files.readAllBytes(Paths.get(urlCompletaFoto));
				fotoCodificada = Base64.getEncoder().encodeToString(contenido);
			} else {
				archivoSalida = "";
			}

		} catch (Exception e) {
			archivoSalida = null;
		}
		
		FotoDTO verFoto = FotoDTO.builder().nombreArchivo(nombreFoto)
				.codificacion(fotoCodificada).build();

		FotoDTO result = modelMapper.map(verFoto, FotoDTO.class);

		
		return result;
	}

}
