package linktic.lookfeel.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response {
	/**
	 * 
	 */
	private int code;
	private String message;
	private Object data;

	public Response(int code, String message, Object data) {
		super();
		this.code = code;
		this.message = message;
		this.data = data;
	}


}
