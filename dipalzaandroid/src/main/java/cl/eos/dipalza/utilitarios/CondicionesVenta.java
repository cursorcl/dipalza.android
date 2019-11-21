package cl.eos.dipalza.utilitarios;
/**
 * Enumerado de condiciones de venta.
 * @author cursor
 */
public enum CondicionesVenta {
	PAGO_CONTADO(1, "CONTADO", "001", 0), 
	PAGO_CREDITO_7_DIAS(2,"CREDITO 7", "002", 7), 
	PAGO_CREDITO_15_DIAS(3, "CREDITO 15", "003", 15), 
	PAGO_EFECTIVO(4, "EFECTIVO", "004", 0), 
	PAGO_CHEQUET_15_DIAS(5, "CHEQUE T. 15", "005", 15), 
	PAGO_CHEQUET_20_DIAS(6, "CHEQUE T. 20", "006", 20), 
	PAGO_CHEQUET_30_DIAS(7,"CHEQUE T. 30", "007", 30), 
	PAGO_CHEQUE_7_DIAS(8, "CHEQUE 7","008", 7);

	/**
	 * Identificador interno.
	 * @uml.property  name="id"
	 */
	private int id;
	/**
	 * Descripción de la condición.
	 * @uml.property  name="descripcion"
	 */
	private String descripcion;
	/**
	 * Código hacia la bd. de la condición.
	 * @uml.property  name="codigo"
	 */
	private String codigo;
	/**
	 * Valor del número de días asociado.
	 * @uml.property  name="dias"
	 */
	private int dias;

	/**
	 * Constructor del enumerado.
	 * @param id Identificador interno.
	 * @param descripcion Descripción de la condición.
	 * @param codigo Código hacia la bd. de la condición.
	 * @param dias Valor del número de días asociado.
	 */
	private CondicionesVenta(int id, String descripcion, String codigo,
			int dias) {
		this.setId(id);
		this.setDescripcion(descripcion);
		this.setCodigo(codigo);
		this.setDias(dias);
	}

	/**
	 * Establece el código.
	 * @param  codigo
	 * @uml.property  name="codigo"
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return
	 * @uml.property  name="codigo"
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param id
	 * @uml.property  name="id"
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return
	 * @uml.property  name="id"
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param descripcion
	 * @uml.property  name="descripcion"
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return
	 * @uml.property  name="descripcion"
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param dias
	 * @uml.property  name="dias"
	 */
	public void setDias(int dias) {
		this.dias = dias;
	}

	/**
	 * @return
	 * @uml.property  name="dias"
	 */
	public int getDias() {
		return dias;
	}
	
	public static CondicionesVenta getById(int id) {
		CondicionesVenta condicion = CondicionesVenta.PAGO_CONTADO;
		switch(id) {
		case 1:
			condicion = CondicionesVenta.PAGO_CONTADO;
			break;
		case 2:
			condicion = CondicionesVenta.PAGO_CREDITO_7_DIAS;
			break;
		case 3:
			condicion = CondicionesVenta.PAGO_CREDITO_15_DIAS;
			break;
		case 4:
			condicion = CondicionesVenta.PAGO_EFECTIVO;
			break;
		case 5:
			condicion = CondicionesVenta.PAGO_CHEQUET_15_DIAS;
			break;
		case 6:
			condicion = CondicionesVenta.PAGO_CHEQUET_20_DIAS;
			break;
		case 7:
			condicion = CondicionesVenta.PAGO_CHEQUET_30_DIAS;
			break;
		case 8:
			condicion = CondicionesVenta.PAGO_CHEQUE_7_DIAS;
			break;
		}
		return condicion;
	}

}
