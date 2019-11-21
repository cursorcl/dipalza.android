package cl.eos.dipalza;

import java.util.LinkedList;
import java.util.List;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import cl.eos.dipalza.vistas.impl.IOyente;

/**
 * Class description
 * 
 * 
 * @version Enter version here..., 22.mayo 2012
 * @author Enter your name here...
 */
public class ActivityConfiguracion extends PreferenceActivity implements
				 OnSharedPreferenceChangeListener
{
	public final static String PREF_IP = "pref_ip";
	public final static String PREF_VENDEDOR = "pref_vendedor";
	public final static String PREF_RUTA = "pref_ruta";
	public final static String PREF_RUTA_ADICIONAL = "pref_ruta_adicional";
	public final static String PREF_IVA = "pref_iva";
	public final static String PREF_ILA = "pref_ila";
	public final static String FECHA_TRANSMISION = "fecha_transmision";
	public final static String FECHA_RECEPCION = "fecha_recepcion";

	/**
	 * Lista de oyentes.
	 * 
	 * @uml.property name="listOyentes"
	 * @uml.associationEnd multiplicity="(0 -1)"
	 *                     elementType="main.dipalza.vistas.impl.IOyente"
	 */
	private List<IOyente> listOyentes;
	/**
	 * Quien va a realizar el procesamiento de la informacion.
	 * 
	 * @uml.property name="processor"
	 * @uml.associationEnd
	 */
	private EditTextPreference vendedor;
	private EditTextPreference ruta;
	private EditTextPreference rutaAdicional;
	private EditTextPreference iva;
	private EditTextPreference ila;
	private EditTextPreference ip;

	/**
	 * Agrega un oyente a la actividad.
	 * 
	 * @param oyente
	 *            Oyente a ser agregado.
	 */
	public void agregarOyente(final IOyente oyente)
	{
		if (listOyentes == null)
		{
			listOyentes = new LinkedList<IOyente>();
		}
		listOyentes.add(oyente);
	}

	/**
	 * Method description
	 * 
	 * 
	 * @return
	 */
	public List<IOyente> obtenerOyentes()
	{
		return listOyentes;
	}

	/**
	 * Method description
	 * 
	 * 
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.configuracion);
		vendedor = (EditTextPreference) findPreference(PREF_VENDEDOR);
		
		ruta = (EditTextPreference) findPreference(PREF_RUTA);
		
		rutaAdicional = (EditTextPreference) findPreference(PREF_RUTA_ADICIONAL);
		iva = (EditTextPreference) findPreference(PREF_IVA);
		ila = (EditTextPreference) findPreference(PREF_ILA);
		ip = (EditTextPreference) findPreference(PREF_IP);
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		vendedor.setSummary(getString(R.string.config_vendedor) + preferences.getString(PREF_VENDEDOR, ""));
		ruta.setSummary(getString(R.string.config_ruta) + preferences.getString(PREF_RUTA, ""));
		rutaAdicional.setSummary(getString(R.string.config_ruta) + preferences.getString(PREF_RUTA_ADICIONAL, ""));
		iva.setSummary(getString(R.string.config_iva) + preferences.getString(PREF_IVA, "") + "%");
		ila.setSummary(getString(R.string.config_ila) + preferences.getString(PREF_ILA, "") + "%");
		ip.setSummary(getString(R.string.config_direccion) + preferences.getString(PREF_IP, ""));
		// Set up a listener whenever a key changes
		getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	}


	/************** Shared preferences ****************/
	public void onSharedPreferenceChanged(SharedPreferences preferences, String key)
	{
		vendedor.setSummary(getString(R.string.config_vendedor) + preferences.getString(PREF_VENDEDOR, ""));
		ruta.setSummary(getString(R.string.config_ruta) + preferences.getString(PREF_RUTA, ""));
		rutaAdicional.setSummary(getString(R.string.config_ruta) + preferences.getString(PREF_RUTA_ADICIONAL, ""));
		iva.setSummary(getString(R.string.config_iva) + preferences.getString(PREF_IVA, "") + "%");
		ila.setSummary(getString(R.string.config_ila) + preferences.getString(PREF_ILA, "") + "%");
		ip.setSummary(getString(R.string.config_direccion) + preferences.getString(PREF_IP, ""));
	}
}
// ~ Formatted by Jindent --- http://www.jindent.com
