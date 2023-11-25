package linktic.lookfeel.service;


import linktic.lookfeel.dtos.MenuDto;
import linktic.lookfeel.dtos.MenuListsDto;
import linktic.lookfeel.dtos.ServicioParamDTO;

import java.util.List;

public interface IMenuService {

    List<MenuDto> getListServiceCatalog(Long institucion, String perfil);

    List<MenuDto> getListMenuGeneral(Long institucion);

    List<MenuDto> getListSubMenuPrivate(Long institucion, String perfil);

    List<MenuDto> getListMenuPublicParam();

    List<MenuDto> getListMenuPrivateParam(String Perfil);

    MenuListsDto getAllMenuLists(Long institucion, String perfil);
    
    List<ServicioParamDTO> getParamServices(Long codigo, String path);

}
