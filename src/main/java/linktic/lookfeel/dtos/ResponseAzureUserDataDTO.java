package linktic.lookfeel.dtos;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseAzureUserDataDTO {

    private String sub;
    private String name;
    private String family_name;
    private String given_name;
    private String picture;
    private String email;


    public ResponseAzureUserDataDTO() {

    }

    public ResponseAzureUserDataDTO(String sub, String name, String family_name, String given_name, String picture, String email) {
        this.sub = sub;
        this.name = name;
        this.family_name = family_name;
        this.given_name = given_name;
        this.picture = picture;
        this.email = email;
    }
}
