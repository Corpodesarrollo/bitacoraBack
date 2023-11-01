package linktic.lookfeel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import linktic.lookfeel.model.Grupo;

public interface GrupoRepository extends JpaRepository<Grupo, Long>{
	
	@Query(nativeQuery = true, value = "Select gru.* \r\n"
			+ "from grupo gru, g_jerarquia gjer\r\n"
			+ "where gru.grucodjerar = gjer.g_jercodigo\r\n"
			+ "and gjer.g_jerinst =:codInstitucion\r\n"
			+ "and gjer.g_jersede =:codSede\r\n"
			+ "and gjer.g_jerjorn =:codJornada\r\n"
			+ "and gjer.g_jergrado =:codGrado\r\n"
			+ "and gru.grujergrupo =:codGrupo")
	Grupo grupoPorDatoEstudiante(long codInstitucion, long codSede,  long codJornada, long codGrado, long codGrupo);
	

}
