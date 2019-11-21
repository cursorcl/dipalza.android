package cl.eos.dipalza.transmision.eventos.ottransmision;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import cl.eos.dipalza.ot.IOTObject;
import cl.eos.dipalza.ot.OTInformaDatosATransmitir;

public class OTTInformaDatoATransmitir extends AbstractOTTransmision {

	public static final String CLIENTE = "com.grupo.basedatos.Cliente";
	public static final String PRODUCTO = "com.grupo.basedatos.Producto";
	/**
	 * @uml.property  name="object"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private OTInformaDatosATransmitir object;

	/**
	 * Constructor por defecto.
	 */
	public OTTInformaDatoATransmitir() {
		super();
		object = new OTInformaDatosATransmitir();
	}

	/**
	 * Constructor de la clase.
	 * @param object Objeto de datos.
	 */
	public OTTInformaDatoATransmitir(final OTInformaDatosATransmitir object) {
		super();
		this.object = object;
	}

	/**
	 * {@inheritDoc}
	 */
	public void decode(DataInputStream inputStream) {
		try {
			object.setName(inputStream.readUTF());
			object.setCantidad(inputStream.readInt());
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
			outputStream.writeUTF(object.getName());
			outputStream.writeInt(object.getCantidad());
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
		return object;
	}

}
