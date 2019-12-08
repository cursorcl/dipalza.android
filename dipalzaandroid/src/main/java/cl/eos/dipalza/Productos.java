package cl.eos.dipalza;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.TextView;

import cl.eos.dipalza.factory.Fabrica;
import cl.eos.dipalza.ot.OTProducto;
import cl.eos.dipalza.ot.OTProductoVenta;
import cl.eos.dipalza.ot.decorador.DecProductoArticulo;
import cl.eos.dipalza.ot.decorador.DecProductoNombre;
import cl.eos.dipalza.utilitarios.EmisorMensajes;

public class Productos extends DashboardActivity /* implements TextWatcher */ {
    /**
     * Constante de producto agregado a la venta.
     */
    private static final String PRODUCTO_VENTA = "productoVenta";
    /**
     * Constante de lista de productos.
     */
    public static final String LISTA_PRODUCTOS = "listaProductos";
    /**
     * Campo de autocompletado de datos de productos.
     */
    private AutoCompleteTextView autoCompleteProducto;
    /**
     * Campo de autocompletado de datos de productos por articulo.
     */
    private AutoCompleteTextView autoCompleteArticulo;
    /**
     * Listra de productos decorados por articulo.
     */
    private List<DecProductoArticulo> listaArticulos;
    /**
     * Lista de productos por nombre.
     */
    private List<DecProductoNombre> listaNombres;

