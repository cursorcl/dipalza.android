package cl.eos.dipalza.ot;

import java.io.Serializable;

public class OTCliente implements Serializable, IOTObject {

	/**
	 * Numero serial de la clase.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * @uml.property  name="idCliente"
	 */
	private long idCliente;
	/**
	 * @uml.property  name="nombre"
	 */
	private String nombre;
	/**
	 * @uml.property  name="direccion"
	 */
	private String direccion;
	/**
	 * @uml.property  name="codigo"
	 */
	private String codigo;
	/**
	 * @uml.property  name="rut"
	 */
	private String rut;
	/**
	 * @uml.property  name="telefono"
	 */
	private String telefono;
	/**
	 * @uml.property  name="comuna"
	 */
	private String comuna;
	/**
	 * @uml.property  name="ciudad"
	 */
	private String ciudad;

	/**
	 * @return
	 * @uml.property  name="idCliente"
	 */
	public long getIdCliente() {
		return idCliente;
	}

	/**
	 * @param idCliente
	 * @uml.property  name="idCliente"
	 */
	public void setIdCliente(long idCliente) {
		this.idCliente = idCliente;
	}

	/**
	 * @return
	 * @uml.property  name="nombre"
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 * @uml.property  name="nombre"
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return
	 * @uml.property  name="direccion"
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * @param direccion
	 * @uml.property  name="direccion"
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	@Override
	public String toString() {
		return nombre;
	}

	/**
	 * Obtiene el valor del atributo codigo.
	 * @return  El valore de codigo.
	 * @uml.property  name="codigo"
	 */
	public final String getCodigo() {
		return codigo;
	}

	/**
	 * Establece el valor al atributo codigo.
	 * @param codigo  el valor codigo a establecer.
	 * @uml.property  name="codigo"
	 */
	public final void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * Obtiene el valor del atributo rut.
	 * @return  El valore de rut.
	 * @uml.property  name="rut"
	 */
	public final String getRut() {
		return rut;
	}

	/**
	 * Establece el valor al atributo rut.
	 * @param rut  el valor rut a establecer.
	 * @uml.property  name="rut"
	 */
	public final void setRut(String rut) {
		this.rut = rut;
	}

	/**
	 * Obtiene el valor del atributo telefono.
	 * @return  El valor de telefono.
	 * @uml.property  name="telefono"
	 */
	public final String getTelefono() {
		return telefono;
	}

	/**
	 * Establece el valor al atributo telefono.
	 * @param telefono  el valor telefono a establecer.
	 * @uml.property  name="telefono"
	 */
	public final void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	 * Obtiene el valor del atributo comuna.
	 * @return  El valor de comuna.
	 * @uml.property  name="comuna"
	 */
	public final String getComuna() {
		return comuna;
	}

	/**
	 * Establece el valor al atributo comuna.
	 * @param comuna  el valor comuna a establecer.
	 * @uml.property  name="comuna"
	 */
	public final void setComuna(String comuna) {
		this.comuna = comuna;
	}

	/**
	 * Obtiene el valor del atributo ciudad.
	 * @return  El valor de ciudad.
	 * @uml.property  name="ciudad"
	 */
	public final String getCiudad() {
		return ciudad;
	}

	/**
	 * Establece el valor al atributo ciudad.
	 * @param ciudad  el valor ciudad a establecer.
	 * @uml.property  name="ciudad"
	 */
	public final void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

}
