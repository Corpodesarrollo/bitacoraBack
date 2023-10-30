package linktic.lookfeel.service;

import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import linktic.lookfeel.dtos.BitacoraDto;
import linktic.lookfeel.dtos.BitacoraFiltroDto;
import linktic.lookfeel.dtos.ColegioFiltroDto;
import linktic.lookfeel.dtos.ComboDto;
import linktic.lookfeel.dtos.JornadaDTO;
import linktic.lookfeel.dtos.JornadaFiltroDto;
import linktic.lookfeel.dtos.SedeFiltroDto;
import linktic.lookfeel.dtos.TipoLogDto;
import linktic.lookfeel.dtos.UsuarioFiltroDto;
import linktic.lookfeel.model.Bitacora;
import linktic.lookfeel.model.BitacoraReporte;
import linktic.lookfeel.model.Constante;
import linktic.lookfeel.model.Institucion;
import linktic.lookfeel.model.Jornada;
import linktic.lookfeel.model.Personal;
import linktic.lookfeel.model.Response;
import linktic.lookfeel.model.Sede;
import linktic.lookfeel.model.TipoLog;
import linktic.lookfeel.model.Usuario;
import linktic.lookfeel.repositories.BitacoraReporteRepository;
import linktic.lookfeel.repositories.BitacoraRepository;
import linktic.lookfeel.repositories.InstitucionRepository;
import linktic.lookfeel.repositories.PersonalRepository;
import linktic.lookfeel.repositories.SedeRepository;
import linktic.lookfeel.security.repositories.TipoLogRepository;
import linktic.lookfeel.security.repositories.UsuarioRepository;
import linktic.lookfeel.util.Utilidades;

/**
*
* @author Ing. Giovanny Romero Correa
* @descripcion Servicios Para la operaciones relacionadas con la bitacora
* @fechacreacion 22/10/2023
* @requrimiento HU002
* @version 1.0
*/

@Service
public class BitacoraService implements IBitacoraService{

	@Autowired
	private final BitacoraRepository bitacoraRepository;
	private final BitacoraReporteRepository bitacoraReporteRepository;
	private final TipoLogRepository tipoLogBitacoraRepository;
	private final UsuarioRepository usuarioRepository;
	private final PersonalRepository personalRepository;
	private final InstitucionRepository institucionRepository;
	private final SedeRepository sedeRepository;

    BitacoraService(BitacoraRepository bitacoraRepository,BitacoraReporteRepository bitacoraReporteRepository,TipoLogRepository tipoLogBitacoraRepository,UsuarioRepository usuarioRepository,PersonalRepository personalRepository,
    		InstitucionRepository institucionRepository,SedeRepository sedeRepository) {
        this.bitacoraRepository = bitacoraRepository;
        this.bitacoraReporteRepository = bitacoraReporteRepository;
        this.tipoLogBitacoraRepository = tipoLogBitacoraRepository;
        this.usuarioRepository = usuarioRepository;
        this.personalRepository = personalRepository;
        this.institucionRepository = institucionRepository;
        this.sedeRepository = sedeRepository;
    }	

	@Override
	public Response insertarBitacora(BitacoraDto bitacora) {
		Bitacora b = new Bitacora();
		b.setUsuario(bitacora.getUsuario());
		b.setModulo(bitacora.getModulo());
		b.setFechaRegistro(LocalDateTime.now());
		b.setTipoLog(bitacora.getTipoLog());
		b.setDescripcion(bitacora.getDescripcion());
		b.setSubmodulo(bitacora.getSubmodulo());
		b.setColegio(bitacora.getColegio());
		b.setJornada(bitacora.getJornada());
		b.setSede(bitacora.getSede());
		b.setPerfil(bitacora.getPerfil());
		
		try {
			bitacoraRepository.save(b);
			return new Response(HttpStatus.OK.value(), "Bitacora insertada correctamente", true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return  new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(),e.getMessage(),null);
		}
	}

	@Override
	public Response obtenerTipoLog() {
		// TODO Auto-generated method stub
		List<TipoLogDto> respuesta = new ArrayList<>();
		List<TipoLog> tiposLog = tipoLogBitacoraRepository.findAll();
		TipoLogDto objD;
		TipoLog obj;
		
		for(int i=0;i<tiposLog.size();i++) {
			objD = new TipoLogDto();
			obj = tiposLog.get(i);
			objD.setId(obj.getId());
			objD.setNombre(obj.getNombre());
			respuesta.add(objD);
		}
		
		return new Response(HttpStatus.OK.value(), "tipos de log consultados correctamente", respuesta);
	}

