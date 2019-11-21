package cl.eos.dipalza.ot.decorador;

import cl.eos.dipalza.ot.OTCliente;

/**
 * Clase decoradora del objeto OTProducto.
 * @author sfarias
 */
public class DecClienteRut extends AbstractDecorator<OTCliente> {
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
	 * @param objetoCliente
	 *            Objeto producto.
	 */
	public DecClienteRut(final OTCliente objetoCliente) {
		super(objetoCliente);
	}

	/**
	 * {@inheritDoc}
	 */
    @Override
    public final String toString()
    {
        String retorno = null;
        final OTCliente object = getObject();
        if (object == null)
        {
            retorno = STRING;
        }
        else
        {
            final StringBuffer buffer = new StringBuffer();
            buffer.append(object.getRut());            
            retorno = buffer.toString();
        }
        return retorno;
    } 
}
