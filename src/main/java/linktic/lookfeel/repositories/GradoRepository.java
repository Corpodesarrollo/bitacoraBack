package linktic.lookfeel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import linktic.lookfeel.model.Grado;

public interface GradoRepository extends JpaRepository<Grado, Long>{
	
	@Query(nativeQuery = true, value ="select gr.gracodigo, gr.granombre \r\n"
			+ "from grado gr, institucion inst, metodologia met\r\n"
			+ "where inst.inscodigo = gr.gracodinst\r\n"
			+ "and met.metcodinst = inst.inscodigo\r\n"
			+ "and met.metcodigo = gr.gracodmetod\r\n"
			+ "and gr.gracodinst =:codInstitucion\r\n"
			+ "and gr.gracodmetod =:codMetodologia\r\n"
			+ "and gr.gracodigo =:codGrado")
	Grado gradoPorInstMetodIdEst(long codInstitucion, long codMetodologia, long codGrado);

}
