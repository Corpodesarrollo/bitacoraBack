package linktic.lookfeel.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import linktic.lookfeel.model.Perfil;
import linktic.lookfeel.model.Personal;
import org.springframework.data.repository.query.Param;

public interface PerfilRepository extends JpaRepository<Perfil, Long>{
	
	
	
	/**
	 * 
	 * Perfil de la persona por el correo dado
	 * 
	 * @param email
	 * @return Perfil
	 */
	@Query(nativeQuery = true, value = "select "
			+ "persona.PERNUMDOCUM,"
			+ "perfil.perfnombre,"
			+ "perfil.perfdescripcion,"
			+ "institucion.inscodigo,"
			+ "institucion.insnombre,"
			+ "sede.sedcodigo, \r\n"
			+ "sede.sednombre,"
			+ "sj.SEDJORCODJOR,"
			+ "gcons.G_CONNOMBRE\r\n"
			+ "      from personal persona \r\n"
			+ "        join USUARIO u on persona.PERNUMDOCUM=to_char(u.USUPERNUMDOCUM)\r\n"
			+ "        inner join g_jerarquia j on u.usucodjerar=j.g_jercodigo\r\n"
			+ "        inner join perfil perfil on perfil.PERFCODIGO=u.usuperfcodigo\r\n"
			+ "        inner join institucion institucion on institucion.inscodigo = j.g_jerinst\r\n"
			+ "        left join sede sede on institucion.inscodigo=sede.sedcodins and sede.SEDCODIGO = j.G_JERSEDE\r\n"
			+ "        left join sede_jornada sj on institucion.INSCODIGO = sj.SEDJORCODINST \r\n"
			+ "            and j.G_JERJORN = sj.SEDJORCODJOR and SEDJORCODSEDE=j.G_JERSEDE and sede.SEDESTADO = 'A'\r\n"
			+ "        left JOIN JORNADA jor on jor.JORCODINS = sj.SEDJORCODINST AND jor.JORCODIGO = sj.SEDJORCODJOR\r\n"
			+ "        left JOIN G_CONSTANTE gcons ON gcons.G_CONTIPO = 5 AND gcons.G_CONCODIGO = sj.SEDJORCODJOR\r\n"
			+ "    where pernumdocum=:numIdentificacion")
	List<Object[]> findPerfilByNumDocumento(String numIdentificacion);
	
	
	/**
	 * 
	 * Perfil de la persona por el correo dado
	 * 
	 * @param email
	 * @return Perfil
	 */
	@Query(nativeQuery = true, value = "SELECT p.PERNUMDOCUM,   "
			+ "			 pr.PERFCODIGO AS perfil_id,  "
			+ "             REPLACE(REPLACE(LOWER(pr.perfnombre), '(a)',''),' acade','') AS perfil_nombre, pr.perfnombre AS perfil_etiqueta,   "
			+ "			 i.inscodigo AS colegio_id,  "
			+ "             i.insnombre AS colegio_nombre,   "
			+ "			 l.lc_codi_id AS localidad_instituto_id,  "
			+ "             l.lc_nomb AS localidad_instituto_nombre,   "
			+ "			 s.sedcodigo AS sede_id,  "
			+ "             s.sednombre AS sede_nombre,   "
			+ "			 sj.SEDJORCODJOR AS jornada_id,  "
			+ "             gcons.g_connombre AS jornada_nombre,  "
			+ "             i.inscoddane AS institucion_codigo_dane, "
			+ "             pr.perfnivel AS nivel_perfil_id,   "
			+ "			 j.G_JERDEPTO AS dependencia_id,  "
			+ "             d.g_connombre AS dependencia_nombre,   "
			+ "			 ln.lc_codi_id AS localidad_id,  "
			+ "             ln.lc_nomb AS localidad_nombre,   "
			+ "             U.USUCODJERAR AS codigo_jerarquia   "
			+ "			 FROM personal p    "
			+ "			 INNER JOIN USUARIO u on p.PERNUMDOCUM=to_char(u.USUPERNUMDOCUM)   "
			+ "			 INNER JOIN g_jerarquia j on u.usucodjerar=j.g_jercodigo   "
			+ "			 INNER JOIN perfil pr on pr.PERFCODIGO=u.usuperfcodigo   "
			+ "			 LEFT JOIN g_constante d ON d.G_CONTIPO = 42 AND d.G_CONCODIGO = j.G_JERDEPTO   "
			+ "			 LEFT JOIN egd_localidad ln ON ln.lc_codi_id=j.g_jerlocal   "
			+ "			 LEFT JOIN institucion i on i.inscodigo = j.g_jerinst   "
			+ "			 LEFT JOIN egd_localidad l ON l.lc_codi_id=i.inscodlocal   "
			+ "			 LEFT JOIN sede s on s.SEDCODINS = i.inscodigo AND s.SEDESTADO='A'    "
			+ "			 LEFT JOIN sede_jornada sj on i.inscodigo=sj.sedjorcodinst AND s.SEDCODIGO = sj.SEDJORCODSEDE   "
			+ "			 LEFT JOIN JORNADA jor on jor.JORCODINS = sj.SEDJORCODINST AND jor.JORCODIGO = sj.SEDJORCODJOR   "
			+ "			 LEFT JOIN G_CONSTANTE gcons ON gcons.G_CONTIPO = 5 AND gcons.G_CONCODIGO = sj.SEDJORCODJOR   "
			+ "			 WHERE pernumdocum = :numIdentificacion AND u.usuestado = 1 "
			+ "			 ORDER BY pr.perfnombre ASC, i.insnombre ASC, s.sednombre ASC, gcons.g_connombre ASC")
	List<Object[]> findRectorPerfilByNumDocumento(String numIdentificacion);


