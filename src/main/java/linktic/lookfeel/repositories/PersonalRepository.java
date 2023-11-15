package linktic.lookfeel.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import linktic.lookfeel.model.Personal;

/**
 *
 * @author Ing. Alexander A. Muñoz Coneo
 * @fechacreacion 19/07/2023
 * @requrimiento HU007_SED, HU008_SED
 * @version 1.0
 */

public interface PersonalRepository extends JpaRepository<Personal, Long> {

	@Query(nativeQuery = true, value = "select persona.*\r\n"
			+ "from personal persona\r\n"
			+ "where pernumdocum=:usuLogin")
	Personal findByPerNumDocum(String usuLogin);
	
	@Query(nativeQuery = true, value = "SELECT DISTINCT\r\n"
			+ "    lower(to_char(p.peremail)) AS email\r\n"
			+ "FROM\r\n"
			+ "    personal p\r\n"
			+ "WHERE\r\n"
			+ "    p.pernumdocum NOT IN (\r\n"
			+ "        SELECT\r\n"
			+ "            per.pernumdocum\r\n"
			+ "        FROM\r\n"
			+ "            personal per\r\n"
			+ "        WHERE\r\n"
			+ "            NOT REGEXP_LIKE ( per.pernumdocum, '^[0-9]+$' )\r\n"
			+ "    )\r\n"
			+ "    AND p.peremail IS NOT NULL\r\n"
			+ "    AND p.pernumdocum IN (\r\n"
			+ "        SELECT DISTINCT\r\n"
			+ "            to_char(u.usupernumdocum)\r\n"
			+ "        FROM\r\n"
			+ "            usuario u,\r\n"
			+ "            g_jerarquia j,\r\n"
			+ "            sede s,\r\n"
			+ "            jornada jor,\r\n"
			+ "            g_constante gc,\r\n"
			+ "            institucion i\r\n"
			+ "       WHERE\r\n"
			+ "            u.usucodjerar = j.g_jercodigo\r\n"
			+ "        AND gc.g_concodigo = jor.jorcodigo\r\n"
			+ "        AND i.inscodigo = j.g_jerinst\r\n"
			+ "        AND s.sedcodins = j.g_jerinst\r\n"
			+ "        AND (:perfilId IS NULL OR INSTR(NVL(u.usuperfcodigo,''), :perfilId)>0) \r\n"
			+ "        AND (:colegioId IS NULL OR INSTR(NVL(j.g_jerinst,''),:colegioId )>0)\r\n"
			+ "        AND (:sedeId IS NULL OR INSTR(NVL(s.sedcodigo,''), :sedeId )>0)\r\n"
			+ "        AND (:jornadaId IS NULL OR INSTR(NVL(jor.jorcodigo,''), :jornadaId )>0)\r\n"
			+ "        AND (:localidadId IS NULL OR INSTR(NVL( i.inscodlocal ,''), :localidadId )>0)\r\n"
			+ "        AND gc.g_contipo=5)")
	List<String> findByPersonalPorPerfil(String perfilId, String colegioId, String localidadId, String sedeId, String jornadaId);

	 @Query(nativeQuery = true, value = "select p.* from personal p"
							    	  + " join usuario u on p.pernumdocum = u.usulogin"
							          + " join g_jerarquia gjer on gjer.g_jercodigo = usucodjerar"
							          + " where g_jerinst= :codInstitucion")
	    List<Personal> getUsuarioXInstutucion(int codInstitucion);
}
