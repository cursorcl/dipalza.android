package cl.eos.dipalza.rest.parameters;

public class PositionData {
	private int vendedor_id;
	private double latitud;
	private double longitud;
	private double rumbo;
	private double velocidad;
	
	
	public PositionData(int vendedor_id, double latitud, double longitud, double rumbo, double velocidad) {
		super();
		this.vendedor_id = vendedor_id;
		this.latitud = latitud;
		this.longitud = longitud;
		this.rumbo = rumbo;
		this.velocidad = velocidad;
	}
	
	
}
