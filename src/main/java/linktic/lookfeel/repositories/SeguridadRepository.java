package linktic.lookfeel.repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import linktic.lookfeel.model.Usuario;

/**
 *
 * @author Ing. Alexander A. Muñoz Coneo
 * @fechacreacion 19/07/2023
 * @requrimiento HU007_SED, HU008_SED
 * @version 1.0
 */

public interface SeguridadRepository extends JpaRepository<Usuario, Long> {

	ArrayList<Usuario> findByUsuLogin(String usuLogin);

	@Query(nativeQuery = true, value = "select distinct usuario.USULOGIN, usuario.USUPASSWORD, usuario.USUCODJERAR"
			+ ", usuario.USUPERFCODIGO, usuario.USUPERNUMDOCUM, usuario.USUPASSWORDTEMPORAL, usuario.USUESTADO  "
			+ "from usuario usuario, personal personal " + "where personal.pernumdocum = usuario.usulogin "
			+ "and usuario.usulogin = :usuLogin")
	ArrayList<Usuario> findByPerNumDocumForUniquePass(String usuLogin);

	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "UPDATE usuario SET usupassword = :usuPasswordNew, usupasswordtemporal = :usuPasswordtemporal\r\n"
			+ "WHERE usupassword IS NOT NULL AND usupassword = :usuPassword and usulogin = :usuLogin")
	void updateByPerNumDocumForUniquePass(String usuLogin, String usuPassword, String usuPasswordNew,
			String usuPasswordtemporal);

	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "update usuario set usupassword = :usuPasswordNew, usupasswordtemporal = :usuPasswordtemporal "
			+ "where usulogin = :usuLogin")
	void updateByUsuLoginPassNull(String usuLogin, String usuPasswordNew, String usuPasswordtemporal);

	@Procedure(procedureName = "update_all_passwords")
    void updateAllPasswords(@Param("in_usuLogin") String usuLogin,
                            @Param("in_usuPassword") String usuPassword,
                            @Param("in_usuPasswordNew") String usuPasswordNew,
                            @Param("in_usuPasswordtemporal") String usuPasswordtemporal);

}
