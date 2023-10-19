package linktic.lookfeel.util;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

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


}
