package cl.eos.dipalza.modelo.vistas;

import java.util.List;

import cl.eos.dipalza.ot.OTCliente;
import cl.eos.dipalza.ot.OTProducto;
import cl.eos.dipalza.ot.OTProductoVenta;
import cl.eos.dipalza.ot.OTVenta;

public interface IModeloDipalza {

	/**
	 * Obtiene lista de clientes disponibles en el sistema.
	 * 
	 * @return Lista de clientes.
	 */
	List<OTCliente> obtenerClientes();

	/**
	 * Obtiene lista de productos disponibles en el sistema.
	 * 
	 * @return Lista de productos.
	 */
	List<OTProducto> obtenerProductos();
	
	
	OTVenta  obtenerVenta(final long idVenta);
	
	List<OTProductoVenta> obtenerItemesVenta(long idVenta);
	
	 List<OTVenta> obtenerListadoVentas();
}
