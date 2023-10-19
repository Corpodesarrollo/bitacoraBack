package linktic.lookfeel.dtos;


public class ResponseMensajeDTO {
	private boolean exito;
	private MensajeDTO mensaje;

	public boolean isExito() {
		return exito;
	}

	public void setExito(boolean exito) {
		this.exito = exito;
	}

	public MensajeDTO getMensaje() {
		return mensaje;
	}

	public void setMensaje(MensajeDTO mensaje) {
		this.mensaje = mensaje;
	}

	public ResponseMensajeDTO(boolean exito, MensajeDTO mensaje) {
		this.exito = exito;
		this.mensaje = mensaje;
	}

	public static ResponseMensajeDTO.Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private boolean exito;
		private MensajeDTO mensaje;

		public Builder exito(boolean exito) {
			this.exito = exito;
			return this;
		}

		public Builder mensaje(MensajeDTO mensaje) {
			this.mensaje = mensaje;
			return this;
		}

		public ResponseMensajeDTO build() {
			return new ResponseMensajeDTO(exito, mensaje);
		}
	}
}
