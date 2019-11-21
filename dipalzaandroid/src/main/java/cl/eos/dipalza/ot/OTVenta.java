package cl.eos.dipalza.ot;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Encapsula una venta.
 * @author cursor
 */
public class OTVenta implements IOTObject, Serializable {

	/**
	 * Numero serial de la clase.
	 */
	private static final long serialVersionUID = 6335996027242123305L;
	/**
	 * Contiene la informacion del encabezado de venta.
	 * @uml.property  name="encabezado"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private OTEVenta encabezado;
	/**
	 * Contiene la lista de registros de venta.
	 * @uml.property  name="registrosVenta"
	 */
	private List<OTItemVenta> registrosVenta;
	
	/**
	 * Constructor por defecto.
	 */
	public OTVenta() {
		super();
		this.encabezado = new OTEVenta();
		this.registrosVenta = new LinkedList<OTItemVenta>();
	}

	
	public OTVenta(OTEVenta encabezado, List<OTItemVenta> registrosVenta) {
		super();
		this.encabezado = encabezado;
		this.registrosVenta = registrosVenta;
	}

	/**
	 * Obtiene el valor del atributo encabezado.
	 * @return  El valor de encabezado.
	 * @uml.property  name="encabezado"
	 */
	public final OTEVenta getEncabezado() {
		return encabezado;
	}


	/**
	 * Establece el valor al atributo encabezado.
	 * @param encabezado  el valor a establecer a encabezado.
	 * @uml.property  name="encabezado"
	 */
	public final void setEncabezado(OTEVenta encabezado) {
		this.encabezado = encabezado;
	}


	/**
	 * Obtiene el valor del atributo registrosVenta.
	 * @return El valor de registrosVenta.
	 */
	public final List<OTItemVenta> getRegistrosVenta() {
		return registrosVenta;
	}


	/**
	 * Establece el valor al atributo registrosVenta.
	 * @param registrosVenta el valor a establecer a registrosVenta.
	 */
	public final void setRegistrosVenta(List<OTItemVenta> registrosVenta) {
		this.registrosVenta = registrosVenta;
	}
}
