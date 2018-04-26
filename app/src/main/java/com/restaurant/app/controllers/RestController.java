package com.restaurant.app.controllers;

import android.content.Context;

import com.restaurant.app.controllers.rest.RestExecutor;
import com.restaurant.app.controllers.rest.mapper.DishMapper;
import com.restaurant.app.controllers.rest.mapper.OrderMapper;
import com.restaurant.app.models.Dish;
import com.restaurant.app.models.Order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestController
{

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

    public void getOrderList(int sessionId, Callback<List<Order>> callback)
    {
        RestExecutor<Order> executor = new RestExecutor<>(mContext);
        String              path     = "/order/list/";
        Map<String, String> params   = new HashMap<>();
        params.put("session_id", sessionId + "");
        OrderMapper mapper = new OrderMapper();
        executor.getList(path, params, mapper, callback);
    }

    public void getOrderList(int sessionId, String status, Callback<List<Order>> callback)
    {
        RestExecutor<Order> executor = new RestExecutor<>(mContext);
        String              path     = "/order/list/";
        Map<String, String> params   = new HashMap<>();
        params.put("session_id", sessionId + "");
        params.put("status", status);
        OrderMapper mapper = new OrderMapper();
        executor.getList(path, params, mapper, callback);
    }

    public void saveOrder(int sessionId, int dishId, String comments,
            Callback<Integer> callback)
    {
        RestExecutor<Dish>  executor = new RestExecutor<>(mContext);
        String              path     = "/order/";
        Map<String, String> params   = new HashMap<>();
        params.put("session_id", sessionId + "");
        params.put("dish_id", dishId + "");
        params.put("comments", comments);
        params.put("status", "ordering");
        executor.post(path, params, "order_id", callback);
    }

    public void deleteOrder(Order orderId, Callback<Void> callback)
    {
        RestExecutor<Dish>  executor = new RestExecutor<>(mContext);
        String              path     = "/order/";
        Map<String, String> params   = new HashMap<>();
        params.put("order_id", orderId + "");
        executor.delete(path, params, callback);
    }

    public void setSessionStatus(int sessionId, String status, Callback<Void> callback)
    {
        RestExecutor<Dish>  executor = new RestExecutor<>(mContext);
        String              path     = "/session/";
        Map<String, String> params   = new HashMap<>();
        params.put("session_id", sessionId + "");
        params.put("status", status);
        executor.put(path, params, callback);
    }

}
