package cl.eos.dipalza.utilitarios;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import cl.eos.dipalza.MainDipalza;
import cl.eos.dipalza.R;

import static android.widget.Toast.makeText;

public class EmisorMensajes {

	public static void notificarInformacion(Context activity, String string) {
		Toast toast = makeText(activity, string, (short)2);
		toast.show();
	}

	public static void mostrarErrorStatusBar(Context activity, String mensaje) {
		// Obtenemos una referencia al servicio de notificaciones
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager notManager = (NotificationManager) activity.getSystemService(ns);

		// Configuramos la notificación
		int icono = android.R.drawable.stat_notify_error;
		CharSequence textoEstado =  mensaje;
		long hora = System.currentTimeMillis();

		NotificationCompat.Builder builder = new NotificationCompat.Builder(activity, activity.getResources().getString(R.string.ERROR_CHANNEL_ID))
				.setSmallIcon(icono)
				.setContentTitle("Error")
				.setContentText(textoEstado)
				.setPriority(NotificationCompat.PRIORITY_DEFAULT);

		Notification notif = builder.build();
		notif.flags |= Notification.FLAG_AUTO_CANCEL;
		// Añadir sonido, vibración y luces
		notif.defaults |= Notification.DEFAULT_SOUND;
		notif.defaults |= Notification.DEFAULT_VIBRATE;
		notif.defaults |= Notification.DEFAULT_LIGHTS;

		Intent notIntent = new Intent(activity, MainDipalza.class);

		PendingIntent contIntent = PendingIntent.getActivity(activity, 0, notIntent, 0);
		notManager.notify((int) System.currentTimeMillis(), notif);
	}

	public static void mostrarInformacionStatusBar(Context activity,
			String mensaje) {
// Obtenemos una referencia al servicio de notificaciones
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager notManager = (NotificationManager) activity.getSystemService(ns);

		// Configuramos la notificación
		int icono = android.R.drawable.stat_notify_more;
		CharSequence textoEstado =  mensaje;
		long hora = System.currentTimeMillis();

		NotificationCompat.Builder builder = new NotificationCompat.Builder(activity, activity.getResources().getString(R.string.INFO_CHANNEL_ID))
				.setSmallIcon(icono)
				.setContentTitle("Información")
				.setContentText(textoEstado)
				.setPriority(NotificationCompat.PRIORITY_DEFAULT);

		Notification notif = builder.build();
		notif.flags |= Notification.FLAG_AUTO_CANCEL;
		// Añadir sonido, vibración y luces
		notif.defaults |= Notification.DEFAULT_SOUND;
		notif.defaults |= Notification.DEFAULT_VIBRATE;
		notif.defaults |= Notification.DEFAULT_LIGHTS;

		Intent notIntent = new Intent(activity, MainDipalza.class);

		PendingIntent contIntent = PendingIntent.getActivity(activity, 0,
				notIntent, 0);
		notManager.notify((int) System.currentTimeMillis(), notif);
	}

	public static void mostrarMensaje(Context activity, String titulo,
			String mensaje) {
		final AlertDialog.Builder alertaCompuesta = new AlertDialog.Builder(
				activity);
		alertaCompuesta.setTitle(titulo);
		alertaCompuesta.setMessage(mensaje);
		alertaCompuesta.setPositiveButton("Aceptar", null);
		alertaCompuesta.show();
	}
	
	public static void mostrarMesajeFlotante(Context activity, String mensaje)
	{
		((Activity)activity).runOnUiThread(() -> {
			makeText(activity, mensaje, (short) 100);
		});


	}
}
