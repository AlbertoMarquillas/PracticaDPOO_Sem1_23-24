package Business.Entities.HerenciasShop;

import Business.Entities.BusinessModel;

/**
 * Clase que representa un modelo de negocio orientado a la lealtad del cliente.
 * @author alberto.marquillas i marc.viñas
 */
public class Loyalty extends BusinessModel {

    float loyaltyThreshold;
    /**
     * Constructor de la clase Loyalty.
     *
     * @param loyaltyThreshold Umbral de lealtad para aplicar descuentos.
     */
    public Loyalty(float loyaltyThreshold) {
        super();
        this.loyaltyThreshold = loyaltyThreshold;
    }

    /**
     * Obtiene el nombre o modelo del negocio.
     *
     * @return Nombre o modelo del negocio (en este caso, "LOYALTY").
     */
    @Override
    public String getModel() {
        return "LOYALTY";
    }

    /**
     * Obtiene el umbral de lealtad que determina si se aplica un descuento.
     *
     * @return Umbral de lealtad.
     */
    public float getLoyaltyThreshold() {
        return loyaltyThreshold;
    }

    /**
     * Calcula el descuento en el precio del producto, si corresponde según el modelo de negocio de lealtad.
     *
     * @param price Precio original del producto.
     * @param iva   Impuesto (IVA) aplicado al producto.
     * @return Precio después del descuento, si el cliente cumple con el umbral de lealtad; de lo contrario, el precio original.
     */
    @Override
    public float descompte(Float price, int iva) {

        return (float) ((price/(1.0 + (iva/100.0))));
    }
}
