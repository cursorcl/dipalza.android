package cl.eos.dipalza.database;

import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;
import android.util.Log;
import cl.eos.dipalza.ot.OTCliente;
import cl.eos.dipalza.ot.OTEVenta;
import cl.eos.dipalza.ot.OTItemVenta;
import cl.eos.dipalza.ot.OTProducto;
import cl.eos.dipalza.ot.OTProductoVenta;
import cl.eos.dipalza.ot.OTResumenVentas;
import cl.eos.dipalza.ot.OTVenta;

public class DBDipalza extends SQLiteOpenHelper {
	private static final String DELETE_FROM = "DELETE FROM ";
	private static final String TAG = "DBDipalza";
	private static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "sqldipalza.db";
	private static final String CLIENTES = "Clientes";
	private static final String DVENTA = "DVenta";
	private static final String EVENTA = "EVenta";
	private static final String PRODUCTOS = "Productos";
	private static final String ESPECIALES = "Especiales";

	SQLiteDatabase db;

	String sqlCreateClientes = "CREATE TABLE Clientes (idCliente INTEGER PRIMARY KEY, ruta TEXT, vendendor TEXT, codigo TEXT, rut TEXT, nombre TEXT, direccion TEXT, telefono TEXT, comuna TEXT, ciudad TEXT)";
	String sqlCreateDVenta = "CREATE TABLE DVenta (idVenta INTEGER, idProducto INTEGER, cantidad NUMERIC, neto NUMERIC, descuento NUMERIC, ila NUMERIC, precio NUMERIC)";
	String sqlCreateEVenta = "CREATE TABLE EVenta (idVenta INTEGER PRIMARY KEY, idCliente INTEGER, condicionVenta INTEGER, neto NUMERIC, iva NUMERIC, ila NUMERIC, vendedor TEXT, fecha DATETIME)";
	String sqlCreateProductos = "CREATE TABLE Productos (idProducto INTEGER PRIMARY KEY, articulo TEXT, nombre TEXT, unidad TEXT, proveedor TEXT, stock NUMERIC, precio NUMERIC, costo NUMERIC, ila NUMERIC)";
	String sqlCreateEspeciales = "CREATE TABLE Especiales (articulo TEXT)";
	String sqlCreateidxClientes = "CREATE INDEX idxClientes ON Clientes(idCliente ASC)";
	String sqlCreateidxVentas = "CREATE INDEX idxVentas ON EVenta(idVenta ASC)";
	String sqlCreateidxProductos = "CREATE INDEX idxProductos ON Productos(idProducto ASC)";
	String sqlCreateidxEspecialesArticulo = "CREATE INDEX idxEspecialesArticulo ON Especiales(articulo ASC)";

	private String vendedor;
	private String ruta;

	public DBDipalza(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		vendedor = prefs.getString("pref_vendedor", "");
		ruta = prefs.getString("pref_ruta", "");
		
	}

	public void open() {
		db = getWritableDatabase();
	}

	public void close() {
		db.close();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.w(TAG, "Creando base de datos");
		db.execSQL(sqlCreateClientes);
		db.execSQL(sqlCreateDVenta);
		db.execSQL(sqlCreateEVenta);
		db.execSQL(sqlCreateEspeciales);
		db.execSQL(sqlCreateProductos);
		db.execSQL(sqlCreateidxClientes);
		db.execSQL(sqlCreateidxVentas);
		db.execSQL(sqlCreateidxProductos);
		db.execSQL(sqlCreateidxEspecialesArticulo);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(TAG, "Actualizando base de datos desde la versión " + oldVersion
				+ " a la  " + newVersion
				+ ", la cual destruir toda su información.");
		db.execSQL("DROP TABLE IF EXISTS " + CLIENTES);
		db.execSQL("DROP TABLE IF EXISTS " + DVENTA);
		db.execSQL("DROP TABLE IF EXISTS " + EVENTA);
		db.execSQL("DROP TABLE IF EXISTS " + PRODUCTOS);
		db.execSQL("DROP TABLE IF EXISTS " + ESPECIALES);
		db.execSQL(sqlCreateClientes);
		db.execSQL(sqlCreateDVenta);
		db.execSQL(sqlCreateEVenta);
		db.execSQL(sqlCreateProductos);
		db.execSQL(sqlCreateEspeciales);
		db.execSQL(sqlCreateidxClientes);
		db.execSQL(sqlCreateidxVentas);
		db.execSQL(sqlCreateidxProductos);
		db.execSQL(sqlCreateidxEspecialesArticulo);
	}