	@Query(nativeQuery = true, value = "select inscoddane12 from institucion where inscodigo =:codInstitucion")
	public Long getByCodInstitucion(@Param("codInstitucion") Long codInstitucion);

	/**
	 * 
	 * Perfil de la persona por el correo dado
	 * 
	 * @param email
	 * @return Perfil
	 */
	@Query(nativeQuery = true, value = "SELECT p.PERNUMDOCUM, "
			+ "			 pr.PERFCODIGO AS perfil_id, "
			+ "			 REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(LOWER(pr.perfnombre), '(a)',''),' acade',''),' conv.',''),'é','e'),' ','_') AS perfil_nombre, "
			+ "			 UPPER(pr.perfnombre) AS perfil_etiqueta, "
			+ "			 i.inscodigo AS colegio_id, "
			+ "			 i.insnombre AS colegio_nombre, "
			+ "			 l.lc_codi_id AS localidad_instituto_id, "
			+ "			 l.lc_nomb AS localidad_instituto_nombre, "
			+ "			 s.sedcodigo AS sede_id, "
			+ "			 s.sednombre AS sede_nombre, "
			+ "			 sj.SEDJORCODJOR AS jornada_id, "
			+ "			 gcons.g_connombre AS jornada_nombre, "
			+ "			 i.inscoddane AS institucion_codigo_dane,"
			+ "             pr.perfnivel AS nivel_perfil_id,"
			+ "             j.G_JERDEPTO AS dependencia_id,"
			+ "             d.g_connombre AS dependencia_nombre,"
			+ "			 ln.lc_codi_id AS localidad_id,"
			+ "             ln.lc_nomb AS localidad_nombre,"
			+ "             U.USUCODJERAR AS codigo_jerarquia"
			+ "			 FROM personal p  "
			+ "			 INNER JOIN USUARIO u on p.PERNUMDOCUM=to_char(u.USUPERNUMDOCUM) "
			+ "			 INNER JOIN g_jerarquia j on u.usucodjerar=j.g_jercodigo "
			+ "			 INNER JOIN perfil pr on pr.PERFCODIGO=u.usuperfcodigo "
			+ "			 INNER JOIN institucion i on i.inscodigo = j.g_jerinst "
			+ "			 INNER JOIN egd_localidad l ON l.lc_codi_id=i.inscodlocal"
			+ "             LEFT JOIN egd_localidad ln ON ln.lc_codi_id=j.g_jerlocal"
			+ "             LEFT JOIN g_constante d ON d.G_CONTIPO = 42 AND d.G_CONCODIGO = j.G_JERDEPTO"
			+ "			 INNER JOIN sede s on s.SEDCODINS = i.inscodigo AND s.sedcodigo=j.g_jersede  AND s.SEDESTADO='A'  "
			+ "			 INNER JOIN sede_jornada sj on i.inscodigo=sj.sedjorcodinst AND s.SEDCODIGO = sj.SEDJORCODSEDE AND j.G_JERJORN=sj.SEDJORCODJOR "
			+ "			 INNER JOIN JORNADA jor on jor.JORCODINS = sj.SEDJORCODINST AND jor.JORCODIGO = sj.SEDJORCODJOR "
			+ "			 INNER JOIN G_CONSTANTE gcons ON gcons.G_CONTIPO = 5 AND gcons.G_CONCODIGO = sj.SEDJORCODJOR "
			+ "			 WHERE pernumdocum = :numIdentificacion AND u.usuestado = 1 "
			+ "			 ORDER BY pr.perfnombre ASC,  i.insnombre ASC, s.sednombre ASC, gcons.g_connombre ASC")
	List<Object[]> findHabitualPerfilByNumDocumento(String numIdentificacion);
	
	
	
	
	
