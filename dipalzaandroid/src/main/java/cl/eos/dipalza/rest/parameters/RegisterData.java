package cl.eos.dipalza.rest.parameters;

public class RegisterData {

	/*
		"name": "Eliecer Osorio Verdugo",
		"email": "cursor.cl@gmail.com", 
		"password": "_l2j1rs2",
		"c_password": "_l2j1rs2"
	*/
	
	final String name;
	final String email;
	final String password;
	final String c_password;
	
	public RegisterData(String name, String email, String password, String c_password) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.c_password = c_password;
	}
	
	
	
}