	/**
	 * Obtiene un cliente desde la base de datos.
	 * 
	 * @param id
	 *            Identificador del cliente.
	 * @return Cliente que cumple con el identificador.
	 */
	public OTCliente getCliente(long id) {
		OTCliente cliente = null;
		Cursor cursor = this.db.query(CLIENTES, null, "idCliente = " + id,
				null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				String rut = cursor.getString(4);
				String nombre = cursor.getString(5);
				String direccion = cursor.getString(6);
				String telefono = cursor.getString(7);
				String comuna = cursor.getString(8);
				String ciudad = cursor.getString(9);
				cliente = new OTCliente();
				cliente.setIdCliente(cursor.getLong(0));
				cliente.setCodigo(cursor.getString(3).trim());
				cliente.setRut(rut);
				cliente.setNombre(nombre);
				cliente.setDireccion(direccion);
				cliente.setTelefono(telefono);
				cliente.setComuna(comuna);
				cliente.setCiudad(ciudad);
			} while (cursor.moveToNext());
		}
		if (!cursor.isClosed()) {
			cursor.close();
		}
		return cliente;
	}

	/**
	 * Obtiene la lista de clientes.
	 * 
	 * @return Lista de clientes.
	 */
	public List<OTCliente> getClientes() {
		List<OTCliente> list = new ArrayList<OTCliente>();
		Cursor cursor = this.db.query(CLIENTES, null, null, null, null, null,
				"nombre ASC");
		if (cursor.moveToFirst()) {
			do {
				OTCliente cliente = new OTCliente();
				// idCliente, ruta, vendendor, codigo, rut, nombre, direccion,
				// telefono,comuna ,ciudad
				cliente.setIdCliente(cursor.getLong(0));
				if (cursor.getString(3) != null) {
					cliente.setCodigo(cursor.getString(3).trim());
				}
				if (cursor.getString(4) != null) {
					cliente.setRut(cursor.getString(4).trim());
				}
				if (cursor.getString(5) != null) {
					cliente.setNombre(cursor.getString(5).trim());
				}
				if (cursor.getString(6) != null) {
					cliente.setDireccion(cursor.getString(6).trim());
				}
				if (cursor.getString(7) != null) {
					cliente.setTelefono(cursor.getString(7).trim());
				}
				if (cursor.getString(8) != null) {
					cliente.setComuna(cursor.getString(8).trim());
				}
				if (cursor.getString(9) != null) {
					cliente.setCiudad(cursor.getString(9).trim());
				}
				list.add(cliente);
			} while (cursor.moveToNext());
		}
		if (!cursor.isClosed()) {
			cursor.close();
		}
		return list;
	}

	public List<OTProducto> getProductos() {
		List<OTProducto> list = new ArrayList<OTProducto>();
		Cursor cursor = this.db.query(PRODUCTOS, null, null, null, null, null,
				"nombre ASC");
		if (cursor.moveToFirst()) {
			do {
				OTProducto producto = new OTProducto();
				// idProducto, articulo, nombre, unidad, proveedor, stock,
				// precio, costo, ila
				producto.setIdProducto(cursor.getInt(0));
				producto.setArticulo(cursor.getString(1).trim());
				producto.setNombre(cursor.getString(2).trim());
				producto.setUnidad(cursor.getString(3).trim());
				producto.setProveedor(cursor.getString(4).trim());
				producto.setStock(cursor.getDouble(5));
				producto.setPrecio(cursor.getFloat(6));
				producto.setCosto(cursor.getDouble(7));
				producto.setIla(cursor.getDouble(8));
				list.add(producto);
			} while (cursor.moveToNext());
		}
		if (!cursor.isClosed()) {
			cursor.close();
		}
		return list;
	}

	public OTProducto getProducto(int idProducto) {
		OTProducto producto = null;
		Cursor cursor = this.db.query(PRODUCTOS, null, "idProducto = "
				+ idProducto, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				producto = new OTProducto();
				// idProducto, articulo, nombre, unidad, proveedor, stock,
				// precio, costo, ila
				producto.setIdProducto(cursor.getInt(0));
				producto.setArticulo(cursor.getString(1).trim());
				producto.setNombre(cursor.getString(2).trim());
				producto.setUnidad(cursor.getString(3).trim());
				producto.setProveedor(cursor.getString(4).trim());
				producto.setStock(cursor.getDouble(5));
				producto.setPrecio(cursor.getFloat(6));
				producto.setCosto(cursor.getDouble(7));
				producto.setIla(cursor.getDouble(8));
			} while (cursor.moveToNext());
		}
		if (!cursor.isClosed()) {
			cursor.close();
		}
		return producto;
	}

	public void grabarProducto(final OTProducto producto) {
		ContentValues registro = new ContentValues();
		Log.w(TAG, "Productos " + producto.getArticulo());
		registro.put("idProducto", producto.getIdProducto());
		registro.put("articulo", producto.getArticulo().trim());
		registro.put("nombre", producto.getNombre().trim());
		registro.put("unidad", producto.getUnidad().trim());
		registro.put("proveedor", producto.getProveedor().trim());
		registro.put("stock", producto.getStock());
		registro.put("precio", producto.getPrecio());
		registro.put("costo", producto.getCosto());
		registro.put("ila", producto.getIla());
		db.insert(PRODUCTOS, null, registro);
	}

	public void grabarCliente(final OTCliente cliente) {
	  try
	  {
		ContentValues registro = new ContentValues();
		Log.w(TAG, "Insertando cliente " + cliente.getNombre());
		registro.put("idCliente", cliente.getIdCliente());
		registro.put("ruta", ruta);
		registro.put("vendendor", vendedor);
		registro.put("codigo", cliente.getCodigo());
		registro.put("rut", cliente.getRut());
		registro.put("nombre", cliente.getNombre());
		registro.put("direccion", cliente.getDireccion());
		registro.put("telefono", cliente.getTelefono());
		registro.put("comuna", cliente.getComuna());
		registro.put("ciudad", cliente.getCiudad());
		db.insert(CLIENTES, null, registro);
	  }
	  catch(Exception e)
	  {
	    e.printStackTrace();
	  }
	}

	/**
	 * Elimina todos los registros almacenados en la base de datos. Se incia el
	 * borrado de las ventas, luego clientes y finalmente productos.
	 */
	public void eliminarRegistros() {
		eliminarVentas();
		eliminarClientes();
		eliminarProductos();
	}

	/**
	 * Borra todos los registros de clientes.
	 */
	public void eliminarClientes() {
		db.execSQL(DELETE_FROM + CLIENTES);
	}

	/**
	 * Elimina todos los registros de productos.
	 */
	public void eliminarProductos() {
		db.execSQL(DELETE_FROM + PRODUCTOS);
	}

	/**
	 * Elimina todos los registros de ventas. Borra primero el detalle de ventas
	 * y luego los encabezados.
	 */
	public void eliminarVentas() {
		db.execSQL(DELETE_FROM + DVENTA);
		db.execSQL(DELETE_FROM + EVENTA);
	}

	/**
	 *  Grabar una venta en la BD
	 * @param venta Registro de venta.
	 */
	public void grabarVenta(OTVenta venta) {
		OTEVenta encabezado = venta.getEncabezado();
		List<OTItemVenta> registros = venta.getRegistrosVenta();
		long id = encabezado.getIdVenta();
		if (id == 0) {
			id = System.currentTimeMillis();
		} else {
			borrarVenta(encabezado.getIdVenta());
		}
		// idVenta, idCliente, condicionVenta, neto, iva, ila, vendedor, fecha
		ContentValues registro = new ContentValues();
		Log.w(TAG, "Insertando Encabezado de ventas " + encabezado.getIdVenta());
		registro.put("idVenta", id);
		registro.put("idCliente", encabezado.getIdCliente());
		registro.put("condicionVenta", encabezado.getCondicionVenta());
		registro.put("neto", encabezado.getNeto());
		registro.put("iva", encabezado.getIva());
		registro.put("ila", encabezado.getIla());
		registro.put("vendedor", encabezado.getVendedor());
		registro.put("fecha", encabezado.getFecha().getTime());
		db.insert(EVENTA, null, registro);
		// idVenta, idProducto, cantidad, neto, descuento, ila, precio
		if (registros != null && !registros.isEmpty()) {
			for (OTItemVenta item : registros) {
				registro = new ContentValues();
				Log.w(TAG,
						"Insertando Detalle de ventas "
								+ encabezado.getIdVenta());
				registro.put("idVenta", id);
				registro.put("idProducto", item.getCodigoProducto());
				registro.put("cantidad", item.getCantidad());
				registro.put("neto", item.getNeto());
				registro.put("descuento", item.getDescuento());
				registro.put("ila", item.getIla());
				registro.put("precio", item.getPrecio());
				db.insert(DVENTA, null, registro);
			}
			rebajarStock(id);
		}
	}

	private void rebajarStock(long idVenta) {
		Cursor cursor = this.db.query(DVENTA, null, "idVenta = " + idVenta,
				null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				int idProducto = cursor.getInt(1);
				String sIdProducto = String.valueOf(idProducto);
				double cantidad = cursor.getDouble(2);

				OTProducto producto = getProducto(idProducto);
				if (producto != null) {
					cantidad = producto.getStock() - cantidad;
					ContentValues content = new ContentValues();
					content.put("stock", cantidad);
					db.update(PRODUCTOS, content, "idProducto" + "=?",
							new String[] { sIdProducto });
				}
			} while (cursor.moveToNext());
		}
		if (!cursor.isClosed()) {
			cursor.close();
		}

	}

	public void borrarVenta(long idVenta) {

		aumentarStock(idVenta);

		String[] params = { String.valueOf(idVenta) };

		db.delete(DVENTA, "idVenta=?", params);
		db.delete(EVENTA, "idVenta=?", params);
	}

	private void aumentarStock(long idVenta) {
		Cursor cursor = this.db.query(DVENTA, null, "idVenta = " + idVenta,
				null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				int idProducto = cursor.getInt(1);
				String sIdProducto = String.valueOf(idProducto);
				double cantidad = cursor.getDouble(2);

				OTProducto producto = getProducto(idProducto);
				if (producto != null) {
					cantidad = producto.getStock() + cantidad;
					ContentValues content = new ContentValues();
					content.put("stock", cantidad);
					db.update(PRODUCTOS, content, "idProducto" + "=?",
							new String[] { sIdProducto });
				}

			} while (cursor.moveToNext());
		}
		if (!cursor.isClosed()) {
			cursor.close();
		}

	}

	public OTVenta obtenerVenta(long idVenta) {
		OTVenta venta = new OTVenta();
		OTEVenta eVenta = null;
		Cursor cursor = this.db.query(EVENTA, null, "idVenta = " + idVenta,
				null, null, null, null);
		if (cursor.moveToFirst()) {
			eVenta = new OTEVenta();
			// idVenta, idCliente, condicionVenta, neto, iva, ila, vendedor,
			// fecha
			do {
				eVenta.setIdVenta(cursor.getInt(0));
				eVenta.setIdCliente(cursor.getLong(1));
				eVenta.setCondicionVenta(cursor.getInt(2));
				eVenta.setNeto(cursor.getDouble(3));
				eVenta.setIva(cursor.getDouble(4));
				eVenta.setIla(cursor.getDouble(5));
				eVenta.setVendedor(cursor.getString(6));
				eVenta.setFecha(new Date(cursor.getLong(7)));
			} while (cursor.moveToNext());
		}
		if (!cursor.isClosed()) {
			cursor.close();
		}
		List<OTItemVenta> itemesVenta = null;
		// idVenta, idProducto, cantidad, neto, descuento, ila
		cursor = this.db.query(DVENTA, null, "idVenta = " + idVenta, null,
				null, null, null);
		if (cursor.moveToFirst()) {
			do {
				OTItemVenta itemVenta = new OTItemVenta();
				itemVenta.setArticulo(cursor.getString(1));
				itemVenta.setCantidad(cursor.getDouble(2));
				itemVenta.setNeto(cursor.getDouble(3));
				itemVenta.setDescuento(cursor.getDouble(4));
				itemVenta.setIla(cursor.getDouble(6));
				if (itemesVenta == null) {
					itemesVenta = new LinkedList<OTItemVenta>();
				}
				itemesVenta.add(itemVenta);
			} while (cursor.moveToNext());
		}
		if (!cursor.isClosed()) {
			cursor.close();
		}
		venta.setEncabezado(eVenta);
		venta.setRegistrosVenta(itemesVenta);
		return venta;
	}

	public List<OTVenta> obtenerListadoVentas() {
		List<OTVenta> listadoVenta = new LinkedList<OTVenta>();
		OTVenta venta;
		OTEVenta eVenta;
		Cursor cursor = this.db.query(EVENTA, null, null, null, null, null,
				"idVenta ASC");
		if (cursor.moveToFirst()) {
			// idVenta, idCliente, condicionVenta, neto, iva, ila, vendedor,
			// fecha
			do {
				venta = new OTVenta();
				eVenta = new OTEVenta();
				eVenta.setIdVenta(cursor.getLong(0));
				eVenta.setIdCliente(cursor.getLong(1));
				eVenta.setCondicionVenta(cursor.getInt(2));
				eVenta.setNeto(cursor.getDouble(3));
				eVenta.setIva(cursor.getDouble(4));
				eVenta.setIla(cursor.getDouble(5));
				eVenta.setVendedor(cursor.getString(6));
				eVenta.setFecha(new Date(cursor.getLong(7)));
				OTCliente cliente = getCliente(eVenta.getIdCliente());
				if (cliente != null) {
					eVenta.setCliente(cliente.getNombre());
				}
				List<OTItemVenta> itemesVenta = null;
				// idVenta, idProducto, cantidad, neto, descuento, ila
				Cursor cursorDetalle = this.db.query(DVENTA, null, "idVenta = "
						+ eVenta.getIdVenta(), null, null, null, "idVenta ASC");
				if (cursorDetalle.moveToFirst()) {
					do {
						OTItemVenta itemVenta = new OTItemVenta();
						itemVenta.setCodigoProducto(cursorDetalle.getInt(1));
						OTProducto producto = getProducto(itemVenta
								.getCodigoProducto());
						if (producto != null) {
							itemVenta.setArticulo(producto.getArticulo());
							itemVenta.setCantidad(cursorDetalle.getDouble(2));
							itemVenta.setNeto(cursorDetalle.getDouble(3));
							itemVenta.setDescuento(cursorDetalle.getDouble(4));
							itemVenta.setIla(cursorDetalle.getDouble(5));
							itemVenta.setPrecio(cursorDetalle.getFloat(6));
							if (itemesVenta == null) {
								itemesVenta = new LinkedList<OTItemVenta>();
							}
							itemesVenta.add(itemVenta);
						}
					} while (cursorDetalle.moveToNext());
				}
				if (!cursorDetalle.isClosed()) {
					cursorDetalle.close();
				}
				venta.setEncabezado(eVenta);
				venta.setRegistrosVenta(itemesVenta);
				listadoVenta.add(venta);
			} while (cursor.moveToNext());
		}
		if (!cursor.isClosed()) {
			cursor.close();
		}
		return listadoVenta;
	}

	/**
	 * Obtiene el listado de los {@link OTProductoVenta} desde la base de datos.
	 * 
	 * @param idVenta
	 *            Identificador de la venta a buscar.
	 * @return Listado de OTProductoVenta.
	 */
	public List<OTProductoVenta> obtenerItemesVenta(long idVenta) {
		List<OTProductoVenta> itemesVenta = null;
		// idVenta, idProducto, cantidad, neto, descuento, ila
		Cursor cursor = this.db.query(DVENTA, null, "idVenta = " + idVenta,
				null, null, null, null);
		int identificadorProducto = 0;
		if (cursor.moveToFirst()) {
			do {
				OTProductoVenta itemProducto = new OTProductoVenta();
				itemProducto.setIdentificadorProducto(++identificadorProducto);
				itemProducto.setIdProducto(cursor.getInt(1));
				itemProducto.setDescuento(cursor.getDouble(4));
				itemProducto.setCantidad(cursor.getDouble(2));
				itemProducto.setValorNeto(cursor.getDouble(3));
				itemProducto.setPrecio(cursor.getFloat(6));
				OTProducto producto = getProducto(cursor.getInt(1));
				if (producto != null) {
					itemProducto.setNombre(producto.getNombre());

					itemProducto.setProducto(producto);
					if (itemesVenta == null) {
						itemesVenta = new LinkedList<OTProductoVenta>();
					}
					itemesVenta.add(itemProducto);
				}
			} while (cursor.moveToNext());
		}
		if (!cursor.isClosed()) {
			cursor.close();
		}
		return itemesVenta;
	}

	String sqlResumenVenta = "select sum(neto), sum(ila), sum(iva) from EVenta";

	public OTResumenVentas obtenerResumenVenta() {
		OTResumenVentas resumen = new OTResumenVentas();
		Cursor cursor = this.db.rawQuery(sqlResumenVenta, null);
		if (cursor.moveToFirst()) {
			do {
				resumen.setNeto(cursor.getDouble(0));
				resumen.setIla(cursor.getDouble(1));
				resumen.setIva(cursor.getDouble(2));
				double bruto = resumen.getNeto() + resumen.getIla()
						+ resumen.getIva();
				resumen.setBruto(bruto);
			} while (cursor.moveToNext());
		}
		if (!cursor.isClosed()) {
			cursor.close();
		}
		return resumen;
	}

	public List<String> obtenerEspeciales()
	{
		List<String> list = new ArrayList<String>();
		Cursor cursor = this.db.query(ESPECIALES, null, null, null, null, null,
				"articulo ASC");
		if (cursor.moveToFirst()) {
			do {
				list.add(cursor.getString(1).trim());
			} while (cursor.moveToNext());
		}
		if (!cursor.isClosed()) {
			cursor.close();
		}
		return list;
	}

	public boolean esEspecial(String articulo)
	{
		Cursor cursor = this.db.query(ESPECIALES, null, "articulo='" + articulo +"'",
				null, null, null, null);
		boolean esEspecial = cursor.moveToFirst();
		if (!cursor.isClosed())
			cursor.close();
		return esEspecial;
	}


    public void grabarEspecial(final String especiales) {
        ContentValues registro = new ContentValues();
        registro.put("articulo", especiales);
        db.insert(ESPECIALES, null, registro);
    }

    public void grabarEspecial(final List<String> especiales) {
        if(especiales == null || especiales.isEmpty())
            return;

        for(String especial: especiales)
            grabarEspecial(especial);
    }

    public void eliminarEspeciales() {
        db.execSQL(DELETE_FROM + ESPECIALES);
    }
}
