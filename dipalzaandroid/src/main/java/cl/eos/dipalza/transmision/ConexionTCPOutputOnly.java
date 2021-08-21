package cl.eos.dipalza.transmision;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.grupo.biblioteca.MessageToTransmit;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import cl.eos.dipalza.ActivityHandler;

public class ConexionTCPOutputOnly{
    private Socket socket;
    private String ipDirection = "localhost";
    private int port = 5500;
    private ObjectOutputStream output = null;
    private boolean alive = false;

    /**
     * Se utiliza para notificar cuando se ha terminado la transmisión, para que hagan lo que
     * estimen conveniente.
     */
    public static interface FinTransmisionEscuchador {
        void finTransmision();
    }

    private Handler handler;
    private ProcesadorCliente procesador;
    private List<FinTransmisionEscuchador> escuchadoresFinTransmision = new ArrayList<FinTransmisionEscuchador>();


    public ConexionTCPOutputOnly(String ipDirection, int port) {
        super();
        this.ipDirection = ipDirection;
        this.port = port;
    }
    public ConexionTCPOutputOnly(String ipDirection, int port, FinTransmisionEscuchador escuchador) {
        super();
        this.ipDirection = ipDirection;
        this.port = port;
        escuchadoresFinTransmision.add(escuchador);
    }

    public void agregarEscuchadorFinTransmision(FinTransmisionEscuchador escuchador){
        escuchadoresFinTransmision.add(escuchador);
    }
    public void quitarEscuchadorFinTransmision(FinTransmisionEscuchador escuchador){
        escuchadoresFinTransmision.remove(escuchador);
    }


    public final void connect() throws IOException {
        procesador = new ProcesadorCliente();
        procesador.setHandler(this.handler);

        socket = new Socket(ipDirection, port);
        output = new ObjectOutputStream(socket.getOutputStream());
        alive = true;
        notifyNotification("Conectado al servidor");
    }

    public final void disconnect() throws IOException {
        if (isConnected()) {
            procesador.setAlive(false);
            socket.close();
            notifyNotification("Desconetado del servidor");
        }
    }

    private boolean isConnected() {
        return (this.socket != null) && (this.socket.isConnected());
    }

    public void send(MessageToTransmit mensaje) {

        new Thread(() -> {
            try {
                output.writeObject(mensaje);
                for(FinTransmisionEscuchador esc: escuchadoresFinTransmision)
                {
                    esc.finTransmision();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private Handler getHandler() {
        return handler;
    }

    public final void setHandler(Handler handler) {
        this.handler = handler;
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


}
