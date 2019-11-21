package cl.eos.dipalza;

import java.text.SimpleDateFormat;

import android.content.Context;
import android.view.Gravity;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import cl.eos.dipalza.ot.OTRegistroVenta;

/**
 * Registro de la venta.
 * 
 * @author sfarias
 * 
 */
public class RowVentaOriginal extends TableRow
{
	private static final LayoutParams LAYOUT_PARAMS_WC = new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
	
	private static final LayoutParams LAYOUT_PARAMS_FP = new LayoutParams(LayoutParams.FILL_PARENT,
					LayoutParams.FILL_PARENT);
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
	 * Campo formateador de fecha.
	 */
	private SimpleDateFormat formatter;
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
	public RowVentaOriginal(Context context)
	{
		super(context);
		inicializarView(context);
		formatter = new SimpleDateFormat("dd-mm-yy");
		((TableRow) this).setLayoutParams(LAYOUT_PARAMS_FP);
		((TableRow) this).setPadding(0, 0, 0, 2);
		((TableRow) this).setBackgroundColor(R.drawable.colortabla);
	}

	/**
	 * Inicializacion de la vista que despliega la informacion.
	 * 
	 * @param context
	 *            Contexto de la vista.
	 */
	private void inicializarView(Context context)
	{
		checBox = new CheckBox(context);
		checBox.setLayoutParams(LAYOUT_PARAMS_WC);
		checBox.setOnCheckedChangeListener((OnCheckedChangeListener) context);
		
		
		LinearLayout layoutRegistro = new LinearLayout(context);
		layoutRegistro.setOrientation(LinearLayout.HORIZONTAL);
		layoutRegistro.setLayoutParams(LAYOUT_PARAMS_FP);
		
		layoutRegistro.addView(checBox);
		
		LinearLayout layoutVenta = new LinearLayout(context);
		layoutVenta.setOrientation(LinearLayout.VERTICAL);
		layoutVenta.setLayoutParams(LAYOUT_PARAMS_FP);
		
		LinearLayout layoutCliente = new LinearLayout(context);
		layoutCliente.setOrientation(LinearLayout.HORIZONTAL);
		
		layoutCliente.setLayoutParams(LAYOUT_PARAMS_FP);
		layoutVenta.addView(layoutCliente);
		
		layoutRegistro.addView(layoutVenta);
		
		cliente = new TextView(context);
		cliente.setLayoutParams(LAYOUT_PARAMS_WC);
		cliente.getLayoutParams().width = 220;
		cliente.setGravity(Gravity.LEFT);
		cliente.setTextAppearance(context, android.R.attr.textAppearanceLarge);
		layoutCliente.addView(cliente);
		
		fecha = new TextView(context);		
		fecha.setLayoutParams(LAYOUT_PARAMS_WC);
		layoutCliente.addView(fecha);
		
		totalVenta = new TextView(context);
		totalVenta.setLayoutParams(LAYOUT_PARAMS_FP);
		layoutVenta.addView(totalVenta);
		
		this.addView(layoutRegistro);
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
		String formattedDate = formatter.format(venta.getVenta().getEncabezado().getFecha());
		this.fecha.setText(formattedDate);
		this.cliente.setText(venta.getVenta().getEncabezado().getCliente());
		this.totalVenta.setText(String.valueOf(venta.getVenta().getEncabezado().getNeto()));
	}

	/**
	 * @return the checBox
	 */
	public CheckBox getChecBox()
	{
		return checBox;
	}
}
