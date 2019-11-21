package cl.eos.dipalza.listener;

import com.grupo.biblioteca.server.events.Notificable;

/**
 * Agrega un par de metodos a {@link Notificable} para obtener resultados.
 * @author cursor
 *
 */
public interface ConectionListenerAndroid extends Notificable
{
	/**
	 * Mensaje de error generado por falla en la conexion.
	 * @param exception Excepcion si es que corresponde.
	 * @param mensaje Mensaje de error propio.
	 */
	void onFail(Exception exception, String mensaje);
	
	/**
	 * Mensaje generado cuando se produce una desconexion.
	 * @param exception Excepcion si es que corresponde.
	 * @param mensaje Mensaje de error propio.
	 */
	void onDisconnect(Exception exception, String mensaje);
	
	/**
	 * Mensaje generado cuando hay conexion exitosa.
	 * @param mensaje Mensaje emitido.
	 */
	void onSucceful(String mensaje);
}
