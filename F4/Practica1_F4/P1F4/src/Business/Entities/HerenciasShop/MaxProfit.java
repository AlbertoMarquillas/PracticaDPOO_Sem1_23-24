package Business.Entities.HerenciasShop;

import Business.Entities.BusinessModel;

public class MaxProfit extends BusinessModel {
    /**
     * Constructor de la clase BusinessModel.
     *
     *
     */
    public MaxProfit() {
        super();
    }

    @Override
    public String getModel() {
        return "MAX_PROFIT";
    }

    @Override
    public float descompte(Float price) {
        return 0;
    }
}
