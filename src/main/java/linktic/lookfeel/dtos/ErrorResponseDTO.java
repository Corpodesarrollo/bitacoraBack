package linktic.lookfeel.dtos;

public class ErrorResponseDTO {

    private boolean exito = false;
    private String error;

    public ErrorResponseDTO(String error) {
        this.error = error;
    }

    public boolean isExito() {
        return exito;
    }

    public String getError() {
        return error;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String error;

        public Builder error(String error) {
            this.error = error;
            return this;
        }

        public ErrorResponseDTO build() {
            return new ErrorResponseDTO(error);
        }
    }
}
