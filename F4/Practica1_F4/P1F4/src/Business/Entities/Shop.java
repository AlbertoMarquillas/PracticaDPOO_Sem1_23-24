package Business.Entities;

import Business.Entities.HerenciasShop.Loyalty;
import Business.Entities.HerenciasShop.MaxProfit;
import Business.Entities.HerenciasShop.Sponsored;

/**
 * Clase que representa una tienda.
 * @author alberto.marquillas i marc.viñas
 */
public class Shop {

    // Atributos
    private String name;
    private String description;
    private int since;
    private float earnings;
    private BusinessModel businessModelObject;
    private ProductCatalog productCatalog;


    public Shop(String name, String description, int since, float earnings, BusinessModel businessModel, ProductCatalog productCatalog) {
        this.name = name;
        this.description = description;
        this.since = since;
        this.earnings = earnings;
        this.businessModelObject = businessModel;
        this.productCatalog = productCatalog;
    }

    /**
     * Constructor de la clase Shop.
     *
     * @param name             Nombre de la tienda.
     * @param description      Descripción de la tienda.
     * @param foundationYear   Año de fundación de la tienda.
     * @param businessModel    Modelo de negocio de la tienda.
     * @param loyaltyThreshold
     * @param sponsoringBrand
     */

    public Shop(String name, String description, int foundationYear, String businessModel, float loyaltyThreshold, String sponsoringBrand) {
        this.name = name;
        this.description = description;
        this.since = foundationYear;
        this.earnings = 0;
        switch (businessModel){
            case "MAX_PROFIT" -> this.businessModelObject = new MaxProfit();
            case "LOYALTY" -> this.businessModelObject = new Loyalty(loyaltyThreshold);
            case "SPONSORED" -> this.businessModelObject = new Sponsored(sponsoringBrand);
        }


        this.productCatalog = new ProductCatalog();
    }

    /**
     * Constructor de la clase Shop.
     *
     * @param name          Nombre de la tienda.
     * @param description   Descripción de la tienda.
     * @param since         Año de fundación de la tienda.
     * @param earnings      Ganancias de la tienda.
     * @param businessModel Modelo de negocio de la tienda.
     * @param products      Catálogo de productos de la tienda.
     */
    public Shop(String name, String description, int since, float earnings, String businessModel, ProductCatalog products, float loyaltyThreshold, String sponsoringBrand) {

        this.name = name;
        this.description = description;
        this.since = since;
        this.earnings = earnings;
        switch (businessModel){
            case "MAX_PROFIT" -> this.businessModelObject = new MaxProfit();
            case "LOYALTY" -> this.businessModelObject = new Loyalty(loyaltyThreshold);
            case "SPONSORED" -> this.businessModelObject = new Sponsored(sponsoringBrand);
        }
        this.productCatalog = products;

    }

    // Getters

    /**
     * Obtiene el nombre de la tienda.
     *
     * @return Nombre de la tienda.
     */
    public String getName() {
        return name;
    }

    /**
     * Obtiene la descripción de la tienda.
     *
     * @return Descripción de la tienda.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Obtiene el año de fundación de la tienda.
     *
     * @return Año de fundación de la tienda.
     */
    public int getFoundationYear() {
        return since;
    }

    /**
     * Obtiene el catálogo de productos de la tienda.
     *
     * @return ProductCatalog de la tienda.
     */
    public ProductCatalog getProductCatalog() {
        return productCatalog;
    }

    /**
     * Establece el catálogo de productos.
     *
     * @param productCatalog Catálogo de productos con la info.
     */
    public void setProductCatalog(ProductCatalog productCatalog) {
        this.productCatalog = productCatalog;
    }

    /**
     * Obtiene los beneficios de una tienda.
     *
     * @return earnings (beneficios) de la tienda.
     */
    public float getEarnings() {
        return earnings;
    }

    /**
     * Establece los beneficios de una tienda.
     *
     * @param earnings Beneficios de la tienda.
     */
    public void setEarnings(float earnings) {
        this.earnings = earnings;
    }


    public float descompte(Float price, int iva){
        return businessModelObject.descompte(price, iva);
    }

    public void setBusinessModel(BusinessModel businessModel) {
        this.businessModelObject = businessModel;
    }

    public BusinessModel getBusinessModel() {
        return businessModelObject;
    }

    public float applyModel(float price) {
        return businessModelObject.descompte(price, 0);
    }
}
