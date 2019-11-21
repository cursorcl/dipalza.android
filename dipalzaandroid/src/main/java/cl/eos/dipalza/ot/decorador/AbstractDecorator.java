package cl.eos.dipalza.ot.decorador;

import java.io.Serializable;

/**
 * Clase decorado de objetos de transferencia.
 * @param <T>
 *            Clase decoradora
 */
public abstract class AbstractDecorator<T> implements Serializable
{
    /**
     * Numero serial de la clase.
     */
    private static final long serialVersionUID = 836800759229303213L;
    /**
	 * Se define el objeto a decorar.
	 * @uml.property  name="objeto"
	 */
    private final T objeto;

    /**
     * Constrcutor de la clase.
     * @param objeto
     *            Objeto a decorar.
     */
    public AbstractDecorator(final T objeto)
    {
        this.objeto = objeto;
    }

    /**
     * Obtiene el objeto decorado.
     * @return Objeto decorado.
     */
    public final T getObject()
    {
        return this.objeto;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract String toString();

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        return this.objeto.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object object)
    {
        boolean igual;
        if ((object != null) && (object instanceof AbstractDecorator<?>))
        {
            igual = this.objeto.equals(((AbstractDecorator<?>) object).getObject());
        }
        else if ((object != null) && (getObject() != null) && object.getClass().equals(getObject().getClass()))
        {
            igual = this.objeto.equals(object);
        }
        else
        {
            igual = this.objeto.equals(object);
        }
        return igual;
    }

}
