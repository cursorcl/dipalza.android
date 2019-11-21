package cl.eos.dipalza.ot;

import java.io.Serializable;

/**
 * Objeto de transporte de productos.
 * @author cursor
 */
public class OTProducto implements Serializable, IOTObject{
	
	/**
	 * Numero serial de la clase.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Identificador de producto.
	 */
	private int idProducto;
	/**
	 * Codigo de sistema del producto.
	 */
	private String articulo;
	/**
	 * Nombre del producto.
	 */
	private String nombre;
	/**
	 * Unidad de medida del producto.
	 */
	private String unidad;
	/**
	 * Nombre del proveedor.
	 */
	private String proveedor;
	/**
	 * Stock existente del prodcuto.
	 */
	private Double stock;
	/**
	 * Precio del producto.
	 */
	private Float precio;

	/**
	 * Precio de costo del producto.
	 */
	private Double costo;
	/**
	 * Valor del ila.
	 */
	private Double ila;
	
	/**
	 * Obtiene el valor del atributo idProducto.
	 * @return  El valor de idProducto.
	 */
	public final int getIdProducto() {
		return idProducto;
	}

	/**
	 * Establece el valor al atributo idProducto.
	 * @param idProducto  el valor a establecer a idProducto.
	 */
	public final void setIdProducto(final int idProducto) {
		this.idProducto = idProducto;
	}

	/**
	 * Obtiene el valor del atributo articulo.
	 * @return  El valor de articulo.
	 */
	public final String getArticulo() {
		return articulo;
	}

	/**
	 * Establece el valor al atributo articulo.
	 * @param articulo  el valor a establecer a articulo.
	 */
	public final void setArticulo(final String articulo) {
		this.articulo = articulo;
	}

	/**
	 * Obtiene el valor del atributo nombre.
	 * @return  El valor de nombre.
	 */
	public final String getNombre() {
		return nombre;
	}

	/**
	 * Establece el valor al atributo nombre.
	 * @param nombre  el valor a establecer a nombre.
	 */
	public final void setNombre(final String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Obtiene el valor del atributo unidad.
	 * @return  El valor de unidad.
	 */
	public final String getUnidad() {
		return unidad;
	}

	/**
	 * Establece el valor al atributo unidad.
	 * @param unidad  el valor a establecer a unidad.
	 */
	public final void setUnidad(final String unidad) {
		this.unidad = unidad;
	}

	/**
	 * Obtiene el valor del atributo proveedor.
	 * @return  El valor de proveedor.
	 */
	public final String getProveedor() {
		return proveedor;
	}

	/**
	 * Establece el valor al atributo proveedor.
	 * @param proveedor  el valor a establecer a proveedor.
	 */
	public final void setProveedor(final String proveedor) {
		this.proveedor = proveedor;
	}

	/**
	 * Obtiene el valor del atributo stock.
	 * @return  El valor de stock.
	 */
	public final Double getStock() {
		return stock;
	}

	/**
	 * Establece el valor al atributo stock.
	 * @param stock  el valor a establecer a stock.
	 */
	public final void setStock(final Double stock) {
		this.stock = stock;
	}

	/**
	 * Obtiene el valor del atributo precio.
	 * @return  El valor de precio.
	 */
	public final Float getPrecio() {
		return precio;
	}

	/**
	 * Establece el valor al atributo precio.
	 * @param precion  el valor a establecer a precio.
	 */
	public final void setPrecio(final Float precio) {
		this.precio = precio;
	}

	/**
	 * Obtiene el valor del atributo costo.
	 * @return  El valor de costo.
	 */
	public final Double getCosto() {
		return costo;
	}

	/**
	 * Establece el valor al atributo costo.
	 * @param costo  el valor a establecer a costo.
	 */
	public final void setCosto(final Double costo) {
		this.costo = costo;
	}

	/**
	 * Obtiene el valor del atributo ila.
	 * @return  El valor de ila.
	 */
	public final Double getIla() {
		return ila;
	}

	/**
	 * Establece el valor al atributo ila.
	 * @param ila  el valor a establecer a ila.
	 */
	public final void setIla(final Double ila) {
		this.ila = ila;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return nombre;
	}
}
