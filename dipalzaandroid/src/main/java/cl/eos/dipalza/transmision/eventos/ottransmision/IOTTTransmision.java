package cl.eos.dipalza.transmision.eventos.ottransmision;

import java.io.DataInputStream;
import java.io.Serializable;

import cl.eos.dipalza.ot.IOTObject;

/**
 * Objetos de transmision. Mantienen compatibilidad con sistema antiguo.
 * @author  cursor
 */
public interface IOTTTransmision extends Serializable{

	/**
	 * Decodifica desde un buffer.
	 * @param buffer Arreglo de byte que contienen la informacion del objeto.
	 */
	void decode(byte[] buffer);
	 
	/**
	 * Decodifica desde un input stream.
	 * @param inputStream
	 */
	void decode(DataInputStream inputStream);
	
	/**
	 * Codifica el objeto.
	 * @return Arreglo de bytes resultante de la codificacion.
	 */
	byte[] encode();
	
	/**
	 * Obtiene el objeto de datos.
	 * @return   Objeto de datos base.
	 * @uml.property  name="object"
	 * @uml.associationEnd  
	 */
	IOTObject getObject();
}
