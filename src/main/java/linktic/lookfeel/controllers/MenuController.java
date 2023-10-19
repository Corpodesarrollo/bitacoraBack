package linktic.lookfeel.controllers;


import linktic.lookfeel.dtos.MenuListsDto;
import linktic.lookfeel.model.Response;
import linktic.lookfeel.service.IMenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(MenuController.MENU)
@CrossOrigin(origins = "*")
public class MenuController {

    static final String MENU = "/api/menu";

    static final String GET_MENUS = "/allmenu/{institucion}/{perfil}";
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
}
