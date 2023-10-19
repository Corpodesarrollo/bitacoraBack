package linktic.lookfeel.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import linktic.lookfeel.model.Institucion;

public interface InstitucionRepository extends JpaRepository<Institucion, Long>{
	
	@Query(nativeQuery = true, value = "select institucion.*\r\n"
			+ "        from personal persona join\r\n"
			+ "         USUARIO u on persona.PERNUMDOCUM=to_char(u.USUPERNUMDOCUM)\r\n"
			+ "        join g_jerarquia j on u.usucodjerar=j.g_jercodigo\r\n"
			+ "        join institucion institucion on institucion.inscodigo = j.g_jerinst\r\n"
			+ "    where pernumdocum=:idPersonal")
	List<Institucion>  findInstitucionByIdPersonal (String idPersonal);
	
	Institucion findByCodigo(Long codigo);
	
	
	@Query(nativeQuery = true, value = "Select * from institucion i where i.inscodlocal =:localidad order by 1 desc")
	List<Institucion> findByLocalidadInstitucions(Long localidad);
	
	

}
