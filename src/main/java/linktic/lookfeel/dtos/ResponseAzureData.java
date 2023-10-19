package linktic.lookfeel.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseAzureData {
	
	private String displayName;
	private String givenName;
	private String jobTitle;
	private String mail;
	private String surname;
	private String id;
	
	
	public ResponseAzureData() {
		
	}


	public ResponseAzureData(String displayName, String givenName, String jobTitle, String mail, String surname, String id) {
		super();
		this.displayName = displayName;
		this.givenName = givenName;
		this.jobTitle = jobTitle;
		this.mail = mail;
		this.surname = surname;
		this.id = id;
	}

}
