package cl.eos.dipalza.transmision;

import com.grupo.basedatos.Cliente;
import com.grupo.basedatos.Producto;

import cl.eos.dipalza.ot.OTCliente;
import cl.eos.dipalza.ot.OTEspeciales;
import cl.eos.dipalza.ot.OTProducto;

import com.grupo.basedatos.Especiales;

public final class UtilitarioConversion
{
	private static long idCliente = 1;
	
	private UtilitarioConversion()
	{
	}
	/**
	 * Transforma un producto remoto a un producto local.
	 * 
	 * @param producto
	 *            Producto remoto.
	 * @return Producto local.
	 */
	public static OTProducto toOTProcucto(Producto producto)
	{
		OTProducto otProducto = new OTProducto();
		otProducto.setArticulo(producto.getArticulo());
		otProducto.setCosto(Double.valueOf(producto.getCosto()));
		otProducto.setIdProducto(Short.valueOf(producto.getIdProducto()));
		otProducto.setIla(Double.valueOf(producto.getIla()));
		otProducto.setNombre(producto.getNombre());
		otProducto.setPrecio(Float.valueOf(producto.getPrecio()));
		otProducto.setStock(Double.valueOf(producto.getStock()));
		otProducto.setUnidad(producto.getUnidad());
		otProducto.setProveedor(producto.getProveedor());
		return otProducto;
	}

	/**
	 * Transforma un cliente remoto en un cliente local.
	 * 
	 * @param cliente
	 *            Cliente remoto.
	 * @return Cliente local.
	 */
	public static OTCliente toOTCliente(Cliente cliente)
	{
		OTCliente otCliente = new OTCliente();
		otCliente.setIdCliente(idCliente++);
		otCliente.setCodigo(cliente.getCodigo());
		otCliente.setDireccion(cliente.getDireccion());
		otCliente.setNombre(cliente.getRazonSocial());
		otCliente.setRut(cliente.getRut());
		otCliente.setTelefono(cliente.getTelefono());
		otCliente.setCiudad(cliente.getCiudad());
		return otCliente;
	}

	public static OTEspeciales toOTEspeciales(Especiales especiales)
	{
		OTEspeciales otEspeciales = new OTEspeciales();
		otEspeciales.setArticulo(especiales.getArticulo());
		return otEspeciales;
	}
}
