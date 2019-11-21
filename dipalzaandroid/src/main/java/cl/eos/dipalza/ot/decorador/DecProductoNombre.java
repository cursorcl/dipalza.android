package cl.eos.dipalza.ot.decorador;

import cl.eos.dipalza.ot.OTProducto;

public class DecProductoNombre extends AbstractDecorator<OTProducto> {
	/**
	 * Numero version de la clase.
	 */
	private static final long serialVersionUID = 4190152468066963885L;
	/**
	 * Constante string.
	 */
	private static final String STRING = "--";

	/**
	 * Constructor de la clase.
	 * 
	 * @param objetoProducto
	 *            Objeto producto.
	 */
	public DecProductoNombre(final OTProducto objetoProducto) {
		super(objetoProducto);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final String toString() {
		String retorno = null;
		final OTProducto object = getObject();
		if (object == null) {
			retorno = STRING;
		} else {
			final StringBuffer buffer = new StringBuffer();
			buffer.append(object.getNombre());
			retorno = buffer.toString();
		}
		return retorno;
	}
}
