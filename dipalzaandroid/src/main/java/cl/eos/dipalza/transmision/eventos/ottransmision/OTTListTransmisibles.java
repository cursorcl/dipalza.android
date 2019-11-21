package cl.eos.dipalza.transmision.eventos.ottransmision;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import cl.eos.dipalza.ot.IOTObject;

/**
 * Case abstracta que permite mantener los datos.
 * 
 * @author cursor
 * 
 * @param <T>
 *            Generico que extiende de un {@link AbstractOTTransmision}.
 */
public abstract class OTTListTransmisibles<T extends AbstractOTTransmision>
		extends AbstractOTTransmision {

	/**
	 * @uml.property  name="list"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="main.dipalza.transmision.eventos.ottransmision.AbstractOTTransmision"
	 */
	protected List<T> list;

	public OTTListTransmisibles() {
		super();
		list = new LinkedList<T>();
	}

	/**
	 * Agrega un item a la lista de transmision.
	 * 
	 * @param value
	 *            Valor a ser agregado.
	 */
	public void add(T value) {
		list.add(value);
	}

	/**
	 * Elimina un elemento de la lista de transmision.
	 * 
	 * @param value
	 *            Valor a ser eliminado.
	 */
	public void remove(T value) {
		list.remove(value);
	}

	/**
	 * Elimina un elemento.
	 * 
	 * @param index
	 *            Indice del elemento a eliminar.
	 */
	public void remove(int index) {
		list.remove(index);
	}

	/**
	 * Obtiene el tamanno de la lista.
	 * 
	 * @return Tamanno de la lista.
	 */
	public int size() {
		return list.size();
	}

	/**
	 * Obtiene un elemebnto desde una posicoion.
	 * 
	 * @param index
	 *            Posicion del elemento buscado.
	 * @return Objeto en la posicion indicada por index.
	 */
	public T elementAt(int index) {
		return list.get(index);
	}

	public List<T> asVector() {
		return list;
	}

	/**
	 * {@inheritDoc}
	 */
	public void decode(DataInputStream inputStream) {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 */
	public byte[] encode() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream outputStream = new DataOutputStream(baos);
		try {
			outputStream.writeInt(list.size());
			for (T item : list) {
				outputStream.write(item.encode());
			}
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
