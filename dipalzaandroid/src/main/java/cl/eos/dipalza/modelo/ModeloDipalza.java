package cl.eos.dipalza.modelo;

import java.util.List;

import android.content.Context;
import cl.eos.dipalza.database.DBDipalza;
import cl.eos.dipalza.modelo.vistas.IModeloDipalza;
import cl.eos.dipalza.ot.OTCliente;
import cl.eos.dipalza.ot.OTProducto;
import cl.eos.dipalza.ot.OTProductoVenta;
import cl.eos.dipalza.ot.OTResumenVentas;
import cl.eos.dipalza.ot.OTVenta;

public class ModeloDipalza implements IModeloDipalza
{
	/**
	 * Acceso a los datos.
	 */
	private DBDipalza dbDipalza;

	/**
	 * Constructor del modelo dipalza.
	 */
	public ModeloDipalza(final Context context)
	{
		dbDipalza = new DBDipalza(context);
		dbDipalza.open();
	}

	public List<OTCliente> obtenerClientes()
	{
		return dbDipalza.getClientes();
	}
	
	public OTCliente obtenerCliente(long idCliente)
	{
		return dbDipalza.getCliente(idCliente);
	}

	public List<OTProducto> obtenerProductos()
	{
		return dbDipalza.getProductos();
	}

	public void grabarCliente(final OTCliente cliente)
	{
		dbDipalza.grabarCliente(cliente);
	}

	public void grabarProducto(final OTProducto producto)
	{
		dbDipalza.grabarProducto(producto);
	}
	
	public void grabarVenta(final OTVenta venta)
	{
		dbDipalza.grabarVenta(venta);
	}
	
	public void borrarVenta(final long idVenta)
	{
		dbDipalza.borrarVenta(idVenta);
	}
	
	public void limpiarBaseDatos()
	{
		dbDipalza.eliminarRegistros();
	}
	
	public OTVenta  obtenerVenta(final long idVenta)
	{
		return dbDipalza.obtenerVenta(idVenta);
	}

	public List<OTProductoVenta> obtenerItemesVenta(long idVenta)
	{
		return dbDipalza.obtenerItemesVenta(idVenta);
	}
	
	public List<OTVenta> obtenerListadoVentas()
	{
		return dbDipalza.obtenerListadoVentas();
	}
	
	public OTResumenVentas obtenerResumenVenta()
	{
		return dbDipalza.obtenerResumenVenta();
	}

	public List<String> obtenerEpesciales() { return dbDipalza.obtenerEspeciales();}
	public boolean esEspecial(String code) { return dbDipalza.esEspecial(code);}
	public void grabarEspecial(String especial) { dbDipalza.grabarEspecial(especial);}
	public void grabarEspecial(List<String> especiales) { dbDipalza.grabarEspecial(especiales);}
}
