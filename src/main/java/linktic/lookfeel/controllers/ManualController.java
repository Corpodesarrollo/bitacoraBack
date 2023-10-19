package linktic.lookfeel.controllers;

import linktic.lookfeel.dtos.ConstanteDto;
import linktic.lookfeel.dtos.ManualDto;
import linktic.lookfeel.model.Manual;
import linktic.lookfeel.model.Response;
import linktic.lookfeel.service.IManualService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(ManualController.MANUALES)
@CrossOrigin(origins = "*")
public class ManualController {

    static final String MANUALES = "/api/manuales";

    static final String SAVE_MANUAL = "/guardar/{category}";
    static final String GET_MANUAL = "/manual/{id}";

    static final String DELETE_MANUAL = "/manual/eliminar/{id}";
    static final String GET_MANUALS = "/manuales/{category}";
    static final String GET_TYPE_MANUALS = "/manuales/tipo";


    private static final Logger log = LoggerFactory.getLogger(ManualController.class);

    private final IManualService service;

    @Autowired
    public ManualController(IManualService service) {
        this.service = service;
    }

    @PostMapping(value = SAVE_MANUAL, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE })
    public Response saveManual(@RequestParam("manualFile") MultipartFile manualFile,@PathVariable("category") Long category){

        log.info("request contains , File: " + manualFile.getOriginalFilename());

        return service.saveManual(manualFile, category);

    }

    @GetMapping(GET_MANUAL)
    public Response getManual(@PathVariable("id") Long id){

        try{
            ManualDto manual = service.getManual(id);
            if(manual != null){
                return  new Response(HttpStatus.OK.value(),"data", manual);
            }else
            {
                return  new Response(HttpStatus.UNPROCESSABLE_ENTITY.value(),"No existe ningun manual con el id ingresado", null);
            }
        }catch(Exception e){
            return  new Response(HttpStatus.NO_CONTENT.value(), "No existe ningun manual con el id ingresado", null);
        }

    }
    @GetMapping(GET_MANUALS)
    public Response getManualsCategory(@PathVariable("category") Long category){
        List<ManualDto> manuals = service.getManualList(category);

        if (!manuals.isEmpty()){
            log.info("obtuvo manuales por categorias");
            return new Response(HttpStatus.OK.value(),"data",manuals);
        }else
        {
            return  new Response(HttpStatus.NO_CONTENT.value(),"No existen categorias", null);
        }
    }
    @GetMapping(GET_TYPE_MANUALS)
    public Response getManualsType(){
       List<ConstanteDto> types =  service.getManualsTypeList();
       return  new Response(HttpStatus.OK.value(),"data",types);
    }

    @DeleteMapping(DELETE_MANUAL)
    public Response deleteManual(@PathVariable("id") Long id){
        Boolean res = service.deleteManual(id);

        if(res){
            return  new Response(HttpStatus.OK.value(),"Se eliminó el manual satisfactoriamente",true);
        }else{
            return  new Response(HttpStatus.NO_CONTENT.value(), "No se pudo eliminar el manual por favor verificar los logs",false);
        }


    }
}
