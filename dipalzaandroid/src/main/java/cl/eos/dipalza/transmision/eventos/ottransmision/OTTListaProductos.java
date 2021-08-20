package cl.eos.dipalza.transmision.eventos.ottransmision;

import android.util.Log;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class OTTListaProductos extends OTTListTransmisibles<OTTProducto> {

	/**
	 * Nombre de la clase.
	 */
	private static final String TAG = "OTTListaProductos";

	@Override
	public void decode(DataInputStream inputStream) {
		try {
			int size = inputStream.readInt();
			list = new ArrayList<OTTProducto>();

			for (int n = 0; n < size; ++n) {
				OTTProducto producto = new OTTProducto();
				producto.decode(inputStream);
				list.add(producto);
			}
		} catch (IOException ex) {
			Log.e(TAG, ex.getLocalizedMessage());
		}

	}

}
