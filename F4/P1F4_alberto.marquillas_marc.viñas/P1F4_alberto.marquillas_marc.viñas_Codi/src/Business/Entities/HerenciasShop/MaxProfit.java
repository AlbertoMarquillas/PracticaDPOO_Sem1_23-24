package Business.Entities.HerenciasShop;

import Business.Entities.BusinessModel;

/**
 * Clase que representa un modelo de negocio orientado a obtener ganancias máximas.
 * @author alberto.marquillas i marc.viñas
 */
public class MaxProfit extends BusinessModel {

    /**
     * Constructor de la clase MaxProfit.
     */
    public MaxProfit() {
        super();
    }

    /**
     * Obtiene el nombre o modelo del negocio.
     *
     * @return Nombre o modelo del negocio (en este caso, "MAX_PROFIT").
     */
    @Override
    public String getModel() {
        return "MAX_PROFIT";
    }

    /**
     * Calcula el descuento en el precio del producto.
     *
     * @param price Precio original del producto.
     * @param iva   Impuesto (IVA) aplicado al producto.
     * @return Precio después del descuento (en este caso, sin descuento, por lo que devuelve el precio original).
     */
    @Override
    public float descompte(float price, int iva) {
        return 0;
    }
}
