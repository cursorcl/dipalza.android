package cl.eos.dipalza.transmision.eventos.ottransmision;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import android.util.Log;
import cl.eos.dipalza.ot.IOTObject;
import cl.eos.dipalza.ot.OTInicializacion;

public class OTTInicializacion extends AbstractOTTransmision implements IOTTTransmision {

	/**
	 * Nombre de la clase.
	 */
	private static final String TAG = "OTTInicializacion";
	/**
	 * Objeto que contiene la informacion.
	 * @uml.property  name="object"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private OTInicializacion object;

	/**
	 * Constructor por defecto.
	 */
	public OTTInicializacion() {
		super();
		object = new OTInicializacion();
	}
	
	public OTTInicializacion(OTInicializacion object) {
		super();
		this.object = object;
	}
	
	
	public void decode(DataInputStream inputStream) {
	    try {
	    	object.setVendedor(inputStream.readUTF());
	    	object.setRuta1(inputStream.readUTF());
	    	object.setRuta2(inputStream.readUTF());
	      } catch (IOException ex) {
	        Log.e(TAG, ex.getMessage());
	      }
	}

	public byte[] encode() {
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    DataOutputStream outputStream = new DataOutputStream(baos);
	    try {
	      outputStream.writeUTF(object.getVendedor());
	      outputStream.writeUTF(object.getRuta1());
	      outputStream.writeUTF(object.getRuta2());
	    } catch (IOException ioe) {
	    	Log.e(TAG, ioe.getMessage());
	    }
	    byte[] buffer = baos.toByteArray();
	    return buffer;
	}

	public IOTObject getObject() {
		return object;
	}

}
