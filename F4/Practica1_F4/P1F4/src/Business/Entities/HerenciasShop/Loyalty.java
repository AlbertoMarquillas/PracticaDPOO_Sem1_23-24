package Business.Entities.HerenciasShop;

import Business.Entities.BusinessModel;

public class Loyalty extends BusinessModel {
    /**
     * Constructor de la clase BusinessModel.
     *
     */
    float loyaltyThreshold;
    public Loyalty(float loyaltyThreshold) {
        super();
        this.loyaltyThreshold = loyaltyThreshold;
    }

    @Override
    public String getModel() {
        return "LOYALTY";
    }

    public float getLoyaltyThreshold() {
        return loyaltyThreshold;
    }

    public void setLoyaltyThreshold(float loyaltyThreshold) {
        this.loyaltyThreshold = loyaltyThreshold;
    }

    @Override
    public float descompte(Float price) {
        return 0;
    }
}
