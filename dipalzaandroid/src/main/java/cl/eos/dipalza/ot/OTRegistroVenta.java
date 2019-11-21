package cl.eos.dipalza.ot;

import java.io.Serializable;

public class OTRegistroVenta implements IOTObject, Serializable {
	/**
	 * Numero serial de la clase.
	 */
	private static final long serialVersionUID = 241217808312437829L;
	/**
	 * Referencia a OTVenta.
	 */
	private OTVenta venta;
	/**
	 * Identificador de venta local.
	 */
	private int identificadorVenta;

	/**
	 * @return the venta
	 */
	public OTVenta getVenta() {
		return venta;
	}

	/**
	 * @param venta
	 *            the venta to set
	 */
	public void setVenta(OTVenta venta) {
		this.venta = venta;
	}

	/**
	 * @return the identificadorVenta
	 */
	public int getIdentificadorVenta() {
		return identificadorVenta;
	}

	/**
	 * @param identificadorVenta
	 *            the identificadorVenta to set
	 */
	public void setIdentificadorVenta(int identificadorVenta) {
		this.identificadorVenta = identificadorVenta;
	}

}
