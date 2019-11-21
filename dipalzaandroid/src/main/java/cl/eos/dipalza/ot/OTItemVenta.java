package cl.eos.dipalza.ot;

import java.io.Serializable;

public class OTItemVenta implements IOTObject, Serializable {

	/**
	 * Numero serial.
	 */
	private static final long serialVersionUID = 1L;
	private int codigoProducto;
	private String articulo;
	private double cantidad;
	private double neto;
	private double descuento;
	private double ila;
	private float precio;

	public OTItemVenta() {
		this.codigoProducto = 0;
		this.articulo = "";
		this.cantidad = 0.0D;
		this.neto = 0.0D;
		this.descuento = 0.0D;
		this.ila = 0.0D;
		this.precio =  0f;
	}

	public OTItemVenta(short codigo, String articulo, double cantidad,
			double total, double descuento, double ila, float precio) {
		this.codigoProducto = codigo;
		this.articulo = articulo;
		this.cantidad = cantidad;
		this.neto = total;
		this.descuento = descuento;
		this.ila = ila;
		this.precio =  precio;
	}

	public double getCantidad() {
		return this.cantidad;
	}

	public int getCodigoProducto() {
		return this.codigoProducto;
	}

	public double getNeto() {
		return this.neto;
	}

	public double getIla() {
		return this.ila;
	}

	public void setIla(double ila) {
		this.ila = ila;
	}

	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}

	public void setCodigoProducto(int codigoProducto) {
		this.codigoProducto = codigoProducto;
	}

	public void setNeto(double total) {
		this.neto = total;
	}

	public String getArticulo() {
		return this.articulo;
	}

	public void setArticulo(String articulo) {
		this.articulo = articulo;
	}

	public double getDescuento() {
		return this.descuento;
	}

	public void setDescuento(double descuento) {
		this.descuento = descuento;
	}

	
	public final float getPrecio() {
    return precio;
  }

  public final void setPrecio(float precio) {
    this.precio = precio;
  }

  public String toString() {
		return this.articulo + "  " + this.cantidad;
	}
}
