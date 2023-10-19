package linktic.lookfeel.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import linktic.lookfeel.dtos.AceptacionUsuarioDto;
import linktic.lookfeel.model.AceptacionPoliticas;
import linktic.lookfeel.model.PoliticasProteccion;

public interface AceptacionPoliticasRepository extends JpaRepository<AceptacionPoliticas, Long> {
	
	@Query(nativeQuery = true, value = "SELECT ap.USULOGIN usuario, CONCAT(p.PERNOMBRE1,p.PERNOMBRE2) nombres,"
			+ "CONCAT(p.PERAPELLIDO1,p.PERAPELLIDO2) apellidos, "
			+ "CASE\r\n"
			+ "            WHEN p.CODIGO_TIPO = 'POLITICA_USO' THEN ACEPTADA \r\n"
			+ "        END aceptaUso, \r\n"
			+ "CASE\r\n"
			+ "            WHEN p.CODIGO_TIPO = 'DATOS_PERSONALES' THEN ACEPTADA \r\n"
			+ "        END aceptaDatos \r\n"
			+ "FROM ACEPTACION_POLITICAS ap\r\n" 
			+ "JOIN POLITICA_PROTECCION_DATOS p ON ap.IDPOLITICA = p.ID "
			+ "JOIN PERSONAL p ON ap.USULOGIN = p.PERNUMDOCUM "
			+ "WHERE p.ESTADO = 1")
	List<Object[]>  verListaAceptacion();
	
	
	@Query(nativeQuery = true, value = "SELECT ap.USULOGIN usuario, CONCAT(p.PERNOMBRE1,p.PERNOMBRE2) nombres,"
			+ "CONCAT(p.PERAPELLIDO1,p.PERAPELLIDO2) apellidos, "
			+ "CASE\r\n"
			+ "            WHEN p.CODIGO_TIPO = 'POLITICA_USO' THEN ACEPTADA \r\n"
			+ "        END aceptaUso, \r\n"
			+ "CASE\r\n"
			+ "            WHEN p.CODIGO_TIPO = 'DATOS_PERSONALES' THEN ACEPTADA \r\n"
			+ "        END aceptaDatos, \r\n"
			+ "ap.REENVIAR reenviar\r\n"
			+ "FROM ACEPTACION_POLITICAS ap\r\n" 
			+ "JOIN POLITICA_PROTECCION_DATOS p ON ap.IDPOLITICA = p.ID "
			+ "JOIN USUARIO u ON ap.USULOGIN = u.USUPERNUMDOCUM "
			+ "JOIN PERSONAL p ON u.USULOGIN = PERSONAL.PERNUMDOCUM "
			+ "WHERE p.ESTADO = 1"
			+ "AND ap.USULOGIN =:usuario")
	AceptacionUsuarioDto verAceptacionUsuario(String usuario);

}
