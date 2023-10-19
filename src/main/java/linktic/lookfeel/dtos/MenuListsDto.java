package linktic.lookfeel.dtos;


import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuListsDto {

    private List<MenuDto> menuServiceCatalog;
    private List<MenuDto> menuGeneralMenu;
    private List<MenuDto> subMenuGeneralPrivate;
    private List<MenuDto> menuPublicParam;
    private List<MenuDto> menuPrivateParam;

}
