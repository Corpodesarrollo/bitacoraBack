package linktic.lookfeel.service;

import linktic.lookfeel.dtos.MenuDto;
import linktic.lookfeel.dtos.MenuListsDto;
import linktic.lookfeel.dtos.ServicioParamDTO;
import linktic.lookfeel.repositories.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuService implements IMenuService {


    private final MenuRepository repository;

    @Autowired
    public MenuService(MenuRepository repository) {
        this.repository = repository;
    }

    /**
     * @param institucion
     * @param perfil
     * @return menu catalogo general por insitucion y perfil
     */
    @Override
    public List<MenuDto> getListServiceCatalog(Long institucion, String perfil) {

        List<Object[]> res = repository.getListGeneralCategory(institucion,perfil);
        List<MenuDto> listMenu = new ArrayList<>();

        if (res == null)
            return null;

        for (Object[] obj : res) {
            MenuDto m = toDtoMenuCatalogNaranja(obj);
            listMenu.add(m);
        }

        return listMenu;
    }

    @Override
    public List<ServicioParamDTO> getParamServices(Long codigo, String recurso) {
        
        List<Object[]> res = repository.getServiciosParamPorCodigoRecurso(codigo, recurso);
        
        return res.stream()
                    .map(object ->ServicioParamDTO.objectoToDTO(object))
                    .collect(Collectors.toList());
    }

    private MenuDto toDtoMenuCatalog(Object[] obj) {

        MenuDto menu = new MenuDto();

        menu.setSerRecurso(obj[0] == null ? "" : String.valueOf(obj[0]));
        menu.setSerTarget(obj[1] == null ? "" : String.valueOf(obj[1]));
        menu.setSerNombre(obj[2] == null ? "" : String.valueOf(obj[2]));
        menu.setSerCodigo(obj[3] == null ? "" : String.valueOf(obj[3]));

        return menu;


    }

    private MenuDto toDtoMenuCatalogNaranja(Object[] obj) {

        MenuDto menu = new MenuDto();

        menu.setSerCatCodigo(obj[0] == null ? "" : String.valueOf(obj[0]));
        menu.setCatNombre(obj[1] == null ? "" : String.valueOf(obj[1]));
        menu.setCatImagen(obj[2] == null ? "" : String.valueOf(obj[2]));
        menu.setSerCodigo(obj[3] == null ? "" : String.valueOf(obj[3]));

        return menu;


    }

    private MenuDto toDtoMenuSubCatalog(Object[] obj) {

        MenuDto menu = new MenuDto();

        menu.setSerRecurso(obj[0] == null ? "" : String.valueOf(obj[0]));
        menu.setSerTarget(obj[1] == null ? "" : String.valueOf(obj[1]));
        menu.setSerNombre(obj[2] == null ? "" : String.valueOf(obj[2]));
        menu.setSerCodigo(obj[3] == null ? "" : String.valueOf(obj[3]));
        menu.setSerCatCodigo(obj[4] == null ? "" : String.valueOf(obj[4]));

        return menu;

    }

    private MenuDto toDtoMenuPublicParam(Object[] obj) {

        MenuDto menu = new MenuDto();

        menu.setParSerCodigo(obj[0] == null ? "" : String.valueOf(obj[0]));
        menu.setParNombre(obj[1] == null ? "" : String.valueOf(obj[1]));
        menu.setParValor(obj[2] == null ? "" : String.valueOf(obj[2]));


        return menu;

    }


    /**
     * @param institucion
     * @return Menu naranja general por intitucion
     */
    @Override
    public List<MenuDto> getListMenuGeneral(Long institucion) {
        List<Object[]> res = repository.getListGeneral(institucion);
        List<MenuDto> listMenu = new ArrayList<>();

        if (res == null)
            return null;

        for (Object[] obj : res) {
            MenuDto m = toDtoMenuCatalog(obj);
            listMenu.add(m);
        }

        return listMenu;
    }

    /**
     * @param institucion
     * @param perfil
     * @return
     */
    @Override
    public List<MenuDto> getListSubMenuPrivate(Long institucion, String perfil) {
        List<Object[]> res = repository.getListGeneralSubCategory(institucion,perfil);
        List<MenuDto> listMenu = new ArrayList<>();

        if (res == null)
            return null;

        for (Object[] obj : res) {
            MenuDto m = toDtoMenuSubCatalog(obj);
            listMenu.add(m);
        }

        return listMenu;
    }

    /**
     * @return list menu public parametros
     */
    @Override
    public List<MenuDto> getListMenuPublicParam() {
        List<Object[]> res = repository.getListGeneralCategoryParam();
        List<MenuDto> listMenu = new ArrayList<>();

        if (res == null)
            return null;

        for (Object[] obj : res) {
            MenuDto m = toDtoMenuPublicParam(obj);
            listMenu.add(m);
        }

        return listMenu;
    }

    /**
     * @param Perfil
     * @return list menu private parameters
     */
    @Override
    public List<MenuDto> getListMenuPrivateParam(String Perfil) {
        List<Object[]> res = repository.getListCategoryPrivateParam(Perfil);
        List<MenuDto> listMenu = new ArrayList<>();

        if (res == null)
            return null;

        for (Object[] obj : res) {
            MenuDto m = toDtoMenuPublicParam(obj);
            listMenu.add(m);
        }

        return listMenu;
    }

    /**
     * @param institucion
     * @param perfil
     * @return
     */
    @Override
    public MenuListsDto getAllMenuLists(Long institucion, String perfil) {
    //TODO
        List<MenuDto> generalMenu = getListServiceCatalog(institucion, perfil);
        List<MenuDto> menuNaranja = getListMenuGeneral(institucion);
        List<MenuDto> subMenu = getListSubMenuPrivate(institucion,perfil);
        List<MenuDto> menuParamPublic = getListMenuPublicParam();
        List<MenuDto> menuParamPrivat = getListMenuPrivateParam(perfil);


        MenuListsDto listado = new MenuListsDto();

        listado.setMenuServiceCatalog(generalMenu);
        listado.setMenuGeneralMenu(menuNaranja);
        listado.setSubMenuGeneralPrivate(subMenu);
        listado.setMenuPublicParam(menuParamPublic);
        listado.setMenuPrivateParam(menuParamPrivat);

        return listado;
    }
}
