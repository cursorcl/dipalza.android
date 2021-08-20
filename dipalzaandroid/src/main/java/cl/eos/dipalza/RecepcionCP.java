package cl.eos.dipalza;

import android.annotation.SuppressLint;
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

import com.grupo.basedatos.IDUnit;
import com.grupo.biblioteca.EMessagesTypes;
import com.grupo.biblioteca.MessageToTransmit;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import cl.eos.dipalza.factory.Fabrica;
import cl.eos.dipalza.transmision.ConexionTCP;
import cl.eos.dipalza.transmision.ProcesadorCliente;
import cl.eos.dipalza.utilitarios.EmisorMensajes;

public class RecepcionCP extends ActivityHandler implements OnClickListener {
  private final String FORMATO = "Recibidos: %d de un total de %d.";
  private Future<ConexionTCP> fConexion;
  private ProgressBar pbClientes;
  private ProgressBar pbProductos;
  private TextView txtRecibidoClientes;
  private TextView txtRecibidoProductos;
  private TextView txtUltimaTransmision;

  private ProgressBar pbEspeciales;
  private TextView txtRecibidoEspeciales;

  private boolean transmiting = false;

  /**
   *
   */
  public RecepcionCP() {
    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.recibe_clientes_productos);
    setTitleFromActivityLabel(R.id.title_text);
    pbClientes = findViewById(R.id.pbClientes);
    pbProductos = findViewById(R.id.pbProductos);
    txtRecibidoClientes = findViewById(R.id.txtRecibidoClientes);
    txtRecibidoProductos = findViewById(R.id.txtRecibidoPorductos);
    txtUltimaTransmision = findViewById(R.id.txtFechaTransmision);
    TextView txtIp = findViewById(R.id.txtIp);
    ImageButton btnTransmitirRecepcion = findViewById(R.id.btnRecibirRecepcion);
    btnTransmitirRecepcion.setOnClickListener(this);
    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
    txtUltimaTransmision
        .setText(prefs.getString(ActivityConfiguracion.FECHA_RECEPCION, "NO TRANSMITIDO"));
    txtIp.setText(String.format("Conectando a:%s", prefs.getString(ActivityConfiguracion.PREF_IP, "NO ASIGNADO")));

