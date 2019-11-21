package cl.eos.dipalza.ot;

/**
 * Para contener los productos los cuales el vendedor puede cambiar precio.
 * Created by curso on 29-08-2016.
 */
public class OTEspeciales {

    /**
     * Identificador del producto.
     */
    private int idProducto;
    /**
     * Codigo de sistema del producto.
     */
    private String articulo;

    public OTEspeciales() {
    }

    public OTEspeciales(int idProducto, String articulo) {
        this.idProducto = idProducto;
        this.articulo = articulo;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getArticulo() {
        return articulo;
    }

    public void setArticulo(String articulo) {
        this.articulo = articulo;
    }
}
