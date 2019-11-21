package cl.eos.dipalza.ot;

public class OTInformaDatosATransmitir implements IOTObject {
	/**
	 * @uml.property  name="name"
	 */
	private String name;
	/**
	 * @uml.property  name="cantidad"
	 */
	private int cantidad;
	
	

	/**
	 * @param cantidad
	 * @uml.property  name="cantidad"
	 */
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * @return
	 * @uml.property  name="cantidad"
	 */
	public int getCantidad() {
		return cantidad;
	}

	/**
	 * @param name
	 * @uml.property  name="name"
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return
	 * @uml.property  name="name"
	 */
	public String getName() {
		return name;
	}
}
