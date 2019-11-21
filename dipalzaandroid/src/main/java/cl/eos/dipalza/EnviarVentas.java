package cl.eos.dipalza;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import cl.eos.dipalza.factory.Fabrica;
import cl.eos.dipalza.ot.OTCliente;
import cl.eos.dipalza.ot.OTEVenta;
import cl.eos.dipalza.ot.OTItemVenta;
import cl.eos.dipalza.ot.OTVenta;
import cl.eos.dipalza.transmision.ConexionTCP;
import cl.eos.dipalza.utilitarios.EmisorMensajes;

import com.grupo.basedatos.EncabezadoVenta;
import com.grupo.basedatos.IDUnit;
import com.grupo.basedatos.ItemVenta;
import com.grupo.basedatos.ItemesVenta;
import com.grupo.basedatos.Venta;
import com.grupo.biblioteca.EMessagesTypes;
import com.grupo.biblioteca.MessageToTransmit;
import com.grupo.biblioteca.VectorVenta;

public class EnviarVentas extends ActivityHandler implements OnClickListener
{
	public static final String ENCABEZADO = "ENCABEZDO";
	public static final String DETALLE = "DETALLE";
	private static final String FORMATO = "Emitidos: %d de un total de %d.";
	private Future<ConexionTCP> fConexion;
	private String vendedor;
	private String ruta;
	private String ruta_adicional;
	private ProgressBar pbVentas;
	private ProgressBar pbRegistro;
	private TextView txtEmitidoVentas;
	private TextView txtEmitidoRegistro;
	private TextView txtUltimaTransmision;
	private TextView txtIp;
	private ImageButton btnTransmitirEmision;
	private boolean transmiting = false;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.emite_ventas);
		setTitleFromActivityLabel(R.id.title_text);
		pbVentas = (ProgressBar) findViewById(R.id.pbVentas);
		pbRegistro = (ProgressBar) findViewById(R.id.pbRegistros);
		txtEmitidoVentas = (TextView) findViewById(R.id.txtEmitidoVentas);
		txtEmitidoRegistro = (TextView) findViewById(R.id.txtEmitidoRegistro);
		txtUltimaTransmision = (TextView) findViewById(R.id.txtFechaTransmision);
		txtIp = (TextView) findViewById(R.id.txtIp);
		btnTransmitirEmision = (ImageButton) findViewById(R.id.btnTransmitirEmision);
		btnTransmitirEmision.setOnClickListener(this);
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		vendedor = prefs.getString(ActivityConfiguracion.PREF_VENDEDOR, "");
		ruta = prefs.getString(ActivityConfiguracion.PREF_RUTA, "");
		ruta_adicional = prefs.getString(ActivityConfiguracion.PREF_RUTA_ADICIONAL, "");
		txtUltimaTransmision.setText(prefs.getString(ActivityConfiguracion.FECHA_TRANSMISION, "NO TRANSMITIDO"));
		txtIp.setText("Conectando a:" + prefs.getString(ActivityConfiguracion.PREF_IP, "NO ASIGNADO"));
	}

	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onBackPressed()
	 */
	@Override
	public void onBackPressed()
	{
		if (transmiting)
		{
			EmisorMensajes.notificarInformacion(this, "Espere termino transmisión");
		}
		else
		{
			super.onBackPressed();
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
	 * Enviar ventas por red.
	 */
	private final void emitirVentas()
	{
		if (conectar())
		{
		    VectorVenta vectorVentas = new VectorVenta();
		    
		    
			transmiting = true;
			IDUnit identificacion = new IDUnit(vendedor, ruta);
			identificacion.setIdUnit(vendedor);
			identificacion.setIdRutaAdicional(ruta_adicional);
			/*
			 * Se obtiene el listado de ventas.
			 */
			List<OTVenta> ventas = Fabrica.obtenerInstancia().obtenerModeloDipalza().obtenerListadoVentas();
			int nroVentas = 0;
			notificarAvance(ENCABEZADO, 0, ventas.size());
			for (OTVenta venta : ventas)
			{
			    Venta rVenta = new Venta();
				OTEVenta otEncabezado = venta.getEncabezado();
				EncabezadoVenta encabezado = fromOTEVEntaToEncabezado(otEncabezado);
				rVenta.setEncabezado(encabezado);
				notificarAvance(ENCABEZADO, ++nroVentas, ventas.size());

				List<OTItemVenta> itemVentas = venta.getRegistrosVenta();
				int nroRegistros = 0;
				notificarAvance(DETALLE, 0, itemVentas.size());
				ItemesVenta rIemesVenta = new ItemesVenta(); 
				for (OTItemVenta item : itemVentas)
				{
					ItemVenta itemVenta = fromOTItemVentaToItemVenta(item);
					rIemesVenta.add(itemVenta);
					notificarAvance(DETALLE, ++nroRegistros, itemVentas.size());
				}
				rVenta.setVentas(rIemesVenta);
				rVenta.setFecha(encabezado.getFecha());
				
				vectorVentas.add(rVenta);
			}
			
			MessageToTransmit mensaje = new MessageToTransmit();
			mensaje.setIdPalm(identificacion);
			mensaje.setType(EMessagesTypes.MSG_VECTORVENTAS);
			mensaje.setData(vectorVentas);
			enviarMensaje(mensaje);
			
			Date fecha = new Date();
			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
			SharedPreferences.Editor editor = preferences.edit();
			editor.putString(ActivityConfiguracion.FECHA_TRANSMISION, fecha.toLocaleString());
			editor.commit();
			txtUltimaTransmision.setText(fecha.toLocaleString());
			try
			{
				fConexion.get().disconnect();
			}
			catch (IOException e)
			{
				notificarError("Dipalza:" + e.getLocalizedMessage());
				transmiting = false;
			}
			catch (InterruptedException e)
			{
				notificarError("Dipalza:" + e.getLocalizedMessage());
				transmiting = false;
			}
			catch (ExecutionException e)
			{
				notificarError("Dipalza:" + e.getLocalizedMessage());
				transmiting = false;
			}
			finally
			{
				transmiting = false;
			}
		}
	}

	/**
	 * Obtiene el item de venta a partir del OTItem de venta.
	 * 
	 * @param otItem
	 * @return
	 */
	private ItemVenta fromOTItemVentaToItemVenta(OTItemVenta otItem)
	{
		ItemVenta item = new ItemVenta();
		item.setArticulo(otItem.getArticulo());
		item.setCantidad((float) otItem.getCantidad());
		item.setCodigoProducto((short) otItem.getCodigoProducto());
		item.setDescuento((float) otItem.getDescuento());
		item.setIla((float) otItem.getIla());
		item.setNeto((float) otItem.getNeto());
		item.setOldCantidad((float) otItem.getCantidad());
		item.setPrecio(otItem.getPrecio());
		return item;
	}

	private EncabezadoVenta fromOTEVEntaToEncabezado(OTEVenta otEncabezado)
	{
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		float iva = Float.parseFloat(preferences.getString(ActivityConfiguracion.PREF_IVA, "19"));
		OTCliente cliente = Fabrica.obtenerInstancia().obtenerModeloDipalza()
						.obtenerCliente(otEncabezado.getIdCliente());
		EncabezadoVenta encabezado = new EncabezadoVenta();
		encabezado.setCodigoCliente(cliente.getCodigo());
		encabezado.setCondicionVenta((byte) otEncabezado.getCondicionVenta());
		encabezado.setDroped(false);
		encabezado.setFecha(otEncabezado.getFecha());
		encabezado.setNeto((float) otEncabezado.getNeto().floatValue());
		encabezado.setPorcentajeIva(iva);
		encabezado.setVendedor(otEncabezado.getVendedor());
		encabezado.setNombreCliente(cliente.getNombre());
		encabezado.setRut(cliente.getRut());
		return encabezado;
	}

	private void enviarMensaje(MessageToTransmit mensaje)
	{
		try
		{
			ConexionTCP conexion = fConexion.get();
			conexion.send(mensaje);
		}
		catch (InterruptedException e)
		{
			notificarError("Dipalza:" + e.getLocalizedMessage());
			transmiting = false;
		}
		catch (ExecutionException e)
		{
			notificarError("Dipalza:" + e.getLocalizedMessage());
			transmiting = false;
		}
	}

	private final boolean conectar()
	{
		WifiManager wifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
		if (wifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLED)
		{
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
			EmisorMensajes.mostrarMesajeFlotante(this,
							"Conectando al sevidor " + prefs.getString(ActivityConfiguracion.PREF_IP, "NO SERVER"));
			ExecutorService executor = Executors.newFixedThreadPool(1);
			fConexion = executor.submit(new Connector());
			return true;
		}
		else
		{
			EmisorMensajes.mostrarMensaje(this, "WIFI desconectado", "Conecte el wifi.");
			return false;
		}
	}

	/**
	 * Una conexion que se inicia cuando se abre el dialogo.
	 * Al momento de cambiar la direccion IP, se procede a la reconexion.
	 * 
	 * @author cursor
	 * 
	 */
	private class Connector implements Callable<ConexionTCP>
	{
		public ConexionTCP call()
		{
			ConexionTCP connection = Fabrica.obtenerInstancia().obtenerConexion();
			try
			{
				connection.setHandler(getHandler());
				connection.connect();
			} catch (IOException e)
			{
				notificarError("Dipalza:" + e.getLocalizedMessage());
				transmiting = false;
			}
			return connection;
		}
	}

	@Override
	protected void procesarMensajeNotificacion(Message message)
	{
		if (message.what == ActivityHandler.MSG_NOTIFICACION)
		{
			notificarInfo(message.getData().getString(ActivityHandler.ATRIBUTO));
		}
	}

	private void notificarInfo(String string)
	{
		EmisorMensajes.mostrarInformacionStatusBar(this, string);
	}

	@Override
	protected void procesarMensajeError(Message message)
	{
		if (message.what == ActivityHandler.MSG_ERROR)
		{
			notificarError(message.getData().getString(ActivityHandler.CAUSA));
		}
	}

	private void notificarError(String string)
	{
		EmisorMensajes.mostrarErrorStatusBar(this, string);
	}

	@Override
	protected void procesarMensajeAvance(Message message)
	{
		if (message.what == ActivityHandler.MSG_AVANCE)
		{
			String atributo = message.getData().getString(ActivityHandler.ATRIBUTO);
			int step = message.getData().getInt(ActivityHandler.STEP);
			int total = message.getData().getInt(ActivityHandler.TOTAL);
			notificarAvance(atributo, step, total);
		}
	}

	private void notificarAvance(String atributo, int step, int total)
	{
		if (atributo.equals(ENCABEZADO))
		{
			txtEmitidoVentas.setText(String.format(FORMATO, step, total));
			pbVentas.setMax(total);
			pbVentas.setProgress(step);
		}
		else if (atributo.equals(DETALLE))
		{
			txtEmitidoRegistro.setText(String.format(FORMATO, step, total));
			pbRegistro.setMax(total);
			pbRegistro.setProgress(step);
		}
	}

	public void onClick(View v)
	{
		Builder confirm = new AlertDialog.Builder(this);
		confirm.setIcon(android.R.drawable.ic_dialog_alert);
		confirm.setTitle("Advertencia");
		confirm.setMessage("Confirma transmisión de ventas.?");
		confirm.setPositiveButton("Si", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which)
			{
				EnviarVentas.this.emitirVentas();
			}
		});
		confirm.setNegativeButton("No", null);
		confirm.show();
	}

  @Override
  protected void procesarMensajeFinalizado(Message message) {
    if (message.what != ActivityHandler.MSG_FINALIZADO)
      return;
    EmisorMensajes.mostrarMensaje(this, "FINALIZADO", "Transferencia finalizada");
  }
}
