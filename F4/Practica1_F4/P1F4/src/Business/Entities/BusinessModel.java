package Business.Entities;

/**
 * Clase que representa un modelo de negocio.
 * @author alberto.marquillas i marc.viñas
 */
public abstract class BusinessModel {

    /**
     * Constructor de la clase BusinessModel.
     */
    public BusinessModel() {

    }


    /**
     * Obtiene el nombre o modelo del negocio.
     *
     * @return Nombre o modelo del negocio.
     */
    public abstract String getModel();

    /**
     * Calcula un descuento o ajuste de precio basado en el precio original y el impuesto (IVA).
     *
     * @param price Precio original del producto.
     * @param iva   Impuesto (IVA) aplicado al producto.
     * @return Precio ajustado después del descuento o ajuste.
     */
    public abstract float descompte(Float price, int iva);


}