	@Override
	public Response obtenerUsuarios(UsuarioFiltroDto usuario) {
		List<Personal> personal=new ArrayList<>();
		List<ComboDto> lista = new ArrayList<>();
		
		if(usuario.getNivelPerfil()==6) {
			personal = personalRepository.getUsuarioXInstutucion(usuario.getInstitucion());			
		}
		else if(usuario.getNivelPerfil()==1 && usuario.getPerfil()==110) {
			personal = personalRepository.findAll();
		}
		
		for(int i=0;i<personal.size();i++) {
			ComboDto c = new ComboDto();
			Personal p = personal.get(i);
			c.setCodigo(p.getPernumdocum());
			c.setNombre(p.getPernombre1()+" "+p.getPernombre2()==null?"":p.getPernombre2()+" "+p.getPerapellido1()+" "+p.getPerapellido2()==null?"":p.getPerapellido2());
			lista.add(c);	
		}
		
		return new Response(HttpStatus.OK.value(), "Usuarios consultados correctamente", lista);
	}

	@Override
	public Response obtenerColegios(ColegioFiltroDto colegio) {
		int nivelPerfil = usuarioRepository.nivelUsuario(colegio.getUsuario());
		long localidad = usuarioRepository.localidadUsuario(colegio.getUsuario());
		long institucion = usuarioRepository.institucionUsuario(colegio.getUsuario());
		
		List<Institucion> colegios = new ArrayList<>();
		
		if(nivelPerfil ==1) {
			colegios = institucionRepository.findByLocalidadInstitucions(localidad);
		}else if(nivelPerfil ==6){
			colegios.add(institucionRepository.findByCodigo(institucion));
		}
		
		return new Response(HttpStatus.OK.value(), "Colegios consultados correctamente", colegios);
	}

	@Override
	public Response obtenerSedes(SedeFiltroDto colegio) {
		// TODO Auto-generated method stub
		List<Sede> sedes = sedeRepository.findSedeByInstitucion(colegio.getColegio());
		
		return new Response(HttpStatus.OK.value(), "Sedes consultadas correctamente", sedes);
	}

	@Override
	public Response obtenerJornadas(JornadaFiltroDto colegio) {
		// TODO Auto-generated method stub
		List<Object[]> jornadasObj = new ArrayList<>();
		List<JornadaDTO> jornadas = new ArrayList<>();
		JornadaDTO jornada;
		jornadasObj = sedeRepository.findJornadasBySede(colegio.getSede());
		
		for(int i=0;i<jornadasObj.size();i++) {
			Object[] obj = jornadasObj.get(i);
			jornada = new JornadaDTO();
			jornada.setId(((BigDecimal) obj[0]).longValue());
			jornada.setNombre(obj[1].toString());
			jornadas.add(jornada);
 		}
		
		return new Response(HttpStatus.OK.value(), "Jornadas consultadas correctamente", jornadas);
	}

	@Override
	public Response obtenerBitacora(BitacoraFiltroDto bitacora) {
		// TODO Auto-generated method stub
		Pageable page = PageRequest.of(bitacora.getPaginaActual(), bitacora.getItemsPagina());
		String descripcion = "%"+bitacora.getDescripcion()+"%";
		List<Bitacora> bitacoras = bitacoraRepository.consultaBitacora(bitacora.getFechaInicio(), bitacora.getFechaFin(), bitacora.getUsuario(), bitacora.getColegio(), bitacora.getSede(), bitacora.getJornada(),
				bitacora.getTipoLogBitacora(), descripcion,page);
		
		return new Response(HttpStatus.OK.value(), "Bitacora consultada correctamente", bitacoras);
	}
	
	@Override
	public Response exportarBitacoraAPdf(long id) {
		// TODO Auto-generated method stub
		try {
			BufferedImage logo = ImageIO.read(getClass().getResource("/imagenes/LOGOSED2.png"));
			List<BitacoraReporte> bitacoras = bitacoraReporteRepository.consultaBitacoraReporte(id);
			byte[] respuesta = Utilidades.exportReportToPdf(bitacoras, "plantillaBitacoraPdf", new HashMap<String, Object>() {{ put("logo", logo); }});
			return new Response(HttpStatus.OK.value(), "Reporte generado correctamente", respuesta);
		} catch (Exception e) {
			return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
		}
	}
	
	@Override
	public Response exportarBitacoraAExcel(long id) {
		// TODO Auto-generated method stub
		try {
			BufferedImage logo = ImageIO.read(getClass().getResource("/imagenes/LOGOSED2.png"));
			List<BitacoraReporte> bitacoras = bitacoraReporteRepository.consultaBitacoraReporte(id);
			byte[] respuesta = Utilidades.exportReportToXlsx(bitacoras, "plantillaBitacoraPdf", new HashMap<String, Object>() {{ put("logo", logo); }});
			return new Response(HttpStatus.OK.value(), "Reporte generado correctamente", respuesta);
		} catch (Exception e) {
			return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
		}
	}
	
	
}
