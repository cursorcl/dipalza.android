package cl.eos.dipalza.rest.responses;

public class Position {
	int id;
	int vendedor_id;
	double latitud; 
	double longitud; 
	double rumbo; 
	double velocidad;
	String actualizado;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getVendedor_id() {
		return vendedor_id;
	}
	public void setVendedor_id(int vendedor_id) {
		this.vendedor_id = vendedor_id;
	}
	public double getLatitud() {
		return latitud;
	}
	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}
	public double getLongitud() {
		return longitud;
	}
	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}
	public double getRumbo() {
		return rumbo;
	}
	public void setRumbo(double rumbo) {
		this.rumbo = rumbo;
	}
	public double getVelocidad() {
		return velocidad;
	}
	public void setVelocidad(double velocidad) {
		this.velocidad = velocidad;
	}
	public String getActualizado() {
		return actualizado;
	}
	public void setActualizado(String actualizado) {
		this.actualizado = actualizado;
	}
	@Override
	public String toString() {
		return "PositionResponse [id=" + id + ", vendedor_id=" + vendedor_id + ", latitud=" + latitud + ", longitud="
				+ longitud + ", rumbo=" + rumbo + ", velocidad=" + velocidad + ", actualizado=" + actualizado + "]";
	}
	
}
