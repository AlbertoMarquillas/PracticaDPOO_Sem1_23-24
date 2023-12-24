package Business.Entities.HerenciasProduct;

import Business.Entities.Product;

import java.util.ArrayList;

public class General extends Product {
    public General(String name, String brand, String category, float maxPrice, ArrayList<String> rating) {
        super(name, brand, category, maxPrice, rating);
    }

    public General(String name, String brand, String category, float maxPrice, ArrayList<String> rating, float price) {
        super(name, brand, category, maxPrice, rating, price);
    }
    public int getIva() {
        return 21;
    }
    @Override
    public float applyIVA(float price) {
        return (float) (price*(getIva()/100.0));
    }
}
