package linktic.lookfeel.model;

public enum TipoPoliticaEnum {
	USO("POLITICA_USO"),
    DATOS("DATOS_PERSONALES");

    private final String valor;

	TipoPoliticaEnum(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }


}
