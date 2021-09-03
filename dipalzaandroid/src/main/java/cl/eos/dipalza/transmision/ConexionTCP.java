package cl.eos.dipalza.transmision;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.grupo.biblioteca.EMessagesTypes;
import com.grupo.biblioteca.MessageToTransmit;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import cl.eos.dipalza.ActivityHandler;
import cl.eos.dipalza.R;

public class ConexionTCP implements Runnable {
    private Socket socket;
    private String ipDirection = "localhost";
    private int port = 5502;
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
        InetSocketAddress sockAdr = new InetSocketAddress(ipDirection, port);
        socket = new Socket();
        socket.connect(sockAdr, 5000);
        output = new ObjectOutputStream(socket.getOutputStream());
        output.flush();
        input = new ObjectInputStream(socket.getInputStream());
        alive = true;

        notifyNotification("Conectado al servidor");
        start();
    }

    public final void disconnect() throws IOException {
        if (isConnected()) {
            procesador.setAlive(false);
            input.close();
            output.close();
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
                        Log.i("MSG_VECTORVENTAS", "Llega un mensaje " + ((MessageToTransmit) obj).getType().getName());
                        procesador.addToProcess(obj);
                        if(((MessageToTransmit)obj).getType().equals(EMessagesTypes.MSG_FINALIZED))
                        {
                            Log.i("MSG_VECTORVENTAS", "Desconectando");
                            alive = false;
                        }
                    }
                } catch (Exception e) {
                    notifyError("Excepción ha ocurrido en ConexionTCP-hebra.");
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
                output.flush();
                MessageToTransmit finish = new MessageToTransmit();
                finish.setIdPalm(mensaje.getIdPalm());
                finish.setType(EMessagesTypes.MSG_FINALIZED);
                output.writeObject(finish);
                output.flush();
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
