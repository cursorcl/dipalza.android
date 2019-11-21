package cl.eos.dipalza.listener;

import java.util.EventObject;


/**
 * Quien quiera escuchar informacion desde el procesador debe implementar esta interface.
 * @author cursor
 */
public interface ProcessListenerAndroid
{
	
	void procesa(EventObject eventObject);
	/**
	 * Informacion del onjeto que es procesado.
	 * @param objetoProcesado Objeto procesado.
	 */
	void onProgress(Object objetoProcesado);
	/**
	 * Mensaje de error generado en el procesamiento.
	 * @param exception Excepcion si es que corresponde.
	 * @param mensaje Mensaje de error propio.
	 */
	void onError(Exception exception, String mensaje);
}
