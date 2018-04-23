package com.restaurant.app.controllers.rest;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.restaurant.app.controllers.Callback;
import com.restaurant.app.controllers.rest.mapper.Mapper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;


public class RestExecutor<T>
{

    private final String SERVER = "http://192.168.0.4:3000/api";

    private Context mContext;

    public RestExecutor(Context context)
    {
        mContext = context;
    }


    public void getList(String path, Map<String, String> params, Mapper<T> mapper,
            Callback<List<T>> callback)
    {
        RequestQueue queue = Volley.newRequestQueue(mContext);
        StringRequest request = new StringRequest(SERVER + path, response -> {
            try {
                JSONArray json = new JSONArray(response);
                callback.execute(mapper.map(json), Callback.OK);
            } catch (Exception e) {
                callback.execute(null, Callback.ERROR);
            }
        }, error -> callback.execute(null, Callback.ERROR))
        {
            @Override
            protected Map<String, String> getParams()
            {
                return params;
            }
        };
        queue.add(request);
    }

    public void get(String path, Map<String, String> params, Mapper<T> mapper,
            Callback<T> callback)
    {
        RequestQueue queue = Volley.newRequestQueue(mContext);
        StringRequest request = new StringRequest(SERVER + path, response -> {
            try {
                JSONObject json = new JSONObject(response);
                callback.execute(mapper.map(json), Callback.OK);
            } catch (Exception e) {
                Log.e("RestExecutor", e.getMessage());
                callback.execute(null, Callback.ERROR);
            }
        }, error -> {
            Log.e("RestExecutor", error.getMessage());
            callback.execute(null, Callback.ERROR);
        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                return params;
            }
        };
        queue.add(request);
    }

    public void post(String path, Map<String, String> params, String id,
            Callback<Integer> callback)
    {
        RequestQueue queue = Volley.newRequestQueue(mContext);
        StringRequest request =
                new StringRequest(Request.Method.POST, SERVER + path, response -> {
                    try {
                        JSONObject json = new JSONObject(response);
                        callback.execute(json.getInt(id), Callback.OK);
                    } catch (Exception e) {
                        Log.e("RestExecutor", e.getMessage());
                        callback.execute(null, Callback.ERROR);
                    }
                }, error -> {
                    Log.e("RestExecutor", error.getMessage());
                    callback.execute(null, Callback.ERROR);
                })
                {
                    @Override
                    protected Map<String, String> getParams()
                    {
                        return params;
                    }
                };
        queue.add(request);
    }

    public void delete(String path, Map<String, String> params, Callback<T> callback)
    {
        request(Request.Method.DELETE, path, params, callback);
    }


    public void put(String path, Map<String, String> params, Callback<T> callback)
    {
        request(Request.Method.DELETE, path, params, callback);
    }

    private void request(int type, String path, Map<String, String> params,
            Callback<T> callback)
    {
        RequestQueue queue = Volley.newRequestQueue(mContext);
        StringRequest request = new StringRequest(type, SERVER + path, response -> {
            callback.execute(null, Callback.OK);
        }, error -> {
            Log.e("RestExecutor", error.getMessage());
            callback.execute(null, Callback.ERROR);
        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                return params;
            }
        };
        queue.add(request);
    }


}
