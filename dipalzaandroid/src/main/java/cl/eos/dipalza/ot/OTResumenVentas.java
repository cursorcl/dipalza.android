package cl.eos.dipalza.ot;

import java.io.Serializable;

public class OTResumenVentas implements IOTObject, Serializable {

	/**
	 * Numero serial de la clase.
	 */
	private static final long serialVersionUID = 1L;

	private double neto;
	private double ila;
	private double iva;
	private double bruto;

	/**
	 * @return the neto
	 */
	public double getNeto() {
		return neto;
	}

	/**
	 * @param neto
	 *            the neto to set
	 */
	public void setNeto(double neto) {
		this.neto = neto;
	}

	/**
	 * @return the ila
	 */
	public double getIla() {
		return ila;
	}

	/**
	 * @param ila
	 *            the ila to set
	 */
	public void setIla(double ila) {
		this.ila = ila;
	}

	/**
	 * @return the iva
	 */
	public double getIva() {
		return iva;
	}

	/**
	 * @param iva
	 *            the iva to set
	 */
	public void setIva(double iva) {
		this.iva = iva;
	}

	/**
	 * @return the bruto
	 */
	public double getBruto() {
		return bruto;
	}

	/**
	 * @param bruto
	 *            the bruto to set
	 */
	public void setBruto(double bruto) {
		this.bruto = bruto;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
