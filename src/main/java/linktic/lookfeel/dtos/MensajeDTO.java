package linktic.lookfeel.dtos;

import java.util.Date;

public interface MensajeDTO {
    Long getId();
    String getAsunto();
    String getEnviado_por();
    String getContenido();
    Long getLeido();
    Date getFecha();
    Date getFecha_creacion();
}