    txtRecibidoEspeciales = findViewById(R.id.txtRecibidoEspeciales);
    pbEspeciales = findViewById(R.id.pbEspeciales);

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
  }

  private boolean conectar() {
    WifiManager wifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
    if (wifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLED) {
      SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
      EmisorMensajes.mostrarMesajeFlotante(RecepcionCP.this,
          "Conectando al sevidor " + prefs.getString(ActivityConfiguracion.PREF_IP, "NO SERVER"));
      ExecutorService executor = Executors.newFixedThreadPool(1);
      fConexion = executor.submit(new Connector());
      return true;
    } else {
      EmisorMensajes.mostrarMensaje(this, getString(R.string.WIFI_DESCONECTADO),
          getString(R.string.CONECTE_WIFI));
      return false;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see android.app.Activity#onBackPressed()
   */
  @Override
  public void onBackPressed() {
    if (transmiting) {
      EmisorMensajes.notificarInformacion(this, "Espere termino transmisión");
    } else {
      super.onBackPressed();
    }
  }

  /**
   * Solicita clientes y productos.
   */
  @SuppressLint("DefaultLocale")
  private void solicitaCltesProd() {
    int step = 0;
    int total = 0;
    txtRecibidoClientes.setText(String.format(FORMATO, step, total));
    pbClientes.setMax(total);
    pbClientes.setProgress(step);
    txtRecibidoProductos.setText(String.format(FORMATO, step, total));
    pbProductos.setMax(total);
    pbProductos.setProgress(step);
    txtRecibidoEspeciales.setText(String.format(FORMATO, step, total));
    pbEspeciales.setMax(total);
    pbEspeciales.setProgress(step);

    if (conectar()) {
      MessageToTransmit mensaje = new MessageToTransmit();
      SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
      String vendedor = prefs.getString(ActivityConfiguracion.PREF_VENDEDOR, "");
      String ruta = prefs.getString(ActivityConfiguracion.PREF_RUTA, "");
      String ruta_adicional = prefs.getString(ActivityConfiguracion.PREF_RUTA_ADICIONAL, "");
      IDUnit identificacion = new IDUnit(vendedor, ruta);
      mensaje.setIdPalm(identificacion);
      identificacion.setIdUnit(vendedor);
      identificacion.setIdRutaAdicional(ruta_adicional);
      mensaje.setType(EMessagesTypes.MSG_DATOSINICIALIZACION);
      mensaje.setData(identificacion);
      enviarMensaje(mensaje);
      SharedPreferences.Editor editor = prefs.edit();
      editor.putString(ActivityConfiguracion.FECHA_RECEPCION, "NO RECIBIDO");
      editor.commit();
    }
  }

  private void enviarMensaje(MessageToTransmit mensaje) {
    try {
      transmiting = true;
      ConexionTCP conexion = fConexion.get();
      Fabrica.obtenerInstancia().obtenerModeloDipalza().limpiarBaseDatos();
      conexion.send(mensaje);
    } catch (InterruptedException e) {
      notificarError(e.getLocalizedMessage());
      transmiting = false;
    } catch (ExecutionException e) {
      e.printStackTrace();
      notificarError(e.getLocalizedMessage());
      transmiting = false;
    }
  }

  /**
   * Una conexion que se inicia cuando se abre el dialogo. Al momento de cambiar la direccion IP, se
   * procede a la reconexion.
   *
   * @author cursor
   */
  private class Connector implements Callable<ConexionTCP> {
    public ConexionTCP call() {
      ConexionTCP connection = Fabrica.obtenerInstancia().obtenerConexion();
      try {
        connection.setHandler(getHandler());
        connection.connect();
      } catch (IOException e) {
        notificarError(getString(R.string.DIPALZA_NAME) + e.getLocalizedMessage());
        transmiting = false;
      }
      return connection;
    }
  }

  /**
   * Procesa mensaje de notificacion enviado desde una hebra.
   *
   * @param message Mensaje a procesar.
   */
  protected void procesarMensajeNotificacion(Message message) {
    if (message.what == ActivityHandler.MSG_NOTIFICACION) {
      notificarInfo(message.getData().getString(ActivityHandler.ATRIBUTO));
    }
  }

  private void notificarInfo(String string) {
    EmisorMensajes.mostrarInformacionStatusBar(this, string);
  }

  /**
   * Procesa mensaje de error enviado desde una hebra.
   *
   * @param message Mensaje a procesar.
   */
  protected void procesarMensajeError(Message message) {
    if (message.what == ActivityHandler.MSG_ERROR) {
      notificarError(message.getData().getString(ActivityHandler.CAUSA));
    }
  }

  private void notificarError(String string) {
    EmisorMensajes.mostrarErrorStatusBar(this, string);
    EmisorMensajes.mostrarMesajeFlotante(this, string);
  }

  protected void procesarMensajeFinalizado(Message message) {
    if (message.what != ActivityHandler.MSG_FINALIZADO)
      return;
    transmiting = false;
    try {
      fConexion.get().disconnect();
      EmisorMensajes.mostrarInformacionStatusBar(this, "Transferencia finalizada");
    } catch (IOException | InterruptedException | ExecutionException e) {
      notificarError(R.string.DIPALZA_NAME + e.getLocalizedMessage());
      transmiting = false;
    }
  }

  /**
   * Procesa mensaje de avance enviado desde una hebra.
   *
   * @param message Mensaje a procesar.
   */
  protected void procesarMensajeAvance(Message message) {
    if (message.what == ActivityHandler.MSG_AVANCE) {
      String atributo = message.getData().getString(ActivityHandler.ATRIBUTO);
      int step = message.getData().getInt(ActivityHandler.STEP);
      int total = message.getData().getInt(ActivityHandler.TOTAL);
      notificarAvance(atributo != null ? atributo : "", step, total);
    }
  }

  private void notificarAvance(String atributo, int step, int total) {
    if (atributo.equals(ProcesadorCliente.CLIENTES)) {
      txtRecibidoClientes.setText(String.format(FORMATO, step, total));
      pbClientes.setMax(total);
      pbClientes.setProgress(step);
    } else if (atributo.equals(ProcesadorCliente.PRODUCTOS)) {
      txtRecibidoProductos.setText(String.format(FORMATO, step, total));
      pbProductos.setMax(total);
      pbProductos.setProgress(step);
    } else if (atributo.equals(ProcesadorCliente.ESPECIALES)) {
      txtRecibidoEspeciales.setText(String.format(FORMATO, step, total));
      pbEspeciales.setMax(total);
      pbEspeciales.setProgress(step);
    }
    // Se almacena la última hora de recepción.
    if (step == total) {
      Date fecha = new Date();
      SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
      SharedPreferences.Editor editor = preferences.edit();
      editor.putString(ActivityConfiguracion.FECHA_RECEPCION, fecha.toLocaleString());
      editor.commit();
      txtUltimaTransmision.setText(fecha.toLocaleString());
    }

  }

  /*
   * (non-Javadoc)
   * 
   * @see main.dipalza.DashboardActivity#goHome(android.content.Context)
   */
  @Override
  public void goHome(Context context) {
    if (transmiting) {
      EmisorMensajes.notificarInformacion(this, getString(R.string.TERMINO_TRANSMISION));
    } else {
      super.goHome(context);
    }
  }

  public void onClick(View v) {
    Builder confirm = new Builder(this);
    confirm.setIcon(android.R.drawable.ic_dialog_alert);
    confirm.setTitle("Advertencia");
    confirm.setMessage(R.string.BORRAR_INFORMACION);
    confirm.setPositiveButton("Si", new DialogInterface.OnClickListener() {

      public void onClick(DialogInterface dialog, int which) {
        RecepcionCP.this.solicitaCltesProd();
      }
    });

    confirm.setNegativeButton("No", null);
    confirm.show();
  }

}
