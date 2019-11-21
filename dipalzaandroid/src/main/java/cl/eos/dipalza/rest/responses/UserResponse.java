package cl.eos.dipalza.rest.responses;

public class UserResponse {

	String success;
	User data;
	String message;
	
	
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public User getData() {
		return data;
	}
	public void setData(User data) {
		this.data = data;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "UserResponse [success=" + success + ", data=" + data + ", message=" + message + "]";
	}
	
	

}
