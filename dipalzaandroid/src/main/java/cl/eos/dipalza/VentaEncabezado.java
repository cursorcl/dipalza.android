package cl.eos.dipalza;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import cl.eos.dipalza.factory.Fabrica;
import cl.eos.dipalza.ot.OTCliente;
import cl.eos.dipalza.ot.OTEVenta;
import cl.eos.dipalza.ot.OTVenta;
import cl.eos.dipalza.ot.ObjetosClase;
import cl.eos.dipalza.ot.decorador.DecClienteNombre;
import cl.eos.dipalza.ot.decorador.DecClienteRut;
import cl.eos.dipalza.utilitarios.EmisorMensajes;

/**
 * Interfaz de ventas, espcificamente el encabezado.
 * 
 * @author sfarias
 * 
 */
public class VentaEncabezado extends DashboardActivity
{
	/**
	 * Constante de cliente, para traspasar informacion a la vista.
	 */
	private static final String CLIENTE = "cliente";
	/**
	 * Constante de ventas, para traspasar informacion de modelo a la vista.
	 */
	private static final String VENTA = "venta";
	/**
	 * Cliente seleccionado.
	 */
	private OTCliente clienteSeleccionado;
	/**
	 * Listra de productos decorados por articulo.
	 */
	private List<DecClienteRut> listaRut;
	/**
	 * Lista de productos por nombre.
	 */
	private List<DecClienteNombre> listaNombres;
	/**
	 * Referencia de venta en proceso.
	 */
	private OTVenta venta;
	/**
	 * Referencia de encabezado venta en proceso.
	 */
	private OTEVenta ventaEncabezado;
	/**
	 * Campo de autocompletado.
	 */
	private AutoCompleteTextView autoCompleteRut;
	/**
	 * Campo de autocompletado.
	 */
	private AutoCompleteTextView autoCompleteCliente;
	/**
	 * Componente grafico.
	 */
	private TextView txtCodigo;
	/**
	 * Componente grafico.
	 */
	private TextView txtDireccion;
	/**
	 * Componente grafico.
	 */
	private TextView txtTelefono;
	/**
	 * Componente grafico.
	 */
	private ImageButton buttonVerCliente;
	/**
	 * Componente grafico.
	 */
	private ImageButton buttonDetalleVenta;
	/**
	 * Componente grafico.
	 */
	private ImageButton buttonTerminarVenta;
	/**
	 * Componente grafico.
	 */
	private ImageButton buttonCancelar;
	/**
	 * Componente grafico.
	 */
	private Spinner spinnerFormaPago;
	/**
	 * Condicion de pago.
	 */
	private int condicionPago;
	/**
	 * Lista de condiciones de pago.
	 */
	private List<ObjetosClase> listaCondicionPago;
	private boolean internal = false;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ventaencabezado);
		setTitleFromActivityLabel(R.id.title_text);
		Bundle bundle = getIntent().getExtras();
		venta = (OTVenta) bundle.get(VENTA);
		inicializarComponentesGraficos();
		inicializarComponente();
		generarDecoradosListaClientes();
		inicializarAutocompletePorNombre();
		inicializarAutocompletePorRut();
		if (venta != null)
		{
			inicializacionModificacionVenta();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);
	}

	/**
	 * Metodo que inicializa componente spinner.
	 */
	private void inicializarComponente()
	{
		spinnerFormaPago = (Spinner) findViewById(R.id.spinnerCondicion);
		spinnerFormaPago.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
		{
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
			{
				Object item = parent.getItemAtPosition(pos);
				if (item instanceof ObjetosClase)
				{
					condicionPago = ((ObjetosClase) item).getId();
				}
			}

			public void onNothingSelected(AdapterView<?> parent)
			{
			}
		});
		// Creamos la lista
		listaCondicionPago = new LinkedList<ObjetosClase>();
		// La poblamos con los ejemplos
		listaCondicionPago.add(new ObjetosClase(1, "CONTADO"));
		listaCondicionPago.add(new ObjetosClase(2, "CREDITO 7"));
		listaCondicionPago.add(new ObjetosClase(3, "CREDITO 15"));
		listaCondicionPago.add(new ObjetosClase(4, "CHEQUE V. 30"));
		listaCondicionPago.add(new ObjetosClase(5, "CHEQUE T. 15"));
		listaCondicionPago.add(new ObjetosClase(6, "CHEQUE T. 20"));
		listaCondicionPago.add(new ObjetosClase(7, "CHEQUE T. 30"));
		// Creamos el adaptador
		ArrayAdapter<ObjetosClase> spinner_adapter = new ArrayAdapter<ObjetosClase>(this,
						android.R.layout.simple_spinner_item, listaCondicionPago);
		// Añadimos el layout para el menú y se lo damos al spinner
		spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerFormaPago.setAdapter(spinner_adapter);
	}

	/**
	 * Metodo que inicializa interfaz de modificacion de venta.
	 */
	private void inicializacionModificacionVenta()
	{
		ventaEncabezado = venta.getEncabezado();
		for (DecClienteRut cliente : listaRut)
		{
			OTCliente otCliente = cliente.getObject();
			if (otCliente.getIdCliente() == venta.getEncabezado().getIdCliente())
			{
				autoCompleteRut.setText(otCliente.getRut());
				txtCodigo.setText(otCliente.getCodigo());
				txtDireccion.setText(otCliente.getDireccion());
				txtTelefono.setText(otCliente.getTelefono());
				clienteSeleccionado = otCliente;
				break;
			}
		}
		autoCompleteCliente.setText(venta.getEncabezado().getCliente());
		int formaVenta = venta.getEncabezado().getCondicionVenta();
		for (int i = 0; i <= listaCondicionPago.size() - 1; i++)
		{
			if (listaCondicionPago.get(i).getId() == formaVenta)
			{
				spinnerFormaPago.setSelection(i);
				break;
			}
		}
		autoCompleteRut.setEnabled(false);
		autoCompleteRut.requestFocus();
		autoCompleteRut.dismissDropDown();
		autoCompleteRut.setDropDownHeight(0);
		autoCompleteCliente.setEnabled(false);
		autoCompleteCliente.requestFocus();
		autoCompleteCliente.dismissDropDown();
		autoCompleteCliente.setDropDownHeight(0);
		txtCodigo.setEnabled(false);
		txtDireccion.setEnabled(false);
		txtTelefono.setEnabled(false);
	}

	/**
	 * Metodo que inicializa componentes graficos de la interfaz.
	 */
	private void inicializarComponentesGraficos()
	{
		autoCompleteRut = (AutoCompleteTextView) findViewById(R.id.autoCompleteTexRut);
		autoCompleteRut.addTextChangedListener(new AbstractTextWatcher()
		{
			public void afterTextChanged(Editable s)
			{
				limpiarControlRut();
			}
		});
		autoCompleteCliente = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextCliente);
		autoCompleteCliente.addTextChangedListener(new AbstractTextWatcher()
		{
			public void afterTextChanged(Editable s)
			{
				limpiarControlCliente();
			}
		});
		txtCodigo = (TextView) findViewById(R.id.textCodigo);
		txtDireccion = (TextView) findViewById(R.id.textDireccion);
		txtTelefono = (TextView) findViewById(R.id.textTelefono);
		buttonVerCliente = (ImageButton) findViewById(R.id.imgBtnVerCliente);
		buttonVerCliente.setOnClickListener(new OnClickListener()
		{
			public void onClick(View view)
			{
				verDetalleCliente();
			}
		});
		buttonDetalleVenta = (ImageButton) findViewById(R.id.imgBtnDetalleVenta);
		buttonDetalleVenta.setOnClickListener(new OnClickListener()
		{
			public void onClick(View view)
			{
				generarDetalleVenta();
			}
		});
		buttonTerminarVenta = (ImageButton) findViewById(R.id.imgBtnTerminarVenta);
		buttonTerminarVenta.setOnClickListener(new OnClickListener()
		{
			public void onClick(View view)
			{
				terminarVenta();
			}
		});
		buttonCancelar = (ImageButton) findViewById(R.id.imgBtnCancelarVenta);
		buttonCancelar.setOnClickListener(new OnClickListener()
		{
			public void onClick(View view)
			{
				cancelarVenta();
			}
		});
	}

	protected void limpiarControlCliente()
	{
		if (autoCompleteCliente.length() == 0 && !internal)
		{
			clienteSeleccionado = null;
			txtCodigo.setText(null);
			txtDireccion.setText(null);
			txtTelefono.setText(null);
			spinnerFormaPago.setSelection(0);
			internal = true;
			autoCompleteRut.setText("");
			internal = false;
		}
	}

	protected void limpiarControlRut()
	{
		if (autoCompleteRut.length() == 0  && !internal)
		{
			clienteSeleccionado = null;
			txtCodigo.setText(null);
			txtDireccion.setText(null);
			txtTelefono.setText(null);
			spinnerFormaPago.setSelection(0);
			internal = true;
			autoCompleteCliente.setText("");
			internal = false;
		}
	}

	/**
	 * Metodo que genera decoradores de la lista de clientes.
	 */
	private void generarDecoradosListaClientes()
	{
		List<OTCliente> listaClientes = Fabrica.obtenerInstancia().obtenerModeloDipalza().obtenerClientes();
		listaRut = new LinkedList<DecClienteRut>();
		listaNombres = new LinkedList<DecClienteNombre>();
		for (OTCliente elemento : listaClientes)
		{
			DecClienteRut clienteRut = new DecClienteRut(elemento);
			DecClienteNombre clienteNombre = new DecClienteNombre(elemento);
			listaRut.add(clienteRut);
			listaNombres.add(clienteNombre);
		}
	}

	/**
	 * Metodo que inicializa componente grafico de autocomplete de rut.
	 */
	private void inicializarAutocompletePorRut()
	{
		autoCompleteRut.setAdapter(new ArrayAdapter<DecClienteRut>(this, android.R.layout.simple_dropdown_item_1line,
						listaRut));
		autoCompleteRut.setOnItemClickListener(new OnItemClickListener()
		{
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
			{
				clienteSeleccionado = ((DecClienteRut) arg0.getAdapter().getItem(position)).getObject();
				autoCompleteRut.setText(clienteSeleccionado.getRut());
				autoCompleteCliente = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextCliente);
				autoCompleteCliente.setText(clienteSeleccionado.getNombre());
				completarDatosClienteSeleccionado();
			}
		});
	}

	/**
	 * Metodo que inicializa componente grafico de autocomplete de nombre.
	 */
	private void inicializarAutocompletePorNombre()
	{
		autoCompleteCliente.setAdapter(new ArrayAdapter<DecClienteNombre>(this,
						android.R.layout.simple_dropdown_item_1line, listaNombres));
		autoCompleteCliente.setOnItemClickListener(new OnItemClickListener()
		{
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
			{
				clienteSeleccionado = ((DecClienteNombre) arg0.getAdapter().getItem(position)).getObject();
				autoCompleteCliente.setText(clienteSeleccionado.getNombre());
				AutoCompleteTextView autoCompleteRut = (AutoCompleteTextView) findViewById(R.id.autoCompleteTexRut);
				autoCompleteRut.setText(clienteSeleccionado.getRut());
				completarDatosClienteSeleccionado();
			}
		});
	}

	/**
	 * Metodo que completa datos de clientes despues de haber seleccionado el
	 * cliente en los campos de autocomplete.
	 */
	private void completarDatosClienteSeleccionado()
	{
		txtCodigo.setText(clienteSeleccionado.getCodigo());
		txtDireccion.setText(clienteSeleccionado.getDireccion());
		txtTelefono.setText(clienteSeleccionado.getTelefono());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menuencabezado, menu);
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		case R.id.itemVerCliente:
			verDetalleCliente();
			return true;
		case R.id.itemDetalleVenta:
			generarDetalleVenta();
			return true;
		case R.id.itemTerminarVenta:
			terminarVenta();
			return true;
		case R.id.itemCancelarVenta:
			cancelarVenta();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * Metodo que cancela venta.
	 */
	private void cancelarVenta()
	{
		finish();
	}

	/**
	 * Metodo que termina el proceso de venta.
	 */
	private void terminarVenta()
	{
		if (clienteSeleccionado == null)
		{
			EmisorMensajes.mostrarMensaje(this, "Cliente", "Debe ingresar cliente a la venta");
		}
		else
		{
			generarOTVenta();
			Intent resultIntent;
			resultIntent = new Intent();
			resultIntent.putExtra(VENTA, venta);
			setResult(Activity.RESULT_OK, resultIntent);
			finish();
		}
	}

	/**
	 * Metodo que permite visualizar detalle del cliente en proceso.
	 */
	private void verDetalleCliente()
	{
		if (clienteSeleccionado == null)
		{
			EmisorMensajes.mostrarMensaje(this, "Cliente", "Debe ingresar cliente a la venta");
		}
		else
		{
			Intent intent = new Intent(this, Clientes.class);
			intent.putExtra(CLIENTE, clienteSeleccionado);
			this.startActivity(intent);
		}
	}

	/**
	 * Metodo que permite visualizar interfaz de venta de productos.
	 */
	private void generarDetalleVenta()
	{
		if (clienteSeleccionado == null)
		{
			EmisorMensajes.mostrarMensaje(this, "Cliente", "Debe ingresar cliente a la venta");
		}
		else
		{
			generarOTVenta();
			Intent intent = new Intent(this, VentaDetalle.class);
			intent.putExtra(VENTA, venta);
			this.startActivityForResult(intent, 1);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode)
		{
		case (1):
		{
			if (resultCode == Activity.RESULT_OK)
			{
				Bundle bundle = data.getExtras();
				venta = (OTVenta) bundle.get(VENTA);
				if (clienteSeleccionado != null)
				{
					Intent resultIntent;
					resultIntent = new Intent();
					resultIntent.putExtra(VENTA, venta);
					setResult(Activity.RESULT_OK, resultIntent);
					finish();
				}
			}
			break;
		}
		}
	}

	/**
	 * Metodo que genera objeto OTVenta.
	 */
	private void generarOTVenta()
	{
		if (ventaEncabezado == null)
		{
			ventaEncabezado = new OTEVenta();
		}
		if (clienteSeleccionado != null)
		{
			ventaEncabezado.setIdCliente(clienteSeleccionado.getIdCliente());
			ventaEncabezado.setCliente(clienteSeleccionado.getNombre());
			ventaEncabezado.setFecha(new Date(System.currentTimeMillis()));
			SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(this);
			ventaEncabezado.setVendedor(shared.getString("pref_vendedor", "Vendedor"));
			ventaEncabezado.setCondicionVenta(condicionPago);
		}
		if (venta == null)
		{
			venta = new OTVenta();
		}
		venta.setEncabezado(ventaEncabezado);
	}
}
