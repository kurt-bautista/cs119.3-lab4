package com.kurtbautista.lab4;

/**
 * Created by Student on 6/30/2016.
 */
public class Food {

    private String name;
    private double price;
    private String description;
    private int rating;
    private String filename;

    public Food(String name, double price, String description, int rating)
    {
        this.name = name;
        this.price = price;
        this.description = description;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

}
