package Business.Entities.HerenciasProduct;

import Business.Entities.Product;

import java.util.ArrayList;

public class SuperReduced extends Product {

    public SuperReduced(String name, String brand, String category, float maxPrice, ArrayList<String> rating) {
        super(name, brand, category, maxPrice, rating);
    }

    public SuperReduced(String name, String brand, String category, float maxPrice, ArrayList<String> rating, float price) {
        super(name, brand, category, maxPrice, rating, price);
    }
    public int getIva() {
        return 4;
    }

    @Override
    public float originalPrice(float price, float totalRating) {
        if (price > 100) {
            return (float) ((price) / (1.0 + (getSuperReducedIva()/100.0)));
        } else {
            return (float) ((price) / (1.0 + (getIva()/100.0)));
        }
    }


    public int getSuperReducedIva() {
        return 0;
    }

    public float applyIVA(float price) {
        return (float) ((price*(getIva()/100.0)));
    }
}
