package Business.Entities.HerenciasShop;

import Business.Entities.BusinessModel;

/**
 * Clase que representa un modelo de negocio patrocinado.
 * @author alberto.marquillas i marc.viñas
 */
public class Sponsored extends BusinessModel {

    String sponsoringBrand;

    /**
     * Constructor de la clase Sponsored.
     *
     * @param sponsoringBrand Marca que patrocina el negocio.
     */
    public Sponsored(String sponsoringBrand) {
        super();
        this.sponsoringBrand = sponsoringBrand;
    }

    /**
     * Obtiene el nombre o modelo del negocio.
     *
     * @return Nombre o modelo del negocio (en este caso, "SPONSORED").
     */
    @Override
    public String getModel() {
        return "SPONSORED";
    }

    /**
     * Obtiene la marca que patrocina el negocio.
     *
     * @return Marca que patrocina el negocio.
     */
    public String getSponsoringBrand() {
        return sponsoringBrand;
    }

    /**
     * Calcula un descuento en el precio del producto patrocinado.
     *
     * @param price Precio original del producto patrocinado.
     * @param iva   Impuesto (IVA) aplicado al producto.
     * @return Precio ajustado después del descuento.
     */
    @Override
    public float descompte(float price, int iva) {
        return (float)(price - price * (1.0/10.0));
    }
}
