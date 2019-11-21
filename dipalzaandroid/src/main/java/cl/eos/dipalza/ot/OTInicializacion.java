package cl.eos.dipalza.ot;

import java.io.Serializable;

/**
 * Objeto de transporte que almacena la informacion de configuracion del equipo.
 * 
 * @author cursor
 */
public class OTInicializacion implements Serializable, IOTObject
{

	/**
	 * Numero de serie.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Vendedor asociado a la aplicacion.
	 * @uml.property  name="vendedor"
	 */
	private String vendedor;
	/**
	 * Nombre del vendedor.
	 * @uml.property  name="nombreVendedor"
	 */
	private String nombreVendedor;
	/**
	 * Ruta del primer dia.
	 * @uml.property  name="ruta1"
	 */
	private String ruta1;
	/**
	 * Ruta del primer dia.
	 * @uml.property  name="ruta2"
	 */
	private String ruta2;
	/**
	 * Ruta del primer dia.
	 * @uml.property  name="ruta3"
	 */
	private String ruta3;
	/**
	 * Ruta del primer dia.
	 * @uml.property  name="ruta4"
	 */
	private String ruta4;
	/**
	 * Ruta del primer dia.
	 * @uml.property  name="ruta5"
	 */
	private String ruta5;
	/**
	 * Ruta del primer dia.
	 * @uml.property  name="ruta6"
	 */
	private String ruta6;
	/**
	 * Ruta del primer dia.
	 * @uml.property  name="iva"
	 */
	private Double iva;
	/**
	 * Ruta del primer dia.
	 * @uml.property  name="ila"
	 */
	private Double ila;
	/**
	 * Ruta del primer dia.
	 * @uml.property  name="ip"
	 */
	private String ip;

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
	 * Obtiene el valor del atributo ruta1.
	 * @return  El valor de ruta1.
	 * @uml.property  name="ruta1"
	 */
	public final String getRuta1() {
		return ruta1;
	}

	/**
	 * Establece el valor al atributo ruta1.
	 * @param ruta1  el valor a establecer a ruta1.
	 * @uml.property  name="ruta1"
	 */
	public final void setRuta1(String ruta1) {
		this.ruta1 = ruta1;
	}

	/**
	 * Obtiene el valor del atributo ruta2.
	 * @return  El valor de ruta2.
	 * @uml.property  name="ruta2"
	 */
	public final String getRuta2() {
		return ruta2;
	}

	/**
	 * Establece el valor al atributo ruta2.
	 * @param ruta2  el valor a establecer a ruta2.
	 * @uml.property  name="ruta2"
	 */
	public final void setRuta2(String ruta2) {
		this.ruta2 = ruta2;
	}

	/**
	 * Obtiene el valor del atributo ruta3.
	 * @return  El valor de ruta3.
	 * @uml.property  name="ruta3"
	 */
	public final String getRuta3() {
		return ruta3;
	}

	/**
	 * Establece el valor al atributo ruta3.
	 * @param ruta3  el valor a establecer a ruta3.
	 * @uml.property  name="ruta3"
	 */
	public final void setRuta3(String ruta3) {
		this.ruta3 = ruta3;
	}

	/**
	 * Obtiene el valor del atributo ruta4.
	 * @return  El valor de ruta4.
	 * @uml.property  name="ruta4"
	 */
	public final String getRuta4() {
		return ruta4;
	}

	/**
	 * Establece el valor al atributo ruta4.
	 * @param ruta4  el valor a establecer a ruta4.
	 * @uml.property  name="ruta4"
	 */
	public final void setRuta4(String ruta4) {
		this.ruta4 = ruta4;
	}

	/**
	 * Obtiene el valor del atributo ruta5.
	 * @return  El valor de ruta5.
	 * @uml.property  name="ruta5"
	 */
	public final String getRuta5() {
		return ruta5;
	}

	/**
	 * Establece el valor al atributo ruta5.
	 * @param ruta5  el valor a establecer a ruta5.
	 * @uml.property  name="ruta5"
	 */
	public final void setRuta5(String ruta5) {
		this.ruta5 = ruta5;
	}

	/**
	 * Obtiene el valor del atributo ruta6.
	 * @return  El valor de ruta6.
	 * @uml.property  name="ruta6"
	 */
	public final String getRuta6() {
		return ruta6;
	}

	/**
	 * Establece el valor al atributo ruta6.
	 * @param ruta6  el valor a establecer a ruta6.
	 * @uml.property  name="ruta6"
	 */
	public final void setRuta6(String ruta6) {
		this.ruta6 = ruta6;
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
	 * Obtiene el valor del atributo ip.
	 * @return  El valor de ip.
	 * @uml.property  name="ip"
	 */
	public final String getIp() {
		return ip;
	}

	/**
	 * Establece el valor al atributo ip.
	 * @param ip  el valor a establecer a ip.
	 * @uml.property  name="ip"
	 */
	public final void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * Obtiene el valor del atributo nombreVendedor.
	 * @return  El valor de nombreVendedor.
	 * @uml.property  name="nombreVendedor"
	 */
	public String getNombreVendedor() {
		return nombreVendedor;
	}

	/**
	 * Establece el valor al atributo nombreVendedor.
	 * @param nombreVendedor  el valor a establecer a nombreVendedor.
	 * @uml.property  name="nombreVendedor"
	 */
	public void setNombreVendedor(String nombreVendedor) {
		this.nombreVendedor = nombreVendedor;
	}

}
