package linktic.lookfeel.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MensajePaginado {
	
	private List<Mensaje> mensajes;
    private long totalRegistros;
    private int numeroPaginas;
    private int paginaActual;
    
	public MensajePaginado(List<Mensaje> mensajes, long totalRegistros, int numeroPaginas, int paginaActual) {
		this.mensajes = mensajes;
		this.totalRegistros = totalRegistros;
		this.numeroPaginas = numeroPaginas;
		this.paginaActual = paginaActual;
	}
    
    

}
