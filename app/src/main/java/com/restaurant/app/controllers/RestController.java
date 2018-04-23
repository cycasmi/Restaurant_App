package com.restaurant.app.controllers;

import android.content.Context;

import com.restaurant.app.controllers.rest.RestExecutor;
import com.restaurant.app.controllers.rest.mapper.DishMapper;
import com.restaurant.app.models.Dish;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestController
{

    private final String SERVER = "http://192.168.0.4:3000";

    private Context mContext;

    public RestController(Context context)
    {
        mContext = context;
    }

    public void getDishDetails(int dishId, Callback<Dish> callback)
    {
        RestExecutor<Dish>  executor = new RestExecutor<>(mContext);
        String              path     = "/dish/";
        Map<String, String> params   = new HashMap<>();
        params.put("dish_id", dishId + "");
        DishMapper mapper = new DishMapper();
        executor.get(path, params, mapper, callback);
    }

    public void getDishList(Callback<List<Dish>> callback)
    {
        RestExecutor<Dish>  executor = new RestExecutor<>(mContext);
        String              path     = "/dish/list/";
        Map<String, String> params   = new HashMap<>();
        DishMapper          mapper   = new DishMapper();
        executor.getList(path, params, mapper, callback);
    }


}
