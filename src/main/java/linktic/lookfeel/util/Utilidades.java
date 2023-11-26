package linktic.lookfeel.util;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.util.ResourceUtils;

import linktic.lookfeel.service.BitacoraService;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.Exporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

public class Utilidades {

	private static final String ALGORITHM = "AES";

	public static char getSeparadorSO() {

		String sistemaOperativo = System.getProperty("os.name");
		char result = '\\';

		if (sistemaOperativo.startsWith("Windows")) {
			result = '\\';
		} else if (sistemaOperativo.startsWith("Linux")) {
			result = '/';
		} else if (sistemaOperativo.startsWith("Mac")) {
			result = '/';
		} else {
			System.err.println("Sistema operativo no compatible");
		}

		return result;
	}

	public static String getHash(String txt, String hashType) {
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance(hashType);
			byte[] array = md.digest(txt.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
			}
			return sb.toString();
		} catch (java.security.NoSuchAlgorithmException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public static String decrypt(String encryptedText, String encryptionKey) throws Exception {
		byte[] encryptedData = Base64.getDecoder().decode(encryptedText);
		SecretKey secretKey = new SecretKeySpec(encryptionKey.getBytes(StandardCharsets.UTF_8), ALGORITHM);

		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.SECRET_KEY, secretKey);
		byte[] decryptedData = cipher.doFinal(encryptedData);

		return new String(decryptedData, StandardCharsets.UTF_8);
	}

	public static String getNombrePerfil(String nombre) {

		switch (nombre) {
		case "RECTOR(A)":
			return "rector";
		case "RECTOR(A)  PRIV":
			return "rector";
		case "RECTOR(A) PRUEBA":
			return "rector";
		case "COORDINADOR(A) ACADE":
			return "coordinador";
		case "COORDINADOR(A) CONV.":
			return "coordinador";
		case "DOCENTE":
			return "docente";
		case "ORIENTADOR(A)":
			return "orientador";
		case "SRIO(A) ACADÉMICO(A)":
			return "secretario_academico";
		case "ADMINSEC":
			return "admin_sec";
		case "ADMINISTRATIVO":
			return "administrativo";
		default:
			return nombre;
		}
	}

	public static String code(String pass) throws Exception {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(pass.getBytes());
			BigInteger number = new BigInteger(1, messageDigest);
			String hashtext = number.toString(16);
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
	
	 public static String generateRandomPassword(int len){
	        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$^&()_=+-*/%<>?[]{}";
	 
	        SecureRandom random = new SecureRandom();
	        StringBuilder sb = new StringBuilder();
 
	        for (int i = 0; i < len; i++)
	        {
	            int randomIndex = random.nextInt(chars.length());
	            sb.append(chars.charAt(randomIndex));
	        }
	 
	        return sb.toString();
	    }
	 
	 public static boolean validarToken(String storedToken, String token) {
	       return storedToken != null && storedToken.equals(token);
	    }

	 private static JasperPrint getReport(List list, InputStream templateName, Map<String, Object> ... params) throws FileNotFoundException, JRException {
		 Map<String, Object> parameters = new HashMap<>();
		 if(params.length > 0) {
			 parameters = params[0];
		 }
		 JasperPrint report = JasperFillManager.fillReport(templateName, 
				 parameters, new JRBeanCollectionDataSource(list,false));
		 return report;
	 }
	 
	 public static byte[] exportReportToPdf(List list, InputStream templateName, Map<String, Object> ... params) throws FileNotFoundException, JRException  {
		 JasperPrint report = Utilidades.getReport(list, templateName, params);
		 return JasperExportManager.exportReportToPdf(report);
	 }
	 
	 public static byte[] exportReportToXlsx(List list, InputStream templateName, Map<String, Object> ... params) throws FileNotFoundException, JRException  {
		 JasperPrint report = Utilidades.getReport(list, templateName, params);
		 ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		 Exporter exporter = new JRXlsExporter();
		 exporter.setExporterInput(new SimpleExporterInput(report));
		 exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));
		 SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
		 configuration.setOnePagePerSheet(true);
		 configuration.setIgnoreGraphics(false);
		 exporter.setConfiguration(configuration);
		 exporter.exportReport();
		 return byteArrayOutputStream.toByteArray();
	 }
}
