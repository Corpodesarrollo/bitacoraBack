package linktic.lookfeel.security.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class ApplicationProps.
 */
@Component

/**
 * Gets the origins.
 *
 * @return the origins
 */
@Getter

/**
 * Sets the origins.
 *
 * @param origins the new origins
 */
@Setter
@ConfigurationProperties(prefix = "config")
public class ApplicationProps {

    /** The methods. */
    private List<String> methods = new ArrayList<>();
    
    /** The header. */
    private List<String> header = new ArrayList<>();
    
    /** The origins. */
    private List<String> origins = new ArrayList<>();
}
