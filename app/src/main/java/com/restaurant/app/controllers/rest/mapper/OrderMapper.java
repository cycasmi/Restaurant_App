package com.restaurant.app.controllers.rest.mapper;

import com.restaurant.app.models.Order;

import org.json.JSONException;
import org.json.JSONObject;

public class OrderMapper extends Mapper<Order>
{
    @Override
    public Order map(JSONObject json) throws JSONException
    {
        return new Order( //
                json.getInt("order_id"), //
                json.getInt("session_id"), //
                json.getInt("dish_id"), //
                json.getString("comments"), //
                json.getString("status"));
    }
}
