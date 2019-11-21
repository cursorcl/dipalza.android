package cl.eos.dipalza;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.TextView;
import cl.eos.dipalza.factory.Fabrica;
import cl.eos.dipalza.ot.OTResumenVentas;

/**
 * 
 * @author sfarias
 * 
 */
public class VentasResumen extends DashboardActivity {
	/**
	 * Componente grafico.
	 */
	private TextView txtNeto;
	/**
	 * Componente grafico.
	 */
	private TextView txtILA;
	/**
	 * Componente grafico.
	 */
	private TextView txtIVA;
	/**
	 * Componente grafico.
	 */
	private TextView txtBruto;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ventasresumen);
		setTitleFromActivityLabel(R.id.title_text);
		inicializarComponentesGraficos();
		inicializarResumenVentas();
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	    super.onConfigurationChanged(newConfig);
	}

	
	/**
	 * Metodo que inicializa componentes graficos de la interfaz.
	 */
	private void inicializarComponentesGraficos() {
		txtNeto = (TextView) findViewById(R.id.textRNeto);
		txtILA = (TextView) findViewById(R.id.textRILA);
		txtIVA = (TextView) findViewById(R.id.textRIVA);
		txtBruto = (TextView) findViewById(R.id.textRBruto);
	}

	/**
	 * Metodo que inicializa interfaz con datos obtendos por ventas realizadas
	 * por el vendedor desde la base de datos.
	 */
	private void inicializarResumenVentas() {
		OTResumenVentas resumen = Fabrica.obtenerInstancia()
				.obtenerModeloDipalza().obtenerResumenVenta();
		if (resumen != null) {
			txtNeto.setText(String.valueOf(resumen.getNeto()));
			txtILA.setText(String.valueOf(resumen.getIla()));
			txtIVA.setText(String.valueOf(resumen.getIva()));
			txtBruto.setText(String.valueOf(resumen.getBruto()));
			
//			txtNeto.setText(Fabrica.getDecimalFormat().format(resumen.getNeto()));
//			txtILA.setText(Fabrica.getDecimalFormat().format(resumen.getIla()));
//			txtIVA.setText(Fabrica.getDecimalFormat().format(resumen.getIva()));
//			txtBruto.setText(Fabrica.getDecimalFormat().format(resumen.getBruto()));
		} 
	}

}
