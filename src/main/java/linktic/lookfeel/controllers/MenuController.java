package linktic.lookfeel.controllers;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import linktic.lookfeel.dtos.MenuListsDto;
import linktic.lookfeel.dtos.ServicioParamDTO;
import linktic.lookfeel.model.Response;
import linktic.lookfeel.service.IMenuService;

@RestController
@RequestMapping(MenuController.MENU)
@CrossOrigin(origins = "*")
public class MenuController {

    static final String MENU = "/api/menu";

    static final String GET_MENUS = "/allmenu/{institucion}/{perfil}";
    
    static final String PARAM_SERVICES= "/getParamServicios/{codigo}/{recurso}";
    
    private static final Logger log = LoggerFactory.getLogger(MenuController.class);

    private final IMenuService service;

    @Autowired
    public MenuController(IMenuService service) {
        this.service = service;
    }


    @GetMapping(GET_MENUS)
    public Response getAllMenus(@PathVariable("institucion") Long institucion, @PathVariable("perfil") String perfil){

    MenuListsDto menus = service.getAllMenuLists(institucion,perfil);

        return  new Response(HttpStatus.OK.value(),"data", menus);

    }
    
    @GetMapping(PARAM_SERVICES)
    public Response getParamServicios(@PathVariable("codigo") String codigo, @PathVariable("recurso") String recurso){
    	List<ServicioParamDTO> respuesta = service.getParamServices(Long.valueOf(codigo), recurso);
          return new Response(HttpStatus.OK.value(),"data", respuesta);
    }
    
}
