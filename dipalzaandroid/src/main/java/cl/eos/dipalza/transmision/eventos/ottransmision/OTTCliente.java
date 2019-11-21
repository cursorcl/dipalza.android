package cl.eos.dipalza.transmision.eventos.ottransmision;

import java.io.DataInputStream;

import cl.eos.dipalza.ot.IOTObject;
import cl.eos.dipalza.ot.OTCliente;

public class OTTCliente extends AbstractOTTransmision {

	/**
	 * Objeto de datos.
	 * @uml.property  name="object"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private OTCliente object;
	
	/**
	 * Constructor por defecto.
	 */
	public OTTCliente() {
		super();
		object = new OTCliente();
	}
	
	/**
	 * Constructor de la clase.
	 * @param object Objeto cliente.
	 */
	public OTTCliente(final OTCliente object) {
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
