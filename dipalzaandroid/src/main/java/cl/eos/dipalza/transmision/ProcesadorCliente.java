package cl.eos.dipalza.transmision;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.grupo.MsgInformaDatos;
import com.grupo.basedatos.Cliente;
import com.grupo.basedatos.Especiales;
import com.grupo.basedatos.Producto;
import com.grupo.biblioteca.ListEspeciales;
import com.grupo.biblioteca.MessageToTransmit;
import com.grupo.biblioteca.VectorClientes;
import com.grupo.biblioteca.VectorProductos;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.locks.ReentrantLock;

import cl.eos.dipalza.ActivityHandler;
import cl.eos.dipalza.factory.Fabrica;
import cl.eos.dipalza.ot.OTCliente;
import cl.eos.dipalza.ot.OTProducto;

public class ProcesadorCliente implements Runnable {
  public static final String CLIENTES = "CLIENTES";
  public static final String PRODUCTOS = "PRODUCTOS";
  public static final String ESPECIALES = "ESPECIALES";
  public static final String FINALIZA = "FINALIZA";

  private final String TAG = "ProcesadorCliente";
  private BlockingQueue<Object> queue;
  private boolean alive;
  private ReentrantLock lock = new ReentrantLock();

  /**
   * Encargado de comunicar resultados a la HMI.
   */
  private Handler handler;
  private int totalClientes;
  private int totalProductos;
  private int totalProductosEspeciales;
  private int stepProducto;
  private int stepCliente;

  public ProcesadorCliente() {
    this.queue = new SynchronousQueue<>();
    alive = true;
    (new Thread(this)).start();
  }

  /**
   * Obtiene el valor del atributo alive.
   *
   * @return El valor de alive.
   */
  private boolean isAlive() {
    return alive;
  }

  void setAlive(boolean alive) {
    this.alive = alive;
  }

