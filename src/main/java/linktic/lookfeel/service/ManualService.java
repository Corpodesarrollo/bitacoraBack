package linktic.lookfeel.service;

import linktic.lookfeel.dtos.ConstanteDto;
import linktic.lookfeel.dtos.ManualDto;
import linktic.lookfeel.model.Manual;
import linktic.lookfeel.model.Response;
import linktic.lookfeel.repositories.ManualRepository;
import linktic.lookfeel.util.Utilidades;
import org.apache.el.parser.BooleanNode;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ManualService implements IManualService {

    private final ManualRepository manualRepository;

    private final ModelMapper modelMapper;

    private final ConstanteService constanteService;

    private static final Long COD_TYPE_MANUALS = 64L;

    private static final Long ACTIVE_MANUAL = 1L;
    private static final Long INACTIVE_MANUAL = 0L;

    private static final Logger log = LoggerFactory.getLogger(ManualService.class);

    @Value("${manual.path}")
    private String manualPath;

    @Value("${manual.extension.allowed}")
    private String manualExtensionAllowed;

    @Value("${manual.size.allowed}")
    private String manualSizeAllowed;

    @Autowired
    public ManualService(ManualRepository manualRepository, ModelMapper modelMapper, ConstanteService constanteService) {
        this.manualRepository = manualRepository;
        this.modelMapper = modelMapper;
        this.constanteService = constanteService;
    }

    /**
     * @param category
     * @return
     */
    @Override
    public List<ManualDto> getManualList(Long category) {

        try {
            List<Manual> manuals = manualRepository.getListByTipoManual(category);
            if (!manuals.isEmpty()) {
                return manuals.stream()
                        .map(this::convertToDto)
                        .collect(Collectors.toList());
            } else {
                log.error("No existe ningun manual con la categoria indicada");
                return new ArrayList<>();
            }
        } catch (Exception e) {
            log.error("Se produjo un error base de datos : " + e.getMessage() + " por la siguiente causa: " + e.getCause());
            return null;
        }

    }

    /**
     * @param id
     */
    @Override
    public Boolean deleteManual(Long id) {
        Optional<Manual> manual = manualRepository.findById(id);

        if (manual.isPresent()) {
            String manualName = concatFileName(id, manual.get().getNombreArchivo());
            Boolean res = deleteFile(manualName);

            if (res) {
                manualRepository.deleteById(id);
                return true;
            } else {
                log.error("No se pudo eliminar el manual, por favor verificar los logs para mas información:  " + id);
                return false;
            }

        } else {
            log.error("No existe un manual con el id " + id);
            return false;
        }
    }

    /**
     * @param id
     * @return el manual dto con la info y el archivo codificado en base 64
     */
    @Override
    public ManualDto getManual(Long id) {

        Optional<Manual> manual = manualRepository.findById(id);
        log.info("Se obtiene el manual de la base de datos: "  + id);

        if(manual.isPresent()){
            ManualDto manualDto = convertToDto(manual.get());
            String decodedFile = encodedFile(manual.get().getId(),  manualDto.getNombreArchivo());
            manualDto.setEncoded(decodedFile);
            return manualDto;
        }else{
            return null;
        }


    }

    private ManualDto convertToDto(Manual manual) {

        ManualDto manualDto = modelMapper.map(manual, ManualDto.class);

        manualDto.createManualDto(manual.getId(), manual.getTipoManual(), manual.getActivo(), manual.getNombreArchivo(), manual.getFecha());

        return manualDto;
    }

    /***
     *
     * @return el listado de los tipos de manuales
     */
    @Override
    public List<ConstanteDto> getManualsTypeList() {
        return constanteService.GetManualsType(COD_TYPE_MANUALS);
    }

    /**
     * @param file
     * @return el manual guarwdado en la base de datos y en el file system
     */
    @Override
    public Response saveManual(MultipartFile file, Long manualCategory) {

        try {
            if (file.getOriginalFilename() != null) {
                Long size = file.getSize();
                String fileName = file.getOriginalFilename();
                if (!checkAllowedSize(size)) {
                    log.error("El archivo supera el maximo permitido 10 mb");
                    return null;
                } else {

                    if (!checkAllowedExtension(fileName)) {
                        log.error("Solo se permiten archivos de extensión PDF");
                        return null;
                    } else {

                        Manual manual = new Manual();
                        manual.setTipoManual(manualCategory);
                        manual.setActivo(ACTIVE_MANUAL);
                        manual.setNombreArchivo(fileName);
                        manual.setFecha(new Date());


                        Optional<Manual> existManual = manualRepository.findByNombreArchivoAndTipoManual(fileName, manualCategory);

                        if (existManual.isPresent()){
                            log.info("Ya existe un manual con el nombre: " + fileName);
                            return new Response(HttpStatus.NO_CONTENT.value(), "Ya existe un manual con el nombre: " + fileName , null);
                        }
                        Manual manualSaved = manualRepository.save(manual);

                        encodedAndSaveFile(file, manualSaved.getId());
                        log.info("Se guardo satisfactoriamente el archivo.");
                        return new Response(HttpStatus.OK.value(), "se guardo satisfactoriamente el archivo", manualSaved);
                    }
                }

            } else {
                log.error("El manual esta vacío");
                return null;
            }
        } catch (Exception e) {
            log.error("Se produjo el siguiente al guardar el manual en la base de datos : " + e.getMessage() + " por la siguiente causa: " + e.getCause());
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Se produjo el siguiente al guardar el manual en la base de datos : " + e.getMessage() + " por la siguiente causa: " + e.getCause(), null);
        }

    }

    private String concatFileName(Long id, String fileName) {

        return String.format("%s_%s", id.toString(), fileName);

    }

    /***
     * Guardar el archivo en el file system
     * @param file
     */
    private void encodedAndSaveFile(MultipartFile file, Long id) {

        String out_file = manualPath.replace('.', Utilidades.getSeparadorSO());
        String finalName = concatFileName(id, file.getOriginalFilename());
        Path filepath = Paths.get(out_file, finalName);

        try (OutputStream os = Files.newOutputStream(filepath)) {
            os.write(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /***
     *
     * @param fileName
     * @return el archivo codificado en base 64
     */
    private String encodedFile(Long id , String fileName) {
        String decodedfile;
        String fullUrlFile;
        String outputFile;
        try {
            String finalName = concatFileName(id, fileName);
            outputFile = manualPath.replace('.', Utilidades.getSeparadorSO()) + Utilidades.getSeparadorSO();
            java.io.File f = new java.io.File(String.format("%s%s", outputFile, finalName));
            log.info("se obtiene el manaul en la ruta especifica: "  + fileName);
            if (f.exists()) {
                fullUrlFile = String.format("%s%s", outputFile, finalName);
                byte[] content = Files.readAllBytes(Paths.get(fullUrlFile));
                log.info("Se reliza cocoded del archivo...: "  + fileName);
                decodedfile = Base64.getEncoder().encodeToString(content);
                log.info("Finalizado encoded... "  + fileName);
            } else decodedfile = null;
        } catch (Exception e) {
            log.error("Se produjo el siguiente error en la decodificación del archivo : " + e.getMessage() + " por la siguiente causa: " + e.getCause());
            decodedfile = null;
        }
        return decodedfile;
    }

    @NotNull
    private Boolean deleteFile(String fileName) {

        boolean fileDeleted;
        String outputFile;
        try {

            outputFile = manualPath.replace('.', Utilidades.getSeparadorSO()) + Utilidades.getSeparadorSO();
            java.io.File f = new java.io.File(String.format("%s%s", outputFile, fileName));

            if (f.exists()) {

                fileDeleted = f.delete();
                log.info("Se eliminó el manual" + fileName + " satisfactoriamente!");

            } else {
                log.info("No se pudo eliminar el manual por que no existe!");
                return false;
            }

        } catch (Exception e) {
            log.error("No se pudo eliminar el manual : " + e.getMessage() + " por la siguiente causa: " + e.getCause());
            return false;
        }
        return fileDeleted;
    }

    /***
     *
     * @param fileSize
     * @return false or true si el archivo pesa 10mb o menos
     */
    private Boolean checkAllowedSize(Long fileSize) {

        return fileSize <= Long.parseLong(manualSizeAllowed);

    }

    /***
     *
     * @param extension
     * @return true si el archivo tiene la extensión permitida
     */
    private Boolean checkAllowedExtension(String extension) {
        String substring = extension.substring(extension.length() - 3);

        return manualExtensionAllowed.contains(substring.toLowerCase());

    }




}
