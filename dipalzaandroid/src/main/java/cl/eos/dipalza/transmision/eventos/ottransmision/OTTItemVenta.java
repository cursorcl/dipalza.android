package cl.eos.dipalza.transmision.eventos.ottransmision;

import java.io.DataInputStream;

import cl.eos.dipalza.ot.IOTObject;
import cl.eos.dipalza.ot.OTItemVenta;

public class OTTItemVenta extends AbstractOTTransmision {

	/**
	 * @uml.property  name="object"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private OTItemVenta object;
	
	/**
	 * Constrcutor de la clase.
	 */
	public OTTItemVenta() {
		super();
		object = new OTItemVenta();
	}
	
	/**
	 * Constrcutor de la clase.
	 * @param object Objeto de datos.
	 */
	public OTTItemVenta(final OTItemVenta object) {
		super();
		this.object = object;
	}
	
	public void decode(DataInputStream inputStream) {
		// TODO Auto-generated method stub

	}

	public byte[] encode() {
		// TODO Auto-generated method stub
		return null;
	}

	public IOTObject getObject() {
		return object;
	}

}
