package cl.eos.dipalza.transmision.eventos.ottransmision;

import java.io.DataInputStream;

import cl.eos.dipalza.ot.IOTObject;
import cl.eos.dipalza.ot.OTProducto;

public class OTTProducto extends AbstractOTTransmision {

	/**
	 * @uml.property  name="object"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private OTProducto object;
	
	/**
	 * Constructor por defecto.
	 */
	public OTTProducto() {
		super();
		object = new OTProducto();
	}
	
	/**
	 * Constructor por defecto.
	 * @param object Objeto de datos.
	 */
	public OTTProducto(final OTProducto object) {
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
