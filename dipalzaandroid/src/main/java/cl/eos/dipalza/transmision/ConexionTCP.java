package cl.eos.dipalza.transmision;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

import com.grupo.biblioteca.MessageToTransmit;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import cl.eos.dipalza.ActivityHandler;

public class ConexionTCP implements Runnable {
    private Socket socket;
    private String ipDirection = "localhost";
    private int port = 5500;
    private ObjectOutputStream output = null;
    private ObjectInputStream input = null;
    private boolean alive = false;

    private Handler handler;
    private ProcesadorCliente procesador;


    public ConexionTCP(String ipDirection, int port) {
        super();
        this.ipDirection = ipDirection;
        this.port = port;
    }


    private void start() {
        (new Thread(this)).start();
    }

    public final void connect() throws IOException {
        procesador = new ProcesadorCliente();
        procesador.setHandler(this.handler);

        socket = new Socket(ipDirection, port);
        output = new ObjectOutputStream(socket.getOutputStream());
        input = new ObjectInputStream(socket.getInputStream());
        alive = true;

        notifyNotification("Conectado al servidor");
        start();
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

    public void run() {
        try {
            while (alive) {
                Object obj;
                try {
                    obj = input.readObject();
                    if (obj instanceof MessageToTransmit) {
                        procesador.addToProcess(obj);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    alive = false;
                }
            }
            disconnect();
        } catch (UnknownHostException e) {
            notifyError("Direccion de servidor desconocida");
            e.printStackTrace();
        } catch (IOException e) {
            notifyError("Error de escritura/lectura");
            e.printStackTrace();
        }
    }

    public void send(MessageToTransmit mensaje) {

        new Thread(() -> {
            try {
                output.writeObject(mensaje);
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
