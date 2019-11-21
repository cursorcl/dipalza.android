package cl.eos.dipalza.rest.responses;

public class Vendedor {
	

	
	int id;
	String vendedor;
	String nombre;    
	Href href;
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getVendedor() {
		return vendedor;
	}
	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Href getHref() {
		return href;
	}
	public void setHref(Href href) {
		this.href = href;
	}
	@Override
	public String toString() {
		return "Vendedor [ id=" + id + " vendedor=" + vendedor + ", nombre=" + nombre + ", href=" + href + "]";
	}
	
	
}
