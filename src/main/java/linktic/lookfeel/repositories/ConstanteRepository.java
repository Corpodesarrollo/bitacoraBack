package linktic.lookfeel.repositories;

import linktic.lookfeel.model.Constante;
import linktic.lookfeel.model.ConstantePk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface ConstanteRepository extends JpaRepository<Constante, ConstantePk> {
	
	@Query(nativeQuery = true, value ="SELECT * \r\n"
			+ "FROM G_Constante \r\n"
			+ "WHERE G_ConTipo=10\r\n"
			+ "AND g_concodigo = :id")
	Constante tipoDocumentoPorId(long id);
	
	@Query(nativeQuery = true, value ="SELECT gc.* \r\n"
			+ "FROM G_Constante gc, jornada jor\r\n"
			+ "WHERE gc.g_concodigo = jor.jorcodigo\r\n"
			+ "AND jor.jorcodins = :codInstitucion\r\n"
			+ "And jorcodigo = :codJornada\r\n"
			+ "AND G_ConTipo=5")
	Constante jornadaPorCodigoInsti(long codJornada, long codInstitucion);
	
	@Query(nativeQuery = true, value ="SELECT gc.*\r\n"
			+ "FROM G_Constante gc, metodologia met\r\n"
			+ "WHERE gc.g_concodigo = met.metcodigo\r\n"
			+ "AND met.metcodinst = :codInstitucion\r\n"
			+ "AND met.metcodigo = :codMetodologia\r\n"
			+ "AND gc.G_ConTipo=3")
	Constante metodologiaPorCodigoInsti(long codMetodologia, long codInstitucion);
	
	List<Constante> findByConstantePkTipo(Long tipo);
    
    @Query(nativeQuery = true, value ="SELECT * "
    		+ "FROM G_Constante "
    		+ "WHERE G_ConTipo=10 ORDER BY G_ConOrden")
    List<Constante> listaTiposDocumentos();
    
    @Query(nativeQuery = true, value ="select gcons.*\r\n"
    		+ "from institucion i, sede s, sede_jornada sj, jornada jor, g_constante gcons\r\n"
    		+ "where s.SEDCODINS = i.inscodigo \r\n"
    		+ "AND s.SEDESTADO='A' \r\n"
    		+ "AND i.inscodigo=sj.sedjorcodinst \r\n"
    		+ "AND s.SEDCODIGO = sj.SEDJORCODSEDE\r\n"
    		+ "AND jor.JORCODINS = sj.SEDJORCODINST \r\n"
    		+ "AND jor.JORCODIGO = sj.SEDJORCODJOR\r\n"
    		+ "AND gcons.G_CONTIPO = 5 \r\n"
    		+ "AND gcons.G_CONCODIGO = sj.SEDJORCODJOR\r\n"
    		+ "AND i.inscodigo=:CodInst\r\n"
    		+ "AND s.sedcodigo=:CodSede")
    List<Constante> listaJornadas(Long CodInst, Long CodSede);
    
    
}
