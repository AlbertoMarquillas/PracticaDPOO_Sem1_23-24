package Business.Entities.HerenciasProduct;

import Business.Entities.Product;

import java.util.ArrayList;

public class Reduced extends Product {


    public Reduced(String name, String brand, String category, float maxPrice, ArrayList<String> rating) {
        super(name, brand, category, maxPrice, rating);
    }

    public Reduced(String name, String brand, String category, float maxPrice, ArrayList<String> rating, float price) {
        super(name, brand, category, maxPrice, rating, price);
    }
    public int getIva() {
        return 10;
    }
    public int getReducedIva() {
        return 5;
    }
    @Override
    public float applyIVA(float price) {
        return (float) (price*(getIva()/100.0));
    }

    public float applyRatingIVA(Float price) {
        return (float) (price*(getReducedIva()/100.0));
    }
}
