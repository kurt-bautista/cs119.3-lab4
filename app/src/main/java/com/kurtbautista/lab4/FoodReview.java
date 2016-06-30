package com.kurtbautista.lab4;

/**
 * Created by Student on 6/30/2016.
 */
public class FoodReview {

    private String name;

    private String user;
    private double price;
    private String description;
    private int rating;
    private String filename;

    public FoodReview(String name, String user, double price, String description, int rating)
    {
        this.name = name;
        this.user = user;
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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

}
