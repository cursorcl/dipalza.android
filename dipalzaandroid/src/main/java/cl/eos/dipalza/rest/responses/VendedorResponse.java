package cl.eos.dipalza.rest.responses;

public class VendedorResponse {

	Vendedor data;

	public Vendedor getData() {
		return data;
	}

	public void setData(Vendedor data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "VendedorResponse [data=" + data + "]";
	}
	
	
	

	
	
}
