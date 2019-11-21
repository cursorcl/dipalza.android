package cl.eos.dipalza.ot;

import java.io.Serializable;

public class OTProductoVenta implements Serializable, IOTObject {

	/**
	 * Numero serial de la clase.
	 */
	private static final long serialVersionUID = -2819139684540085721L;
	/**
	 * Identificador de producto local.
	 */
	private int identificadorProducto;
	/**
	 * Identificador de producto en base de datos.
	 */
	private int idProducto;
	/**
	 * Nombre del producto.
	 */
	private String nombre;
	/**
	 * Cantidad producto.
	 */
	private double cantidad;
	/**
	 * Precio producto.
	 */
	private double valorNeto;
	/**
	 * Descuento producto.
	 */
	private double descuento;
	/**
	 * Valor ila.
	 */
	private double ila;
	
	/**
	 * El precio del producto o el que el cliente ha modificado.
	 */
	private float precio;
	/**
	 * Producto.
	 */
	private OTProducto producto;

	/**
	 * 
	 * @return
	 */
	public int getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getCantidad() {
		return cantidad;
	}

	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}

	public double getValorNeto() {
		return valorNeto;
	}

	public void setValorNeto(double valor) {
		this.valorNeto = valor;
	}

	public OTProducto getProducto() {
		return producto;
	}

	public void setProducto(OTProducto producto) {
		this.producto = producto;
	}

	public double getDescuento() {
		return descuento;
	}

	public void setDescuento(double descuento) {
		this.descuento = descuento;
	}

	public int getIdentificadorProducto() {
		return identificadorProducto;
	}

	public void setIdentificadorProducto(int identificadorProducto) {
		this.identificadorProducto = identificadorProducto;
	}

	/**
	 * @return the ila
	 */
	public double getIla() {
		return ila;
	}

	/**
	 * @param ila the ila to set
	 */
	public void setIla(double ila) {
		this.ila = ila;
	}

  public final float getPrecio() {
    return this.precio;
  }

  public final void setPrecio(float precio) {
    this.precio = precio;
  }

}
