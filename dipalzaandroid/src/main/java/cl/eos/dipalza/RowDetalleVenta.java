package cl.eos.dipalza;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TableRow;
import android.widget.TextView;
import cl.eos.dipalza.factory.Fabrica;
import cl.eos.dipalza.ot.OTProductoVenta;

/**
 * 
 * @author sfarias
 * 
 */
public class RowDetalleVenta extends TableRow {
	/**
	 * Referencia a OTproducto.
	 */
	private OTProductoVenta producto;
	/**
	 * Componente grafico.
	 */
	private TextView productoVenta = null;
	/**
	 * Componente grafico.
	 */
	private TextView cantidadVenta = null;
	/**
	 * Componente grafico.
	 */
	private TextView precioVenta = null;
	/**
	 * Componente grafico.
	 */
	private CheckBox checBox;

	/**
	 * Constructor de la clase.
	 * 
	 * @param context
	 *            Contexto de la vista.
	 */
	public RowDetalleVenta(Context context) {
		super(context);
		View view = LayoutInflater.from(context).inflate(R.layout.row_detalle_venta, this, false);
		checBox = (CheckBox) view.findViewById(R.id.checkBoxRow);
		checBox.setOnCheckedChangeListener((OnCheckedChangeListener) context);
		productoVenta = (TextView) view.findViewById(R.id.textProductoRow);
		cantidadVenta = (TextView) view.findViewById(R.id.textCantidadRow);
		precioVenta = (TextView) view.findViewById(R.id.textValorRow);
		this.addView(view);
	}

	public OTProductoVenta getProducto() {
		return producto;
	}

	public void setProducto(OTProductoVenta producto) {
		this.producto = producto;
		productoVenta.setText(producto.getNombre());
		cantidadVenta.setText(Fabrica.getDecimalFormat().format(producto.getCantidad()));
		precioVenta.setText(Fabrica.getDecimalFormat().format(producto.getValorNeto()));
	}

	/**
	 * @return the checBox
	 */
	public CheckBox getChecBox() {
		return checBox;
	}
}
