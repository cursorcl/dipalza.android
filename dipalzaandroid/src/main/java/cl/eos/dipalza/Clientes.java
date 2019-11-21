package cl.eos.dipalza;

import android.os.Bundle;
import android.widget.TextView;
import cl.eos.dipalza.ot.OTCliente;

public class Clientes extends DashboardActivity {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.clientes);
		setTitleFromActivityLabel(R.id.title_text);
		Bundle bundle = getIntent().getExtras();
		OTCliente clienteSeleccionado = (OTCliente) bundle.get("cliente");
		if (clienteSeleccionado != null) {
			TextView txtRut = (TextView) findViewById(R.id.textVRut);
			txtRut.setText(clienteSeleccionado.getRut());
			TextView textnombreCliente = (TextView) findViewById(R.id.textVNombreCliente);
			textnombreCliente.setText(clienteSeleccionado.getNombre());
			TextView txtDireccion = (TextView) findViewById(R.id.textVDireccion);
			txtDireccion.setText(clienteSeleccionado.getDireccion());
			TextView txtCodigo = (TextView) findViewById(R.id.textVCodigoCliente);
			txtCodigo.setText(clienteSeleccionado.getCodigo());
			TextView txtCiudad = (TextView) findViewById(R.id.textVCiudad);
			txtCiudad.setText(clienteSeleccionado.getCiudad());
			TextView txtComuna = (TextView) findViewById(R.id.textVComuna);
			txtComuna.setText(clienteSeleccionado.getComuna());
			TextView txtTelefono = (TextView) findViewById(R.id.textVTelefono);
			txtTelefono.setText(clienteSeleccionado.getTelefono());
		}
	}
}
