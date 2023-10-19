package linktic.lookfeel.repositories;

import linktic.lookfeel.model.Constante;
import linktic.lookfeel.model.ConstantePk;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.util.List;
import java.util.Optional;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;


@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class ConstanteRepositoryTest {

    @Autowired
    private ConstanteRepository repo;

    @BeforeEach
    void setUp() {


    }

    @Test
    public void testGetManualsType(){

        ConstantePk id =  new ConstantePk(64L);
        List<Constante> manuals  = repo.findByConstantePkTipo(id.getTipo());

        assertThat(manuals).isNotEmpty();

    }

    @Test
    public void testNotGetManualsType(){

        ConstantePk id =  new ConstantePk(987877L);
        Optional<Constante> manuals  =repo.findById(id);

        assertThat(manuals).isEmpty();
    }
}