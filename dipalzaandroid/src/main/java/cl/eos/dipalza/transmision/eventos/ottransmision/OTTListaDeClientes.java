package cl.eos.dipalza.transmision.eventos.ottransmision;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.LinkedList;

import android.util.Log;

public class OTTListaDeClientes extends OTTListTransmisibles<OTTCliente> {

	/**
	 * Nombre de la clase.
	 */
	private static final String TAG = "ListaDeClientes";
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void decode(DataInputStream inputStream) {
		try {
			int size = inputStream.readInt();
			list = new LinkedList<OTTCliente>();
			for (int n = 0; n < size; ++n) {
				OTTCliente cliente = new OTTCliente();
				cliente.decode(inputStream);
				list.add(cliente);
			}
		} catch (IOException ex) {
			Log.e(TAG, ex.getLocalizedMessage());
		}

	}

}