	/**
	 * 
	 * Perfil de la persona por el correo dado
	 * 
	 * @param email
	 * @return Perfil
	 */
	@Query(nativeQuery = true, value = "select perfil.*\r\n"
			+ "from usuario usuario \r\n"
			+ "join personal personal on usuario.USUPERNUMDOCUM = personal.PERNUMDOCUM\r\n"
			+ "join perfil perfil on perfil.PERFCODIGO=usuario.usuperfcodigo\r\n"
			+ "where personal.PEREMAIL=:email")
	Perfil findPerfilByEmail (String email);
	
	
	/**
	 * 
	 * Perfil por usuario y contraseña
	 * 
	 * @param numeroIdentificacion
	 * @param pass
	 * @return Perfil
	 */
	@Query(nativeQuery = true, value = "select perfil.*\r\n"
			+ "from usuario usuario \r\n"
			+ "join personal personal on usuario.USUPERNUMDOCUM = personal.PERNUMDOCUM\r\n"
			+ "join perfil perfil on perfil.PERFCODIGO=usuario.usuperfcodigo\r\n"
			+ "where personal.PERNUMDOCUM=:numeroIdentificacion "
			+ "and usuario.USUPASSWORD=:pass ")
	Perfil findPerfilByNumeroIdentificacion (String numeroIdentificacion, String pass);
	
	/**
	 * 
	 * Perfil por usuario y contraseña
	 * 
	 * @param numeroIdentificacion
	 * @param pass
	 * @return Perfil
	 */
	@Query(nativeQuery = true, value = "select distinct 1\r\n"
			+ "from usuario usuario \r\n"
			+ "join personal personal on usuario.USUPERNUMDOCUM = personal.PERNUMDOCUM\r\n"
			+ "where personal.PERNUMDOCUM=:numeroIdentificacion "
			+ "and usuario.USUPASSWORD=:pass ")
	Long findByNumeroIdentificacionAndPass (String numeroIdentificacion, String pass);
	
	
	/**
	 * 
	 * Nombre de fotografia de la persona por el numero de cedula
	 * 
	 * @param email
	 * @return String
	 */
	@Query(nativeQuery = true, value = "select  fotoPersonal.nombre_archivo\r\n"
			+ "from personal\r\n"
			+ "join log_fotografia_personal fotoPersonal on personal.pernumdocum = fotoPersonal.pernumdocum\r\n"
			+ "where PEREMAIL = :email")
	String findNumDocumentoByEmail (String email);
	
	/**
	 * 
	 * Datos de la persona por el correo
	 * 
	 * @param email
	 * @return
	 */
	@Query(value = "select personal"
			+ " from Personal personal "
			+ "where LOWER(personal.peremail) = :email ")
	Personal findPersonalByEmail (String email);
	
	
	/**
	 * Consulta para obtener el perfil del usuario
	 * 
	 * @param numerpIdentificacion - Se entrega el numero de identificacion del usuario
	 * @return Registros de información del perfil del usuario 
	 */
	@Query(nativeQuery = true, value = "SELECT perf.* "
			+ "  FROM usuario usu "
			+ "  JOIN personal per ON per.pernumdocum = usu.usupernumdocum "
			+ "  JOIN perfil perf ON perf.perfcodigo = usu.usuperfcodigo "
			+ " WHERE per.pernumdocum = :numerpIdentificacion "
			+ "   AND ROWNUM <= 1 ")
	List<Object[]> findPerfil(String numerpIdentificacion);
	
