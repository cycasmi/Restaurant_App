package com.restaurant.app.controllers.rest.mapper;

import com.restaurant.app.models.Dish;

import org.json.JSONException;
import org.json.JSONObject;

public class DishMapper extends Mapper<Dish>
{
    @Override
    public Dish map(JSONObject json) throws JSONException
    {
        return new Dish( //
                json.getInt("dish_id"), //
                json.getString("name"), //
                json.getString("description"), //
                json.getString("category"), //
                json.getString("ingredients"), //
                json.getDouble("cost"), //
                json.getDouble("calories"), //
                json.getString("chef"), //
                json.getString("url"), //
                json.getDouble("discount"), //
                json.getBoolean("original"));
    }
}
