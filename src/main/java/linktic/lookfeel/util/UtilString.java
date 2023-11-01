package linktic.lookfeel.util;

public class UtilString {
	
	public static String obtenerValorString(Object valor) {
		return (valor != null) ? valor.toString().trim() : "";
	}
}

