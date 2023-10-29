package linktic.lookfeel.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import linktic.lookfeel.dtos.JornadaDTO;
import linktic.lookfeel.model.Constante;
import linktic.lookfeel.model.Jornada;
import linktic.lookfeel.model.Sede;

public interface SedeRepository extends JpaRepository<Sede,Long>{
	
	
	/**
	 * 
	 * sedes de una institucion
	 * 
	 * @param idInstitucion
	 * @return List<Sede>
	 */
	@Query(nativeQuery = true, value = "select * from sede\r\n"
			+ "where sedcodins=:idInstitucion")
	List<Sede> findSedeByInstitucion (Long idInstitucion);
	
	
	
	/**
	 * 
	 * Jornadas de un instituto por sede
	 * 
	 * @param idInstitucion
	 * @param sede
	 * @return List<Object[]>
	 */
	@Query(nativeQuery = true, value = "SELECT\r\n"
			+ "    		 DISTINCT   sj.SEDJORCODJOR ,\r\n"
			+ "    		    gcons.G_CONNOMBRE \r\n"
			+ "    		FROM SEDE_JORNADA sj\r\n"
			+ "    		INNER JOIN INSTITUCION ins ON ins.INSCODIGO = sj.SEDJORCODINST\r\n"
			+ "    		INNER JOIN SEDE sed ON sed.SEDCODINS = sj.SEDJORCODINST AND sed.SEDCODIGO = sj.SEDJORCODSEDE AND sed.SEDESTADO = 'A'\r\n"
			+ "    		INNER JOIN JORNADA jor ON jor.JORCODINS = sj.SEDJORCODINST AND jor.JORCODIGO = sj.SEDJORCODJOR\r\n"
			+ "    		INNER JOIN G_CONSTANTE gcons ON gcons.G_CONTIPO = 5 AND gcons.G_CONCODIGO = sj.SEDJORCODJOR\r\n"
			+ "    		WHERE sj.SEDJORCODINST = :idInstitucion AND sj.SEDJORCODSEDE = :sede ")
	List<Object[]> findJornadaBySedeANdInstitucion (Long idInstitucion, Long sede);
	
	
	/**
	 * 
	 * Localidad de un instituto por sede
	 * 
	 * @param idInstitucion
	 * @param sede
	 * @return List<Object[]>
	 */
	@Query(nativeQuery = true, value = "SELECT\r\n"
			+ "    		 DISTINCT   sj.SEDJORCODJOR ,\r\n"
			+ "    		    gcons.G_CONNOMBRE \r\n"
			+ "    		FROM SEDE_JORNADA sj\r\n"
			+ "    		INNER JOIN INSTITUCION ins ON ins.INSCODIGO = sj.SEDJORCODINST\r\n"
			+ "    		INNER JOIN SEDE sed ON sed.SEDCODINS = sj.SEDJORCODINST AND sed.SEDCODIGO = sj.SEDJORCODSEDE AND sed.SEDESTADO = 'A'\r\n"
			+ "    		INNER JOIN JORNADA jor ON jor.JORCODINS = sj.SEDJORCODINST AND jor.JORCODIGO = sj.SEDJORCODJOR\r\n"
			+ "    		INNER JOIN G_CONSTANTE gcons ON gcons.G_CONTIPO = 7 AND gcons.G_CONCODIGO = sj.SEDJORCODJOR\r\n"
			+ "    		WHERE sj.SEDJORCODINST = :idInstitucion AND sj.SEDJORCODSEDE = :sede ")
	List<Object[]> findLocalidadByInsSede (Long idInstitucion, Long sede);
	
	
	
	@Query(nativeQuery = true, value = "select * \r\n"
			+ "from sede se, institucion inst\r\n"
			+ "where inst.inscodigo = se.sedcodins\r\n"
			+ "and inst.inscodigo = :codigoInstitucion\r\n"
			+ "and se.sedcodigo = :codigoSede")
	Sede sedePorCodigoInstYCodigoSede(Long codigoSede, Long codigoInstitucion);
	
	@Query(nativeQuery = true, value = "select distinct sj.SEDJORCODJOR,gc.g_connombre from jornada j\r\n"
			+ "join sede_jornada sj on sj.sedjorcodjor = j.jorcodigo \r\n"
			+ "join sede s on sj.sedjorcodsede = s.sedcodigo \r\n"
			+ "join g_constante gc on j.jorcodigo = gc.g_concodigo and gc.g_contipo = 5 "
			+ "where s.sedcodigo=:sede")
	List<Object[]> findJornadasBySede (long sede);

}
