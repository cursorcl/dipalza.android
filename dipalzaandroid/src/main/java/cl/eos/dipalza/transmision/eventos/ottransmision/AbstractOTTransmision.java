package cl.eos.dipalza.transmision.eventos.ottransmision;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

/**
 * Clase abstracata que implementa por defecto el decode.
 * @author cursor
 */
public abstract class AbstractOTTransmision implements IOTTTransmision {

	/**
	 * Numero de serie.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	public void decode(byte[] buffer) {
		ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
		DataInputStream inputStream = new DataInputStream(bais);
		decode(inputStream);		
	}

}
