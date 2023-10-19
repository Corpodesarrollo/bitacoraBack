package linktic.lookfeel.service;


import linktic.lookfeel.dtos.ManualDto;
import linktic.lookfeel.model.Manual;
import linktic.lookfeel.model.Response;
import linktic.lookfeel.repositories.ConstanteRepository;
import linktic.lookfeel.repositories.ManualRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.lang3.RandomStringUtils;

import java.lang.reflect.Field;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import static org.assertj.core.api.Assertions.assertThat;


import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
@ExtendWith(MockitoExtension.class)
class ManualServiceTest {


    private ManualService underTest;
    @Mock
    private ManualRepository underTestRepo;
    private MultipartFile fileSuperaTamanio;

    private MultipartFile fileNoSupera;

    private MultipartFile fileSucces;

    @Mock
    private ConstanteRepository repo;



    private final ModelMapper modelMapper = new ModelMapper();

    private Manual especManual;
    private Manual manual;


    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        ConstanteService constanteService = new ConstanteService(repo, modelMapper);
        underTest = new ManualService(underTestRepo,modelMapper, constanteService);
        int fileSizeSupera = 100 * 1024 * 1024; // > 10 MB
        int fileSizeNoSupera = 520 * 324; // < 10 MB

        String contentSupera = RandomStringUtils.randomAlphanumeric(fileSizeSupera);
        String contentNoSupera = RandomStringUtils.randomAlphanumeric(fileSizeNoSupera);
        fileSuperaTamanio = new MockMultipartFile("archivo.png", // Nombre del archivo
                "testnombreManual.png", // Nombre original del archivo
                ".pdf", // Tipo MIME del archivo
                contentSupera.getBytes() // Contenido del archivo como arreglo de bytes
        );
        fileNoSupera = new MockMultipartFile("archivo.png", // Nombre del archivo
                "testnombreManual.png", // Nombre original del archivo
                "image/png", // Tipo MIME del archivo
                contentNoSupera.getBytes() // Contenido del archivo como arreglo de bytes
        );

        fileSucces = new MockMultipartFile("manual.pdf", // Nombre del archivo
                "FakeFile.pdf", // Nombre original del archivo
                "application/pdf", // Tipo MIME del archivo
                contentNoSupera.getBytes() // Contenido del archivo como arreglo de bytes
        );


        especManual  = new Manual();

        especManual.setTipoManual(0L);
        especManual.setActivo(1L);
        especManual.setNombreArchivo("FakeFile.pdf");
        especManual.setId(1L);

        manual = new Manual();
        manual.setTipoManual(0L);
        manual.setActivo(1L);
        manual.setNombreArchivo("FakeFile.pdf");
        manual.setId(1L);



        String mockSizeValue = "10485760";
        Field field = ManualService.class.getDeclaredField("manualSizeAllowed");
        field.setAccessible(true);
        field.set(underTest, mockSizeValue);

        String mockExtensionValue = ".pdf";
        field = ManualService.class.getDeclaredField("manualExtensionAllowed") ;
        field.setAccessible(true);
        field.set(underTest, mockExtensionValue);

        String mockPathValue = "C:.Reportes.Apoyo_Escolar.WEB-INF.Private.manuales";
        field = ManualService.class.getDeclaredField("manualPath") ;
        field.setAccessible(true);
        field.set(underTest, mockPathValue);

    }
    @Test
    void NotEmptyGetManualList() {

        //when


    }

    @Test
    void getManual() {


    }

    @Test
    void getManualsTypeList() {
    }

    @Test
    void saveManualDtoIsNull() {
        //when
        Response obj = underTest.saveManual(fileSuperaTamanio,null);
        assertThat(obj).isNull();
    }

    @Test
    void saveManualDtoIsNotNullAndSizeAllowed() {
        //when

        Response obj = underTest.saveManual(fileSuperaTamanio,0L);
        assertThat(obj).isNull();
    }

    @Test
    void saveManualDtoAndExtensionAllowed() {
        //when

        Response obj= underTest.saveManual(fileNoSupera,0L);

       //then
        assertThat(obj).isNull();
    }

    @Test
    void saveManual() {
        //when
        given(underTestRepo.save(any(Manual.class))).willReturn(manual);
        Response obj= underTest.saveManual(fileSucces,0L);
        assertThat(obj).isNotNull();
    }
    @Test
    void returnFalseManualNoExist(){

        given(underTestRepo.findById(anyLong())).willReturn(Optional.empty());

        boolean res = underTest.deleteManual(1L);

        assertFalse(res);
    }
    @Test
    void returnTrueManualNoExist(){

        given(underTestRepo.findById(anyLong())).willReturn(Optional.empty());
        given(underTestRepo.findById(anyLong())).willReturn(Optional.ofNullable(manual));

        underTest.saveManual(fileSucces,0L);

        boolean res = underTest.deleteManual(1L);

        assertTrue(res);
    }

}