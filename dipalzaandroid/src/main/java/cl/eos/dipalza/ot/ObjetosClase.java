package cl.eos.dipalza.ot;

public class ObjetosClase {
	int id;
	String nombre;
	//Constructor
	public ObjetosClase(int id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}
	@Override
	public String toString() {
		return nombre;
	}
	public int getId() {
		return id;
	}
}
