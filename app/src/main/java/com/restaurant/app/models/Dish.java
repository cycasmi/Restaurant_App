package com.restaurant.app.models;

public class Dish
{

    public final int     id;
    public final String  name;
    public final String  description;
    public final String  category;
    public final String  ingredients;
    public final double  cost;
    public final double  calories;
    public final String  chef;
    public final String  url;
    public final double  discount;
    public final boolean original;


    public Dish(int id, String name, String description, String category,
            String ingredients, double cost, double calories, String chef, String url,
            double discount, boolean original)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.ingredients = ingredients;
        this.cost = cost;
        this.calories = calories;
        this.chef = chef;
        this.url = url;
        this.discount = discount;
        this.original = original;
    }
}
