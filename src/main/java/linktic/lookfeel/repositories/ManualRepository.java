package linktic.lookfeel.repositories;

import linktic.lookfeel.dtos.ManualDto;
import linktic.lookfeel.model.Manual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ManualRepository extends JpaRepository<Manual, Long> {
    List<Manual> getListByTipoManual(Long category);


    Optional<Manual> findByNombreArchivoAndTipoManual(String name, Long tipoManual);
}