	/**
	 * 
	 * Datos de persona por el numero de identificacion
	 * 
	 * @param numeroIdentificacion
	 * @return Personal
	 */
	@Query(value = "select personal"
			+ " from Personal personal "
			+ "where personal.pernumdocum = :numeroIdentificacion ")
	Personal findPersonalByNumeroIdentificacion (String numeroIdentificacion);
	
	/**
	 * 
	 * Retorna si debe cambiar password
	 * 
	 * @param numeroIdentificacion
	 * @return Personal
	 */
	@Query(value = "Select USUPASSWORDTEMPORAL from usuario where USUPERNUMDOCUM=:numeroIdentificacion ", nativeQuery = true)
	String findPassTemp (String numeroIdentificacion);
	
	@Query(value = "Select perfil.* from perfil where perfcodigo in (410,420,421,422,423,424,460) order by perfnombre ASC", nativeQuery = true)
	List<Perfil> findPerfilesAsc();
	
	@Query(nativeQuery = true, value = "select distinct perfil.perfcodigo AS perfilId\r\n"
			+ "from usuario usuario\r\n"
			+ "join personal personal on usuario.USUPERNUMDOCUM = personal.PERNUMDOCUM\r\n"
			+ "join perfil perfil on perfil.PERFCODIGO=usuario.usuperfcodigo\r\n"
			+ "where personal.PERNUMDOCUM=:numerpIdentificacion")
	List<Object[]> findPerfilId(String numerpIdentificacion);
	
	@Query(nativeQuery = true, value = "SELECT gruser.grusercodigo as codigo\r\n"
			+ "FROM perfil per,\r\n"
			+ "    servicio_perfil serpe,\r\n"
			+ "    grupo_servicio gruser\r\n"
			+ "WHERE per.perfcodigo = serpe.serperfperfcodigo\r\n"
			+ "    AND serpe.serpergrusercodigo = gruser.grusercodigo\r\n"
			+ "    AND gruser.grusercodigo =:permisoId\r\n"
			+ "    AND per.perfcodigo = :perfilId")
	List<Object[]> findPermisoPerfil(String perfilId, long permisoId);
	
	@Query(value = "SELECT *\r\n"
			+ "FROM nivel_perfil np\r\n"
			+ "WHERE np.nipejerarquia>=(SELECT np1.nipejerarquia \r\n"
			+ "FROM perfil p \r\n"
			+ "INNER JOIN nivel_perfil np1 ON p.perfnivel=np1.nipecodigo \r\n"
			+ "WHERE p.perfcodigo = :perfilId)", nativeQuery = true)
	List<Object[]> findPerfilNivel(String perfilId);
	
	@Query(nativeQuery = true, value = "SELECT DISTINCT\r\n"
			+ "    ( u.usuperfcodigo )\r\n"
			+ "FROM\r\n"
			+ "         personal persona\r\n"
			+ "    JOIN usuario     u ON persona.pernumdocum = to_char(u.usupernumdocum)\r\n"
			+ "    JOIN g_jerarquia j ON u.usucodjerar = j.g_jercodigo\r\n"
			+ "    JOIN institucion ins ON ins.inscodigo = j.g_jerinst\r\n"
			+ "WHERE\r\n"
			+ "    ins.inscodigo IN (\r\n"
			+ "        SELECT\r\n"
			+ "            ins.inscodigo\r\n"
			+ "        FROM\r\n"
			+ "            personal    per, usuario     u, g_jerarquia j, institucion ins\r\n"
			+ "        WHERE\r\n"
			+ "                per.pernumdocum = to_char(u.usupernumdocum)\r\n"
			+ "            AND u.usucodjerar = j.g_jercodigo\r\n"
			+ "            AND ins.inscodigo = j.g_jerinst\r\n"
			+ "            AND u.usulogin = :usuarioid\r\n"
			+ "    )")
	List<Object[]> perfilesIdPorInstSegunUsuarioId(String usuarioid);
	
	@Query(value = "Select INITCAP(perfil.perfnombre) from perfil where perfil.perfcodigo =:perfcodigo", nativeQuery = true)
	String findPerfilNombre(String perfcodigo);

}
