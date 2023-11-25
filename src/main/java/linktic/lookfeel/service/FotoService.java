package linktic.lookfeel.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

import jdk.nashorn.internal.runtime.logging.Logger;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import linktic.lookfeel.dtos.FotoDTO;
import linktic.lookfeel.util.Utilidades;
@Service
public class FotoService implements IFotoService {


	@Value("${foto.pathPersonal}")
	private String rutaPersonal;

	@Value("${foto.escudo}")
	private String escudo;
	
	@Value("${foto.pathSilueta}")
	private String rutaSilueta;

	private final String  EXTENSION_JPG = ".jpg";
	private final String  EXTENSION_PNG = ".png";
	private final String  EXTENSION_BPM = ".bpm";

	private final String  EXTENSION_GIF = ".gif";
	
	private ModelMapper modelMapper = new ModelMapper();

	@Override
	public FotoDTO getFotoPersona(String nombreFoto) {
		String archivoSalida = null;
		String nombreFotoExt = "";
		FotoDTO result  = null;
		String fotoCodificada = "";
		try {
			archivoSalida = rutaPersonal.replace('.', Utilidades.getSeparadorSO()) + Utilidades.getSeparadorSO();
			nombreFotoExt = nombreFoto.concat(EXTENSION_JPG);
			System.out.println("Trayendo la foto de " + nombreFotoExt);
			String pahtAndName = archivoSalida.concat(nombreFotoExt);
			fotoCodificada = getBase64Image(pahtAndName);
			if (fotoCodificada == null) {
				nombreFotoExt = nombreFoto.concat(EXTENSION_PNG);
				pahtAndName = archivoSalida.concat(nombreFotoExt);
				fotoCodificada = getBase64Image(pahtAndName);
				if (fotoCodificada == null) {
					pahtAndName = archivoSalida.concat(nombreFoto.concat(EXTENSION_BPM));
					fotoCodificada = getBase64Image(pahtAndName);
					if (fotoCodificada == null) {
						nombreFotoExt = nombreFoto.concat(EXTENSION_GIF);
						pahtAndName = archivoSalida.concat(nombreFotoExt);
						fotoCodificada = getBase64Image(pahtAndName);
					}
				}
			}
			FotoDTO verFoto = FotoDTO.builder().nombreArchivo(nombreFoto)
					.codificacion(fotoCodificada).build();
			result = modelMapper.map(verFoto, FotoDTO.class);
		} catch (Exception e) {
			System.out.println("Se presento un error no controlado al abrir imagen  " + e.getMessage());
		}
		return result;
	}

	@Override
	public FotoDTO getEscudo(String nombreFoto) {
		String archivoSalida = null;
		String nombreFotoExt = "";
		FotoDTO result = null;
		String fotoCodificada = "";
		try {
			archivoSalida = escudo.replace('.', Utilidades.getSeparadorSO()) + Utilidades.getSeparadorSO();
			nombreFotoExt = nombreFoto.concat(EXTENSION_JPG);
			String pahtAndName = archivoSalida.concat(nombreFotoExt);

			System.out.println("Trayendo la foto colegio de " + pahtAndName);
			fotoCodificada = getBase64Image(pahtAndName);
			if (fotoCodificada == null) {
				nombreFotoExt = nombreFoto.concat(EXTENSION_PNG);
				pahtAndName = archivoSalida.concat(nombreFotoExt);
				fotoCodificada = getBase64Image(pahtAndName);
				if (fotoCodificada == null) {
					nombreFotoExt = nombreFoto.concat(EXTENSION_BPM);
					pahtAndName = archivoSalida.concat(nombreFotoExt);
					fotoCodificada = getBase64Image(pahtAndName);
					if (fotoCodificada == null) {
						nombreFotoExt = nombreFoto.concat(EXTENSION_GIF);
						pahtAndName = archivoSalida.concat(nombreFotoExt);
						fotoCodificada = getBase64Image(pahtAndName);
					}
				}
			}
			FotoDTO verFoto = FotoDTO.builder().nombreArchivo(nombreFotoExt)
					.codificacion(fotoCodificada).build();
			result = modelMapper.map(verFoto, FotoDTO.class);
		} catch (Exception e) {
			System.out.println("Se presento un error no controlado al abrir imagen  " + e.getMessage());
		}
		return result;
	}




	public String getBase64Image(String pathAndName){
		File f = null;
		try{
			f = new java.io.File(pathAndName);
			if (f.exists()) {
				byte[] contenido = Files.readAllBytes(Paths.get(pathAndName));
				return  Base64.getEncoder().encodeToString(contenido);
			}
		}catch (Exception e ){
			System.out.println("Se presento un error no controlado al abrir imagen  " + e.getMessage());
		}
		return null;
	}

}