    /**
     * Producto seleccionado.
     */
    private OTProducto productoSeleccionado;
    /**
     * Componente grafico.
     */
    private TextView textPrecio;
    /**
     * Componente grafico.
     */
    private TextView textCantidad;
    /**
     * Componente grafico.
     */
    private TextView textDescuento;
    /**
     * Componente grafico.
     */
    private TextView textPrecioNeto;
    /**
     * Componente grafico.
     */
    private TextView txtStock;
    /**
     * Objeto producto en proceso de venta.
     */
    private OTProductoVenta otProductoVenta;
    private TextView txtUnidad;
    private boolean internal;
    private float floatPrecio;
    private double doubleCantidad;
    private double doubleDescuento;
    private double doubleValorNeto;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.productos);
        setTitleFromActivityLabel(R.id.title_text);
        inicializarDecoradoresListaProductos();
        inicializarComponentesGraficos();
        inicializarAutocompletePorArticulo();
        inicializarAutocompletePorNombre();
        Bundle bundle = getIntent().getExtras();
        otProductoVenta = (OTProductoVenta) bundle.get("productoSeleccionado");
        if (otProductoVenta != null) {
            inicializacionModificacionProducto();
        }
    }

    /**
     * Metodo que inicialida componentes de TextView.
     */
    private void inicializarComponentesGraficos() {
        textPrecio = findViewById(R.id.textPrecioProducto);
        textCantidad = findViewById(R.id.textCantidad);
        textDescuento = findViewById(R.id.textDescuento);
        textPrecioNeto = findViewById(R.id.textNetoProducto);
        txtUnidad = findViewById(R.id.textUnidadProducto);
        txtStock = findViewById(R.id.txtStock);
        textPrecio.addTextChangedListener(new AbstractTextWatcher() {
            public void afterTextChanged(Editable s) {
                calcularPrecioVenta();
            }
        });
        textCantidad.addTextChangedListener(new AbstractTextWatcher() {
            public void afterTextChanged(Editable s) {
                calcularPrecioVenta();
            }
        });
        textDescuento.addTextChangedListener(new AbstractTextWatcher() {
            public void afterTextChanged(Editable s) {
                calcularPrecioVenta();
            }
        });
        ImageButton buttonAceptar = findViewById(R.id.imgBtnAceptarProducto);
        buttonAceptar.setOnClickListener(v -> agregarProducto());
        ImageButton buttonCancelar = findViewById(R.id.imgBtnCancelarProducto);
        buttonCancelar.setOnClickListener(v -> cancelarProducto());

    }

    /**
     * Inicializacion de producto en proceso de modificacion.l
     */
    private void inicializacionModificacionProducto() {
        this.autoCompleteArticulo.setText(otProductoVenta.getProducto().getArticulo());
        this.autoCompleteArticulo.setEnabled(false);
        this.autoCompleteArticulo.setDropDownHeight(0);

        this.autoCompleteProducto.setText(otProductoVenta.getProducto().getNombre());
        this.autoCompleteProducto.setEnabled(false);
        this.autoCompleteProducto.setDropDownHeight(0);
        this.textPrecio
                .setText(Fabrica.getDecimalFormat().format(otProductoVenta.getProducto().getPrecio()));
        this.textCantidad.setText(String.valueOf(otProductoVenta.getCantidad()));
        this.textDescuento.setText(String.valueOf((int) otProductoVenta.getDescuento()));
        this.textPrecioNeto.setText(Fabrica.getDecimalFormat().format(otProductoVenta.getValorNeto()));
        this.txtUnidad.setText(otProductoVenta.getProducto().getUnidad());
        this.productoSeleccionado = otProductoVenta.getProducto();
        double stock = otProductoVenta.getProducto().getStock() + otProductoVenta.getCantidad();
        this.txtStock
                .setText(String.format(Locale.getDefault(), "%7.2f [%s]", stock, otProductoVenta.getProducto().getUnidad()));
        floatPrecio = otProductoVenta.getProducto().getPrecio();
        doubleCantidad = otProductoVenta.getCantidad();
        doubleDescuento = otProductoVenta.getDescuento();
        doubleValorNeto = otProductoVenta.getValorNeto();
    }

    /**
     * Metodo que inicializa campo de autocomplete de producto por articulo.
     */
    private void inicializarAutocompletePorArticulo() {
        autoCompleteArticulo = findViewById(R.id.autoCompleteArticulo);

        autoCompleteArticulo.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, listaArticulos));
        autoCompleteArticulo.setOnItemClickListener((elemento, arg1, position, arg3) -> {
            productoSeleccionado = ((DecProductoArticulo) autoCompleteArticulo.getAdapter().getItem(position)).getObject();
            if (productoSeleccionado == null)
                return;

            autoCompleteArticulo.setText(productoSeleccionado.getArticulo());
            autoCompleteProducto = findViewById(R.id.autoCompleteProducto);
            autoCompleteProducto.setText(productoSeleccionado.getNombre());

            boolean esEspecial = Fabrica.obtenerInstancia().obtenerModeloDipalza().esEspecial(productoSeleccionado.getArticulo());

            textPrecio.setFocusable(esEspecial);
            textPrecio.setEnabled(esEspecial);
            textPrecio.setClickable(esEspecial);
            textPrecio.setFocusableInTouchMode(esEspecial);

            completarDatosProductos();
        });

        autoCompleteArticulo.addTextChangedListener(new AbstractTextWatcher() {
            public void afterTextChanged(Editable s) {
                if (Productos.this.autoCompleteArticulo.length() == 0 && !internal) {
                    limpiarControlArticulo();
                } else if (Productos.this.autoCompleteArticulo.length() == 3 && !internal) {
                    if (listaArticulos == null || listaArticulos.isEmpty()) {
                        limpiarControlArticulo();
                        return;
                    }
                    String codigo = Productos.this.autoCompleteArticulo.getText().toString();
                    DecProductoArticulo dec = listaArticulos.stream().filter(a -> a.getObject().getArticulo().equalsIgnoreCase(codigo)).findFirst().orElse(null);
                    if (dec == null) {
                        limpiarControlArticulo();
                        return;
                    }
                    productoSeleccionado = dec.getObject();
                    autoCompleteProducto = findViewById(R.id.autoCompleteProducto);
                    autoCompleteProducto.setText(dec.getObject().getNombre());
                    autoCompleteProducto.requestFocus();
                    autoCompleteProducto.dismissDropDown();
                    boolean esEspecial = false;
                    if (dec.getObject().getArticulo() != null) {
                        esEspecial = Fabrica.obtenerInstancia().obtenerModeloDipalza()
                                .esEspecial(dec.getObject().getArticulo());
                    }

                    textPrecio.setFocusable(esEspecial);
                    textPrecio.setEnabled(esEspecial);
                    textPrecio.setClickable(esEspecial);
                    textPrecio.setFocusableInTouchMode(esEspecial);
                    productoSeleccionado = dec.getObject();
                    completarDatosProductos();
                    textCantidad.requestFocus();
                }
            }
        });

        autoCompleteArticulo.setOnFocusChangeListener((v, hasFocus) ->
        {
            InputMethodManager imm =
                    (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);

            if (imm == null)
                return;
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        });


    }

    /**
     * Metodo que inicializa campo de autocomplete de producto por nombre.
     */
    private void inicializarAutocompletePorNombre() {
        autoCompleteProducto = findViewById(R.id.autoCompleteProducto);
        autoCompleteProducto.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, listaNombres));
        autoCompleteProducto.setOnItemClickListener((elemento, arg1, position, arg3) -> {
            productoSeleccionado =
                    ((DecProductoNombre) autoCompleteProducto.getAdapter().getItem(position)).getObject();
            autoCompleteProducto.setText(productoSeleccionado.getNombre());
            autoCompleteArticulo = findViewById(R.id.autoCompleteArticulo);
            autoCompleteArticulo.setText(productoSeleccionado.getArticulo());
            completarDatosProductos();

            boolean esEspecial = Fabrica.obtenerInstancia().obtenerModeloDipalza()
                    .esEspecial(productoSeleccionado.getArticulo());

            textPrecio.setFocusable(esEspecial);
            textPrecio.setEnabled(esEspecial);
            textPrecio.setClickable(esEspecial);
            textPrecio.setFocusableInTouchMode(esEspecial);

        });
        autoCompleteProducto.addTextChangedListener(new AbstractTextWatcher() {
            public void afterTextChanged(Editable s) {
                limpiarControlProducto();
            }
        });
    }

    /**
     * Limpia controles de la interfaz.
     */
    protected void limpiarControlArticulo() {

        productoSeleccionado = null;
        internal = true;
        autoCompleteProducto.setText("");
        internal = false;
        textPrecio.setText("0");
        txtUnidad.setText("0");
        textPrecioNeto.setText("0");
        textCantidad.setText("0");
        textDescuento.setText("0");
        txtStock.setText("");
        floatPrecio = 0;
        doubleCantidad = 0;
        doubleDescuento = 0;
        doubleValorNeto = 0;
    }

    /**
     * Limpia controles de la interfaz.
     */
    protected void limpiarControlProducto() {
        if (Productos.this.autoCompleteProducto.length() == 0 && !internal) {
            productoSeleccionado = null;
            internal = true;
            autoCompleteArticulo.setText("");
            internal = false;
            textPrecio.setText("0");
            txtUnidad.setText("0");
            textPrecioNeto.setText("0");
            textCantidad.setText("0");
            textDescuento.setText("0");
            txtStock.setText("");
            floatPrecio = 0;
            doubleCantidad = 0;
            doubleDescuento = 0;
            doubleValorNeto = 0;
        }
    }

    /**
     * Metodo mque completa datos del producto seleccionado en los controles de autocompletacion..
     */
    private void completarDatosProductos() {
        textPrecio.setText("0");
        if (productoSeleccionado != null) {
            if (productoSeleccionado.getPrecio() != null) {
                textPrecio.setText(Fabrica.getDecimalFormat().format(productoSeleccionado.getPrecio()));
                floatPrecio = productoSeleccionado.getPrecio();
            }
            txtUnidad.setText(productoSeleccionado.getUnidad());
            this.txtStock.setText(String.format("%s[%s]",
                    Fabrica.getDecimalFormat().format(productoSeleccionado.getStock()),
                    productoSeleccionado.getUnidad()));
        }
    }

    /**
     * Metodo que genera decorador de la liosta de productos por articulo y por nombre.
     */
    private void inicializarDecoradoresListaProductos() {
        List<OTProducto> listaProductos = Fabrica.obtenerInstancia().obtenerModeloDipalza().obtenerProductos();
        listaArticulos = new LinkedList<DecProductoArticulo>();
        listaNombres = new LinkedList<DecProductoNombre>();

        if (listaProductos == null || listaProductos.isEmpty())
            return;

        listaProductos.forEach(elemento -> {
            listaArticulos.add(new DecProductoArticulo(elemento));
            listaNombres.add(new DecProductoNombre(elemento));
        });
    }

    /**
     * Metodo que se ejecuta desplues de cambiar el texto de los campos cantidad y descuento.
     */
    public void calcularPrecioVenta() {
        try {
            floatPrecio = Float.parseFloat(textPrecio.getText().toString());
        } catch (Exception exception) {
        }

        try {
            doubleCantidad = Double.parseDouble(Productos.this.textCantidad.getText().toString());
        } catch (Exception exception) {
        }
        try {
            doubleDescuento = Double.parseDouble(Productos.this.textDescuento.getText().toString());
        } catch (Exception exception) {
        }
        double valorPrecioNeto = floatPrecio * doubleCantidad;
        doubleValorNeto = valorPrecioNeto * (1.0 - (doubleDescuento / 100d));
        textPrecioNeto.setText(Fabrica.getDecimalFormat().format(doubleValorNeto));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menuproducto, menu);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemAceptarProducto:
                agregarProducto();
                return true;
            case R.id.itemCancelarProducto:
                cancelarProducto();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Metodo que cancela el proceso de agregar un producto a la tabla de ventas de productos.
     */
    private void cancelarProducto() {
        finish();
    }

    /**
     * Metodo que agrega producto a la tabla de ventas de productos.
     */
    private void agregarProducto() {
        if (validadorDatosProducto()) {
            crearProductoVenta();
            Intent resultIntent;
            resultIntent = new Intent();
            resultIntent.putExtra(PRODUCTO_VENTA, otProductoVenta);
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        }
    }

    /**
     * Metodo que valida los datos del producto.
     *
     * @return Verdadero si se ingresaron datos basicos.
     */
    private boolean validadorDatosProducto() {
        boolean retorno = true;
        if (productoSeleccionado == null) {
            EmisorMensajes.mostrarMensaje(this, "Producto", "Debe ingresar producto a ingresar.");
            retorno = false;
        } else if (Productos.this.textCantidad.getText().toString() == null
                || Productos.this.textCantidad.getText().toString().equals("")) {
            EmisorMensajes.mostrarMensaje(this, "Producto", "Debe ingresar cantidad de producto.");
            retorno = false;
        }
        return retorno;
    }

    /**
     * Metodo que crea producto a agregar en la venta.
     */
    private void crearProductoVenta() {
        if (otProductoVenta == null) {
            otProductoVenta = new OTProductoVenta();
            otProductoVenta.setProducto(productoSeleccionado);
            otProductoVenta.setIdProducto(productoSeleccionado.getIdProducto());
            otProductoVenta.setNombre(productoSeleccionado.getNombre());

        }
        otProductoVenta.setCantidad(doubleCantidad);
        otProductoVenta.setDescuento(doubleDescuento);
        otProductoVenta.setValorNeto(doubleValorNeto);
        otProductoVenta.setPrecio(floatPrecio);
    }
}
