package cl.eos.dipalza.transmision.eventos.ottransmision;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import cl.eos.dipalza.ot.IOTObject;

/**
 * Numero de itemes de venta a transmitir.
 * @author cursor
 */
public class OTTNumeroItemesVenta extends AbstractOTTransmision {

	/**
	 * Numero itemes de venta.
	 * @uml.property  name="nroItemesVenta"
	 */
	private int nroItemesVenta;

	/**
	 * Constructor por defecto.
	 */
	public OTTNumeroItemesVenta() {
		this.nroItemesVenta = 0;
	}

	/**
	 * Constructor de la clase.
	 * @param nroItemesVenta Numero de itemes de venta a establecer.
	 */
	public OTTNumeroItemesVenta(int nroItemesVenta) {
		this.nroItemesVenta = nroItemesVenta;
	}

	/**
	 * Obtiene el numero de itemes de venta.
	 * @return  Entero con el valor del numero de itemes.
	 * @uml.property  name="nroItemesVenta"
	 */
	public int getNroItemesVenta() {
		return this.nroItemesVenta;
	}

	/**
	 * Establece el numero de itemes.
	 * @param nroItemesVenta  Numero de itemes a establecer.
	 * @uml.property  name="nroItemesVenta"
	 */
	public void setNroItemesVenta(int nroItemesVenta) {
		this.nroItemesVenta = nroItemesVenta;
	}

	/**
	 * {@inheritDoc}
	 */
	public void decode(final DataInputStream inputStream) {
		try {
			setNroItemesVenta(inputStream.readShort());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public byte[] encode() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream outputStream = new DataOutputStream(baos);
		try {
			outputStream.writeShort(getNroItemesVenta());
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		byte[] buffer = baos.toByteArray();
		return buffer;
	}

	/**
	 * {@inheritDoc}
	 */
	public IOTObject getObject() {
		return null;
	}

}