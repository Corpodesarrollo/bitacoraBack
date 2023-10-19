package linktic.lookfeel.dtos;

import java.util.List;

import linktic.lookfeel.model.Mensajes;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ResponseAllMensajesDTO {
	 private boolean exito;
	    private int registros_x_pagina;
	    private int num_pagina;
	    private int total_registros;
	    private List<MensajeDTO> registros;
	    
	    
	    
		public boolean isExito() {
			return exito;
		}
		public void setExito(boolean exito) {
			this.exito = exito;
		}
		public int getRegistros_x_pagina() {
			return registros_x_pagina;
		}
		public void setRegistros_x_pagina(int registros_x_pagina) {
			this.registros_x_pagina = registros_x_pagina;
		}
		public int getNum_pagina() {
			return num_pagina;
		}
		public void setNum_pagina(int num_pagina) {
			this.num_pagina = num_pagina;
		}
		public int getTotal_registros() {
			return total_registros;
		}
		public void setTotal_registros(int total_registros) {
			this.total_registros = total_registros;
		}

		public void setRegistros(List<MensajeDTO> registros) {
			this.registros = registros;
		}
		public ResponseAllMensajesDTO(boolean exito, int registros_x_pagina, int num_pagina, int total_registros,
				List<MensajeDTO> registros) {
			super();
			this.exito = exito;
			this.registros_x_pagina = registros_x_pagina;
			this.num_pagina = num_pagina;
			this.total_registros = total_registros;
			this.registros = registros;
		}
}
