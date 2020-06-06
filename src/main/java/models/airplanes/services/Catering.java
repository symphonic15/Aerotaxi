package models.airplanes.services;

import java.util.List;

public class Catering {
    private List<String> foods;
    private List<String> drinks;

    public List<String> getFoods() {
        return foods;
    }

    public void setFoods(List<String> foods) {
        this.foods = foods;
    }

    public List<String> getDrinks() {
        return drinks;
    }

    public void setDrinks(List<String> drinks) {
        this.drinks = drinks;
    }
}
