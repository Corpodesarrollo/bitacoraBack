package linktic.lookfeel.dtos;

public class UsuarioFiltroDto {
	private int nivelPerfil;
	private int perfil;
	private int institucion;

	public int getPerfil() {
		return perfil;
	}

	public void setPerfil(int perfil) {
		this.perfil = perfil;
	}

	public int getInstitucion() {
		return institucion;
	}

	public void setInstitucion(int institucion) {
		this.institucion = institucion;
	}

	public int getNivelPerfil() {
		return nivelPerfil;
	}

	public void setNivelPerfil(int nivelPerfil) {
		this.nivelPerfil = nivelPerfil;
	}
}
