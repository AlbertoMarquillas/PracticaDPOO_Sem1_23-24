package Business.Entities.HerenciasShop;

import Business.Entities.BusinessModel;

public class Sponsored extends BusinessModel {
    /**
     * Constructor de la clase BusinessModel.
     *
     */
    String sponsoringBrand;
    public Sponsored(String sponsoringBrand) {
        super();
        this.sponsoringBrand = sponsoringBrand;
    }

    @Override
    public String getModel() {
        return "SPONSORED";
    }

    public String getSponsoringBrand() {
        return sponsoringBrand;
    }

    public void setSponsoringBrand(String sponsoringBrand) {
        this.sponsoringBrand = sponsoringBrand;
    }

    @Override
    public float descompte(Float price) {
        return (float)(price - price * (1/10));
    }
}
