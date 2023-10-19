package linktic.lookfeel.service;

import linktic.lookfeel.dtos.ConstanteDto;
import linktic.lookfeel.dtos.ManualDto;
import linktic.lookfeel.model.Manual;
import linktic.lookfeel.model.Response;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IManualService {

    List<ManualDto> getManualList(Long type);

    Boolean deleteManual(Long id);

    ManualDto getManual(Long id);

    List<ConstanteDto> getManualsTypeList();

    Response saveManual(MultipartFile file, Long categoryManual);



}
