package cl.eos.dipalza;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import android.widget.TextView;
import cl.eos.dipalza.factory.Fabrica;
import cl.eos.dipalza.ot.OTEVenta;
import cl.eos.dipalza.ot.OTItemVenta;
import cl.eos.dipalza.ot.OTProductoVenta;
import cl.eos.dipalza.ot.OTVenta;
import cl.eos.dipalza.utilitarios.EmisorMensajes;

/**
 * @author sfarias
 * 
 */
public class VentaDetalle extends DashboardActivity implements OnCheckedChangeListener
{
	/**
	 * Constante de cliente
	 */
	private static final String PRODUCTO_SELECCIONADO = "productoSeleccionado";
	/**
	 * Constante de producto agregado a la venta.
	 */
	private static final String PRODUCTO_VENTA = "productoVenta";
	/**
	 * Constante de venta.
	 */
	private static final String VENTA = "venta";
	/**
	 * Componente grafico.
	 */
	private TableLayout table;
	/**
	 * Componente grafico.
	 */
	private TextView textNeto;
	/**
	 * Componente grafico.
	 */
	private TextView textIVA;
	/**
	 * Componente grafico.
	 */
	private TextView textILA;
	/**
	 * Componente grafico.
	 */
	private TextView textBruto;
	/**
	 * Componente grafico.
	 */
	private RowDetalleVenta rowSeleccionada;
	/**
	 * Referencia de cliente en proceso de venta.
	 */
	private OTVenta venta;
	/**
	 * Referencia de cliente en proceso de venta.
	 */
	private OTEVenta ventaEncabezado;
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
	private ImageButton buttonFinalizar;
	/**
	 * Metodo que indica registros seleccionados.
	 */
	private int contadorSeleccionados;
	/**
	 * Componente grafico.
	 */
	private ImageButton buttonCancelar;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ventadetalle);
		setTitleFromActivityLabel(R.id.title_text);
		Bundle bundle = getIntent().getExtras();
		venta = (OTVenta) bundle.get(VENTA);
		inicializarComponentesGraficos();
		if (venta != null && venta.getEncabezado().getIdVenta() != 0)
		{
			actualizaRegistroProducto();
		}
	}

	/**
	 * Metodo mque inicializa componentes graficos de la interfaz.
	 */
	private void inicializarComponentesGraficos()
	{
		table = (TableLayout) findViewById(R.id.tableLayoutProducto);
		table.setFocusable(true);
		table.setFocusableInTouchMode(true);
		textNeto = (TextView) findViewById(R.id.textNeto);
		textIVA = (TextView) findViewById(R.id.textIVA);
		textILA = (TextView) findViewById(R.id.textILA);
		textBruto = (TextView) findViewById(R.id.textBruto);
		buttonAgregar = (ImageButton) findViewById(R.id.imgBtnAgregarProducto);
		buttonAgregar.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				agregarModificarProducto(null);
			}
		});
		buttonEliminar = (ImageButton) findViewById(R.id.imgBtnEliminarProducto);
		buttonEliminar.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				eliminarProducto();
			}
		});
		buttonFinalizar = (ImageButton) findViewById(R.id.imgBtnFinalizarProducto);
		buttonFinalizar.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				finalizar();
			}
		});
		buttonCancelar = (ImageButton) findViewById(R.id.imgBtnCancelarProducto);
		buttonCancelar.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				cancelar();
			}
		});
	}

	/**
	 * Metodo que cacnela proceso deventa de productos.
	 */
	protected void cancelar()
	{
		finish();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menudetalle, menu);
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
		case R.id.itemAgregarProducto:
			agregarModificarProducto(null);
			return true;
		case R.id.itemEliminarProducto:
			eliminarProducto();
			return true;
		case R.id.itemFinalizar:
			finalizar();
			return true;
		case R.id.itemCancelar:
			cancelar();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * Metodo que finaliza el ingreso de productos a la venta en proceso.
	 */
	private void finalizar()
	{
		if (table.getChildCount() == 0)
		{
			EmisorMensajes.mostrarMensaje(this, "Finalizar Venta",
							"No se han ingresado registros a la venta. No se puede finalizar el proceso.");
		}
		else
		{
			double valorNeto = 0;
			//double valorBruto = 0;
			double totalIVA = 0;
			double totalILA = 0;
			SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(this);
			double valorIVA = Double.valueOf(shared.getString("pref_iva", "19")) / 100d;
			for (int i = 0; i < table.getChildCount(); i++)
			{
				RowDetalleVenta row = (RowDetalleVenta) table.getChildAt(i);
				OTProductoVenta producto = row.getProducto();
				double valorILA = producto.getProducto().getIla() / 100d;
				producto.setIla(producto.getValorNeto() * valorILA);
				valorNeto = valorNeto + producto.getValorNeto();
				totalIVA = totalIVA + (producto.getValorNeto() * valorIVA);
				totalILA = totalILA + (producto.getIla());
			//	valorBruto = valorNeto + totalIVA + totalILA;
			}
			
			ventaEncabezado = venta.getEncabezado();
			ventaEncabezado.setIla(totalILA);
			ventaEncabezado.setIva(totalIVA);
			ventaEncabezado.setNeto(valorNeto);
			obtenerRegistrosProductos();
			Intent resultIntent;
			resultIntent = new Intent();
			resultIntent.putExtra(VENTA, venta);
			setResult(Activity.RESULT_OK, resultIntent);
			finish();
		}
	}

	/**
	 * Metodo que recorre tabla de productos y lo agrega al objeto OTVenta.
	 */
	private void obtenerRegistrosProductos()
	{
		List<OTItemVenta> registrosVenta = new LinkedList<OTItemVenta>();
		for (int i = 0; i < table.getChildCount(); i++)
		{
			RowDetalleVenta row = (RowDetalleVenta) table.getChildAt(i);
			OTItemVenta item = new OTItemVenta();
			item.setArticulo(row.getProducto().getProducto().getArticulo());
			item.setCantidad(row.getProducto().getCantidad());
			item.setCodigoProducto(row.getProducto().getProducto().getIdProducto());
			item.setDescuento(row.getProducto().getDescuento());
			item.setIla(row.getProducto().getIla());
			item.setNeto(row.getProducto().getValorNeto());
			item.setPrecio((float)row.getProducto().getPrecio());
			registrosVenta.add(item);
		}
		venta.setRegistrosVenta(registrosVenta);
	}

	/**
	 * Metodo que elimina producto de la venta.
	 */
	private void eliminarProducto()
	{
		if (contadorSeleccionados > 0)
		{
			final AlertDialog.Builder alertaCompuesta = new AlertDialog.Builder(this);
			alertaCompuesta.setTitle("Eliminar producto");
			if (contadorSeleccionados == 1)
			{
				alertaCompuesta.setMessage("Esta seguro que desea eliminar el registro de venta selecionado.");
			}
			else
			{
				alertaCompuesta.setMessage("Esta seguro que desea eliminar los registros de venta seleccionados.");
			}
			alertaCompuesta.setPositiveButton("Aceptar", new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog, int which)
				{
					ejecutarOnclickAceptar();
				}
			});
			alertaCompuesta.setNegativeButton("Cancelar", null);
			alertaCompuesta.show();
		}
		else
		{
			EmisorMensajes.mostrarMensaje(this, "Eliminar producto", "Debe seleccionar producto a eliminar.");
		}
	}

	/**
	 * Metodo que elimina registro de ventas.
	 */
	private void ejecutarOnclickAceptar()
	{
		for (int i = table.getChildCount() - 1; i >= 0; i--)
		{
			RowDetalleVenta row = (RowDetalleVenta) table.getChildAt(i);
			if (row.getChecBox().isChecked())
			{
				table.removeView(row);
			}
		}
		actualizarEncabezadoVenta();
		if (contadorSeleccionados == 1)
		{
			EmisorMensajes.notificarInformacion(VentaDetalle.this, "Registro eliminado");
		}
		else
		{
			EmisorMensajes.notificarInformacion(VentaDetalle.this, "Registros eliminados");
		}
		contadorSeleccionados = 0;
	}

	/**
	 * Metodo que modifica producto de la venta.
	 */
	private void modificarProducto()
	{
		if (rowSeleccionada != null)
		{
			OTProductoVenta productoSeleccionado = rowSeleccionada.getProducto();
			if (productoSeleccionado != null)
			{
				agregarModificarProducto(productoSeleccionado);
			}
		}
		else
		{
			EmisorMensajes.mostrarMensaje(this, "Modificar producto", "Debe seleccionar producto a modificar.");
		}
	}

	/**
	 * Metodo que agrega o modifica producto de la venta en proceso.
	 * 
	 * @param productoSeleccionado
	 *            Producto seleccionado.
	 */
	private void agregarModificarProducto(OTProductoVenta productoSeleccionado)
	{
		Intent intent = new Intent(this, Productos.class);
		intent.putExtra(PRODUCTO_SELECCIONADO, productoSeleccionado);
		this.startActivityForResult(intent, 1);
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
				OTProductoVenta productoVenta = (OTProductoVenta) bundle.get(PRODUCTO_VENTA);
				agregarRegistroProducto(productoVenta);
			}
			break;
		}
		}
		rowSeleccionada = null;
	}

	/**
	 * Metodo que agrega registro de producto a la venta.
	 */
	private void actualizaRegistroProducto()
	{
		List<OTProductoVenta> detalleVenta = Fabrica.obtenerInstancia().obtenerModeloDipalza()
						.obtenerItemesVenta(venta.getEncabezado().getIdVenta());
		table.removeAllViews();
		if (detalleVenta != null)
		{
			for (OTProductoVenta otVenta : detalleVenta)
			{
				agregarProductosInicial(otVenta);
			}
		}
	}

	/**
	 * Metodo que agrega registro de producto a la venta.
	 */
	private void agregarRegistroProducto(OTProductoVenta productoVenta)
	{
		if (productoVenta != null)
		{
			RowDetalleVenta registro = new RowDetalleVenta(this);
			registro.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			registro.setClickable(true);
			registro.setProducto(productoVenta);
			if (productoVenta.getIdentificadorProducto() == 0)
			{
				int indiceRow = table.getChildCount();
				productoVenta.setIdentificadorProducto(indiceRow + 1);
				table.addView(registro, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
								LayoutParams.WRAP_CONTENT));
			}
			else
			{
				int indiceRow = productoVenta.getIdentificadorProducto();
				table.removeViewAt(indiceRow - 1);
				table.addView(registro, indiceRow - 1, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
								LayoutParams.WRAP_CONTENT));
			}
			actualizarEncabezadoVenta();
		}
	}

	private void agregarProductosInicial(OTProductoVenta productoVenta)
	{
		RowDetalleVenta registro = new RowDetalleVenta(this);
		registro.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		registro.setClickable(true);
		registro.setProducto(productoVenta);

		int indiceRow = productoVenta.getIdentificadorProducto();
		table.addView(registro, indiceRow - 1, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
						LayoutParams.WRAP_CONTENT));
		actualizarEncabezadoVenta();
		
	}
	public void onClick(View vista)
	{
		if (vista.getParent() instanceof RowDetalleVenta)
		{
			rowSeleccionada = (RowDetalleVenta) vista.getParent();
			modificarProducto();
		}
	}

	/**
	 * Metodo que actualiza los calculos del encabezado de la venta.
	 */
	private void actualizarEncabezadoVenta()
	{
		double valorNeto = 0;
		double valorBruto = 0;
		double totalIVA = 0;
		double totalILA = 0;
		SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(this);
		double valorIVA = Double.valueOf(shared.getString("pref_iva", "19")) / 100d;
		for (int i = 0; i < table.getChildCount(); i++)
		{
			RowDetalleVenta row = (RowDetalleVenta) table.getChildAt(i);
			OTProductoVenta producto = row.getProducto();
			double valorILA = producto.getProducto().getIla() / 100d;
			producto.setIla(producto.getValorNeto() * valorILA);
			valorNeto = valorNeto + producto.getValorNeto();
			totalIVA = totalIVA + (producto.getValorNeto() * valorIVA);
			totalILA = totalILA + (producto.getIla());
			valorBruto = valorNeto + totalIVA + totalILA;
		}
		textNeto.setText(Fabrica.getDecimalFormat().format(valorNeto));
		textIVA.setText(Fabrica.getDecimalFormat().format(totalIVA));
		textILA.setText(Fabrica.getDecimalFormat().format(totalILA));
		textBruto.setText(Fabrica.getDecimalFormat().format(valorBruto));
	}

	/**
	 * Metodo que registra productos seleccionados.
	 */
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
	{
		if (isChecked)
		{
			contadorSeleccionados++;
		}
		else
		{
			contadorSeleccionados--;
		}
	}
}
