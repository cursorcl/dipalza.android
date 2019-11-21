package cl.eos.dipalza.rest.responses;

public class User {

	String token;
	String name;
	
	
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "User [token=" + token + ", name=" + name + "]";
	}
	
	
}
