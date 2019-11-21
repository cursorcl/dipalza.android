package cl.eos.dipalza.ot;

import java.io.Serializable;
import java.sql.Date;

public class OTEVenta implements Serializable, IOTObject {
	
	/**
	 * Correlativo de la venta.
	 */
	public static int identificador = 1;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @uml.property  name="idVenta"
	 */
	private long idVenta;
	/**
	 * Identificador del cliente en la tabla de clientes.
	 */
	private long idCliente;
	/**
	 * Cliente de la venta.
	 */
	private String cliente;
	/**
	 * Condicion de ventas.
	 */
	private int condicionVenta;
	/**
	 * Valor neto total de la venta.
	 */
	private Double neto;
	/**
	 * Valor del iva total de la venta.
	 */
	private Double iva;
	/**
	 * Valor del total ila.
	 */
	private Double ila;
	/**
	 * Identificador del vendedor asociado a la venta.
	 */
	private String vendedor;
	/**
	 * Fecha de la venta.
	 */
	private Date fecha;

	/**
	 * Obtiene el valor del atributo idVenta.
	 * @return  El valor de idVenta.
	 */
	public final long getIdVenta() {
		return idVenta;
	}

	/**
	 * Establece el valor al atributo idVenta.
	 * @param idVenta  el valor a establecer a idVenta.
	 * @uml.property  name="idVenta"
	 */
	public final void setIdVenta(long idVenta) {
		this.idVenta = idVenta;
	}

	/**
	 * Obtiene el valor del atributo idCliente.
	 * @return  El valor de idCliente.
	 * @uml.property  name="idCliente"
	 */
	public final long getIdCliente() {
		return idCliente;
	}

	/**
	 * Establece el valor al atributo idCliente.
	 * @param idCliente  el valor a establecer a idCliente.
	 * @uml.property  name="idCliente"
	 */
	public final void setIdCliente(long idCliente) {
		this.idCliente = idCliente;
	}

	/**
	 * Obtiene el valor del atributo condicionVenta.
	 * @return  El valor de condicionVenta.
	 * @uml.property  name="condicionVenta"
	 */
	public final int getCondicionVenta() {
		return condicionVenta;
	}

	/**
	 * Establece el valor al atributo condicionVenta.
	 * @param condicionVenta  el valor a establecer a condicionVenta.
	 * @uml.property  name="condicionVenta"
	 */
	public final void setCondicionVenta(int condicionVenta) {
		this.condicionVenta = condicionVenta;
	}

	/**
	 * Obtiene el valor del atributo neto.
	 * @return  El valor de neto.
	 * @uml.property  name="neto"
	 */
	public final Double getNeto() {
		return neto;
	}

	/**
	 * Establece el valor al atributo neto.
	 * @param neto  el valor a establecer a neto.
	 * @uml.property  name="neto"
	 */
	public final void setNeto(Double neto) {
		this.neto = neto;
	}

	/**
	 * Obtiene el valor del atributo iva.
	 * @return  El valor de iva.
	 * @uml.property  name="iva"
	 */
	public final Double getIva() {
		return iva;
	}

	/**
	 * Establece el valor al atributo iva.
	 * @param iva  el valor a establecer a iva.
	 * @uml.property  name="iva"
	 */
	public final void setIva(Double iva) {
		this.iva = iva;
	}

	/**
	 * Obtiene el valor del atributo ila.
	 * @return  El valor de ila.
	 * @uml.property  name="ila"
	 */
	public final Double getIla() {
		return ila;
	}

	/**
	 * Establece el valor al atributo ila.
	 * @param ila  el valor a establecer a ila.
	 * @uml.property  name="ila"
	 */
	public final void setIla(Double ila) {
		this.ila = ila;
	}

	/**
	 * Obtiene el valor del atributo vendedor.
	 * @return  El valor de vendedor.
	 * @uml.property  name="vendedor"
	 */
	public final String getVendedor() {
		return vendedor;
	}

	/**
	 * Establece el valor al atributo vendedor.
	 * @param vendedor  el valor a establecer a vendedor.
	 * @uml.property  name="vendedor"
	 */
	public final void setVendedor(String vendedor) {
		this.vendedor = vendedor;
	}

	/**
	 * Obtiene el valor del atributo fecha.
	 * @return  El valor de fecha.
	 * @uml.property  name="fecha"
	 */
	public final Date getFecha() {
		return fecha;
	}

	/**
	 * Establece el valor al atributo fecha.
	 * @param fecha  el valor a establecer a fecha.
	 * @uml.property  name="fecha"
	 */
	public final void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return
	 * @uml.property  name="cliente"
	 */
	public String getCliente() {
		return cliente;
	}

	/**
	 * @param cliente
	 * @uml.property  name="cliente"
	 */
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
}
