package cl.eos.dipalza.transmision.eventos.ottransmision;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.LinkedList;

public class OTTListaDeVenta extends OTTListTransmisibles<OTTVenta> {

	@Override
	public void decode(DataInputStream inputStream) {
		try {
			int size = inputStream.readInt();
			list = new LinkedList<OTTVenta>();
			for (int n = 0; n < size; ++n) {
				OTTVenta venta = new OTTVenta();
				venta.decode(inputStream);
				list.add(venta);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
