package cl.eos.dipalza;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.TableLayout;
import cl.eos.dipalza.factory.Fabrica;
import cl.eos.dipalza.ot.OTRegistroVenta;
import cl.eos.dipalza.ot.OTVenta;
import cl.eos.dipalza.utilitarios.EmisorMensajes;

/**
 * Interfaz de ingreso, modificacion y eliminacion de venta.
 * 
 * @author sfarias
 * 
 */
public class VentaRegistros extends DashboardActivity implements
		OnCheckedChangeListener {

	/**
	 * Constante de ventas, para traspasar informacion de modelo a la vista.
	 */
	public static final String VENTA = "venta";
	/**
	 * Componente grafico.
	 */
	private TableLayout table;
	/**
	 * Fila de venta seleccionada.
	 */
	private RowVenta rowSeleccionada;
	/**
	 * Objeto de transferencia de la venta.
	 */
	private OTVenta venta;
	/**
	 * Componente grafico.
	 */
	private ImageButton buttonAgregar;	
	/**
	 * Componente grafico.
	 */
	private ImageButton buttonEliminar;
	/**
	 * Componente grafico.
	 */
	private ImageButton buttonCancelar;
	/**
	 * Objeto de transferencia.
	 */
	private OTRegistroVenta registroVenta;
	/**
	 * Metodo que indica registros seleccionados.
	 */
	private int contadorSeleccionados;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ventaregistros);
		setTitleFromActivityLabel(R.id.title_text);
		inicializarComponentesGraficos();
		actualizarRegistroVenta();
	}

	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	    super.onConfigurationChanged(newConfig);
	}

	
	/**
	 * Inicializa componente grafico.
	 */
	private void inicializarComponentesGraficos() {
		table = (TableLayout) findViewById(R.id.tableLayoutVenta);
		table.setFocusable(true);
		table.setFocusableInTouchMode(true);
		buttonAgregar = (ImageButton) findViewById(R.id.imgBtnAgregarVenta);
		buttonAgregar.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				agregarModificarVenta(null);
			}
		});		
		buttonEliminar = (ImageButton) findViewById(R.id.imgBtnEliminarVenta);
		buttonEliminar.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				eliminarVenta();
			}
		});

		buttonCancelar = (ImageButton) findViewById(R.id.imageBtnCancelar);
		buttonCancelar.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				cancelarVenta();
			}
		});

	}

	/**
	 * Cancela la venta activa.
	 */
	protected void cancelarVenta() {
		finish();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menuventas, menu);
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.itemAgregarVenta:
			agregarModificarVenta(null);
			return true;		
		case R.id.itemEliminarVentas:
			eliminarVenta();
			return true;
		case R.id.itemCancelarVenta:
			cancelarVenta();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * Metodo que elimina registro de ventas.
	 */
	private void eliminarVenta() {
		if (contadorSeleccionados > 0) {
			final AlertDialog.Builder alertaCompuesta = new AlertDialog.Builder(
					this);
			alertaCompuesta.setTitle("Eliminar venta");
			if (contadorSeleccionados == 1) {
				alertaCompuesta
						.setMessage("Esta seguro que desea eliminar el registro de venta seleccionado.");
			} else {
				alertaCompuesta
						.setMessage("Esta seguro que desea eliminar los registros de venta seleccionados.");
			}
			alertaCompuesta.setPositiveButton("Aceptar",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							ejecutarOnclickAceptar();
						}
					});
			alertaCompuesta.setNegativeButton("Cancelar", null);
			alertaCompuesta.show();
		} else {
			EmisorMensajes.mostrarMensaje(this, "Eliminar venta",
					"Debe seleccionar venta a eliminar.");
		}
	}

	/**
	 * Metodo que ejcuat codigo de eliminacion de registro.
	 */
	private void ejecutarOnclickAceptar() {
		for (int i = table.getChildCount() - 1; i >= 0; i--) {
			RowVenta row = (RowVenta) table.getChildAt(i);
			if (row.getChecBox().isChecked()) {
				Fabrica.obtenerInstancia()
						.obtenerModeloDipalza()
						.borrarVenta(
								row.getVenta().getVenta().getEncabezado()
										.getIdVenta());
			}
		}
		actualizarRegistroVenta();
		if (contadorSeleccionados == 1) {
			EmisorMensajes.notificarInformacion(VentaRegistros.this,
					"Registro eliminado");
		} else {
			EmisorMensajes.notificarInformacion(VentaRegistros.this,
					"Registros eliminados");
		}
		contadorSeleccionados = 0;
	}

	/**
	 * Metodo que obtiene registro de venta a modificar y lo traspasa a interfaz
	 * de modificacion.
	 */
	private void modificarVenta() {
		if (rowSeleccionada != null) {
			registroVenta = rowSeleccionada.getVenta();
			venta = registroVenta.getVenta();
			if (venta != null) {
				agregarModificarVenta(venta);
			}
		} else {
			EmisorMensajes.mostrarMensaje(this, "Modificar venta",
					"Debe seleccionar venta a modificar.");
		}
	}

	/**
	 * Metodo que agrega una venta.
	 */
	private void agregarModificarVenta(OTVenta venta) {
		Intent intent = new Intent(this, VentaEncabezado.class);
		intent.putExtra(VENTA, venta);
		this.startActivityForResult(intent, 1);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case (1): {
			if (resultCode == Activity.RESULT_OK) {
				Bundle bundle = data.getExtras();
				venta = (OTVenta) bundle.get(VENTA);
				Fabrica.obtenerInstancia().obtenerModeloDipalza()
						.grabarVenta(venta);
				actualizarRegistroVenta();
			}
			break;
		}
		}
		rowSeleccionada = null;
	}

	/**
	 * Metodo que agrega registros de venta.
	 */
	private void actualizarRegistroVenta() {
		List<OTVenta> listadoVentas = Fabrica.obtenerInstancia()
				.obtenerModeloDipalza().obtenerListadoVentas();
		table.removeAllViews();
		for (OTVenta otVenta : listadoVentas) {
			RowVenta registro = new RowVenta(this);
			registro.setClickable(true);
			registroVenta = new OTRegistroVenta();
			registroVenta.setVenta(otVenta);
			registro.setVenta(registroVenta);
			table.addView(registro, new TableLayout.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		}
	}
	
	public void onClick(View vista) {
		if (vista.getParent() instanceof RowVenta) {
			rowSeleccionada = (RowVenta) vista.getParent();
			modificarVenta();
		}
	}
	/**
	 * Metodo que permite saber si exiten registros seleccionados.
	 */
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked) {
			contadorSeleccionados++;
		} else {
			contadorSeleccionados--;
		}
	}
}
