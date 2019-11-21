package cl.eos.dipalza;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import cl.eos.dipalza.vistas.impl.IOyente;

/**
 * Clase abstracta que se debe utilizar en todas las actividades que quieran
 * incorporar oyentes.
 * 
 * @author cursor
 * 
 * @param <T>
 *            Clase que implemente a IOyente.
 */
public abstract class ActivityConOyentes<T extends IOyente> extends Activity {
	/**
	 * Lista de oyentes.
	 * @uml.property  name="listOyentes"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="main.dipalza.vistas.impl.IOyente"
	 */
	private List<T> listOyentes;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	/**
	 * Agrega un oyente a la actividad.
	 * 
	 * @param oyente
	 *            Oyente a ser agregado.
	 */
	public void agregarOyente(final T oyente) {
		if (listOyentes == null) {
			listOyentes = new LinkedList<T>();
		}
		listOyentes.add(oyente);
	}
	
	public List<T> obtenerOyentes()
	{
		return listOyentes;
	}
}
