package com.restaurant.app.controllers.rest.mapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public abstract class Mapper<T>
{
    public List<T> map(JSONArray json) throws JSONException
    {
        List<T> result = new ArrayList<>();
        for (int i = 0; i < json.length(); i++) {
            JSONObject item = json.getJSONObject(i);
            result.add(map(item));
        }
        return result;
    }

    public abstract T map(JSONObject json) throws JSONException;
}
