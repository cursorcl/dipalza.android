package cl.eos.dipalza.transmision.eventos.ottransmision;

import java.io.DataInputStream;

import cl.eos.dipalza.ot.IOTObject;
import cl.eos.dipalza.ot.OTEVenta;

public class OTTEVenta extends AbstractOTTransmision {

	/**
	 * Objeto que contiene la informacion de encabezado de venta.
	 * @uml.property  name="object"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private OTEVenta object;
	
	/**
	 * Constructor de la clase.
	 */
	public OTTEVenta() {
		super();
		object = new OTEVenta();
	}	
	
	/**
	 * Constructor de la clase.
	 * @param encabezadoVenta
	 */
	public OTTEVenta(OTEVenta encabezadoVenta) {
		super();
		object =  encabezadoVenta;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void decode(DataInputStream inputStream) {
		
	}

	/**
	 * {@inheritDoc}
	 */
	public byte[] encode() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public IOTObject getObject() {
		return object;
	}

}
