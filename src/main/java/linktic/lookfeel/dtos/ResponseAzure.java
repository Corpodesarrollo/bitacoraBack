package linktic.lookfeel.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseAzure {
	
	private String token_type;
	private int expires_in;
	private int ext_expires_in;
	private String access_token;
	
	
	public ResponseAzure() {
		
	}
	
	public ResponseAzure(String token_type, int expires_in, int ext_expires_in, String access_token) {
		super();
		this.token_type = token_type;
		this.expires_in = expires_in;
		this.ext_expires_in = ext_expires_in;
		this.access_token = access_token;
	}

}