  void addToProcess(final Object paramObject) {
    try {
      queue.put(paramObject);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  /**
   * Procesa mientras existan datos.
   */
  public void run() {
    while (isAlive()) {
      lock.lock();
      try {
        Object object = queue.poll();
        if (object != null) {
          process(object);
        }
        else
        {
          try {
            Thread.sleep(100);
          } catch (InterruptedException ignored) {
          }
        }
      } finally {
        lock.unlock();
      }
    }
  }


  private void process(Object mensajeWrapper) {
    if (mensajeWrapper instanceof MessageToTransmit) {
      MessageToTransmit message = (MessageToTransmit) mensajeWrapper;

      int stepEspeciales;
      switch (message.getType()) {
        case MSG_INITIALIZE_CLIENTE:
          OTCliente otClte = UtilitarioConversion.toOTCliente((Cliente) message.getData());
          Log.d(TAG, "Se ha recibido " + otClte.getNombre());
          Fabrica.obtenerInstancia().obtenerModeloDipalza().grabarCliente(otClte);
          notifyProgress(CLIENTES, ++stepCliente, totalClientes);
          break;
        case MSG_VECTORCLIENTES:
          VectorClientes vCltes = (VectorClientes) message.getData();
          for (int n = 0; n < vCltes.size(); n++) {
            OTCliente otCliente = UtilitarioConversion.toOTCliente(vCltes.elementAt(n));
            Log.d(TAG, "Se ha recibido " + otCliente.getNombre());
            Fabrica.obtenerInstancia().obtenerModeloDipalza().grabarCliente(otCliente);
            notifyProgress(CLIENTES, ++stepCliente, totalClientes);
          }
          break;
        case MSG_INITIALIZE_PRODUCTO:
          OTProducto otProd = UtilitarioConversion.toOTProcucto((Producto) message.getData());
          Log.d(TAG, "Se ha almacenado " + otProd.getNombre());
          Fabrica.obtenerInstancia().obtenerModeloDipalza().grabarProducto(otProd);
          notifyProgress(PRODUCTOS, ++stepProducto, totalProductos);
          break;
        case MSG_VECTORPRODUCTOS:
          VectorProductos vProds = (VectorProductos) message.getData();
          for (Producto producto : vProds.asVector()) {
            OTProducto otProducto = UtilitarioConversion.toOTProcucto(producto);
            Log.d(TAG, "Se ha almacenado " + otProducto.getNombre());
            Fabrica.obtenerInstancia().obtenerModeloDipalza().grabarProducto(otProducto);
            notifyProgress(PRODUCTOS, ++stepProducto, totalProductos);
          }
          break;
        case MSG_INITIALIZE_CLIENTE_FINALIZED:
          Log.d(TAG, "Finalizada la transmisión de clientes");
          notifyNotification("Se ha recibido " + totalClientes + " clientes.");
          break;
        case MSG_INITIALIZE_PRODUCTO_FINALIZED:
          Log.d(TAG, "Finalizada la transmisión de productos");
          notifyNotification("Se ha recibido " + totalProductos + " productos.");
          break;
        case MSG_INITIALIZE_ESPECIALES_FINALIZED:
          Log.d(TAG, "Finalizada la transmisión de productos especiales");
          notifyNotification(
              "Se ha recibido " + totalProductosEspeciales + " productos especiales.");
          break;
        case MSG_INFORMATIVE:
          Log.d(TAG, "Informativo");
          // Me informa cuantos datos vienen.
          MsgInformaDatos info = (MsgInformaDatos) message.getData();
          if (info.getName().equals(Cliente.class.getName())) {
            stepCliente = 0;
            totalClientes = info.getCantidad();
            notifyProgress(CLIENTES, stepCliente, totalClientes);
          } else if (info.getName().equals(Producto.class.getName())) {
            stepProducto = 0;
            totalProductos = info.getCantidad();
            notifyProgress(PRODUCTOS, stepProducto, totalProductos);
          } else if (info.getName().equals(Especiales.class.getName())) {
            totalProductosEspeciales = info.getCantidad();
            notifyProgress(ESPECIALES, stepProducto, totalProductos);
          }

          break;
        case MSG_ESPECIALES:
          Log.d(TAG, "Especiales");
          ListEspeciales especiales = (ListEspeciales) message.getData();
          if (especiales == null || especiales.isEmpty())
            notifyProgress(ESPECIALES, 0, 0);
          else {
            int totalEspeciales = especiales.getEspecialesList().size();
            stepEspeciales = 0;
            for (Especiales especial : especiales.getEspecialesList()) {
              Log.d(TAG, "Se ha almacenado producto especial:" + especial.getArticulo());
              Fabrica.obtenerInstancia().obtenerModeloDipalza().grabarEspecial(especial.getArticulo());
              notifyProgress(ESPECIALES, ++stepEspeciales, totalEspeciales);
            }
          }
          break;
        case MSG_FINALIZED:
          Log.d(TAG, "SE HA FINALIZADO LA TRANSMISION");
          notifyFinished();
          break;
        default:
          String error = String.format("Ha llegado un mensaje errado [%s].",
                  message.getType().getName());
          Log.e(TAG, error);
          notifyError(error);
          break;
      }
    } else {
      String error = String.format("Ha llegado un objeto errado [%s].",
              mensajeWrapper.getClass().getName());
      Log.e(TAG, error);
      notifyError(error);
    }
  }


  private void notifyError(String error) {
    if (getHandler() != null) {
      Message message = getHandler().obtainMessage();
      message.what = ActivityHandler.MSG_ERROR;
      Bundle bundle = new Bundle();
      bundle.putString(ActivityHandler.CAUSA, error);
      message.setData(bundle);
      getHandler().sendMessage(message);
    }

  }

  private void notifyProgress(String atributo, int step, int total) {
    if (getHandler() != null) {
      Message message = getHandler().obtainMessage();
      message.what = ActivityHandler.MSG_AVANCE;
      Bundle bundle = new Bundle();
      bundle.putString(ActivityHandler.ATRIBUTO, atributo);
      bundle.putInt(ActivityHandler.STEP, step);
      bundle.putInt(ActivityHandler.TOTAL, total);
      message.setData(bundle);
      getHandler().sendMessage(message);
    }
  }

  private void notifyNotification(String atributo) {
    if (getHandler() != null) {
      Message message = getHandler().obtainMessage();
      message.what = ActivityHandler.MSG_NOTIFICACION;
      Bundle bundle = new Bundle();
      bundle.putString(ActivityHandler.ATRIBUTO, atributo);
      message.setData(bundle);
      getHandler().sendMessage(message);
    }

  }
  
  private void notifyFinished() {
    if (getHandler() != null) {
      Message message = getHandler().obtainMessage();
      message.what = ActivityHandler.MSG_FINALIZADO;
      getHandler().sendMessage(message);
    }

  }

  /**
   * Obtiene el valor del atributo handler.
   *
   * @return El valor de handler.
   */
  public Handler getHandler() {
    return handler;
  }

  /**
   * Establece el valor al atributo handler.
   *
   * @param handler el valor a establecer a handler.
   */
  public void setHandler(Handler handler) {
    this.handler = handler;
  }
}
