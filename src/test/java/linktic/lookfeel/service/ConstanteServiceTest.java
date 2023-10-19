package linktic.lookfeel.service;

import linktic.lookfeel.dtos.ConstanteDto;
import linktic.lookfeel.model.Constante;
import linktic.lookfeel.model.ConstantePk;
import linktic.lookfeel.repositories.ConstanteRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ConstanteServiceTest {


    private ConstanteService underTest;

    @Mock
    private ConstanteRepository repo;

    private List<Constante> list;

    private List<ConstanteDto> listDto;

    private final ModelMapper modelMapper = new ModelMapper();


    @BeforeEach
    void setUp() {
        list = new ArrayList<>();
        listDto = new ArrayList<>();
        underTest = new ConstanteService(repo,modelMapper);
        Constante obj = new Constante();
        ConstantePk ObjKey = new ConstantePk();
        obj.setAbrev("ABRV");
        obj.setOrden(1L);
        obj.setNombre("Manual del Sistema");
        obj.setActComp(new Date());
        obj.setCodPadre(0L);
        ObjKey.setCodigo(0L);
        ObjKey.setTipo(64L);
        obj.setConstantePk(ObjKey);

        list.add(obj);

    }

    @Test
    public void whenGetManualsType() {

        //when
        given(repo.findByConstantePkTipo(anyLong())).willReturn(list);

        //then
        listDto = underTest.GetManualsType(64L);
        assertThat(listDto).isNotEmpty();
    }


}