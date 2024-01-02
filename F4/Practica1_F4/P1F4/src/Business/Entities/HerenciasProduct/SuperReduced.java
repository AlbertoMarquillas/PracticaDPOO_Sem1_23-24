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

    public int getSuperReducedIva() {
        return 0;
    }

    public float applyIVA(float price) {
        return (float) (price+(price*(getIva()/100.0)));
    }
    public float applySuperReducedIVA(float price) {
        return (price - applyIVA(price));
    }

}
