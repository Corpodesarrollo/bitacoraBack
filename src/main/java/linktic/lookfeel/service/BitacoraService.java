package linktic.lookfeel.service;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
import linktic.lookfeel.model.Institucion;
import linktic.lookfeel.model.Personal;
import linktic.lookfeel.model.Response;
import linktic.lookfeel.model.Sede;
import linktic.lookfeel.model.TipoLog;
import linktic.lookfeel.repositories.BitacoraReporteRepository;
import linktic.lookfeel.repositories.BitacoraRepository;
import linktic.lookfeel.repositories.InstitucionRepository;
import linktic.lookfeel.repositories.PersonalRepository;
import linktic.lookfeel.repositories.SedeRepository;
import linktic.lookfeel.repositories.TipoLogRepository;
import linktic.lookfeel.security.repositories.UsuarioRepository;
import linktic.lookfeel.util.Utilidades;
import net.bytebuddy.asm.Advice.This;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.imageio.ImageIO;
import java.util.HashMap;
import java.util.HashSet;

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
		b.setUsuario(bitacora.getUsuario().toString());
		b.setModulo(bitacora.getModulo());
		Date hoy = new Date();
		b.setFechaRegistro(hoy);
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
		List<TipoLogDto> respuesta = new ArrayList();
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
		//List<ComboDto> lista = new ArrayList<>();
		Set<ComboDto> lista = new HashSet<ComboDto>();
		
		if(usuario.getNivelPerfil()==6 || usuario.getNivelPerfil()==4) {
			personal = personalRepository.getUsuarioXInstutucion(usuario.getInstitucion());			
		}
		else if(usuario.getNivelPerfil()==1 && usuario.getPerfil()==110) {
			personal = personalRepository.findAll();
		}
		
		for(int i=0;i<personal.size();i++) {
			ComboDto c = new ComboDto();
			Personal p = personal.get(i);
			c.setCodigo(p.getPernumdocum());
			c.setNombre(p.getPernombre1()+" "+(p.getPernombre2()==null?"":p.getPernombre2())+" "+p.getPerapellido1()+" "+(p.getPerapellido2()==null?"":p.getPerapellido2()));
			lista.add(c);	
		}
		ComboDto xc = new ComboDto();
		xc.setCodigo(null);
		xc.setNombre("TODOS");
		lista.add(xc);
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
				bitacora.getTipoLogBitacora(), descripcion, page);
		Integer total = bitacoraRepository.totalResgistrosFiltro(bitacora.getFechaInicio(), bitacora.getFechaFin(), bitacora.getUsuario(), bitacora.getColegio(), bitacora.getSede(), bitacora.getJornada(),
				bitacora.getTipoLogBitacora(), descripcion);
		for (Bitacora bitacora2 : bitacoras) {
			bitacora2.setTotalPag(total);
			String nomPerfil = bitacoraRepository.consultarNomPerfil(bitacora2.getPerfil());
			bitacora2.setNomPerfil(nomPerfil);
		}
		
		return new Response(HttpStatus.OK.value(), "Bitacora consultada correctamente", bitacoras);
	}
	
	@Override
	public Response exportarBitacoraAPdf(long id) {
		// TODO Auto-generated method stub
		try {
			InputStream plantillaBitacoraPdf = getClass().getResourceAsStream("/plantillas/plantillaBitacoraPdf.jasper");
			BufferedImage logo = ImageIO.read(getClass().getResource("/imagenes/LOGOSED2.png"));
			List<BitacoraReporte> bitacoras = bitacoraReporteRepository.consultaBitacoraReporte(id);
			for (BitacoraReporte item : bitacoras) {
				if(item.getDescripcion()!=null)
					item.setDescripcion(this.descripcionDecode(item.getDescripcion()));
			}
			byte[] respuesta = Utilidades.exportReportToPdf(bitacoras, plantillaBitacoraPdf, new HashMap<String, Object>() {{ put("logo", logo); }});
			return new Response(HttpStatus.OK.value(), "Reporte generado correctamente", respuesta);
			
//			InputStream i = getClass().getResourceAsStream("/plantillas/plantillaBitacoraPdf.jrxml");
//			return new Response(HttpStatus.OK.value(), "Reporte generado correctamente", IOUtils.toByteArray(i));
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), sw.toString(), null);
		}
	}
	
	@Override
	public Response exportarBitacoraAExcel(long id) {
		// TODO Auto-generated method stub
		try {
			InputStream plantillaBitacoraPdf = getClass().getResourceAsStream("/plantillas/plantillaBitacoraPdf.jasper");
			BufferedImage logo = ImageIO.read(getClass().getResource("/imagenes/LOGOSED2.png"));
			List<BitacoraReporte> bitacoras = bitacoraReporteRepository.consultaBitacoraReporte(id);
			for (BitacoraReporte item : bitacoras) {
				if(item.getDescripcion()!=null)
					item.setDescripcion(this.descripcionDecode(item.getDescripcion()));
			}
			byte[] respuesta = Utilidades.exportReportToXlsx(bitacoras, plantillaBitacoraPdf, new HashMap<String, Object>() {{ put("logo", logo); }});
			return new Response(HttpStatus.OK.value(), "Reporte generado correctamente", respuesta);
		} catch (Exception e) {
			return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
		}
	}

	private String descripcionDecode(String descripcion) throws JsonMappingException, JsonProcessingException {
		StringBuilder result = new StringBuilder();
		if(descripcion.charAt(0)=='{') {
			Map<Object,Object> map = new ObjectMapper().readValue(descripcion, HashMap.class);
			for (Object key : map.keySet()) {
				result.append(this.Tildar(key.toString()) + ": " + map.get(key) + "\n");
		    }
		} else if(descripcion.charAt(0)=='[') {
			if (descripcion.charAt(1)=='{') {
				List<Map<Object, Object>> list = new ObjectMapper().readValue(descripcion, new TypeReference<List<Map<Object,Object>>>(){});
				for (Map<Object, Object> map : list) {
					for (Object key : map.keySet()) {
						result.append(this.Tildar(key.toString()) + ": " + map.get(key) + "\n");
					}
					result.append("\n");
				}
			}
		}
		return result.toString();
	}
	
	private String Tildar(String texto) {
		String newStr = texto;
		String[] con = {"Á", "É", "Í", "Ó", "Ú", "Ý", "á", "é", "í", "ó", "ú", "ý", "Ã", "Ñ", "Õ", "ã", "ñ", "õ"};
		String[] sin = {"&Aacute;", "&Eacute;", "&Iacute;", "&Oacute;", "&Uacute;", "&Yacute;", "&aacute;", "&eacute;", "&iacute;", "&oacute;", "&uacute;", "&yacute;", "&Atilde;", "&Ntilde;", "&Otilde;", "&atilde;", "&ntilde;", "&otilde;"};
		for(int index = 0; index < sin.length; index++) {
			newStr = newStr.replaceAll(sin[index], con[index]);
		}
		return newStr;
	}
	
}