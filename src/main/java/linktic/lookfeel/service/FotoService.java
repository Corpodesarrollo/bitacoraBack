package linktic.lookfeel.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

import linktic.lookfeel.util.EXTENSIONFILE;
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

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public FotoDTO getFotoPersona(String nombreFoto) {
        String archivoSalida = rutaPersonal.replace('.', Utilidades.getSeparadorSO()) + Utilidades.getSeparadorSO();
        String nombreFotoExt = "";
        FotoDTO result = null;
        String fotoCodificada = "";
        try {
            for (EXTENSIONFILE extensionfile : EXTENSIONFILE.values()) {
                nombreFotoExt = nombreFoto.concat(extensionfile.getValue());
                System.out.println("Trayendo la foto de " + nombreFotoExt);
                String pahtAndName = archivoSalida.concat(nombreFotoExt);
                fotoCodificada = getBase64Image(pahtAndName);
                if (fotoCodificada != null) break;
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
        String archivoSalida = escudo.replace('.', Utilidades.getSeparadorSO()) + Utilidades.getSeparadorSO();
        String nombreFotoExt = "";
        FotoDTO result = null;
        String fotoCodificada = "";
        try {
            for (EXTENSIONFILE extensionfile : EXTENSIONFILE.values()) {
                nombreFotoExt = nombreFoto.concat(extensionfile.getValue());
                System.out.println("Trayendo el escudo de " + nombreFotoExt);
                String pahtAndName = archivoSalida.concat(nombreFotoExt);
                fotoCodificada = getBase64Image(pahtAndName);
                if (fotoCodificada != null) {
                    nombreFoto = nombreFotoExt;
                    break;
                };
            }
            FotoDTO verFoto = FotoDTO.builder().nombreArchivo(nombreFoto)
                    .codificacion(fotoCodificada).build();
            result = modelMapper.map(verFoto, FotoDTO.class);
        } catch (Exception e) {
            System.out.println("Se presento un error no controlado al abrir imagen  " + e.getMessage());
        }
        return result;
    }

    public String getBase64Image(String pathAndName) {
        try {
            File f = new java.io.File(pathAndName);
            if (f.exists()) {
                byte[] contenido = Files.readAllBytes(Paths.get(pathAndName));
                return Base64.getEncoder().encodeToString(contenido);
            }
        } catch (Exception e) {
            System.out.println("Se presento un error no controlado al abrir imagen  " + e.getMessage());
        }
        return null;
    }

}
