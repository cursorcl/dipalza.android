package cl.eos.dipalza;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TableRow;
import android.widget.TextView;
import cl.eos.dipalza.factory.Fabrica;
import cl.eos.dipalza.ot.OTRegistroVenta;

/**
 * Registro de la venta.
 * 
 * @author sfarias
 * 
 */
public class RowVenta extends TableRow
{
	/**
	 * Objeto de transferencia de la venta.
	 */
	private OTRegistroVenta venta;
	/**
	 * Componente grafico.
	 */
	private TextView fecha = null;
	/**
	 * Componente grafico.
	 */
	private TextView cliente = null;
	/**
	 * Componente grafico.
	 */
	private TextView totalVenta = null;
	/**
	 * Componente grafico.
	 */
	private CheckBox checBox;

	/**
	 * Constuctor de la clase.
	 * 
	 * @param context
	 *            Contexto de la actividad principal.
	 */
	public RowVenta(Context context)
	{
		super(context);
		View view = LayoutInflater.from(context).inflate(R.layout.row_test, this, false);
		checBox = (CheckBox) view.findViewById(R.id.checkBoxRow);
		checBox.setOnCheckedChangeListener((OnCheckedChangeListener) context);
		cliente = (TextView) view.findViewById(R.id.textClienteRow);
		fecha = (TextView) view.findViewById(R.id.textFechaRow);
		totalVenta = (TextView) view.findViewById(R.id.textValorRow);
		this.addView(view);
	}

	/**
	 * Obtiene venta.
	 * 
	 * @return OTVenta.
	 */
	public OTRegistroVenta getVenta()
	{
		return venta;
	}

	/**
	 * Asigan valores de la venta.
	 * 
	 * @param venta
	 *            Registro de la venta.
	 */
	public void setVenta(OTRegistroVenta venta)
	{
		this.venta = venta;
		String formattedDate = Fabrica.getSimpleDateFormat().format(venta.getVenta().getEncabezado().getFecha());
		this.fecha.setText(formattedDate);
		this.cliente.setText(venta.getVenta().getEncabezado().getCliente());
		this.totalVenta.setText(String.valueOf(venta.getVenta().getEncabezado().getNeto()));
//		this.totalVenta.setText(Fabrica.getDecimalFormat().format(venta.getVenta().getEncabezado().getNeto()));
	}

	/**
	 * @return the checBox
	 */
	public CheckBox getChecBox()
	{
		return checBox;
	}
}
